package Model

/**
* Created by Yaroslav Sokolov on 28.04.17.
*/
data class BitMapInfoBMP(private val byteList: List<Byte>, val vizualizationWay: Int = 1) {

    internal val version: Int
    internal val width: Int
    internal val height: Int
    internal val bitsPerPixel: Int
    internal val data: Array<Int>//Array of pixels

//    private val actualHeight: Int
    private val rawData: ByteArray
    private val rawTable: ByteArray?
    private val table: Array<Int>?

    init {
        val dataStart = bytesToInt(0x0a, 4, byteList)
        rawData = byteList.subList(dataStart, byteList.size).toByteArray()
        version = bytesToInt(0x0E, 4, byteList)
        when (version) {
            12 -> {
                width = bytesToInt(0x12, 2, byteList)
                height = bytesToInt(0x14, 2, byteList)
                bitsPerPixel = bytesToInt(0x18, 2, byteList)
            }
            40, 108, 124 -> {
                width = bytesToInt(0x12, 4, byteList)
                height = bytesToInt(0x16, 4, byteList)
                bitsPerPixel = bytesToInt(0x1C, 2, byteList)
//                TODO("Realize usage of specific fields")
            }
            else -> throw IllegalArgumentException("Something went wrong")
        }

        rawTable = if (bitsPerPixel <= 8) {
            byteList.subList(version + 0X0E, dataStart).toByteArray()
        }
        else null

        if (vizualizationWay == 1) {
            table = parseTable()
            data = parseData()
        }
        else TODO("Realize some other ways of vizualization")
    }

    private fun bytesToInt(startIndex: Int, byteAmount: Int,
                           source: List<Byte>, signed: Boolean = false): Int {

        val subList = source.subList(startIndex, startIndex + byteAmount).reversed()
        var value: Int = 0
        for (byte in subList) {
            val byteInInt: Int = if (signed) byte.toInt() else byteToUnsignedInt(byte)
            value = (value shl 8) + byteInInt
        }
        return value
    }

    private fun byteToUnsignedInt(byte: Byte): Int {
        if (byte.toInt() < 0) {
            return byte.toInt() + 256
        }
        else {
            return byte.toInt()
        }
    }

    //Classic vizualization
    private fun parseTable(): Array<Int>? {
        val table: Array<Int>?
        if (rawTable != null) {
            table = Array<Int>(rawTable.size / 4, {0})
            for (i in 0..rawTable.size - 1 step 4) {
                table[i / 4] = parseBGRA(i, rawTable)
            }
        }
        else {
            table = null
        }
        return table
    }

    //Classic vizualization
    private fun parseData(): Array<Int> {
        val data = Array<Int>(width * height, {0})
        val alignment = (4 - ((width * bitsPerPixel / 8) % 4)) % 4
        when (bitsPerPixel) {
            24 -> {
                for (i in 0..height - 1) {
                    for (j in 0..width - 1) {
                        val index = (i * (3 * width + alignment) + 3 * j)
                        if (index > 2359292) {
                            println(i)
                            println(j)
                            println(index)
                        }
                        data[i * width + j] = parseBGR(index, rawData)
                    }
                }
            }
            32 -> {
                for (i in 0..height - 1) {
                    for (j in 0..width - 1) {
                        val index = (i * (4 * width + alignment) + 4 * j)
                        data[i * width + j] = parseBGRA(index, rawData)
                    }
                }
            }
            8 -> {
                for (i in 0..height - 1) {
                    for (j in 0..width - 1) {
                        val index = (i * (width + alignment) + j)
                        data[i * width + j] = table!![byteToUnsignedInt(rawData[index])]
                    }
                }
            }
            else -> throw IllegalArgumentException("Invalid bits per pixel")
        }
        return data
    }

    //returns one RGB Int
    private fun parseBGR(index: Int, source: ByteArray): Int {
        var pixel = 0

        for (i in index downTo index - 2) {
            pixel = (pixel shl 8) + source[i + 2]
        }

        return pixel
    }

    //returns one ARGB Int
    private fun parseBGRA(index: Int, source: ByteArray): Int {
        var pixel = 0

        for (i in index downTo index - 3) {
            pixel = (pixel shl 8) + source[i + 3]
        }

        return pixel
    }
}