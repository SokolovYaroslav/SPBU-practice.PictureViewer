package Model

import java.awt.image.BufferedImage
import java.util.*

/**
* Created by Yaroslav Sokolov on 26.04.17.
*/
class BMPMaker(override val fileName: String) : ImageMaker, Observable()  {

    override lateinit var image: BufferedImage
    override var width = 0
    override var height = 0

    private lateinit var BMPInfo: BitMapInfoBMP

    @Throws(IllegalArgumentException::class)
    override fun makeImage(byteList: List<Byte>) {
        if (!(byteList[0].toChar() == 'B' && byteList[1].toChar() == 'M')) {
            throw IllegalArgumentException("Illegal actual format")
        }

        BMPInfo = BitMapInfoBMP(byteList)

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