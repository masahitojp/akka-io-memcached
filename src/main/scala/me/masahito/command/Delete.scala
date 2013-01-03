package me.masahito.command

import akka.actor.IO
import akka.util.ByteString
import me.masahito.util.Constants._
import collection.mutable
import me.masahito.util.Iteratees._

/**
 * Created with IntelliJ IDEA.
 * User: masahito
 * Date: 13/01/03
 * Time: 19:22
 */
object Delete extends Command {

  val commandHeadOfThree = ByteString("del")

  def read(socket: IO.SocketHandle, datas: mutable.HashMap[String, String]) = {
    for {
      commandLastString <- IO.take(3).map(ascii(_))
      _ <- IO.drop(1)
      key <- IO.takeUntil(CRLF).map(ascii(_))
    } yield {
      if (commandLastString.equals("ete")){
        datas.get(key) match {
          case Some(x) => {
            datas -= key
            socket.write(ByteString("DELETED\r\n"))
          }
          case None => socket.write(ByteString("NOT_FOUND\r\n"))
        }
      } else {
       socket.write(ERROR)
      }
    }
  }

}