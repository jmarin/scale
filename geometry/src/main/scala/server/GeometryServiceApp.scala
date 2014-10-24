package server

import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem

import akka.http.Http
import akka.http.model._
import akka.io.IO
import akka.pattern.ask
import akka.stream.{ MaterializerSettings, FlowMaterializer }
import akka.stream.scaladsl.Flow
import akka.http.model.HttpMethods._

object GeometryServiceApp extends BaseApp {

  val requestHandler: HttpRequest ⇒ HttpResponse = {
    case HttpRequest(GET, Uri.Path("/"), _, _, _) ⇒
      HttpResponse(
        entity = HttpEntity(MediaTypes.`text/html`,
          "<html><body>Hello world!</body></html>"))

    case HttpRequest(GET, Uri.Path("/ping"), _, _, _) ⇒ HttpResponse(entity = "PONG!")
    case HttpRequest(GET, Uri.Path("/crash"), _, _, _) ⇒ sys.error("BOOM!")
    case _: HttpRequest ⇒ HttpResponse(404, entity = "Unknown resource!")
  }

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
            println(s"Accepted new connection from $remoteAddress")
            Flow(requestProducer).map(requestHandler).produceTo(responseConsumer)
        }
    }

  }

}

