package Model

import java.awt.image.BufferedImage
import java.util.*

/**
* Created by Yaroslav Sokolov on 25.04.17.
*/
interface ImageMaker {

    val fileName: String
    var image: BufferedImage
    var width: Int
    var height: Int

    @Throws(IllegalArgumentException::class)
    public fun makeImage(byteList: List<Byte>)

    public fun addObserver(o: Observer)
}