ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.15"

val http4sVersion = "0.23.30"

lazy val root = (project in file("."))
  .settings(
    name := "learning"
  )

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % "3.5.0",
  "co.fs2" %% "fs2-core" % "3.5.0",
  "org.apache.jena" % "apache-jena-libs" % "4.7.0",
  "org.slf4j" % "slf4j-api" % "2.0.0",
  "org.slf4j" % "slf4j-simple" % "2.0.0",
  "org.scalatest" %% "scalatest" % "3.2.15",
  "org.scalatestplus" %% "scalacheck-1-18" % "3.2.19.0" % "test",
  "com.github.cb372" %% "cats-retry" % "3.0.0",
)