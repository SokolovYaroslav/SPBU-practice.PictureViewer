package Controller

import Model.BMPMaker
import Model.ImageMaker
import View.Viewer
import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.nio.file.Path
import java.nio.file.Paths

/**
* Created by Yaroslav Sokolov on 26.04.17.
*/
class Controller(private val viewer: Viewer) {

	private var model: ImageMaker = BMPMaker()

	fun openFile(filePath: String) {
//        if (filePath.split(".").size != 2) {
//            println("Invalid file extension")
//        }
        val fileName = filePath.split("/").last()
        when (filePath.split(".").last()) {
            "bmp" -> {
                if (!(model is BMPMaker)) {
                    model = BMPMaker()
                }
            }
            else -> {
                println("Illegal file extension")
                return
                TODO("Realize other file formats")
            }
        }

        model.fileName = fileName
        val path: Path
        val byteArray: ByteArray
        try {
            path = Paths.get(filePath)
            byteArray = Files.readAllBytes(path)
        }
        catch (e: NoSuchFileException) {
            println("No such file")
            return
        }

		model.addObserver(viewer)
        try {
            model.makeImage(byteArray)
        }
        catch (e: IllegalArgumentException) {
            println(e.message)
            return
        }
	}
}