name := "scala-drools"

version := "0.1"

scalaVersion := "2.13.7"

val droolsVersion = "7.62.0.Final"
val slf4jVersion = "1.7.9"
val logbackVersion = "1.2.7"
val scalatestVersion = "3.1.0-SNAP13"

libraryDependencies ++= Seq(
  "org.drools" % "drools-core" % droolsVersion,
  "org.drools" % "drools-compiler" % droolsVersion,
  "org.drools" % "drools-mvel" % droolsVersion,
  "org.kie" % "kie-api" % droolsVersion,
  "org.kie" % "kie-internal" % droolsVersion,
  "org.slf4j" % "slf4j-api" % slf4jVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  "org.scalatest" %% "scalatest" % scalatestVersion % "test",
)
