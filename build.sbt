name := "applibrary"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.8"

lazy val versions = new {
  val finatra = "19.10.0"
  val guice = "4.0"
  val mockito = "1.9.5"
  val scalatest = "3.2.0-M1"
}

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com")

libraryDependencies ++= Seq(
  "com.twitter" %% "finatra-http" % versions.finatra,
  "com.github.seratch" %% "awscala" % "0.5.+",
  "com.twitter" %% "finagle-redis" % "19.10.0",
  "org.elasticsearch" % "elasticsearch" % "7.4.0",
  "com.sksamuel.elastic4s" %% "elastic4s-core" % "7.3.1",
  "com.sksamuel.elastic4s" %% "elastic4s-jackson" % "6.7.3",
  "com.sksamuel.elastic4s" %% "elastic4s-http" % "6.7.3",
  "com.sksamuel.elastic4s" %% "elastic4s-tcp" % "6.2.10",
  "com.sksamuel.elastic4s" %% "elastic4s-client-esjava" % "7.3.1",
  "org.slf4j" % "slf4j-simple" % "1.7.21",

  "com.twitter" %% "finatra-http" % versions.finatra % "test",
  "com.twitter" %% "inject-server" % versions.finatra % "test",
  "com.twitter" %% "inject-app" % versions.finatra % "test",
  "com.twitter" %% "inject-core" % versions.finatra % "test",
  "com.twitter" %% "inject-modules" % versions.finatra % "test",
  "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test",

  "com.twitter" %% "finatra-http" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-server" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-app" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-core" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-modules" % versions.finatra % "test" classifier "tests",

  "org.mockito" % "mockito-core" % versions.mockito % "test",
  "org.scalatest" %% "scalatest" % versions.scalatest % "test",
  "junit" % "junit" % "4.12" % "test"
)
