// Project name (artifact name in Maven)
name := "muehle"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.11"

// project description
description := "Project for the Lectures SE and SA"

//Added strict conflict manager for akka dependency mess
conflictManager := ConflictManager.strict

// library dependencies. (organization name) % (project name) % (version)
libraryDependencies ++= Seq(
  "com.google.inject" % "guice" % "4.1.0",
  "log4j" % "log4j" % "1.2.17",
  "org.hibernate" % "hibernate-core" % "5.2.9.Final",
  "com.typesafe.akka" % "akka-actor_2.11" % "2.5.2",
  "junit" % "junit" % "4.12" % "test",  // Test-only dependency
  "com.db4o" % "com.db4o" % "7.7.67",

  //Logger dependencies
  "ch.qos.logback" % "logback-core" % "1.2.3",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.slf4j" % "slf4j-api" % "1.7.25",

  //Akka Modules
  "com.typesafe.akka" %% "akka-actor" % "2.5.2",
  //Included because of some random !@*?& see https://github.com/akka/akka-http/issues/821
  "com.typesafe.akka" %% "akka-stream" % "2.5.2",

  "com.typesafe.akka" %% "akka-http" % "10.0.7",

  //Test dependencies
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.7" % Test,
  "junit" % "junit" % "4.12" % Test
)

//Overriding dependencies to force latest akka Version
dependencyOverrides += "com.typesafe.akka" %% "akka-actor" % "2.5.2"
dependencyOverrides += "com.typesafe.akka" %% "akka-stream" % "2.5.2"