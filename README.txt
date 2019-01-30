Hello! This is the classic Sudoku game, by Shaya Zarkesh.

This is built like the classic sudoku game. You are given a set of initial uncovered spots on a 9x9 grid.
 Your goal is to determine the numerical value (1-9) of the rest of the elements of the grid, based on the 
 following rules:
1) Every row must have the numbers 1 through 9 exactly once."
2) Every column must have the numbers 1 through 9 exactly once." +
3) Each mini-square must have the numbers 1 through 9 exactly once."

Input or delete numbers by clicking on the square you want to enter, then using the keyboard"
You can use the check button to see if what you've put in is right, but beware, it adds 10 seconds to the timer.
	        		
Here are the 4 components I have implemented.

2D Arrays: My game board is modeled with a 2-D 9-by-9 array. In the backend, it is a combination of arrays of
ints (one for the overall solution, one for the user-inputted values, and one for the uncovered cells to start).

Collections: A huge part of implementing sudoku is actually generating the levels. I wrote my own algorithm for 
solution generation, which involves constructing a level and checking if the board is valid at all times. The function
that checks if a given board is valid uses a TreeSet, since it is important to check for repeated elements in any 
row, column or 3x3 minigrid. The order of the elements in these pieces does not matter -- only their duplication --
so a TreeSet was the right collection to use.

File I/O: I have implemented high score with file I/O. When you win the game, the program asks you for your name,
and it writes your name, the date of score, and the time taken to complete into a file and sorts it among the other
scores. You can see the high scores at any time by clicking on the "High Scores" button in the main window.

Recursive Algorithm: I used recursive algorithms in creating my solution-bot. This bot was important in determining
whether a given start-state was solvable. The recursive algorithm works by putting in whatever cells it can solve,
from the initial state, and then calling itself given the partially solved start-state. Doing this over and over,
it sees if it can find a solution based on the basic algorithms.

---------------------------------------------

The overall game (view/controller) works in the "Game" class, whereas the "Board" class serves as the "model"
side -- it contains the 2D array with the current state of the board.

Keyboard entries are taken through the "Game" class and inputted into the board with the method "enter" from the
Board class.

Finally, the SudokuBoardLines class serves as the JPanel object with lines drawn on it, to allow for the
Sudoku design (2 vertical and 2 horizontal lines) within a grid layout.