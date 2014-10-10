package geometry

import org.scalacheck.{ Arbitrary, Prop, Gen }
import org.scalacheck.Prop.forAll
import com.vividsolutions.jts.{ geom => jts }

trait GeometryGenerators {

  def round(d: Double): Double = {
    val x = d * 100
    val y = Math.round(x)
    y * 100
  }

  implicit def points: Gen[Point] = {
    for {
      x <- Gen.choose(-180.0, 180.0)
      y <- Gen.choose(-90.0, 90.0)
      z <- Arbitrary.arbitrary[Double]
    } yield Point(x, y, z)
  }

  def pointList: Gen[List[Point]] = {
    for {
      pts <- Gen.listOfN[Point](50, points)
    } yield pts
  }

  implicit def lines: Gen[Line] = {
    for {
      pts <- Gen.listOfN[Point](10, points)
    } yield Line(pts)
  }

  def closedLines: Gen[Line] = {
    for {
      p <- points
      l <- lines
    } yield Line(p :: (p :: l.points.toList).reverse)
  }

  def polygons: Gen[Polygon] = {
    for {
      p <- points
      l <- closedLines
    } yield Polygon(p :: (p :: l.points.toList).reverse)
  }

  def multipoints: Gen[MultiPoint] = {
    for {
      pts <- Gen.listOf[Point](points)
    } yield MultiPoint(pts)
  }

  def multilines: Gen[MultiLine] = {
    for {
      lines <- Gen.listOf[Line](lines)
    } yield MultiLine(lines)
  }
}

