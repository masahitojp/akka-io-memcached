package me.masahito.command

import akka.actor.IO
import akka.util.ByteString
import collection.mutable

import me.masahito.util.Constants._
import me.masahito.util.Iteratees._

/**
 * Created with IntelliJ IDEA.
 * User: masahito
 * Date: 13/01/03
 * Time: 19:22
 */
object Quit extends Command {

  val commandHeadOfThree = ByteString("qui")

  def read(socket: IO.SocketHandle, datas: mutable.HashMap[String, String]) = {
    for {
      commandLastOneString <- IO.take(1).map(ascii(_))
      _ <- IO.takeUntil(CRLF)
    } yield {
      if (commandLastOneString.equals("t")){
       socket.close()
      } else {
       socket.write(ERROR)
      }
    }
  }

}