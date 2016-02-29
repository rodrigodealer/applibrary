name := "quickstart"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

lazy val versions = new {
  val finatra = "2.1.4"
  val guice = "4.0"
  val mockito = "1.9.5"
  val scalatest = "2.2.3"
}

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com")

libraryDependencies ++= Seq(
  "com.twitter.finatra" %% "finatra-http" % versions.finatra,
  "com.twitter.finatra" %% "finatra-slf4j" % versions.finatra,
  "com.github.seratch" %% "awscala" % "0.5.+",
  "com.scalapenos" %% "riak-scala-client" % "0.9.5",
  "com.twitter.finatra" %% "finatra-http" % versions.finatra % "test",
  "com.twitter.inject"  %% "inject-server" % versions.finatra % "test",
  "com.twitter.inject"  %% "inject-app" % versions.finatra % "test",
  "com.twitter.inject"  %% "inject-core" % versions.finatra % "test",
  "com.twitter.inject"  %% "inject-modules" % versions.finatra % "test",
  "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test",

  "com.twitter.finatra" %% "finatra-http" % versions.finatra % "test" classifier "tests",
  "com.twitter.inject"  %% "inject-server" % versions.finatra % "test" classifier "tests",
  "com.twitter.inject"  %% "inject-app" % versions.finatra % "test" classifier "tests",
  "com.twitter.inject"  %% "inject-core" % versions.finatra % "test" classifier "tests",
  "com.twitter.inject"  %% "inject-modules" % versions.finatra % "test" classifier "tests",

  "org.mockito"         % "mockito-core" % versions.mockito % "test",
  "org.scalatest"       %% "scalatest" % versions.scalatest % "test",
  "junit"               % "junit" % "4.12" % "test"
)
