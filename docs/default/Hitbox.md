<h1>Class: HitboxGrid</h1>

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
int |width |width of the GameGrid this is associated with
int |height |height of the GameGrid
List&lt;Hitbox> |hb |list of hitboxes to store in
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>HitboxGrid(int width, int height)</h4>
 
 Argument | Description
 --- | ---
width |width of the GameGrid this is associated with
height |height of the GameGrid

Initialise members to given value, empty list.

<h3>boolean add(Hitbox newh)</h3>
Add a hitbox to the list.
Returns: If hitbox was successfully added.

Argument | Description
--- | ---
newh |A hit box to add to the list

If the position is outside of the grid then don't add.  
Check if the position already contains a hitbox. If no, add, if yes only add if the power is greater.
 
<h3>boolean add(int x, int y, int power, int lifetime, int xspeed, int yspeed)</h3>
Add a hitbox to the list.
Returns: If hitbox was successfully added.

Argument | Description
--- | ---
x |x position
y |y position
power |the stat used to determine damage output
lifetime |how long the hitbox is active for
xspeed |how fast the hitbox moves in x direction
yspeed |how fast the hitbox moves in y direction

Create a new hitbox with the given parameters.
If the position is outside of the grid then don't add.  
Check if the position already contains a hitbox. If no, add, if yes only add if the power is greater.

<h3>void update(int playerx, int playery)</h3>
Update hitboxes relative to player position.

Argument | Description
--- | ---
playerx |The player's x position
playery |The player's y position

Update every hitbox in the list, passing player x and y position.  
After updating, remove it if it has been set as inactive.

<h3>void paint(Graphics g, int sx, int sy, boolean debug)</h3>
Draw hitboxes to screen.

Argument | Description
--- | ---
g |Graphics object from Frame
sx |screen x - the x position of the game view
sy |screen y - the x position of the game view
debug |whether debug mode is enabled

If it has a sprite set, draw each hitbox at the right place on screen.  
If debug mode is enabled, draw a translucent square to represent the hitbox.

<h1>Class: Hitbox</h1>

class Hitbox  
implements Comparable<main>\<Hitbox></main>

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
int |x |x position
int |y |y position
int |age |time since creation in frames
int |lifetime |how long to live in frames
double |power |the stat used to determine damage output
double |xspeed |how many frames to update position in x direction
double |yspeed |how many frames to update position in y direction
boolean |active |is this hitbox active
boolean |multihit |does this hitbox stay active if it hits something
Chara.char_class |owner_class |which class does the creator of this hitbox belong to
Image |sprite |sprite to draw
double |sprite_rotation |rotate sprite by this angle (in degrees)
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>Hitbox(int x, int y, double power, int lifetime, double xspeed, double yspeed, Chara.char_class owner)</h4>
 
Argument | Description
--- | ---
x |x position
y |y position
age |time since creation in frames
lifetime |how long to live in frames
power |the stat used to determine damage output
xspeed |how many frames to update position in x direction
yspeed |how many frames to update position in y direction
active |is this hitbox active
multihit |does this hitbox stay active if it hits something
owner_class |which class does the creator of this hitbox belong to

Initialise members to given values. Age is set to 0.

<h3>int compareTo(Hitbox other)</h3>
Defines such that Hitboxes are sorted by their position, by x first. Returns positive if larger, negative if smaller, 0 if equal.

Argument | Description
--- | ---
other |The hitbox to compare this one to

Compare x, y, age in that order. If different return this - other.  
If 'this' should come first it is 'less' - further top left or younger.

<h3>void update(int playerx, int playery)</h3>
Update relative to player position.

Argument | Description
--- | ---
playerx |The player's x position
playery |The player's y position

Increment age. Move by one in appropriate direction(s) if it is the right time. 

<h3>void used</h3>
If this is not a multihit hitbox then set as inactive.

<h1>Class: SpreadHitbox</h1>
class SpreadHitbox  
extends Hitbox

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
int |spread |the radius of the spread - as number of squares from centre
int |dimFac |percentage of power kept when spreading
int |iteration |time since creation in frames
boolean |square |is the spread in square pattern
boolean |dim |should power be diminished when spreading
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>SpreadHitbox(int x, int y, double power, int lifetime, Chara.char_class owner,
                 int spread_radius, boolean square_spread, boolean diminish_power, int diminish_factor)</h4>

Argument | Description
--- | ---
x |x position
y |y position
power |the stat used to determine damage output
lifetime |how long to live in frames
owner |which class does the creator of this hitbox belong to
spread_radius |the radius of the spread - as number of squares from centre
square_spread |is the spread in square pattern
diminish_power |does this hitbox stay active if it hits something
diminsh_factor |should power be diminished when spreading

Initialise members with given values.  
If radius given is less than 1, set to 1.  
If told not to diminish power, or the given factor is outside range 0-100 the set it to 100 (no diminishing)

<h3>List&lt;Hitbox> spread()</h3>
Process which squares to spread to.  
Returns: List of new hitboxes - the result of the spread.

Only spread if the desired radius has not yet been reached.  
Calculate diminished power - take percentage of base power.  
Trace square around centre, creating a new hitbox at each place.  
Increment iteration when completed.