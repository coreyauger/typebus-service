import sbt.Resolver.bintrayRepo

resolvers ++= Seq(
  bintrayRepo("typesafe", "maven-releases"),
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
)


resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.18")

