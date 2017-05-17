import View.Viewer

/**
* Created by Yaroslav Sokolov on 25.04.17.
*/
fun main(args: Array<String>) {
    val viewer = Viewer()

    if (args.size != 1) {
        println("Invalid amount of args")
    }
    viewer.drawImage(args[0])
}