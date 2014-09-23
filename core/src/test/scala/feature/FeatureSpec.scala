package feature

import org.specs2.mutable.Specification
import geometry._

class FeatureSpec extends Specification {

  val p1 = Point(-77, 39)
  val id = "1"
  val values = Map("DESCRIPTION" -> "First Point")
  val f = Feature(id, p1, values)

  "A Feature" should {
    "have some values" in {
      f.countFields must be equalTo (1)
    }
    "add a field with a new value" in {
      val uf = f.addOrUpdate("NEWFIELD", "New Value")
      uf.countFields must be equalTo (2)
      val newValue = uf.get("NEWFIELD").getOrElse("")
      newValue must be equalTo ("New Value")
    }
    "update a field with a new value" in {
      val uf = f.addOrUpdate("DESCRIPTION", "Updated Value")
      uf.countFields must be equalTo (1)
      val updatedValue = uf.get("DESCRIPTION").getOrElse("")
      updatedValue must be equalTo ("Updated Value")
    }
    "have fields with different value types" in {
      val values1 = Map("DESCRIPTION" -> "First Point", "ID" -> 1)
      val f1 = Feature(id, p1, values1)
      f1.countFields must be equalTo (2)
      f1.id must be equalTo ("1")
      f1.get("ID").getOrElse("") must be equalTo (1)
    }
    "update its geometry" in {
      val p2 = Point(-105, 37)
      val fn = f.updateGeometry(p2)
      fn.geometry must be equalTo (p2)
    }

  }

}
