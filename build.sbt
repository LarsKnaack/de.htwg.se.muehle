// Project name (artifact name in Maven)
name := "muehle"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.11"

// project description
description := "Project for the Lectures SE and SA"

// Do not append Scala versions to the generated artifacts
crossPaths := false

// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false

// library dependencies. (organization name) % (project name) % (version)
libraryDependencies ++= Seq(
  "com.google.inject" % "guice" % "4.1.0",
  //Logger
  "ch.qos.logback" % "logback-core" % "1.2.3",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.slf4j" % "slf4j-api" % "1.7.25",

  "org.hibernate" % "hibernate-core" % "5.2.9.Final",
  "com.typesafe.akka" % "akka-actor_2.11" % "2.5.2",
  "junit" % "junit" % "4.12" % "test"  // Test-only dependency
)