#include "Board.h"

Board::Board() {

}

void Board::initBoard() {
    // Continue until all random mines have been created.
    for (int i = 0; i < GRID_ROWS; i++) {
        for (int j = 0; j < GRID_COLUMNS; j++) {
            gameBoard[i][j] = '-';
        }
    }

    // seeding at a random position
    srand(time(nullptr));

    for (int i = 0; i < TOTAL_MINES;) {
        int random = rand() % (GRID_ROWS * GRID_COLUMNS);
        int x = random / GRID_ROWS;
        int y = random % GRID_COLUMNS;
        bool mineMarks[GRID_ROWS * GRID_COLUMNS];
        memset(mineMarks, false, sizeof(mineMarks));

        // If no mine is present, add mine
        if (!mineMarks[random]) {
            mineLocations[i][0] = x;
            mineLocations[i][1] = y;

            // allocate mine in game board
            gameBoard[mineLocations[i][0]][mineLocations[i][1]] = 'X';
            mineMarks[random] = true;
            i++;
        }
    }

    for (int i = 0; i < 16; i++) {
        for (int j = 0; j < 16; j++) {
            if (!isMine(i, j)) {
                gameBoard[i][j] = '0' + mineCount(i, j);
            }
            clearedCells[i][j] = false;
        }
    }


}

void Board::printFullBoard() {
    int i, j;

    for (i = 0; i < 16; i++)
        cout << "\t" << i;

    cout << "\n";

    for (i = 0; i < 16; i++) {
        cout << i;
        for (j = 0; j < 16; j++)
            cout << "\t" << gameBoard[i][j];
        cout << "\n";
    }
}

void Board::displayBoard(int x, int y) {
    int i, j;

    //catering for edge cases
    if (!isMine(x, y)) {
        if (x == 0 && y == 0) {
            clearedCells[x][y] = true;
            clearedCells[x + 1][y] = true;
            clearedCells[x][y + 1] = true;
            clearedCells[x + 1][y + 1] = true;
        }
        if (x == 15 && y == 15) {
            clearedCells[x][y] = true;
            clearedCells[x - 1][y] = true;
            clearedCells[x - 1][y - 1] = true;
            clearedCells[x][y - 1] = true;
        }
        if (x == 15 && y == 0) {
            clearedCells[x][y] = true;
            clearedCells[x - 1][y] = true;
            clearedCells[x - 1][y + 1] = true;
            clearedCells[x][y + 1] = true;
        }
        if (y == 0) {
            clearedCells[x][y] = true;
            clearedCells[x - 1][y] = true;
            clearedCells[x - 1][y + 1] = true;
            clearedCells[x][y + 1] = true;
            clearedCells[x + 1][y] = true;
            clearedCells[x + 1][y + 1] = true;
        }if(x==0&&y==15) {
            clearedCells[x][y] = true;
            clearedCells[x][y - 1] = true;
            clearedCells[x + 1][y] = true;
            clearedCells[x + 1][y - 1] = true;
        }if(y==15){
            clearedCells[x][y] = true;
            clearedCells[x-1][y]=true;
            clearedCells[x+1][y]=true;
            clearedCells[x][y-1]=true;
            clearedCells[x-1][y-1] = true;
            clearedCells[x+1][y-1] = true;
        } else {
            clearedCells[x][y] = true;
            clearedCells[x + 1][y] = true;
            clearedCells[x][y + 1] = true;
            clearedCells[x - 1][y] = true;
            clearedCells[x][y - 1] = true;
            clearedCells[x + 1][y + 1] = true;
            clearedCells[x - 1][y - 1] = true;
            clearedCells[x + 1][y - 1] = true;
            clearedCells[x - 1][y + 1] = true;
        }
    }

    for (i = 0; i < 16; i++)
        cout << "\t" << i;

    cout << "\n";

    for (i = 0; i < 16; i++) {
        cout << i;
        for (j = 0; j < 16; j++) {
            if (clearedCells[i][j]) {
                cout << "\t" << gameBoard[i][j];
            } else {
                cout << "\t" << '-';
            }
        }

        cout << "\n";
    }
}

bool Board::isMine(int i, int j) {
    if (gameBoard[i][j] == 'X') {
        return true;
    } else {
        return false;
    }
}

int Board::mineCount(int i, int j) {
    int count = 0;
    if (isMine(i - 1, j)) {
        count++;
    }
    if (isMine(i + 1, j)) {
        count++;
    }
    if (isMine(i, j - 1)) {
        count++;
    }
    if (isMine(i, j + 1)) {
        count++;
    }
    if (isMine(i - 1, j + 1)) {
        count++;
    }
    if (isMine(i + 1, j + 1)) {
        count++;
    }
    if (isMine(i - 1, j - 1)) {
        count++;
    }

    if (isMine(i + 1, j - 1)) {
        count++;
    }

    /*
    Cell: Current Cell (row, col)
    N: North        (row-1, col)
    S: South        (row+1, col)
    E: East         (row, col+1)
    W: West         (row, col-1)

    NE: North-East   (row-1, col+1)
    NW: North-West   (row-1, col-1)
    SE: South-East   (row+1, col+1)
    SW: South-West   (row+1, col-1)
     */

    return count;
}

void Board::checkAllCleared(){
    int clearedCount=0;
    for (bool x: this->clearedCells){
        if(this->clearedCells[x]) {
            clearedCount++;
        }
    }

    if(clearedCount==256){
        cout << "Congratulations, you have won Minesweeper!" << endl;
    }

}