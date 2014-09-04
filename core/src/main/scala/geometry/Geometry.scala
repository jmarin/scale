package geometry

import com.vividsolutions.jts.{ geom => jts }

trait Geometry {

  val p: jts.Geometry

  def intersects(geom: Option[jts.Geometry]): Boolean = geom match {
    case None => false
    case Some(g) => if (g != null) p.intersects(g) else false
  }

}
