organization := "org.smop"

name := "smop-saxon-scala"

description := "Light Scala wrappers around the Saxon XSLT/XPath library"

licenses += ("BSD Simplified", url("http://opensource.org/licenses/bsd-license"))

scalaVersion := "2.11.0"

crossScalaVersions := Seq("2.11.0", "2.10.4")

incOptions := incOptions.value.withNameHashing(true)

libraryDependencies ++= Seq(
  "net.sf.saxon" % "Saxon-HE" % "9.5.1-5",
  "org.specs2" %% "specs2" % "2.3.11" % "test"
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


releaseSettings

seq(bintrayPublishSettings:_*)
