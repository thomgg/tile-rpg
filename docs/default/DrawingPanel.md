<h1>Class: MainFrame</h1>

class MainFrame  
extends JFrame

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
 boolean |fullscreen |in fullscreen mode or not?
 

<h2>Methods</h2>
<h3>Constructors</h3>
<h4>MainFrame()</h4>
Set default parameters (size, close operation etc). Set fullscreen if fullscreen is enabled.

<h1>Class: DrawingPanel</h1>

class DrawingPanel  
extends JPanel

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
Game |game |reference to the game's Game object
double |fps |the framerate that the game is currently running at

<h2>Methods</h2>
<h3>Constructors</h3>
<h4>DrawingPanel(Game g)</h4>

| Argument | Description |
| --- | --- |
| g | reference to the game's Game object. Should be passed from the game object itself |

Initialise Game parameter.

<h3>void paintComponent(Graphics g)</h3>
Overridden from JPanel  
  
Draw the game scene to the screen. The scene is a 13x13 grid. The order of events described below is equivalent to the order in which things are rendered.

| Argument | Description |
| --- | --- |
| g | graphics object which the drawing is applied to. Should be from a JFrame |

1. Initialisation  
Use the screen's width and height to determine the scale at which to draw the image.
Find the position of the top-left of the game view such that it is centred in the window, and translate g to this position.
Store this as the default position.

2. Screen Scroll  
Compute the position the game view should be in so that the player is in the middle of the grid. 
Unless the player is at the edge of the screen, then keep the edge of the game grid aligned with the edge of the view.

3. Rendering the game
Iterate through every grid square that is on-screen.
First draw a 'background' tile so that tiles that have transparency have a background.
Then draw the current GO.  
Drawing non-grid objects: 
Draw each OffGridGO that is on-screen, making sure it is in its exact position and rotation.  
Hitboxes are also drawn if they have their own sprite (this is handled by the Hitbox system).

4. UI  
    - If enabled, every hitbox is visible with a red square, overlaid if it already has a sprite.
    - If enabled, grid squares are drawn
    - Draw the player health bar
    - If debug enabled:
        - draw FPS display
        - draw player coordinates
        - draw screen coordinates
        - draw the Input and Action buffers (see Keys for detailed explanation of buffers)
        - draw the direction the player is facing (as an x and y direction)
    - If message dialogue is open
        - Draw a box on screen for the dialogue
        - Draw the appropriate text on the box
    - If the menu is open
        - Draw a box for the menu background
        - Draw the menu   
     