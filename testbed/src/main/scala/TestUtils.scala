package testbed
class  TestUtils extends Serializable {

import geotrellis.raster.io.geotiff.reader.GeoTiffReader
import geotrellis.raster.io.geotiff._
import geotrellis.macros._
import geotrellis.util._
import org.apache.spark.input.PortableDataStream

object Utils {
   def findMinMax(f: PortableDataStream): (Int, Int) = {
      val geoTiff: SinglebandGeoTiff = GeoTiffReader.readSingleband(org.apache.commons.io.IOUtils.toByteArray(f.open))
      return geoTiff.raster.tile.findMinMax
   }
 }
}