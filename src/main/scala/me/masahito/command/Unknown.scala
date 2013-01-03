package me.masahito.command

import akka.actor.IO
import me.masahito.util.Constants._
import collection.mutable

/**
 * Created with IntelliJ IDEA.
 * User: masahito
 * Date: 13/01/03
 * Time: 19:22
 */
object Unknown extends Command {

  val commandHeadOfThree = null

  def read(socket: IO.SocketHandle, datas: mutable.HashMap[String, String]): IO.Iteratee[Unit] = {
    for {
      _ <- IO.takeUntil(CRLF)
    } yield {
      socket.write(ERROR)
    }
  }

}