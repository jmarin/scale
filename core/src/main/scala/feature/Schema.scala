package feature

abstract class FieldType
case class GeometryType() extends FieldType
case class StringType() extends FieldType
case class BooleanType() extends FieldType
case class IntType() extends FieldType
case class LongType() extends FieldType
case class DoubleType() extends FieldType
case class BigDecimalType() extends FieldType
case class DateType() extends FieldType

case class Field(name: String, fieldType: FieldType) {
  def isGeometry = if (fieldType == GeometryType()) true else false
}

object Schema {

  def apply(fields: Field*): Schema = {
    Schema(fields)
  }
}

case class Schema(fields: Iterable[Field]) {
  def numFields: Int = fields.size
}

