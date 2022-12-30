#include <iostream>
#include "Board.h"
#include "Game.h"
using namespace std;
int main() {
    Game g;
    Board b;
    int *p;
    int x,y;

   g.displayWelcomeMessage();
   b.initBoard();
   b.printFullBoard(); //for testing purposes

   cout << "\n";


   do{
       p=g.enterCoords();
       x=p[0];
       y=p[1];

       if(!b.isMine(x,y)){
           b.displayBoard(x,y);
       }

       if(b.isMine(x,y)){
           cout << "GAME OVER - You have encountered a mine!" <<endl;
           b.printFullBoard();
       }

       // winning game output!

   }while(!b.isMine(x,y));

    return 0;
}
