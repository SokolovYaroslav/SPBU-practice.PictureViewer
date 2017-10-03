package Model

import java.awt.image.BufferedImage
import java.util.*

interface ImageMaker {

    var fileName: String
    var image: BufferedImage
    var width: Int
    var height: Int

    @Throws(IllegalArgumentException::class)
    fun makeImage(byteArray: ByteArray)

    fun addObserver(o: Observer)
}