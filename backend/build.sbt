name := """backend"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.14"

//libraryDependencies += guice
//libraryDependencies += "com.h2database" % "h2" % "2.3.232"
libraryDependencies ++= Seq(
  jdbc
)
libraryDependencies += "com.lucidchart" %% "relate" % "3.2.0"
libraryDependencies += "com.mysql" % "mysql-connector-j" % "9.0.0"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
libraryDependencies += "com.lihaoyi" %% "upickle" % "3.3.1"
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
