package feature

import org.specs2.mutable.Specification
import geometry._

class FeatureSpec extends Specification {

  val p1 = Point(-77, 39)
  val id = "1"
  val values = Map("DESCRIPTION" -> "First Point")
  val f = Feature(id, p1, values)

  "A Feature" should {
    "Have some values" in {
      f.countFields must be equalTo (1)
    }

  }

}
