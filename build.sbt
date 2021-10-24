name := """cosmeticsOMS"""
organization := "com.muqaddas.cosmetics"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.6"

libraryDependencies ++= Seq(
  guice,
  "org.projectlombok" % "lombok" % "1.18.22",
  javaJpa,
  "org.hibernate" % "hibernate-core" % "5.4.30.Final"
)



//PlayKeys.externalizeResourcesExcludes += baseDirectory.value / "conf" / "META-INF" / "persistence.xml"
