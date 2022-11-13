//
// Created by Julianne Vella on 13/11/2022.
//

#ifndef TASK2_BOARD_H
#define TASK2_BOARD_H


class Board {
public:
    // constructor initialises the randomly allocated board
    Board();
    // method to print the board
    void printBoard();
    // method to check if a cell is a mine
    bool isMine(int i, int j);
    static char grid[16][16];
    int countMines(int i, int j);
    // attributes of the class
};


#endif //TASK2_BOARD_H
