package Model

/**
* Created by Yaroslav Sokolov on 28.04.17.
*/
data class BitMapInfoBMP(private val byteArray: ByteArray, val vizualizationWay: Int = 1) {

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
        val dataStart = bytesToInt(0x0a, 4, byteArray)
        rawData = byteArray.sliceArray(dataStart..byteArray.size - 1)
        version = bytesToInt(0x0E, 4, byteArray)
        when (version) {
            12 -> {
                width = bytesToInt(0x12, 2, byteArray)
                height = bytesToInt(0x14, 2, byteArray)
                bitsPerPixel = bytesToInt(0x18, 2, byteArray)
            }
            40, 108, 124 -> {
                width = bytesToInt(0x12, 4, byteArray)
                height = bytesToInt(0x16, 4, byteArray)
                bitsPerPixel = bytesToInt(0x1C, 2, byteArray)
//                TODO("Realize usage of specific fields")
            }
            else -> throw IllegalArgumentException("Something went wrong")
        }

        rawTable = if (bitsPerPixel <= 8) {
            byteArray.sliceArray(version + 0X0E..dataStart - 1)
        }
        else null

        if (vizualizationWay == 1) {
            table = parseTable()
            data = parseData()
        }
        else TODO("Realize some other ways of vizualization")
    }

    private fun bytesToInt(startIndex: Int, byteAmount: Int, source: ByteArray): Int {

        var value: Int = 0

        for (i in startIndex + byteAmount - 1 downTo startIndex) {
            val byte = byteToUnsignedInt(source[i])
            value = (value shl 8) + byte
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

    private fun parseTable(): Array<Int>? {
        val table: Array<Int>?
        if (rawTable != null) {
            table = Array(rawTable.size / 4, {0})
            for (i in 0..rawTable.size - 1 step 4) {
                table[i / 4] = bytesToInt(i, 4, rawTable)
            }
        }
        else {
            table = null
        }
        return table
    }

    //Classic vizualization
    private fun parseData(): Array<Int> {
        val data = Array(width * height, {0})
        val alignment = (4 - ((width * bitsPerPixel / 8) % 4)) % 4
        when (bitsPerPixel) {
            24 -> {
                for (i in 0..height - 1) {
                    for (j in 0..width - 1) {
                        val index = (i * (3 * width + alignment) + 3 * j)
                        data[i * width + j] = bytesToInt(index, 3, rawData)
                    }
                }
            }
            32 -> {
                for (i in 0..height - 1) {
                    for (j in 0..width - 1) {
                        val index = (i * (4 * width + alignment) + 4 * j)
                        data[i * width + j] = bytesToInt(index, 4, rawData)
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
}