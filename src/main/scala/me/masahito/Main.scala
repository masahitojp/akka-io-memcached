package me.masahito

import akka.actor.{Props, ActorSystem}
import sys.ShutdownHookThread
import java.net.SocketAddress
import akka.dispatch.Promise

/**
 * Created with IntelliJ IDEA.
 * User: masahito
 * Date: 13/01/04
 * Time: 0:34
 */

object Main extends App {
  val port = Option(System.getenv("PORT")).map(_.toInt).getOrElse(11211)
  val system = ActorSystem("memcachedlike")
  val addressPromise = Promise[SocketAddress]()(system.dispatcher)
  val server = system.actorOf(Props(new Smemcached(port, addressPromise)))

  addressPromise.map(address => println("Started MemCached Like Server, listening on:" + address))

  ShutdownHookThread {
    println("Socket Server exiting...")
    system.shutdown()
    system.awaitTermination()
  }
}