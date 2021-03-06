// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.0-RC2")

// Workaround for Logging in 2.6
// see https://github.com/playframework/playframework/issues/7422
//libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.25"

// Web plugins
addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.1.0")

// Play enhancer - this automatically generates getters/setters for public fields
// and rewrites accessors of these fields to use the getters/setters. Remove this
// plugin if you prefer not to have this feature, or disable on a per project
// basis using disablePlugins(PlayEnhancer) in your build.sbt
addSbtPlugin("com.typesafe.play" % "play-enhancer" % "1.2.1")

// Play Ebean support, to enable, uncomment this line, and enable in your build.sbt using
// enablePlugins(PlayEbean).
addSbtPlugin("com.typesafe.sbt" % "sbt-play-ebean" % "4.0.2")
