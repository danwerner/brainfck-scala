name := "brainfck-scala"

version := "0.2"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
  "org.specs2" %% "specs2-core" % "latest.integration" % "test"
)
scalacOptions in Test ++= Seq(
  "-Yrangepos"
)
