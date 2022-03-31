<h1>Class: Controller</h1>
public class Controller

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
static GameObject |playerref |Reference to the current game's player
GameObject |me |reference to the GameObject that the Controller is controlling
int |xmove |the next move in x-direction
int |ymove |the next move in y-direction 
int |xdir |facing direction on x-axis
int |ydir |facing direction on y-axis 
int |move_lag |time to wait between moves (frames)
int |lag_timer |count how long since last move (frames)
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>Controller()</h4>
Initialise members to default value (0, apart from move_lag, which by default is 30)
 
<h3>void setGameObjectRef(GameObject gameObject)</h3>
Set the reference of the GameObject to be controlled by the controller.

| Argument | Description |
| --- | --- |
|gameObject |The GameObject to be controlled by the controller |
 
<h3>void update()</h3>
This class is not intended to be used, but extended. As such, this update method does nothing. 
 
<h1>Class: VBAIController</h1>
class VBAIController  
extends Controller

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
Random |random |For generating random numbers - to pick actions
boolean |moving |Am I already in motion
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>VBAIController()</h4>
Call super-constructor. Pick random x direction, then if x direction was 0, pick a random y direction (that is not 0).
 
<h3>void update()</h3>
Overridden from Controller.  
Update the state of the Controller.

Randomly decide to switch between moving or not.  
Randomly pick a direction to face.  
If in moving state and enough time has elapsed then move in the facing direction (by 1)
 
 
<h1>Class: BasicEnemyController</h1>

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
boolean |enemy_spotted |Whether the player is visible or not
Hitbox |myHB |Store a hitbox so it can be accessed from Game
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>BasicEnemyController(GameObject gameObject)</h4>

 Type | Name | Description
------|------|-------------
GameObject |me |reference to the GameObject that the Controller is controlling

Call default super-constructor. Initialise GameObject reference. Set move_lag to 60 frames.
 
<h3>void update()</h3>
Overridden from Controller.  
Update the state of the Controller.

Compute if the player is 'visible' by finding if the distance to the player is small enough.  
If enough time has elapsed since last action:  
Try to set the next move to move towards the player. If the player is adjacent then don't move.  
In that case, attempt to attack the player.  
If the player is not visible call the super-class's method - i.e. move randomly.