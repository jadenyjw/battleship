#Algorithm for A.I board placement
Define a 10x10 boolean array with x and y coordinates.
Define the aircraft carrier, battleship, patrol boat, submarine, and destroyer (5, 4, 2, 3, 3)
Go through the ships in order from largest to smallest:
{
Pick 2 random numbers and use that as the coordinates for ship. If it's not valid, try again.
Find valid orientations If it's not valid (conflicts with board edges or another ship), repeat until it is.
Pick a random orientation from the random ones (up,down,left,right)
}

#Algorithm for Human board placement
Define a 10x10 boolean array with x and y coordinates.
Define another 10X10 array record shots.
Define the aircraft carrier, battleship, cruiser, submarine, and destroyer
User drag and drops onto grid until all are ships are gone.
Right Click to change its orientation. 
Deny bad placements.

#Game vs. Human Algorithm
Repeat until someone loses:
{
17 valid pegs.
One person makes clicks on a grid. If it's a hit, reduce the user's valid pegs left. 
Mark the shot's place on the shot counting array
Check if the player has won (0 pegs left). If not, other player's move.
}

#Game vs. AI Algorithm
Repeat until someone loses:
{
Refer to Game vs. Human Algorithm
A.I turn:
Define a last hit coordinate.
If there was no previous last hit coordinate, pick a random coordinate as a shot. 
If there was a previous last hit, pick a random valid adjacent block.
Display to user where and whether it hits or not.
If it hits, set the last hit coordinate to the hit coordinate.
}

#Network:
TODO

#Graphics:
TODO

Example: http://en.battleship-game.org/

