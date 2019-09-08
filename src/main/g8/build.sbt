name := "$name;format="normalize"$"

version := "0.0.1-SNAPSHOT"

organization in ThisBuild := "$organization$"

scalaVersion := "2.12.5"

resolvers += Resolver.sonatypeRepo("snapshots")

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8", "-language:postfixOps")

lazy val `$name;format="normalize"$` =
  (project in file("."))

val akkaV = "2.5.23"
val typebusV =  "0.0.12-SNAPSHOT"
val phantomVersion = "2.41.0"
val akkaManagementVersion = "1.0.1"

libraryDependencies ++= Seq(
  "com.sksamuel.avro4s" %% "avro4s-core" % "2.0.2",
  "com.typesafe.akka" %% "akka-cluster-sharding" % akkaV,
  "io.surfkit" %% "typebus-kafka" % typebusV,
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.6.0",
  "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided",
  "com.outworkers"  %% "phantom-dsl" % phantomVersion,

  "com.typesafe.akka" %% "akka-discovery" % akkaV,
  "com.lightbend.akka.management" %% "akka-management-cluster-bootstrap" % akkaManagementVersion,
  "com.lightbend.akka.discovery" %% "akka-discovery-kubernetes-api" % akkaManagementVersion,
  "com.typesafe.akka" %% "akka-remote" % akkaV,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaV,
  "com.typesafe.akka" %% "akka-cluster-typed" % akkaV,
  "com.typesafe.akka" %% "akka-cluster-sharding-typed"% akkaV,
  "com.typesafe.akka" %% "akka-persistence-cassandra" % "0.91",
  "com.typesafe.akka" %% "akka-http"   % "10.1.8",

  "org.scalatest" %% "scalatest" % "3.0.4" % Test,
  "io.surfkit" %% "typebus-testkit" % typebusV % Test
)

val paradiseVersion = "2.1.1"

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full)

scalacOptions += "-Yrangepos"

dockerBaseImage := "adoptopenjdk/openjdk8"
packageName in Docker := name.value
version in Docker := "latest"

dockerRepository in Docker := Option(System.getProperty("dockerRepository"))


