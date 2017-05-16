package View

import Controller.Controller
import Model.ImageMaker
import java.util.*
import javax.swing.JFrame

/**
* Created by Yaroslav Sokolov on 25.04.17.
*/
class Viewer() : Observer {

	private val controller = Controller(this)
	private val frame = JFrame()
	private val drawer = Drawer(frame)

	init {
	    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
	}

	override fun update(o: Observable?, arg: Any?) {
		o as ImageMaker

		frame.title = o.fileName
		drawer.draw(o)
		frame.isVisible = true
	}

	fun drawImage(filePath: String) {
		controller.openFile(filePath)
	}
}