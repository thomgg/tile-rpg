


<h1>Class: Keys</h1>

public Class Keys  
extends KeyAdapter
<h2>Members</h2>

 Type | Name | Description
------|------|-------------
boolean |pause |is the game paused
boolean |text_pause |is the game paused because of a dialogue
boolean |close_text |signal to close text dialogue
boolean |switchFS |signal to switch between fullscreen and windowed
boolean |grid |is the grid displayed
boolean |debug |is debug mode on
boolean |interact |signal that player is attempting to interact
int |vert |vertical movement - positive for up, negative for down
int |hor |horizontal movement - positive for right, negative for left
int |xdir |facing direction in x-axis - positive for right, negative for left
int |ydir |facing direction in y-axis - positive for up, negative for down
long |time_of_last_movement |the time (in nanoseconds) that the player last moved
long |time_of_last_action |the time (in nanoseconds) that the player last did an action
long |action_lag |the duration during which the player cannot act
long[] |lag_table |the time until the player can act again for each action
int |input_buffer |store the key code of movement input if the player is already moving
int |action_buffer |store the key code of action input if the player is already acting
int |attack |the id of the attack
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>Keys()</h4>
Initialise members to default values. For times this is the current time.

<h3>void keyPressed(KeyEvent e)</h3>
Handle KeyEvents - when the user presses a key.

| Argument | Description |
| --- | --- |
|e |Information about keyboard input |

For actions, set the buffer to the action corresponding to the key. 
This is so that the action is executed when the key is released.  
For movement, if there is currently no movement input, then the corresponding 
direction can be set. Otherwise, set the movement buffer to the new key.  
For other keys, the corresponding flag can be set.

<h3>void keyReleased(KeyEvent e)</h3>
Handle KeyEvents - when the user releases a key.

| Argument | Description |
| --- | --- |
|e |Information about keyboard input |

If the key released is the one in the buffer, then clear the buffer.  
For movement, if the key released is the one corresponding the current movement, then revert to the default value.
Then check the buffer input.  
For attacks, the attack id is set, buffer is cleared and the lag is set.
For interaction command, if no dialogue is open, set flag to true, otherwise it is false.
If a dialogue is open, 'close_text' is set to true, which will either advance the text, or close the dialogue depending on the situation.

<h3>void checkBuffer()</h3>
Used to carry out buffer input when the current action's key is released.
  
If the buffer isn't empty and the buffer's input corresponds to movement, then set that movement.