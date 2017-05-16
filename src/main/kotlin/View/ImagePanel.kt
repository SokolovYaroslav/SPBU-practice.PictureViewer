package View

import java.awt.Graphics
import java.awt.image.BufferedImage
import javax.swing.JPanel

/**
* Created by Yaroslav Sokolov on 25.04.17.
*/
class ImagePanel(private val image: BufferedImage) : JPanel() {

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        g.drawImage(image, 0, 0, this)
    }
}