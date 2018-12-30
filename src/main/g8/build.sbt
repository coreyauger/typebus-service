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
  "io.surfkit" %% "typebus-squbs" % "0.0.1-SNAPSHOT",
  $if(cluster_sharding.truthy)$
  "com.typesafe.akka" %% "akka-cluster-sharding" % akkaV,
  $endif$
  "io.surfkit" %% "typebus-$bus_type;format="lower"$" % "0.0.5-SNAPSHOT"
)

mainClass in (Compile, run) := Some("org.squbs.unicomplex.Bootstrap")

enablePlugins(PackPlugin)

packMain := Map("$name;format="normalize"$" -> "org.squbs.unicomplex.Bootstrap")


