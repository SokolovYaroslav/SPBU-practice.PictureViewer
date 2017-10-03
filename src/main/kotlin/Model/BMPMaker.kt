package Model

import java.awt.image.BufferedImage
import java.util.*

class BMPMaker : ImageMaker, Observable()  {

    override var fileName: String = "SampleImage"
    override lateinit var image: BufferedImage
    override var width = 0
    override var height = 0

    private lateinit var BMPInfo: BitMapInfoBMP

    @Throws(IllegalArgumentException::class)
    override fun makeImage(byteArray: ByteArray) {
        if (!(byteArray[0].toChar() == 'B' && byteArray[1].toChar() == 'M')) {
            throw IllegalArgumentException("Illegal actual format")
        }

        BMPInfo = BitMapInfoBMP(byteArray)

        getImage()
        width = BMPInfo.width
        height = BMPInfo.height

        setChanged()
        notifyObservers(fileName)
    }

    private fun getImage() {
        image = BufferedImage(BMPInfo.width, BMPInfo.height, 1)

        var index = BMPInfo.data.size - 1
        if (BMPInfo.height > 0) {
            for (y in 0..BMPInfo.height - 1) {
                for (x in BMPInfo.width - 1 downTo  0) {
                    image.setRGB(x, y, BMPInfo.data[index])
                    index--
                }
            }
        }
        else {
            for (y in BMPInfo.height - 1 downTo 0) {
                for (x in BMPInfo.width - 1 downTo  0) {
                    image.setRGB(x, y, BMPInfo.data[index])
                    index--
                }
            }
        }
    }
}