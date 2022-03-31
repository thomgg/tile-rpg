<h1>Class: LevelLoader</h1>
public class LevelLoader

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
static Map<String, String> |levelNames |maps Name to file name
static final String |EXTENSION |file extension for level files
static String |directory |the directory to look in for levels
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>LevelLoader has no constructors</h4>
 
<h3>loadLevelNames(String[] names, String[] filenames)</h3>

Argument | Description
--- | ---
names |names of levels
filenames |the file names of the level files

Initialise the levelNames map. For each pair of given names and filenames, add them to the map.
 
<h3>static void loadLevelNames()</h3>

Initialise empty levelNames map.

<h3>static GameGrid loadLevel(String levelName)</h3>
Returns: A GameGrid that contains the loaded level.

Argument | Description
--- | ---
levelName |the name of the level to load

Read the length and width from the file and make a new grid with these dimensions.  
For every character in the file, attempt to find the corresponding GameObject using <i>getGameObject</i> and place it in the grid.  
After the grid is specification of other objects. Parse these lines and produce a GameObject with the specified parameters. Add them to the GameObject list.

<h3>static void saveLevel(GameGrid game, String levelName)</h3>

Argument | Description
--- | ---
game |GameGrid to get data from
levelName |Name of level to save to

Iterate through grid writing appropriate character using <i>getChar</i> to the file.  
Write other objects to the file using their String representation.

<h3>static char getChar(GameObject go)</h3>
Returns: Corresponding character for the GameObject's type.

Argument | Description
--- | ---
go |GameObject with which to find a character

Defines character representation of types of GameObject. For example, Walls are 'x', Empty space is '.'.  
Must be consistent with <i>getGameObject</i>

<h3>static GameObject getGameObject(int x, int y, char c)</h3>
Returns: A GameObject of the correct type.

Argument | Description
--- | ---
x |x position in grid
y |y position in grid
c |the character from the file

Defines how to choose a GameObject type from a character. For example, 'x' makes a wall, '.' makes empty space.  
Must be consistent with <i>getChar</i>

<h3>static String loadText(String filename)</h3>
Returns: the file text as a String

Argument | Description
--- | ---
filename |the filename to read from

Read each line from the file and append it to a String(Builder).
 