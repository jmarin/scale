package server

import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem

object GeometryServiceApp {

  def main(args: Array[String]): Unit = {
    if (args.isEmpty)
      startup(Seq("2551"))
    else
      startup(args)
  }

  def startup(ports: Seq[String]): Unit = {
    ports.foreach { port =>
      val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port").
        withFallback(ConfigFactory.load())

      val system = ActorSystem("GeometrySystem", config)

    }
  }

}

