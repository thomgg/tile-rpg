<h1>Class: GameObject</h1>
public class GameObject

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
int |x |x position in grid
int |y |y position in grid. note: top is 0, so +ve y is down
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>GameObject()</h4>
Initialise x and y to default value (0)

<h4>GameObject(int x, int y)</h4>

 Argument | Description
 --- | ---
 x |x position in grid
 y |y position in grid
 
 Initialise x and y to given values.
 
<h3>void move(int h, int v)</h3>

Argument | Description
--- | ---
h |horizontal move
v |vertical move, where +ve is up, -ve is down

<h3>void paint(Graphics g)</h3>
Default paint method if extensions don't have their own.  
Draws a blue square in that fills the grid space.

<h3>void hit(Hitbox hb)</h3>
What to do when hit by a hitbox.

Argument | Description
--- | ---
hb | The hithbox that this GameObject has been hit by.

Default GameObjects shouldn't react to being hit.

<h3>&lt;GOT extends GameObject> boolean collide(GOT other)</h3>
Define how to collide with other GameObjects  
Returns: Can this object pass through the other

Argument | Description
--- | ---
other |The GameObject this object is colliding with

Never allow this object to tbe passed over.

<h3>void update()</h3>
Update this GameObject by a frame.

By default, do nothing.

<h3>static double dist(GameObject a, GameObject b)</h3>
Calculate distance between two GameObjects

Argument | Description
--- | ---
a |First GameObject
b |Second GameObject

square root of \[(x2 - x1)^2 + (y2 - y1)^2]

<h1>Class: Wall</h1>

class Wall  
extends GameObject

<h2>Members</h2>

Type | Name | Description
-----|------|-------------
boolean |solid |Can hitboxes pass through it or not

<h2>Methods</h2>
<h3>void paint(Graphics g)</h3>
Render a wall with the wall sprite.

<h3>void hit(Hitbox hb)</h3>
Hitbox is used if the Wall is 'solid'

<h1>Class: Chara</h1>

class Chara  
extends GameObject

<h2>Members</h2>

Type | Name | Description
-----|------|-------------
enum |char_class |which classes are there: (none, player or enemy)
char_class |cclass |Which class does this belong to.
int |max_hp |the maximum hp the Chara can have
int |atk |the attack stat, used to calculate damage dealt
int |def |the defense stat, used to calculate damage taken
double |hp |the amount of health currently
boolean |dead |is the Chara dead?
Image |sprite |the sprite to render
String |prefab |the name of the prefab used to make this object
Map<Item, Integer> |inventory |Inventory of items, and number of them.
static Map<String, Chara> |prefabs |Map of Name->Prefab

<h2>Methods</h2>
<h3>Constructors</h3>
<h4>Chara(int x, int y, char_class cclass, int max_hp, int atk, int def, Image sprite)</h4>

Argument | Description
--- | ---
x |x position in grid
y |y position in grid
cclass |Which class does this belong to.
max_hp |the maximum hp the Chara can have
atk |the attack stat, used to calculate damage dealt
def |the defense stat, used to calculate damage taken
sprite |the sprite to render

Initialise members to given values.

<h3>void setInventory(Map<Item, Integer> inventory)</h3>

Argument | Description
--- | ---
inventory |Inventory of items, and number of them.

Set inventory member to be the given Map.

<h3>static Chara newChara(Chara prefab, int x, int y)</h3>
Make a new object from a prefab
Return value: a new Chara object as defined by the given Chara

Argument | Description
--- | ---
prefab |Object to copy values from
x |x position
y |y position

Create a new Chara with the parameters of the given one. Also intialise the Controller.

<h3>void update()</h3>
Overridden from GameObject

If HP is 0 (or less) then the Chara is dead.  
Update the controller.

<h3>void hit(Hitbox hb)</h3>

If the char_class is the same as the hitbox's owner then don't hit.  
Calculate damage and take damage from health.  
Then the hitbox is 'used'

<h1>Class: Sign</h1>

class Sign  
extends Wall

<h2>Members</h2>

Type | Name | Description
-----|------|-------------
String |text |The text 'written on' the sign

<h2>Methods</h2>
<h3>Constructors</h3>
<h4>Sign(int x, int y, String text)</h4>

Argument | Description
--- | ---
x |x position in grid
y |y position in grid
text |The text 'written on' the sign

<h1>Class: Door</h1>

<h2>Methods</h2>
<h3>Constructors</h3>
<h4>Door(int x, int y)</h4>
Pass to GameObject constructor.

<h3>&lt;GOT extends GameObject> boolean collide(GOT other)</h3>
Overridden from super class.

Only let another object through if it holds a key that can open this Door.

<h1>Other Classes</h1>
Water, WindowWall extends Wall - not solid

All extensions are rendered with their own sprite.
