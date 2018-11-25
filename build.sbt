name := "scala_graalvm_app"

version in Global := "0.1"

scalaVersion in Global := "2.12.7"

val loggerDependencies = Seq(
  "org.slf4j" % "slf4j-api" % "1.7.25",
   "ch.qos.logback" % "logback-classic" % "1.2.3",
  // "ch.qos.logback" % "logback-core" % "1.2.3"
)

val commonDependencies = {
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

lazy val scalaGraalVm = (project in file("."))
  .settings(commonSettings("scalaGraalVm"))
  .aggregate(main)

lazy val main = (project in file("modules/main"))
  .enablePlugins(JmhPlugin)
  .settings(commonSettings("main"))
  .settings(
    assemblyOutputPath in assembly := file(s"build/${name.value}.jar"),
    assemblyMergeStrategy in assembly := MergeStrategy.defaultMergeStrategy
  )
