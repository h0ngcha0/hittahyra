name := """hittahyra"""

organization in ThisBuild := "io.spass"

version in ThisBuild := "0.0.1"

scalaVersion in ThisBuild := "2.11.2"

startYear := Some(2013)

homepage := Some(url("https://github.com/spass/spass"))

licenses := Seq("GNU AFFERO GENERAL PUBLIC LICENSE, Version 3" -> url("http://www.gnu.org/licenses/agpl-3.0.txt"))

resolvers in ThisBuild += Resolver.url("Edulify Repository", url("http://edulify.github.io/modules/releases/"))(Resolver.ivyStylePatterns)

lazy val web = (project in file("modules/web"))
  .enablePlugins(PlayScala)

lazy val users = (project in file("modules/users"))
  .enablePlugins(PlayScala)
  .dependsOn(web)

lazy val spass = (project in file("."))
  .enablePlugins(PlayScala)
  .dependsOn(web, users)
  .aggregate(web, users)

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.3.0",
  "org.webjars" % "angularjs" % "1.2.24",
  "org.webjars" % "bootstrap" % "3.2.0",
  "org.webjars" % "requirejs" % "2.1.14-1"
)

libraryDependencies in ThisBuild ++= Seq(
  cache,
  jdbc,
  "com.edulify" %% "play-hikaricp" % "1.4.1",
  "com.github.tototoshi" %% "slick-joda-mapper" % "1.2.0",
  "com.typesafe.play" %% "play-slick" % "0.8.0",
  "org.joda" % "joda-convert" % "1.6",
  "com.jsuereth" %% "scala-arm" % "1.4",
  "ws.securesocial" %% "securesocial" % "3.0-M1"
)

pipelineStages := Seq(rjs, digest, gzip)

scalacOptions in ThisBuild ++= Seq(
  "-target:jvm-1.7",
  "-encoding", "UTF-8",
  //"-deprecation", // warning and location for usages of deprecated APIs
  "-feature", // warning and location for usages of features that should be imported explicitly
  "-unchecked", // additional warnings where generated code depends on assumptions
  "-Xlint", // recommended additional warnings
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
  "-Ywarn-value-discard", // Warn when non-Unit expression results are unused
  "-Ywarn-inaccessible",
  "-Ywarn-dead-code",
  "-language:reflectiveCalls"
)
