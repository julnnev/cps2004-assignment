//
// Created by Julianne Vella on 13/11/2022.
//

#include "Game.h"

Game::Game() {

}

int* Game::enterCoords() {
    int x, y;
    int coords[2];
    bool validXType, validYType;

    //Accepting x and y co-ordinates of the cell to clear
    cout << "Enter x co-ordinate: " << endl;
    do {
        cin >> x;
        validXType=true;
        if(cin.fail()){
            cout << "Enter an integer value!" << endl;
            cin.clear();
            cin.ignore(80,'\n');
            validXType=false;
        }
    } while (!validateInputRange(x) || !validXType);

    cout << "Enter y co-ordinate: " << endl;
    do {
        cin >> y;
        validYType=true;
        if(cin.fail()){
            cout << "Enter an integer value!" << endl;
            cin.clear();
            cin.ignore(80,'\n');
            validYType=false;
        }
    } while (!validateInputRange(y) || !validYType);

    coords[0]=x;
    coords[1]=y;
    return coords;
}

bool Game::validateInputRange(int crd) {
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