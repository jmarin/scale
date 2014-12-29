/**
 * http://www.dbase.com/KnowledgeBase/int/db7_file_fmt.htm
 */

package io.shapefile

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.ByteBuffer
import java.nio.ByteOrder

object DBFHeader {
  def apply(path: String): DBFHeader = {
    val paths = Paths.get(path)
    val bytes = Files.readAllBytes(paths)
    val bb = ByteBuffer.wrap(bytes)
    bb.order(ByteOrder.LITTLE_ENDIAN)
    val dbfType = bb.get()
    val year = bb.get() + 1900
    val month = bb.get()
    val day = bb.get()
    val numRecords = bb.getInt()
    val headerLength = bb.getShort()
    val recordLength = bb.getShort()
    val reserved1 = bb.getShort()
    val incompleteTrans = bb.get()
    val encryptFlag = bb.get()
    val freeRecordThread = bb.get()
    val reserved2 = bb.getInt()
    val reserved3 = bb.getInt()
    val mdxFlag = bb.get()
    val languageDriver = bb.get()
    val reserved4 = bb.getShort()

    val field = DBFField(bb)
    println(field)

    DBFHeader(numRecords)

  }

}

case class DBFHeader(numRecords: Int)
