import sbt.Keys._
import sbt._

object ProjectBuild extends AutoPlugin {
  import de.heikoseeberger.sbtheader.HeaderPlugin
  import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport._

  override def trigger = allRequirements

  override def requires: Plugins = plugins.JvmPlugin && HeaderPlugin

  override def buildSettings = Seq(
    scalaVersion := "2.12.7",
    organization := "cn.51scala",
    organizationName := "51scala",
    startYear := Some(2018)
  )

  override def projectSettings = Seq(
    headerLicense := Some(HeaderLicense.ALv2(CurrentYear.toString, "51scala")),
    scalacOptions in Compile ++= Seq("-encoding", "UTF-8", "-target:jvm-1.8", "-deprecation", "-feature", "-unchecked", "-Xlint:package-object-classes", "-Ybackend-parallelism", "8", "-opt:l:method"),
    javacOptions in Compile ++= Seq("-source", "1.8", "-target", "1.8", "-encoding", "UTF-8", "-Xlint:unchecked", "-Xlint:deprecation"),
    licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
    logLevel := Level.Info,
    parallelExecution in Test := true,
    fork := true
  )

  lazy val CurrentYear = java.time.Year.now.getValue
}

object Build {
  import ScriptedPlugin.autoImport._
  import bintray.BintrayKeys._
  import de.heikoseeberger.sbtheader.AutomateHeaderPlugin
  import plugins.SbtPlugin

  val root = (project in file("."))
    .enablePlugins(AutomateHeaderPlugin, SbtPlugin, ScriptedPlugin)
    .settings(
      name := "flyway-sbt",
      libraryDependencies ++= Seq(
        "org.flywaydb" % "flyway-core" % "5.2.3"
      ),
      scriptedLaunchOpts := { scriptedLaunchOpts.value ++
        Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
      },
      scriptedBufferLog := false,
      publishMavenStyle := false,
      bintrayRepository := "sbt-plugins",
      bintrayOrganization in bintray := None,
      bintrayPackageLabels := Seq("flyway", "sbt", "flyway-sbt")
    )
}