package me.masahito.command

/**
 * Created with IntelliJ IDEA.
 * User: masahito
 * Date: 13/01/03
 * Time: 18:47
 */
import akka.actor.IO
import akka.util.ByteString
import collection.mutable

trait Command {

  def commandHeadOfThree: ByteString

  def read(socket: IO.SocketHandle, datas: mutable.HashMap[String, String]): IO.Iteratee[Unit]
}