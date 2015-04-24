package geometry

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
