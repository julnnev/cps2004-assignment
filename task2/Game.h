//
// Created by Julianne Vella on 13/11/2022.
//

#ifndef TASK2_GAME_H
#define TASK2_GAME_H
#include <iostream>
#include "Board.h"
using namespace std;


class Game{
public:
    Game();
    //play minesweeper method
    void playGame();
    void setUserBoard();
    void printUserBoard();
    bool validateInput(int crd);
    void displayWelcomeMessage();

    char userBoard[GRID_ROWS][GRID_COLUMNS];
};


#endif //TASK2_GAME_H
