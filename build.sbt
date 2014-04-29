name := "smop-saxon-scala"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.0"

crossScalaVersions := Seq("2.11.0", "2.10.4")

incOptions := incOptions.value.withNameHashing(true)

libraryDependencies ++= Seq(
  "net.sf.saxon" % "Saxon-HE" % "9.5.1-5",
  "org.specs2" %% "specs2" % "2.3.11" % "test"
)

