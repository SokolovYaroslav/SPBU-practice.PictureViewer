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

	private lateinit var model: ImageMaker

	public fun openFile(filePath: String) {
//        if (filePath.split(".").size != 2) {
//            println("Invalid file extension")
//        }

        when (filePath.split(".").last()) {
            "bmp" -> model = BMPMaker(filePath.split("/").last())
            else -> {
                println("Illegal file extension")
                return
                TODO("Realize other file formats")
            }
        }

        val path: Path
        val byteList: List<Byte>
        try {
            path = Paths.get(filePath)
            byteList = Files.readAllBytes(path).toList()
        }
        catch (e: NoSuchFileException) {
            println("No such file")
            return
        }

		model.addObserver(viewer)
        try {
            model.makeImage(byteList)
        }
        catch (e: IllegalArgumentException) {
            println(e.message)
            return
        }
	}
}