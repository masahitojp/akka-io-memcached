package me.masahito.util

import akka.util.ByteString

/**
 * Created with IntelliJ IDEA.
 * User: masahito
 * Date: 13/01/03
 * Time: 19:31
 */
object Constants {
  val CRLF = ByteString("\r\n")
  val SPACE = ByteString(" ")

  // Return Commands
  val STORED = ByteString("Stored\r\n")
  val END = ByteString("END\r\n")
  val ERROR = ByteString("ERROR\r\n")

}
