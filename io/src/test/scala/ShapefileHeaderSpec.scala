package io.shapefile

import geometry.Envelope
import org.specs2.mutable.Specification

class ShapefileHeaderSpec extends Specification {

  val shpFile = "/Users/marinj/Downloads/address_subset.shp"

  "A Shapefile" should {
    "Have the correct header" in {
      val shpHeader = ShapefileHeader(shpFile)
      val env = Envelope(-92.74113469543019, 34.46891584503276, -92.60505523040624, 34.58730971395552)
      shpHeader.envelope must be equalTo (env)
      shpHeader.shapeType must be equalTo (Point())
    }
  }

}
