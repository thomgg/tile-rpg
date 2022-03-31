<h1>Class: MenuBox</h1>

public class MenuBox  
implements Comparable

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
MenuBox |parent |the parent of this Box, null if root
TreeSet<MenuBox> |children |set of children Boxes
int |position |ordering among siblings
int |depth |how deep into the tree is this
String |text |the text to display to represent this
Rectangle |bounds |defines space to use when drawing
Image |image |the image to represent this when drawing
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>MenuBox(String text, Rectangle bounds)</h4>

Argument | Description
--- | ---
text |the text to display to represent this
bounds |defines space to use when drawing

Create new Set for children, initialise members.

<h3>void setBounds(Rectangle bounds)</h3>
Set the bounds of the menu. Needed when called to draw so that they are consistent between sub-levels.

Argument | Description
--- | ---
bounds |defines space to use when drawing
 
<h3>void addChild(MenuBox menuBox, int position)</h3>
Add a child to this menu.

Argument | Description
--- | ---
menuBox |the menu to add
position |the position to add it at (Optional)

The new item's parent is set as this menu, its depth is 1 higher than this.  
If no position is given it can be set to be after all others, otherwise it is as given, then all other positions have to be updated.  
Then add the child to the set.
 
<h3>void updatePositions(int starting_position)</h3>
Update positions so they are numbered correctly.

Argument | Description
--- | ---
starting_position |position to start at to skip unnecessary checks

Start at given position, iterate through remaining members of the set.  
If the next item has the same position as the current, increment it. This fixes positions while allowing non-consecutive positions to be used.
 
<h3>void setText(String text)</h3>

Argument | Description
--- | ---
text |the text to display to represent this

Set this menu's text.
 
<h3>int compareTo(Object o)</h3>
Overridden from super-class.  
  
One Box is less than another if:  
First its depth is higher (a lower number), then its parent's position is lower, then its position is lower.

<h3>String toString()</h3>
Overridden from super-class.  
  
For text-based menu. Result is the this Box's text, then a list of children's text on new lines.

<h3>void draw(Graphics g, MenuBox selected)</h3>
Draw the current menu.

Argument | Description
--- | ---
g |the graphics object to draw to
selected |the currently selected menu (which should be a child of this)

Calculate where to draw each line based on the size of the bounding rectangle.  
Write each option on a new line. The currently selected option has an indicator before it.

<h1>Class: MenuAction</h1>

public interface MenuAction

<h2>Methods</h2>
<h3>void action()</h3>
The action to perform when selected

<h3>MenuBox destination()</h3>
Returns: where to go when selected

<h1>Class: MenuOption</h1>

public abstract class MenuOption  
extends MenuBox implements MenuAction  

This class is required because it is not possible to implement an interface in an anonymous class.
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>MenuOption(String text)</h4>
Call super-class constructor with same arguments.

<h3>void action()</h3>
The action to perform when selected  

No action to be performed.

<h3>MenuBox destination()</h3>
Returns: where to go when selected  

Return null. i.e. do not move when this is selected.

<h1>Class: MenuGrid</h1>

public class MenuGrid  
extends MenuBox

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
int |gridWidth |number of columns of the grid
int |maxHeight |maximum number of grid rows

<h2>Methods</h2>
<h3>Constructors</h3>
<h4>MenuGrid(String text, Rectangle bounds, int cols, int maxRows)</h4>
 
Argument | Description
--- | ---
text |the text to display to represent this
bounds |defines space to use when drawing
cols |number of columns of the grid
maxRows |maximum number of grid rows (optional)

Initialise members to given values.

<h3>void draw(Graphics g, MenuBox selected)</h3>
Overridden from super-class.  
  
Calculate the size to draw grid squares based on the bounding rectangle.  
Draw each grid space in the correct place, with its image if it has one. If a slot is selected, draw an indicator.
 