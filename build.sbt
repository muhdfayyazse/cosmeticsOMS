name := """cosmeticsOMS"""
organization := "com.muqaddas.cosmetics"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.6"

libraryDependencies ++= Seq(
  guice,
  javaJpa,
  "org.projectlombok" % "lombok" % "1.18.22",
  "org.hibernate" % "hibernate-core" % "5.4.30.Final",
  "org.mariadb.jdbc" % "mariadb-java-client" % "3.0.2-rc"
)



//PlayKeys.externalizeResourcesExcludes += baseDirectory.value / "conf" / "META-INF" / "persistence.xml"
