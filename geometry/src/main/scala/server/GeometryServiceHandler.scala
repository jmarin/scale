package server

import akka.http.model._
import akka.http.model.HttpMethods._

object GeometryServiceHandler {

  val requestHandler: HttpRequest ⇒ HttpResponse = {
    case HttpRequest(GET, Uri.Path("/"), _, _, _) ⇒
      HttpResponse(
        entity = HttpEntity(MediaTypes.`text/html`,
          "<html><body>Hello world!</body></html>"))

    case HttpRequest(GET, Uri.Path("/ping"), _, _, _) => HttpResponse(entity = "PONG!")
    case HttpRequest(GET, Uri.Path("/crash"), _, _, _) => sys.error("BOOM!")
    case HttpRequest(POST, Uri.Path("/geometry/centroid"), headers, requestEntity, _) =>
      HttpResponse(entity = requestEntity)
    case _: HttpRequest ⇒ HttpResponse(404, entity = "Unknown resource!")
  }

}
