scalaVersion := "2.11.0"


name := "testbed"
organization := "eoc.dlr.de"
version := "1.0"


libraryDependencies += "org.typelevel" %% "cats" % "0.9.0"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "eoc.dlr.de",
      scalaVersion := "2.11.0",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Hello",
    libraryDependencies ++= Seq(
      "org.alluxio" % "alluxio-core-common" % "1.6.1" % "provided",
	  "org.alluxio" % "alluxio-core-client-fs" % "1.6.1" % "provided",
      "org.locationtech.geotrellis" % "geotrellis-vector_2.11" % "1.1.1",
      "org.locationtech.geotrellis" % "geotrellis-raster_2.11" % "1.1.1",
      "org.locationtech.geotrellis" % "geotrellis-spark_2.11" % "1.1.1",
	  "org.locationtech.geotrellis" % "geotrellis-util_2.11" % "1.1.1",
	  "org.locationtech.geotrellis" % "geotrellis-macros_2.11" % "1.1.1",
      "org.apache.spark" % "spark-core_2.11" % "2.2.0" % "provided"
    )
  )

assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 case x => MergeStrategy.first
}