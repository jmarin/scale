package geometry

import org.specs2.mutable.Specification
import org.specs2.ScalaCheck
import org.scalacheck.Prop
import org.scalacheck.Prop.forAll
import com.vividsolutions.jts.{ geom => jts }

class LineSpec extends Specification with ScalaCheck with GeometryGenerators {

  val p1 = Point(-77, 39)
  val p2 = Point(-76, 40)
  val p3 = Point(-75, 38)
  val p4 = Point(-77, 39)
  val p5 = Point(-78, 39.1)
  val line = Line(Array(p1, p2, p3))
  val closedLine = Line(Array(p1, p2, p3, p4))

  def hasPoints = Prop.forAll(lines) { (l: Line) =>
    l.numPoints must be greaterThan (2)
  }

  def endPoints = Prop.forAll(lines) { (l: Line) =>
    val p1 = l.points(0)
    val p2 = l.points(l.points.size - 1)
    l.startPoint must be equalTo (p1)
    l.endPoint must be equalTo (p2)
  }

  def hasPositiveLength = Prop.forAll(lines) { (l: Line) =>
    l.length must be greaterThanOrEqualTo (0)
  }

  def isClosed = Prop.forAll(closedLines) { (l: Line) =>
    l.isClosed must beTrue
  }

  def reverse = Prop.forAll(lines) { (l: Line) =>
    l.reverse.startPoint must be equalTo (l.endPoint)
    l.reverse.endPoint must be equalTo (l.startPoint)
    round(l.reverse.length) must be equalTo (round(l.length))
    l.reverse.isValid must beTrue
  }

  def isCoordinate = Prop.forAll(lines) { (l: Line) =>
    val p = l.startPoint
    l.isCoordinate(p) must beTrue
    l.isCoordinate(Point(1000, 1000)) must beFalse
  }

  "A Line" should {

    "be valid" in {
      line.isValid must beTrue
      line.isSimple must beTrue
    }

    "always have some points" ! hasPoints

    "have a start and end point" ! endPoints

    "have a positive length" ! hasPositiveLength

    "report if it is a ring" in {
      line.isClosed must beFalse
      closedLine.isClosed must beTrue
    }

    "determine if it is closed" ! isClosed

    "compute the reverse" ! reverse

    "get nth point" in {
      line.pointAt(2) must be equalTo (p3)
    }

    "check if point is coordinate on line" ! isCoordinate

    "Serialize to WKT" in {
      line.wkt must be equalTo ("LINESTRING (-77 39, -76 40, -75 38)")
    }

    "Extract points at certain distances (Linear Referencing)" in {
      line.pointAtDist(0.6) must be equalTo (Point(-76.57573593128807, 39.42426406871193))
      line.pointAtDist(-0.6) must be equalTo (Point(-75.26832815729998, 38.53665631459995))
    }
  }

}
