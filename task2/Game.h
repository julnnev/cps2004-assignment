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
    int* enterCoords();
    bool validateInputRange(int crd);
    void displayWelcomeMessage();
};


#endif //TASK2_GAME_H
