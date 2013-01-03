package me.masahito

import akka.actor.IO._
import akka.actor.{ IO, IOManager, Actor}
import akka.event.Logging
import java.net.{SocketAddress, InetSocketAddress}
import collection.mutable
import command._
import akka.dispatch.Promise


class Smemcached(port: Int, addressPromise: Promise[SocketAddress]) extends Actor {
  val log = Logging(context.system, this)
  val state = IterateeRef.Map.async[IO.Handle]()(context.dispatcher)
  val datas = new mutable.HashMap[String, String]()

  var hServer: IO.ServerHandle = null

  override def preStart() {
    datas += ("test" -> "test")
    hServer = IOManager(context.system).listen( new InetSocketAddress(port) )
  }
  override def postStop(){
    hServer.close()
    state.keySet foreach (_.close())
  }

  def receive = {
    case Listening(server, address) => {
      addressPromise.success(address)
    }
    case NewClient(server) => {
      log.info("new client")
      val socket = server.accept()
      state(socket) flatMap (_ => this.processCommandKai(socket))
    }
    case Read(socket, bytes) => {
      state(socket)(Chunk(bytes))
    }
    case Closed(socket, cause) => {
      state(socket)(EOF(None))
      state -= socket
      log.info("socket closed")
    }
  }


  def processCommandKai(socket: IO.SocketHandle): IO.Iteratee[Unit]  = {
    IO.repeat {
      IO.take(3).flatMap {
        case Get.commandHeadOfThree => Get.read(socket, datas)
        case Set.commandHeadOfThree => Set.read(socket, datas)
        case Delete.commandHeadOfThree => Delete.read(socket, datas)
        case Quit.commandHeadOfThree => Quit.read(socket, datas)
        case Unknown.commandHeadOfThree => Unknown.read(socket, datas)
      }
    }
  }
}
