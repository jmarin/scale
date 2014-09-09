package geometry

import com.vividsolutions.jts.{ geom => jts }
import jts.Coordinate

object Util {

  def points2JTSCoordinateArray(points: Array[Point]): Array[Coordinate] = {
    points.map { point =>
      new Coordinate(point.x, point.y, point.z)
    }
  }

}
