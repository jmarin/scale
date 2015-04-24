package geometry

import upickle._

object Point {
  implicit val pointWriter = upickle.Writer[Point]{
    case p => Js.Str("POINT(" + p.x + " " + p.y + ")")
  }


  def apply(x: Double, y: Double): Point = {
    Point(x, y, 0)
  }
}


case class Point(x: Double, y: Double, z:Double) {

  private def roundAt(p: Int, n: Double): Double = {
    val s = math.pow(10, p)
    math.round(n * s) / s
  }

  def roundCoordinates(s: Int): Point = {
    val xr = roundAt(s, x)
    val yr = roundAt(s, y)
    val zr = roundAt(s, z)
    Point(xr, yr, zr)
  }

}
