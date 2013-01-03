package me.masahito.util

import akka.util.ByteString

/**
 * Created with IntelliJ IDEA.
 * User: masahito
 * Date: 13/01/03
 * Time: 23:50
 */
object Iteratees {
    def ascii(bytes: ByteString): String = bytes.decodeString("US-ASCII")
}
