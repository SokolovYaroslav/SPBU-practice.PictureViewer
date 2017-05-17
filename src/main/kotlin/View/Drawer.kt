package View

import Model.ImageMaker
import javax.swing.JFrame

/**
* Created by Yaroslav Sokolov on 15.05.17.
*/
class Drawer(private val frame: JFrame) {
    fun draw(model: ImageMaker) {
        val panel = ImagePanel(model.image)
        panel.setSize(model.width, model.height)

        frame.setBounds(100, 100, model.width, model.height)
        frame.contentPane = panel
    }

}