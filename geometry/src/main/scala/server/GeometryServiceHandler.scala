package server

import akka.actor.ActorSystem
import akka.util.Timeout
import akka.stream.MaterializerSettings
import akka.stream.scaladsl2.FlowMaterializer
import akka.http.model._
import akka.http.model.HttpMethods._
import geojson.FeatureJsonProtocol._

trait GeometryServiceHandler {

  def requestHandler(system: ActorSystem): HttpRequest ⇒ HttpResponse = {

    case HttpRequest(GET, Uri.Path("/"), _, _, _) ⇒
      HttpResponse(
        entity = HttpEntity(MediaTypes.`text/html`,
          "<html><body>Hello world!</body></html>"))

    case HttpRequest(GET, Uri.Path("/ping"), _, _, _) => HttpResponse(entity = "PONG!")
    case HttpRequest(GET, Uri.Path("/crash"), _, _, _) => sys.error("BOOM!")
    case HttpRequest(POST, Uri.Path("/geometry/centroid"), headers, requestEntity, _) =>
      val settings = Settings(system)
      import settings.httpService._

      implicit val actorRefFactory = system
      implicit val ec = system.dispatcher
      implicit val m = FlowMaterializer(MaterializerSettings(actorRefFactory))
      implicit val timeout = askTimeout.duration
      //val ff = requestEntity.toStrict(timeout)
      //ff.map { f =>
      //  println(f.data.asByteBuffer.toString)
      //}
      //println(feature)
      val f = requestEntity.dataBytes
      println(f)

      HttpResponse(entity = requestEntity)
    case _: HttpRequest ⇒ HttpResponse(404, entity = "Unknown resource!")
  }

}
