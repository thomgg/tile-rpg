<h1>Class: PauseMenu</h1>

public class PauseMenu  
extends MenuContainer

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
Game |game |reference to the current Game object
boolean |enabled |is the menu open
InvMenuGrid |inv |MenuGrid representing the Game's inventory

<h2>Methods</h2>
<h3>Constructors</h3>
<h4>PauseMenu()</h4>

Initialise the container with title of "Pause".  
Adds the pause menu's main options, by creating anonymous class extensions of MenuOption.
 
<h3>void setGame(Game game)</h3>
Set the reference to the Game

Argument | Description
--- | ---
game| Reference to the current Game.

Also sets InvMenuGrid's reference to the inventory.
 
<h1>Class: InvMenuGrid</h1>

class InvMenuGrid  
extends MenuGrid

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
Map<Item, Integer> |inv |reference to the Game inventory
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>InvMenuGrid(int start_size)</h4>

Argument | Description
--- | ---
start_size |the starting amount of spaces in the inventory.

Initialise inventory menu by creating a 5 by 5 grid and adding the start_size amount of slots.
 
<h3>setInv(Map<Item, Integer> inventory)</h3>
Set the reference to the Game's inventory.

Argument | Description
--- | ---
inventory |inventory reference from the Game

<h3>void addItem(Item item)</h3>
Given an item, add a slot in the inventory.

Argument | Description
--- | ---
item |the Item to add

If there is an empty slot, add the item to that slot. If there are no empty slots, then add a new one.

<h1>Class: InvSlot</h1>

class InvSlot  
extends MenuBox implements MenuAction

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
Item |item |the item that this slot holds
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>InvSlot(Item item)</h4>

Argument | Description
--- | ---
item |the item to hold

Set the item, this object's text to be the same as the item's name and the image to be the item's image.
 
<h3>void setItem(Item item)</h3>

Argument | Description
--- | ---
item |the item to hold

Set the item, this object's text to be the same as the item's name, and the image to be the item's image.

<h3>void action()</h3>
Overridden from super-class.

No action to be performed.

<h3>MenuBox destination()</h3>
Returns: the sub-menu to navigate to when selected.

If there is no item, do not navigate. Otherwise navigate to this menu.
 