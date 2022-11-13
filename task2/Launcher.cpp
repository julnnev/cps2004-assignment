#include <iostream>
#include "Board.h"
int main() {
    std::cout << "Welcome to Minesweeper!" << std::endl;
    Board b;
    b.initBoard();
    b.printBoard();
    return 0;
}
