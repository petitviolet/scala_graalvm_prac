name := "scala_graalvm_function"

version in Global := "0.1"

scalaVersion in Global := "2.12.7"

val loggerDependencies = Seq(
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "ch.qos.logback" % "logback-core" % "1.2.3"
)

val commonDependencies = {
  val AKKA_HTTP = "10.1.5"
  Seq(
    "com.typesafe.akka" %% "akka-http" % AKKA_HTTP,
    "com.typesafe.akka" %% "akka-http-spray-json" % AKKA_HTTP,
    "com.typesafe.akka" %% "akka-stream" % "2.5.16",
  ) ++ loggerDependencies
}

def commonSettings(_name: String) = Seq(
  libraryDependencies ++= commonDependencies,
  name := _name,
  trapExit := false,
  scalafmtConfig := Some(file(".scalafmt.conf")),
  scalafmtOnCompile := true,
)

lazy val scalaGraalVmFunction = (project in file("."))
  .settings(commonSettings("scalaGraalVmFunction"))
  .aggregate(main)

lazy val main = (project in file("modules/main"))
  .enablePlugins(JmhPlugin)
  .settings(commonSettings("main"))
  .settings(
    assemblyOutputPath in assembly := file(s"build/${name.value}.jar")
  )
