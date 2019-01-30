Hello! This is the classic Sudoku game, by Shaya Zarkesh.

This is built like the classic sudoku game. You are given a set of initial uncovered spots on a 9x9 grid.
 Your goal is to determine the numerical value (1-9) of the rest of the elements of the grid, based on the 
 following rules:
1) Every row must have the numbers 1 through 9 exactly once."
2) Every column must have the numbers 1 through 9 exactly once." +
3) Each mini-square must have the numbers 1 through 9 exactly once."

Input or delete numbers by clicking on the square you want to enter, then using the keyboard"
You can use the check button to see if what you've put in is right, but beware, it adds 10 seconds to the timer.


The overall game (view/controller) works in the "Game" class, whereas the "Board" class serves as the "model"
side -- it contains the 2D array with the current state of the board.

Keyboard entries are taken through the "Game" class and inputted into the board with the method "enter" from the
Board class.

Finally, the SudokuBoardLines class serves as the JPanel object with lines drawn on it, to allow for the
Sudoku design (2 vertical and 2 horizontal lines) within a grid layout.