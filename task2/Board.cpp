#include "Board.h"
Board::Board() {

}
void Board::initBoard(){
    // Continue until all random mines have been created.
    for(int i=0;i<16;i++){
        for(int j=0;j<16;j++){
            gameBoard[i][j]='-';
        }
    }

    for (int i = 0; i < TOTAL_MINES;) {
        int random = rand() % (GRID_ROWS * GRID_COLUMNS);
        int x = random / GRID_ROWS;
        int y = random % GRID_COLUMNS;
        bool mark[GRID_ROWS * GRID_COLUMNS];
        memset(mark, false, sizeof(mark));

        // Add the mine if no mine is placed at this position
        if (mark[random] == false) {
            // mine row index
            mineLocations[i][0] = x;
            // column row index
            mineLocations[i][1] = y;

            // allocate mine in game board
            gameBoard[mineLocations[i][0]][mineLocations[i][1]] = 'X';
            mark[random] = true;
            i++;
        }
    }
}
void Board::printBoard() {
    int i, j;

    for (i=0; i<16; i++)
        cout<< "\t" << i;

    cout << "\n";

    for (i=0; i<16; i++)
    {
        cout << i;
        for (j=0; j<16; j++)
            cout << "\t" << gameBoard[i][j];
        cout << "\n";
    }
}

bool Board::isMine(int i, int j) {
    if(gameBoard[i][j]=='X'){
        return true;
    }else{
        return false;
    }
}

int Board::mineCount(int i, int j) {
    int count=0;
    if(isMine(i-1,j)){
        count++;
    }
    if(isMine(i+1,j)){
        count++;
    }
    if(isMine(i,j-1)){
        count++;
    }
    if(isMine(i,j+1)){
        count++;
    }
    if(isMine(i-1,j+1)){
        count++;
    }
    if(isMine(i+1,j+1)){
        count++;
    }
    if(isMine(i-1,j-1)){
        count++;
    }

    if(isMine(i+1,j-1)){
        count++;
    }

    /*
    Cell-->Current Cell (row, col)
    N -->  North        (row-1, col)
    S -->  South        (row+1, col)
    E -->  East         (row, col+1)
    W -->  West         (row, col-1)

    N.E--> North-East   (row-1, col+1)
    N.W--> North-West   (row-1, col-1)
    S.E--> South-East   (row+1, col+1)
    S.W--> South-West   (row+1, col-1)
     */

    //cater for edge cases?
    return count;
}

