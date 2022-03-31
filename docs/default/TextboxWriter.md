<h1>Class: TextboxWriter</h1>

<h2>Members</h2>

 Type | Name | Description
------|------|-------------
String |fullText |the whole text to write
double |update_speed |how quickly the text advances. char per frame.
double |char_pointer |the current char to write up to
boolean |end |has pointer reached the end of the text
 
<h2>Methods</h2>
<h3>Constructors</h3>
<h4>Default constructor only</h4>
 
<h3>void setText(String text)</h3>

Argument | Description
--- | ---
text |the text to be written

Set full text, set pointer to start, end is false.

<h3>void update()</h3>

Advance the pointer by the amount of update_speed. If reached the end set end to true.

<h3>void draw(Graphics g)</h3>

Argument | Description
--- | ---
g| Graphics object to draw to

Get the text up to the pointer's position, then split it into lines.  
For each line, write the line's text, then move down.  
Note that there is no wrapping of text, line breaks are only where newline character appears in text.