import sbt._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "drytaiga"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    javaCore,
    javaJdbc,
    javaEbean,
    "mysql" % "mysql-connector-java" % "5.1.17",
    "postgresql" % "postgresql" % "9.1-901.jdbc4",
    "javax.mail" % "mail" % "1.4.1",
    "com.amazonaws" % "aws-java-sdk" % "1.3.11",
    "com.newrelic.agent.java" % "newrelic-agent" % "2.21.0",
    "com.newrelic.agent.java" % "newrelic-api" % "2.21.0",
    "joda-time" % "joda-time" % "2.2",
    "org.apache.commons" % "commons-lang3" % "3.1"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
  )

}