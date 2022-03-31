<h1>Class: Game</h1>
public class Game
<h2>Members</h2>

 Type | Name | Description
------|------|-------------
List&lt;GameObject> |go| List for keeping track of moving GameObjects
List&lt;OffGridGO> |nongo| List for OffGridGOs, since they are not stored in the Grid
Map&lt;Item, Integer> |inventory| The player's inventory, modelled as a map of items to count of how many 
GameObject |player| Reference to the player's GameObject
GameGrid |gg| Object that models the game world. It contains an actual 'grid' of GameObjects
HitboxGrid |hbg| Object that keeps within it currently loaded Hitboxes
MainFrame |gameWindow| The window in which the game is displayed
DrawingPanel |gameScreen| Panel that sits within MainFrame, which everything is drawn to (this is how Swing works)
PauseMenu |menu| Implementation of a MenuSystem packaged in a single class to act as the game's Pause menu.
Keys |ctrl| Class that gets and stores information about player input
static TextboxWriter |txt| System for writing messages to the screen. Static so it can be accessed with reference to an object

<h2>Methods</h2>

<h3>Constructors</h3>
<h4>Game()</h4>
Initialises members, and the game as a whole. Loads level names from Levels directory so they can be used later, load the first level to the Grid.
The MainFrame is setup so it can display the game, and so user input is directed to appropriate controlling classes.
It is also used to hard-code loading objects into the game, to get around the limitations of the Loading system.

<h3>static void Main(String[] args)</h3>

Entry point to the program. Creates a Game object that represents (the current instance of) the game.  
This method holds the game loop;
The game is updated and is re-drawn to screen.
In order to keep the frame-rate consistent, The time the last frame took is used to find out how long to sleep the thread so that the target time is reached for the target framerate. The frame-rate display is also updated.

<h3>void update()</h3>
This method handles frame updates for the entire game.  
It also handles what to do based on the state of the Controller;  
- Switch between fullscreen and windowed
- What to do if paused (nothing)
- Advance text if a dialogue is open, or close it if the text is finished
- Check if movement or attacking is allowed, then process it if so.
 
Attacking;
- Get the attacking input
- Generate appropriate Hitboxes or OffGridGOs
- Add the generated objects to the respective list
- Update controller's cooldown

The update methods on each type of Object being kept track of.  
(Dynamic) GameObject:
- If the the GO is within 20 grid spaces of the player then update it
- Use GameGrid's move method to move GOs into the correct place
- If the GO is an enemy, handle its attacks
- If the GO is 'dead' then remove it from the list and the grid.

OffGridGOs:  
- Handle collisions with grid objects, for example if it is an item, it can be picked up by the player
- Remove if not 'alive'

Hitboxes:
- Update hitboxes based on player position
- For spreading hitboxes, find them in hitbox list
- get the hitboxes they have spawned and add them to the list

<h3>static &lt;T> void addItem(Map<T, Integer> map, T item)</h3>
Utility method used for adding items to inventory, but genericised.

| Argument | Description |
| --- | --- |
| map | the map to add to |
| item | the item (of any type) to add to the map |

Add item to map, increment the number or set it to 0 if new item.

<h3>static &lt;T> void removeItems(Map<T, Integer> map, T item, int number)</h3>
Utility method used for removing items from inventory, but genericised.

| Argument | Description |
| --- | --- |
| map | the map to add to |
| item | the item (of any type) to add to the map |
| number | the number to be added |

If the item exists, decrement its count. If the number goes below 1 then remove it from the map.