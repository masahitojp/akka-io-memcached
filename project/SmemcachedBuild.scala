import sbt._
import sbt.Keys._

object SmemcachedBuild extends Build {

  lazy val smemcached = Project(
    id = "smemcached",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "smemcached",
      organization := "me.masahito",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.9.2",
      resolvers ++= Seq(
	"Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
	"couchbase" at "http://files.couchbase.com/maven2/"
      ),
      libraryDependencies ++= Seq(
	"com.typesafe.akka" % "akka-actor" % "2.0.4",
        // test
	"com.typesafe.akka" % "akka-testkit" % "2.0.4" % "test",
        "org.scalatest" %% "scalatest" % "1.8" % "test",
	"org.scalacheck" %% "scalacheck" % "1.10.0" % "test",
	"spy" % "spymemcached" % "2.8.4" % "test",
        //log
	"com.typesafe.akka" % "akka-slf4j" % "2.0.4",
        "ch.qos.logback" % "logback-classic" % "1.0.1"
      )
      // add other settings here
    )
  )
}
