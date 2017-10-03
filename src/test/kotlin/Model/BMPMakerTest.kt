package Model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.imageio.ImageIO.read

internal class BMPMakerTest {

    private val pictures = listOf(
            "beaut_24bit",
            "bogts_8bit",
            "dodj_24bit",
            "freebsd2_8bit",
            "fu_32bit",
            "haker_24bit",
            "hm_8bit",
            "led_32bit",
            "love_24bit",
            "man",
            "ogon_24bit",
            "pahom_32bit",
            "per_24bit",
            "sh_32bit",
            "su85_24bit",
            "taet_led_24bit",
            "warrios_24bit")

    @Test
    fun testWithStandartLibrary() {

        for (picture in pictures) {
            val model = BMPMaker()
            val realImage: BufferedImage
            val myImage: BufferedImage

            print(picture + " : ")

            val filePath = "./src/pictures/bmp/" + picture + ".bmp"

            realImage = read(File(filePath))
            model.makeImage(Files.readAllBytes(Paths.get(filePath)))
            myImage = model.image

            assertEquals(realImage.height, myImage.height)
            assertEquals(realImage.width, myImage.width)

            for (i in 0..realImage.height - 1) {
                for (j in 0..realImage.width - 1) {
                    assertEquals(realImage.getRGB(j, i), myImage.getRGB(j, i))
                }
            }
            println("OK")
        }
    }
}