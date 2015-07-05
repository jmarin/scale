package geometry

import upickle._

object Point {
  
  implicit val pointWriter = upickle.Writer[Point] {
    case p =>
      Js.Obj(
        ("type", Js.Str("Point")),
        ("coordinates", Js.Arr(Js.Num(p.x), Js.Num(p.y)))
      )
  }
  
  implicit val pointReader = upickle.Reader[Point] {
    case Js.Obj(geomType, coords) =>
      val coordinates = (coords._2)
      coordinates match {
        case cs: Js.Arr =>
          val c = cs.value.map (x => x.value.asInstanceOf[Double])
          Point(c(0), c(1))
        case _ =>
          Point(0, 0)
      }
  } 

}

case class Point(x: Double, y: Double) {

  private def roundAt(p: Int, n: Double): Double = {
    val s = math.pow(10, p)
    math.round(n * s) / s
  }

  def roundCoordinates(s: Int): Point = {
    val xr = roundAt(s, x)
    val yr = roundAt(s, y)
    Point(xr, yr)
  }

  override def toString: String = {
    s"POINT(${x}, ${y})"
  }

}
