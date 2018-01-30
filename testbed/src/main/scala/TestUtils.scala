package testbed

import geotrellis.macros._
import geotrellis.raster
import geotrellis.raster._
import geotrellis.raster.io.geotiff._
import geotrellis.raster.io.geotiff.reader.GeoTiffReader

import geotrellis.util._
import org.apache.spark.input.PortableDataStream

class  TestUtils extends Serializable {

  object Utils {
    def findMinMax(f: PortableDataStream): (Int, Int) = {
      val geoTiff: SinglebandGeoTiff = GeoTiffReader.readSingleband(org.apache.commons.io.IOUtils.toByteArray(f.open))
      return geoTiff.raster.tile.findMinMax
    }
  }
}

