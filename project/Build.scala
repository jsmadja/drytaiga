import sbt._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "drytaiga"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    "postgresql" % "postgresql" % "9.1-901.jdbc4",
    "javax.mail" % "mail" % "1.4.1",
    "com.amazonaws" % "aws-java-sdk" % "1.3.11",
    "com.newrelic.agent.java" % "newrelic-api" % "2.21.0"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(

  )

}
