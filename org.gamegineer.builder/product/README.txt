Gamegineer
Version 0.3.0
DD MM YYYY

Copyright 2008-2010 Gamegineer contributors and others. 
All rights reserved. 


OVERVIEW

Gamegineer provides a virtual tabletop for playing traditional board and card games over the Internet.  The foundation of Gamegineer is the Equinox OSGi core framework, which provides other developers with opportunities to extend the Gamegineer tabletop to support new games and features not originally envisioned.

The current release implements a basic card table.  Future releases will add support for a generic table that can be used for various purposes.

For more information, visit the Gamegineer home page <http://gamegineer.sourceforge.net>.

This offering is based on technology from the Eclipse project <http://www.eclipse.org>.


LICENSE

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.

A copy of the GNU General Public License is contained in the file LICENSE.txt, sibling to this file, and is also available at <http://www.gnu.org/licenses/>.


USAGE

(Until a proper help system is implemented in the application, this file will serve as basic documentation for using Gamegineer.)

Gamegineer requires a Java 6 or later runtime to be installed.  To start Gamegineer, run the gamegineer.bat file, sibling to this file.  (Users of non-Windows operating systems should simply copy and execute the contents of this batch file.)

All commands are available from the main menu at the top of the screen.  Context-sensitive commands are available from a popup menu that is activated using the appropriate keyboard and/or mouse trigger of your operating system (e.g. right-clicking the mouse on Windows).

The table consists of a collection of card piles.  Use the Add Card Pile and Remove Card Pile commands to add/remove card piles to/from the table, respectively.  A card pile can be given the input focus by left-clicking it with the mouse.  A green border will appear around the pile to indicate it has the input focus.  Most commands will be executed in the context of the focused card pile.  Card piles can be moved by holding down the CONTROL key while left-clicking and dragging a card pile with the mouse.

A card pile consists of a collection of cards.  Use the Add Card and Remove Card commands to add/remove cards to/from a card pile, respectively.  Alternatively, the Add Deck command can be used to add an entire deck of cards to a card pile at once.  The Flip Card command will toggle the orientation (face up or face down) of the card at the top of the card pile.  All of these commands are executed in the context of the focused card pile.  Cards can be moved from one card pile to another by left-clicking and dragging a card with the mouse.
