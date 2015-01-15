/**
 * Adapted from https://orrsella.com/2014/10/28/embedded-elasticsearch-server-for-scala-integration-tests/
 */
package elasticsearch

import org.osgeo.proj4j.CRSFactory
import geometry._
import feature._
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class ElasticsearchFeatureDaoSpec extends Specification {

  lazy val crsFactory = new CRSFactory

  val p1 = Point(-77, 38)
  val values = Map("geometry" -> p1, "DESCRIPTION" -> "First Point")
  val schema = Schema(List(Field("geometry", GeometryType()), Field("DESCRIPTION", StringType())))
  val crs = crsFactory.createFromName("EPSG:4326")
  val f = Feature(crs, schema, values)

  val server = new ElasticsearchServer

  trait Context extends Scope {
    val dao = new ElasticsearchFeatureDao(server.client)
  }

  step {
    server.start()
    server.createAndWaitForIndex("features")
  }

  "Elasticsearch Feature Dao" should {
    "create a new feature" in new Context {
      val isCreated = dao.create(f, "features", "points")
      isCreated must be equalTo (true)
    }
  }

  step {
    server.stop()
  }

}
