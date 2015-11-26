#Algorithm for A.I board placement
1. Define a 10x10 boolean array with x and y coordinates.
2. Define the aircraft carrier, battleship, cruiser, submarine, and destroyer (5, 4, 2, 3, 3)
3. Repeat until all ships are placed:
{
1. Pick 2 random numbers and use that as the coordinates for ship. If it's not valid, try again.
2. Pick a random orientation (up,down,left,right)
3. Pick a random ship out of the predefined ones.
4. Randomly determine an orientation and place it on. If it's not valid (conflicts with board edges or another ship), repeat until it is.
}

#Algorithm for Human board placement
1. Define a 10x10 boolean array with x and y coordinates.
2. Define the aircraft carrier, battleship, cruiser, submarine, and destroyer
TODO

#Game vs. Human Algorithm
1. Repeat until someone loses:
{
1. 17 valid pegs.
2. One person makes clicks on a grid. If it's a hit, reduce the user's valid pegs left and turn cell into red, if not, turn cell into 3. white. 
4. Check if someone has lost (0 pegs left). If not, other player's move.
}

#Game vs. AI Algorithm
1. Repeat until someone loses:
{
1. Refer to Game vs. Human Algorithm
A.I turn:
1. Define a last hit coordinate.
2. If there is no previous last hit coordinate, pick a random coordinate as a shot. If there is a previous last hit, pick a random adjacent block.
3. Display to user where and whether it hits or not.
4. If it hits, set the last hit coordinate to the hit coordinate.
}

#Network
TODO

Example: http://en.battleship-game.org/
