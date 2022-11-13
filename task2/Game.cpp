//
// Created by Julianne Vella on 13/11/2022.
//

#include "Game.h"

Game::Game() {

}

void Game::playGame() {
    int x, y;
    displayWelcomeMessage();
    setUserBoard();
    printUserBoard();

    //while loop required until game over!

    //Accepting x and y co-ordinates of the cell to clear!
    cout << "Enter x co-ordinate" << endl;
    do {
        cin >> x;
    } while (!validateInput(x));

    cout << "Enter y co-ordinate" << endl;
    do {
        cin >> y;
    } while (!validateInput(y));



    //... continue with game logic
}

void Game::printUserBoard() {
    int i, j;

    for (i = 0; i < 16; i++)
        cout << "\t" << i;

    cout << "\n";

    for (i = 0; i < 16; i++) {
        cout << i;
        for (j = 0; j < 16; j++)
            cout << "\t" << userBoard[i][j];
        cout << "\n";
    }
}

void Game::setUserBoard() {
    for (int i = 0; i < 16; i++) {
        for (int j = 0; j < 16; j++) {
            userBoard[i][j] = '-';
        }
    }
}

bool Game::validateInput(int crd) {
    if (crd < 0 || crd > GRID_ROWS-1) {
        cout << "Enter a co-ordinate between 0 and 15\n";
        return false;
    } else {
        return true;
    }
}

void Game::displayWelcomeMessage(){
    cout << "Welcome to Minesweeper!" << endl;
    cout << "40 mines have been randomly allocated on a 16x16 grid." << endl;
    cout << "To play, enter x and y co-ordinates of a cell one at a time.\nYou need to clear the grid without hitting any mines!\n"
         << endl;
}