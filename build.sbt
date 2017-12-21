organization := "org.smop"

name := "smop-saxon-scala"

description := "Light Scala wrappers around the Saxon XSLT/XPath library"

licenses += ("BSD Simplified", url("http://opensource.org/licenses/bsd-license"))

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.12.4", "2.11.4", "2.10.4")

incOptions := incOptions.value.withNameHashing(true)

libraryDependencies ++= Seq(
  //"com.saxonica" % "saxon9ee" % "9.3.0.4",
  "net.sf.saxon" % "Saxon-HE" % "9.5.1-5",
  "org.specs2" %% "specs2-core" % "3.8.9" % "test",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value
)

pomExtra :=
  <url>http://github.com/bartschuller/smop-saxon-scala</url>
  <licenses>
    <license>
      <name>BSD Simplified</name>
      <url>http://opensource.org/licenses/bsd-license</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>https://github.com/bartschuller/smop-saxon-scala</url>
    <connection>scm:git:https://github.com/bartschuller/smop-saxon-scala.git</connection>
    <developerConnection>scm:git:git@github.com:bartschuller/smop-saxon-scala.git</developerConnection>
  </scm>
  <developers>
    <developer>
      <id>bartschuller</id>
      <name>Bart Schuller</name>
      <url>https://github.com/bartschuller</url>
    </developer>
  </developers>

