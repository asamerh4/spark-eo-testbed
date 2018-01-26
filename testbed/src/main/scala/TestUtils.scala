package testbed

import geotrellis.macros._
import geotrellis.raster
import geotrellis.raster._
import geotrellis.raster.io.geotiff._
import geotrellis.raster.io.geotiff.reader.GeoTiffReader
import geotrellis.raster.isNoData
import geotrellis.raster.mapalgebra.local.LocalTileBinaryOp
import geotrellis.raster.mapalgebra.local.Max
import geotrellis.util._
import org.apache.spark.input.PortableDataStream

class  TestUtils extends Serializable {

  object Utils {
    def findMinMax(f: PortableDataStream): (Int, Int) = {
      val geoTiff: SinglebandGeoTiff = GeoTiffReader.readSingleband(org.apache.commons.io.IOUtils.toByteArray(f.open))
      return geoTiff.raster.tile.findMinMax
    }
  }

  //geotrellis-gitter hint from @metasim to avoid NODATA return values
  object DataBiasedOp {
    object BiasedMin extends DataBiasedOp {
      def op(z1: Int, z2: Int) = math.min(z1, z2)
      def op(z1: Double, z2: Double) = math.min(z1, z2)
    }

    object BiasedMax extends DataBiasedOp {
      def op(z1: Int, z2: Int) = math.max(z1, z2)
      def op(z1: Double, z2: Double) = math.max(z1, z2)
    }

    object BiasedAdd extends DataBiasedOp {
      def op(z1: Int, z2: Int) = z1 + z2
      def op(z1: Double, z2: Double) = z1 + z2
    }
  }
  trait DataBiasedOp extends LocalTileBinaryOp {
    def op(z1: Int, z2: Int): Int
    def op(z1: Double, z2: Double): Double

    def combine(z1: Int, z2: Int): Int =
      if (isNoData(z1) && isNoData(z2)) raster.NODATA
      else if (isNoData(z1)) z2
      else if (isNoData(z2)) z1
      else op(z1, z2)

    def combine(z1: Double, z2: Double): Double =
      if (isNoData(z1) && isNoData(z2)) raster.doubleNODATA
      else if (isNoData(z1)) z2
      else if (isNoData(z2)) z1
      else op(z1, z2)
  }
  trait MaxMethods extends MethodExtensions[Tile] {
  /** Max a constant Int value to each cell. */
  def biasedLocalMax(i: Int): Tile = DataBiasedOp.BiasedMax(self, i)
  /** Max a constant Double value to each cell. */
  def biasedLocalMax(d: Double): Tile = DataBiasedOp.BiasedMax(self, d)
  /** Max the values of each cell in each raster.  */
  def biasedLocalMax(r: Tile): Tile = DataBiasedOp.BiasedMax(self, r)
  /** Max the values of each cell in each raster.  */
  def biasedLocalMax(rs: Traversable[Tile]): Tile = DataBiasedOp.BiasedMax(self +: rs.toSeq)
}
}

