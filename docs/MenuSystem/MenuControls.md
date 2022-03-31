<h1>Class: MenuControls</h1>

public class MenuControls  
extends KeyAdapter

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
public class MenuControls extends KeyAdapter {
int |key_code |the code of the last key pressed
long |time_of_last_action the time (in nanoseconds) that the user last changed the state of the menu
 
<h2>Methods</h2>
<h3>Constructors</h3>
Default constructor only.
 
<h3>void keyPressed(KeyEvent e)</h3>

Argument | Description
--- | ---
|e |Information about keyboard input |

Set key_code to be the code of the pressed key

<h3>void keyReleased(KeyEvent e)</h3>

Argument | Description
--- | ---
|e |Information about keyboard input |

If key_code is the same as the released key, then unset key_code (set tp 0)