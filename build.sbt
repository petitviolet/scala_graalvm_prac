name := "scala_graalvm_app"

version in Global := "0.1"

scalaVersion in Global := "2.12.7"

val loggerDependencies = Seq(
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "ch.qos.logback" % "logback-core" % "1.2.3"
)

val commonDependencies = {
//  val AKKA = "2.5.18"
//  val AKKA_HTTP = "10.1.5"
//  Seq(
//    "com.typesafe.akka" %% "akka-slf4j" % AKKA,
//    "com.typesafe.akka" %% "akka-http" % AKKA_HTTP,
//    "com.typesafe.akka" %% "akka-http-spray-json" % AKKA_HTTP,
//    "com.typesafe.akka" %% "akka-stream" % AKKA,
//  ) ++ loggerDependencies
  List(
    "org.http4s" %% "http4s-blaze-server" % "0.18.9",
    "org.http4s" %% "http4s-circe" % "0.18.9",
    "org.http4s" %% "http4s-dsl" % "0.18.9",
  ) ++ loggerDependencies
}

def commonSettings(_name: String) = Seq(
  libraryDependencies ++= commonDependencies,
  name := _name,
  trapExit := false,
  scalacOptions += "-Ypartial-unification",
  scalafmtConfig := Some(file(".scalafmt.conf")),
  scalafmtOnCompile := true,
)

lazy val scalaGraalVmFunction = (project in file("."))
  .settings(commonSettings("scalaGraalVmFunction"))
  .aggregate(main)

lazy val nativeImage = taskKey[Unit]("Compiles GraalVM native image")
lazy val nativeImageDocker = taskKey[Unit]("Creates a docker container with compiled GraalVM native image")

lazy val nativeImageName = settingKey[String]("The name of the native image.")
lazy val nativeImagePath = settingKey[File]("The parent path of the native image.")
lazy val nativeImageDockerfile = settingKey[String]("The dockerfile for a native image.")
lazy val nativeImageDockerTags = settingKey[Seq[String]]("The docker image tags.")

lazy val main = (project in file("modules/main"))
  .enablePlugins(JmhPlugin)
  .settings(commonSettings("main"))
  .settings(
    assemblyOutputPath in assembly := file(s"build/${name.value}.jar")
  )
