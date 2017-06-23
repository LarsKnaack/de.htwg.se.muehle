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
  "log4j" % "log4j" % "1.2.17",
  "org.hibernate" % "hibernate-core" % "5.2.9.Final",
  "com.typesafe.akka" % "akka-actor_2.11" % "2.5.2",
  "junit" % "junit" % "4.12" % "test",  // Test-only dependency
  "com.db4o" % "com.db4o" % "7.7.67"
)