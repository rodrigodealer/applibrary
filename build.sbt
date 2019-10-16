name := "applibrary"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.8"

lazy val versions = new {
  val finatra = "19.10.0"
  val guice = "4.0"
  val mockito = "1.9.5"
  val scalatest = "3.2.0-M1"
  val elastic4sVersion = "7.3.1"
}

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com")

libraryDependencies ++= Seq(

  "com.twitter" %% "finatra-http" % versions.finatra,

  "com.twitter" %% "util-core" % versions.finatra,

  "ch.qos.logback" % "logback-classic" % "1.2.3",

  "com.twitter" %% "finatra-http" % versions.finatra % "test",
  "com.twitter" %% "inject-server" % versions.finatra % "test",
  "com.twitter" %% "inject-app" % versions.finatra % "test",
  "com.twitter" %% "inject-core" % versions.finatra % "test",
  "com.twitter" %% "inject-modules" % versions.finatra % "test",

  "com.twitter" %% "finatra-http" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-server" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-app" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-core" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-modules" % versions.finatra % "test" classifier "tests",

  "org.mockito" % "mockito-core" % versions.mockito % "test",
  "org.scalatest" %% "scalatest" % versions.scalatest % "test",
  "junit" % "junit" % "4.12" % "test",

  "com.sksamuel.elastic4s" %% "elastic4s-core" % versions.elastic4sVersion,

  "com.sksamuel.elastic4s" %% "elastic4s-http" % "6.7.3",

  // for the default http client
  "com.sksamuel.elastic4s" %% "elastic4s-client-esjava" % versions.elastic4sVersion,

  // if you want to use reactive streams
  "com.sksamuel.elastic4s" %% "elastic4s-http-streams" % versions.elastic4sVersion,

  // testing
  "com.sksamuel.elastic4s" %% "elastic4s-testkit" % versions.elastic4sVersion % "test"
)
