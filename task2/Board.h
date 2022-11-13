//
// Created by Julianne Vella on 13/11/2022.
//

#ifndef TASK2_BOARD_H
#define TASK2_BOARD_H
#define TOTAL_MINES 40
#define GRID_COLUMNS 16
#define GRID_ROWS 16

class Board {
public:
    // constructor initialises the randomly allocated board
    Board();
    // method to print the board
    void printBoard();
    // method to check if a cell is a mine
    bool isMine(int i, int j);
    //method to count number of mines
    int countMines(int i, int j);

    // attributes of the class
    static char gameBoard[GRID_ROWS][GRID_COLUMNS];
};


#endif //TASK2_BOARD_H
