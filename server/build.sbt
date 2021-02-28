name := "coffee-time"
organization := "elsevier.hackday"

scalaVersion := "2.12.10"

releaseCrossBuild := true
crossScalaVersions := Seq("2.12.10")

parallelExecution in Test := false

val http4sVersion = "1.0-234-d1a2b53"
val circeVersion = "0.13.0"

libraryDependencies ++= Seq(
  // Config
  "com.github.pureconfig" %% "pureconfig" % "0.12.3",

  // Logging
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3",

  // HTTP
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "io.circe" %% "circe-generic" % circeVersion,

  // AWS
  "com.elsevier.recs" %% "recs-aws" % "1.0.161",

  // RDBMS
  "org.postgresql" % "postgresql" % "42.2.18",
  "org.scalikejdbc" % "scalikejdbc_2.12" % "3.5.0",

  // JSON
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.11.0",

  // Testing
  "org.scalatest" %% "scalatest" % "3.1.0" % Test,
  "org.mockito" %% "mockito-scala" % "1.14.8" % Test,
  "org.mockito" %% "mockito-scala-scalatest" % "1.14.8" % Test
)
