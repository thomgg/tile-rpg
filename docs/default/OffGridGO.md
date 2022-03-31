<h1>Class: OffGridGO</h1>

public class OffGridGO  
extends GameObject

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
double |offX |offset from middle of grid position, as fraction of a grid square length. From -0.5 to 0.5 - in x direction
double |offY |same as above for y direction
double |velx |velocity in x direction
double |vely |velocity in y direction
Image |sprite |the sprite rendered to represent this object
boolean |airborne |is the object airborne (can go over things)
boolean |alive |is it alive
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>OffGridGO(int x, int y, double xoffset, double yoffset)</h4>
 
Argument | Description
--- | ---
x |x position
y |y position
xoffset |offset from middle of grid position on x axis
yoffset |offset from middle of grid position on y axis
 
<h3>void fixGridPos()</h3>
Fix position variables so that offsets don't go out of range.

For x and y offsets, if they are out of range, truncate them and change the grid position, then remove the integer part from the offset.

<h3>void offGridMove(double xmove, double ymove)</h3>
Move the object by changing the off-grid position

Argument | Description
--- | ---
xmove |how much to change off-grid x position
ymove |how much to change off-grid y position

Add given values to offsets, then fix the position back to grid.

<h3>OffGridGO setVelocity(double vx, double vy)</h3>
Returns: This object with the velocity set.

Argument | Description
--- | ---
velx |x velocity
vely |y velocity

Set the velocities to the given values.

<h3>void update()</h3>
Overridden from GameObject

Move the object by the amount given by velocities.

<h3>void paint(Graphics g)</h3>
Overridden from GameObject

If sprite is set draw the sprite.
Otherwise the default for OffGridGOs is a cyan rectangle smaller than grid square. 

<h1>Class: Item</h1>

class Item  
extends OffGridGO

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
String |name |The name of this item
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>See super-class constructors</h4>
 
<h3>Item getThisWithName(String name)</h3>
Returns: This object with name set.

Argument | Description
--- | ---
name |The name to set

Set the name as given value. Attempt to set sprite to one with same name.

<h3>boolean equals(Object obj)</h3>
Overridden from super-class.  
Returns: If this object is equal to another.

Argument | Description
--- | ---
obj |The object to compare to.

Two items are equal if they have the same name.

<h3>static Image grabImage(Item item)</h3>
Attempt to get sprite with same name as an item.

Argument | Description
--- | ---
item| The item with name to use

If the item has no name, return no Image.  
If there is not corresponding sprite, return the 'null item' sprite.  
Otherwise, can return the corresponding sprite.

<h1>Class: Key</h1>

class Key  
extends Item

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
Door |door |The door that this key opens
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>See super-class constructors</h4>

<h3>Key setDoor(Door door)</h3>
Returns: This object.
 
Argument | Description
--- | ---
door |The door that this key opens

Set the door that this key opens.

<h3>boolean equals(Object obj)</h3>
Overridden from super-class.  
Returns: If this object is equal to another.

Argument | Description
--- | ---
obj |The object to compare to.

Two items are equal if they open the same Door.

<h1>Class: Projectile</h1>

class Projectile  
extends OffGridGO

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
Hitbox |hitbox |A hitbox that is associated with this projectile
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>See super-class constructors</h4>
 
<h3>Projectile setHitbox(Hitbox hitbox)</h3>
Returns: This object.

Argument | Description
--- | ---
hitbox| The hitbox to set.

Sets the hitbox member to given object.

<h3>void update()</h3>
Overridden from super-class.

If the hitbox is no longer alive, neither should this object.  
Set the hitbox position to be the (integer) position of this object.

<h1>Class: Grenade</h1>

class Grenade  
extends Projectile

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
int |hangtime |how long it stays 'airborne' - in frames
int |detonator |how long until it 'explodes' - in frames
int |lifetime |how long it has been alive for - in frames
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>Grenade(int x, int y, double xoffset, double yoffset, double velx, double vely, int detonator_length)</h4>
 
 Argument | Description
 --- | ---
 
Initialise members to the given values. Airborne by default starts as true.
Hangtime is set to be the magnitude of the resultant vector of the velocities, and scaled up.

<h3>void update()</h3>
Overridden from super-class.

Calls super-class update.  
Increment lifetime counter. If its life has exceeded hangtime that it is set to no airborne.  
If it is not airborne, it is stopped from moving.  
If its life has exceeded the detonator time, then it is set to not be alive.