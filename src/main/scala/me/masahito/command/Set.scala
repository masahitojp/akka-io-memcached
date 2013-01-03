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
object Set extends Command {
  val commandHeadOfThree = ByteString("set")

  def read(socket: IO.SocketHandle, datas: mutable.HashMap[String, String]): IO.Iteratee[Unit] = {
    for {
      _ <- IO.drop(1) // スペースの削除
      line <- IO.takeUntil(CRLF).map(ascii(_).trim)
      data <- IO.takeUntil(CRLF).map(ascii(_))
    } yield {
      val token = line.split(" ")
      token match {
        case Array(key, flags, expire, bytes) => {
          datas += (key -> data)
          socket.write(STORED)
        }
        case _ => {
          socket.write(ERROR)
        }
      }
    }
  }
}
