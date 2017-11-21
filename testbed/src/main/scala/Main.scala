package testbed

import org.apache.spark.{SparkContext, SparkConf}
import alluxio.master.MasterClientConfig;

object SparkApp {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("spark-eo-testbed")
    val sc = new SparkContext(conf)

	val alluxio_master = MasterClientConfig.defaults.getMasterInquireClient.getMasterRpcAddresses.get(0).toString
    val b = sc.binaryFiles("alluxio:/"+alluxio_master+"/sentinel3/30/U/*/*/*/*/*.tif")
	val c = new testbed.TestUtils()
    b.mapValues(c.Utils.findMinMax).collect()
  }
}