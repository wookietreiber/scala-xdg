// cross building
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "0.5.0")
addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % "0.5.0")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.24")
addSbtPlugin("org.scala-native" % "sbt-scala-native" % "0.3.8")
// versioning
addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "1.0.0")
// linting
addSbtPlugin("org.scalastyle" % "scalastyle-sbt-plugin" % "1.0.0")
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.3.2")
