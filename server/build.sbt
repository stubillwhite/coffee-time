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

  // JSON
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.11.0",

  // Testing
  "org.scalatest" %% "scalatest" % "3.1.0" % Test,
  "org.mockito" %% "mockito-scala" % "1.14.8" % Test,
  "org.mockito" %% "mockito-scala-scalatest" % "1.14.8" % Test
)

enablePlugins(JavaAppPackaging)

herokuAppName in Compile := "lit-oasis-11347"

enablePlugins(BuildInfoPlugin)

buildInfoPackage := "elsevier.hackday.coffeetime"

buildInfoKeys := Seq[BuildInfoKey](
  version
)

enablePlugins(AssemblyPlugin)

artifact in(Compile, assembly) := {
  val assemblyArtifact = (artifact in(Compile, assembly)).value
  assemblyArtifact.withClassifier(Some("assembly"))
}

addArtifact(artifact in(Compile, assembly), assembly)

// WARNING: Only valid for JDK 8
// Discard module-info.class files as they are unused in JDK 8
assemblyMergeStrategy in assembly := {
  case "module-info.class" => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}