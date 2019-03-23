name := "$name;format="normalize"$"

version := "0.0.1-SNAPSHOT"

organization in ThisBuild := "$organization$"

scalaVersion := "2.12.5"

resolvers += Resolver.sonatypeRepo("snapshots")

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8", "-language:postfixOps")

lazy val `$name;format="normalize"$` =
  (project in file("."))

val akkaV = "2.5.13"

libraryDependencies ++= Seq(
  "com.sksamuel.avro4s" %% "avro4s-core" % "2.0.2",
  "com.typesafe.akka" %% "akka-cluster-sharding" % akkaV,
  "io.surfkit" %% "typebus-kafka" % "0.0.5-SNAPSHOT",
  "com.typesafe.akka" %% "akka-persistence-cassandra" % "0.91",
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.6.0",
  "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided",

  "com.lightbend.lagom" %% "lagom-scaladsl-persistence" % "1.5.0-RC2",
  "com.lightbend.lagom" %% "lagom-scaladsl-persistence-cassandra" % "1.5.0-RC2",

  "org.scalatest" %% "scalatest" % "3.0.4" % Test,
  "com.lightbend.lagom" %% "lagom-scaladsl-testkit" % "1.5.0-RC2" % Test,
  "io.surfkit" %% "typebus-testkit" % "0.0.5-SNAPSHOT" % Test
)

val paradiseVersion = "2.1.1"

addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full)

scalacOptions += "-Yrangepos"

dockerBaseImage := "adoptopenjdk/openjdk8"
packageName in Docker := name.value
version in Docker := "latest"
//dockerRepository := Some("127.0.0.1:5000/test")


