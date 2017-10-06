# PictureViewer
This console-application is able to display files of any formats. This is achieved through the MVC architecture, which makes it easy to extend. However, only the BMP format is now supported.

Below is the UML diagram of the application.
![alt text](https://github.com/SokolovYaroslav/SPBU-practice.PictureViewer/blob/master/UML.png)

As you can see, you can easily add a new file format. You just need to create a new class that implements ImageMaker. Also here you can easily add a user interface by changing the class Viewer.
