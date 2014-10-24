/*
 * Copyright 2014 Heiko Seeberger
 * Copyright 2014 Juan Marin Otero
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package server

import akka.actor.{ Actor, ExtendedActorSystem, Extension, ExtensionKey }
import akka.util.Timeout
import scala.concurrent.duration.{ Duration, MILLISECONDS => Millis }

object Settings extends ExtensionKey[Settings]

class Settings(system: ExtendedActorSystem) extends Extension {

  object httpService {

    implicit val askTimeout: Timeout =
      Duration(geometry.getDuration("http-service.ask-timeout", Millis), Millis)

    val hostname: String =
      geometry.getString("http-service.hostname")

    val port: Int =
      geometry.getInt("http-service.port")
  }

  private val geometry = system.settings.config.getConfig("geometry")
}

trait SettingsActor {
  this: Actor =>

  val settings: Settings =
    Settings(context.system)
}
