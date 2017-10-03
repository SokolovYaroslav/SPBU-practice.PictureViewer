package View

import Controller.Controller
import Model.ImageMaker
import java.util.*
import javax.swing.JFrame

class Viewer : Observer {

	private val controller = Controller(this)
	private val frame = JFrame()
	private val drawer = Drawer(frame)

	init {
	    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
	}

	/*By agreement, there 'o' can't be NULL*/
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