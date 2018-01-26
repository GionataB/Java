# 1024
This project originally was an assignment for Computer Science II.

The original project is divided in two parts: the back-end and the front-end of the software.

The "game1024" folder inside src contains the back-end code for the game, it manages the calculations and actions to make the game working. The game can be tested in its basic form through a text user interface.

The "gui1024" folder inside src contains the front-end code for the game, a graphical user interface that lets the user choose different options: resize the grid, change the time limit, create a new game, undo the previous move and a few other things.

The game is played on a grid, the standard rules: a 4x4 grid, starting with 2 numbers placed randomly on the grid. Using the arrow keys, the user can move everything on the grid to the right, the left, uppwards or downwards, however upon movement all the numbers will slide to the further empty cell in that direction, starting from the numbers closer to that direction's end-side in the grid, making it impossible for a cell to "surpass" and adjacent one. When there are two equal numbers, upon movement the program will merge them togheter, adding the values together. After a move, another number will be randomly placed on the grid. If played through the gui, the user wins the game if they are able to merge two cells and make the required number, 1024 by default. There are two ways to lose, either the grid becomes full and there is no room for moving, or the time runs out. 
