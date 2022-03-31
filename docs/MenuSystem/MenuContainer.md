<h1>Class: MenuContainer</h1>
class MenuContainer

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
MenuBox |root |the starting menu item
MenuBox |current_box |the current menu item
MenuBox |selection |the currently selected item in the current menu
MenuControls |ctrl |keyboard input for controlling in menus
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>MenuContainer(MenuBox root_menu)</h4>

Argument | Description
--- | ---
root_menu |the menu to set as the root

Set the root to given object. It becomes the current_box also. Selection becomes the first child of this object.

<h3>void addChild(MenuBox child)</h3>
Add a child to the root.

Argument | Description
--- | ---
child |the item to add to the root menu

Add the item to the root menu. Set the child's parent reference to be the root.  
If the root is the current menu and selection is unset then the item becomes the selection.
 
<h3>void navigate(MenuBox to)</h3>
Go to the given menu.

Argument | Description
--- | ---
to| The menu to go to

Set the current active menu to the given item, and set the current selection to be its first child.  
If the given item is null, then don't change.
 
<h3>void setRootBounds(Rectangle bounds)</h3>

Argument | Description
--- | ---
bounds |The dimensions that the menu should take

Set the bounds of the root menu to the give values.

<h3>void update()</h3>
Update the menu for the next frame, processing user input.  

Only process input if enough time has passed since last input.  
By default, pressing up goes to previous item, down goes to next.  
For MenuGrids, pressing goes up one row, down goes down, left goes to previous and right goes to next.  
Shift to go up a level, enter to go down a level to the selected item - unless the selected item implements MenuAction then execute its action 
 
<h3>void draw(Graphics g)</h3>
Draws the whole menu.

Argument | Description
--- | ---
g |graphics object to draw the menu to

Draw a translucent rectangle to contain the menu, then call the current menu's draw method.

