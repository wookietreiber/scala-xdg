import sbtcrossproject.CrossPlugin.autoImport.crossProject

// ----------------------------------------------------------------------------
// sbt plugins
// ----------------------------------------------------------------------------

enablePlugins(GitVersioning)

val scala210 = "2.10.7"
val scala211 = "2.11.12"
val scala212 = "2.12.6"

lazy val baseSettings = Seq(
  organization := "com.github.wookietreiber",
  git.baseVersion := "0.2.0",
  scalaVersion := scala212,
  crossScalaVersions := Seq(scala210, scala211, scala212),
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-feature",
    "-unchecked"
  ),
  scalacOptions ++= Seq(scalaBinaryVersion.value match {
    case v if v.startsWith("2.12") => "-target:jvm-1.8"
    case _                         => "-target:jvm-1.7"
  }),
  scalacOptions in (Compile, doc) ++= Seq(
    "-doc-title", "scala-xdg",
    "-doc-version", git.baseVersion.value,
    "-sourcepath", (baseDirectory in ThisBuild).value.toString,
    "-doc-source-url", s"https://github.com/wookietreiber/scala-xdg/tree/v${git.baseVersion.value}€{FILE_PATH}.scala",
    "-diagrams",
    "-groups",
    "-implicits"
  ),
  autoAPIMappings := true,
  scalastyleConfig := file(".scalastyle-config.xml"),
  wartremoverErrors in (Compile, compile) ++= Seq(
    Wart.ArrayEquals,
    Wart.FinalCaseClass,
    Wart.OptionPartial,
    Wart.TraversableOps,
    Wart.TryPartial
  ),
  description := "freedesktop.org for Scala",
  homepage := Some(url("https://github.com/wookietreiber/scala-xdg")),
  startYear := Some(2018),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/wookietreiber/scala-xdg"),
      "scm:git:git://github.com/wookietreiber/scala-xdg.git",
      Some("scm:git:https://github.com/wookietreiber/scala-xdg.git")
    )),
  licenses := Seq(
    "BSD 3-Clause" -> url("https://opensource.org/licenses/BSD-3-Clause")
  ),
  developers += Developer(
    email = "christian.krause@mailbox.org",
    id = "wookietreiber",
    name = "Christian Krause",
    url = url("https://github.com/wookietreiber")
  ),
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  publishArtifact in Test := false
)

lazy val noPublish = Seq(
  publishArtifact := false,
  publish := {},
  publishLocal := {}
)

baseSettings
noPublish

lazy val basedir = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .settings(
    name := "scala-xdg-basedir",
    baseSettings,
    scalacOptions in (Compile, doc) ++= Seq(
      "-doc-root-content", "basedir/rootdoc.txt"
    )
    // TODO apiURL
  )
  .jsSettings()
  .nativeSettings(
    scalaVersion := scala211,
    crossScalaVersions := Seq(scala211),
    nativeLinkStubs := true
  )

lazy val basedirJS = basedir.js
lazy val basedirJVM = basedir.jvm
lazy val basedirNative = basedir.native
