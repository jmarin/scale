package io.shapefile

import java.nio.ByteBuffer

object DBFField {
  def apply(bb: ByteBuffer): Option[DBFField] = {
    val descriptorTerminator = 0x0d
    val firstByte = bb.get()
    if (firstByte == descriptorTerminator) {
      None
    } else {
      val bytes = new Array[Byte](11)
      //bb.get(bytes)
      bb.get(bytes, 1, 10)
      bytes(0) = firstByte
      val fname = new String(bytes, "ISO-8859-1")
      println(fname)
      val name = new String(bytes, "UTF-8")
      val fieldType = bb.get().asInstanceOf[Char]
      println(fieldType)
      val reserverd1 = bb.getInt()
      val fieldLength = bb.get() & 0xFF
      val fieldFlag = bb.get()
      val autoIncrNext = bb.getInt()
      val autoIncrStep = bb.get()

      //println(fieldType)
      Some(DBFField(name, ""))
    }
  }

}

case class DBFField(name: String, fieldType: String)

