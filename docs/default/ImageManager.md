<h1>Class: ImageManager</h1>
public class ImageManager

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
static String |path |the directory to look in
static String |ext |the file extension of images
static Map&lt;String, Image> |images |Map that associates a name with each image
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>There are no constructors.</h4>
 
<h3>static Image getImage(String s)</h3>
Returns: the Image with the name given

Argument | Description
--- | ---
s |the name of the image to get
 
<h3>static Image loadImage(String fname)</h3>
Returns: the loaded image

Argument | Description
--- | ---
fname |the filename of the image to load

Load the image from the file. Add it to the map with its filename as the name. Return the Image.
 
<h3>static void loadImages(String[] fNames)</h3>

Argument | Description
--- | ---
fNames |Array of names of files

Load each of the images from the given list of filenames
 
<h3>static void loadImagesFromDirectory()</h3>

Load every image file with the default extension in the default directory.
