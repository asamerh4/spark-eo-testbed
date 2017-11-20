package testbed

import org.apache.spark.{SparkContext, SparkConf}

object SparkApp {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("spark-eo-testbed")
    val sc = new SparkContext(conf)

    val b = sc.binaryFiles("alluxio://192.168.0.105:19998/sentinel3/30/U/*/*/*/*/*.tif")
    val c = new testbed.TestUtils()
    b.mapValues(c.Utils.findMinMax).collect()
  }
}