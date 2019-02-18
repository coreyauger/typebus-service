name := "$name;format="normalize"$"

version := "0.0.1-SNAPSHOT"

organization in ThisBuild := "$organization$"

scalaVersion := "2.12.5"

resolvers += Resolver.sonatypeRepo("snapshots")

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8", "-language:postfixOps")

lazy val `$name;format="normalize"$` =
  (project in file("."))

val akkaV = "2.5.13"

val squbsV = "0.11.0"

libraryDependencies ++= Seq(
  "org.squbs" %% "squbs-unicomplex" % squbsV,
  "com.sksamuel.avro4s" %% "avro4s-core" % "2.0.2",
  "io.surfkit" %% "typebus-squbs" % "0.0.5-SNAPSHOT",
  $if(cluster_sharding.truthy)$
  "com.typesafe.akka" %% "akka-cluster-sharding" % akkaV,
  $endif$
  "io.surfkit" %% "typebus-$bus_type;format="lower"$" % "0.0.5-SNAPSHOT",
  "com.typesafe.akka" %% "akka-persistence-cassandra" % "0.91",
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.6.0",
  "com.codahale.metrics" % "metrics-jvm" % "3.0.2"
)

mainClass in (Compile, run) := Some("org.squbs.unicomplex.Bootstrap")

enablePlugins(PackPlugin)

packMain := Map("$name;format="normalize"$" -> "org.squbs.unicomplex.Bootstrap")

val paradiseVersion = "2.1.1"

addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full)


