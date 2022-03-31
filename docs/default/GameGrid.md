<h1>Class: GameGrid</h1>

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
GameObject[][] |grid |the grid that holds GameObjects (2D array). Index represents position.
List<GameObject> |list |list of (dynamic, static objects not required) GameObjects in the grid. Used for quicker access.
int |width |the width of the grid
int |height |the height of the grid
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>GameGrid(int width, int height)</h4>
 
Argument | Description |
--- | --- |
width |the width of the grid
height |the height of the grid

Save the width and height of the grid and create a new grid with these dimensions. 
 
<h3>boolean move(GameObject go, int hor_move, int vert_move)</h3>
Move an object in the grid by the required amount.
Returns true if move is completed, false if it cannot be.

Argument | Description
--- | ---
go |the GameObject to move
hor_move |the amount to move horizontally
vert_move |the amount to move vertically

If the move would put the object outside of the grid or the new position is the same as the old one, then stop.  
If the new position is empty or the GameObject collison returns true, then update the grid and the object.

 