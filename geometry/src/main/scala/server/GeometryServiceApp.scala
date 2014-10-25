package server

import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem

import akka.http.Http
import akka.io.IO
import akka.pattern.ask
import akka.stream.{ MaterializerSettings, FlowMaterializer }
import akka.stream.scaladsl.Flow

object GeometryServiceApp extends BaseApp {

  import GeometryServiceHandler._

  override protected def run(system: ActorSystem, opts: Map[String, String]): Unit = {
    implicit val actorRefFactory = system
    implicit val ec = system.dispatcher
    implicit val materializer = FlowMaterializer(MaterializerSettings(actorRefFactory))
    val settings = Settings(system)
    import settings.httpService._

    val bindingFuture = IO(Http) ? Http.Bind(interface = hostname, port = port)
    bindingFuture.foreach {
      case Http.ServerBinding(localAddress, connectionStream) =>
        Flow(connectionStream).foreach {
          case Http.IncomingConnection(remoteAddress, requestProducer, responseConsumer) =>
            Flow(requestProducer).map(requestHandler).produceTo(responseConsumer)
        }
    }

  }

}

