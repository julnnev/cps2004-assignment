#include <cstdlib>
#include <cstring>
#include <iostream>
#ifndef TASK2_BOARD_H
#define TASK2_BOARD_H
#define TOTAL_MINES 40
#define GRID_COLUMNS 16
#define GRID_ROWS 16
using namespace std;

class Board {
public:
    // constructor
    Board();
    //initialising random locations
    void initBoard();
    // method to print the board
    void printBoard();
    // method to check if a cell is a mine
    bool isMine(int i, int j);
    //method to count number of mines
    int countMines(int i, int j);

    // attributes of the class
     char gameBoard[GRID_ROWS][GRID_COLUMNS];
     int mineLocations[TOTAL_MINES][2];
};


#endif //TASK2_BOARD_H
