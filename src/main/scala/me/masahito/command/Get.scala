package me.masahito.command

import akka.util.ByteString
import akka.actor.IO
import collection.mutable

import me.masahito.util.Constants._
import me.masahito.util.Iteratees._

/**
 * Created with IntelliJ IDEA.
 * User: masahito
 * Date: 13/01/03
 * Time: 18:48
 */
object Get extends Command {
  val commandHeadOfThree = ByteString("get")

  def read(socket: IO.SocketHandle, datas: mutable.HashMap[String, String]): IO.Iteratee[Unit] = {
    for {
      _ <- IO.drop(1) // スペースの削除
      key <- IO.takeUntil(CRLF).map(ascii(_).trim)
    } yield {
      datas.get(key) match {
        case Some(x) => socket.write(ByteString("VALUE %s 0 %d\r\n%s\r\nEND\r\n".format(key, x.length, x)))
        case None => socket.write(END)
      }
    }
  }
}
