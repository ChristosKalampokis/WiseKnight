package com.example.mychess;

import java.util.*;

//This is the class that calculates the movements of the knight
//The class requires the original and final positions (in our case given by the user by touching the screen) as well as the boardSize
//It calculates the legal movements a knight can make between those two positions and returns those that are within the 3 movement limit
public class KnightMove {

    ArrayList<chessSquare> knightMoves = new ArrayList<chessSquare>();


    public KnightMove(int boardSize, int originalPosition[], int destination[]) {


        calculateMovements(originalPosition, destination, boardSize);

    }

    ArrayList<chessSquare> retMoves() {
        return knightMoves;
    }

    void calculateMovements(int[] originalPosition, int[] destination, int boardSize) {

        //The L-shaped movements for the 8 cells a knight can visit around our own
        int moveX[] = {1, 1, -1, -1, 2, 2, -2, -2};
        int moveY[] = {2, -2, 2, -2, 1, -1, 1, -1};


        ArrayList<chessSquare> positions = new ArrayList<chessSquare>();

        positions.add(new chessSquare(originalPosition[0], originalPosition[1], null));

        while (positions.size() != 0) {

            for (int i = 0; i < 8; i++) {

                int tempX = positions.get(0).x + moveX[i];
                int tempY = positions.get(0).y + moveY[i];


                if (validSquare(tempX, tempY, boardSize)) {


                    if (positions.get(0).parent == null) {
                        positions.get(0).parent = positions.get(0);
                    }

                    if ((tempX != positions.get(0).parent.x) || (tempY != positions.get(0).parent.y)) {

                        //Checks whether we have reached our destination within 3 movements. If that's true it saves the movement in our list of valid ones until all valid movements are saved
                        if (positions.get(0).depth < 3) {

                            if (positions.get(0).x + moveX[i] == destination[0] && positions.get(0).y + moveY[i] == destination[1]) {
                                knightMoves.add(new chessSquare(positions.get(0).x + moveX[i], positions.get(0).y + moveY[i], positions.get(0), positions.get(0).depth + 1));
                            }
                            positions.add(new chessSquare(positions.get(0).x + moveX[i], positions.get(0).y + moveY[i], positions.get(0), positions.get(0).depth + 1));

                        }

                    }

                }

            }
            //Once all paths from a certain square have been explored it is removed from the top of the list so that the next one will take its place until the list is empty
            //It is done to ensure that all valid paths will be explored.
            positions.remove(0);

        }


        for (int i = 0; i < knightMoves.size(); i++) {
            System.out.println("Solution " + i);
            if (knightMoves.get(i).x == destination[0] && knightMoves.get(i).y == destination[1]) {
                System.out.println("Depth " + knightMoves.get(i).depth + " " + knightMoves.get(i).x + " " + knightMoves.get(i).y);

                chessSquare cell = knightMoves.get(i).parent;

                while (cell.x != originalPosition[0] && cell.y != originalPosition[1]) {
                    System.out.println("Depth " + cell.depth + " " + cell.x + " " + cell.y);
                    cell = cell.parent;

                }

            }


        }

    }

    //Checks whether the square is outside the board, in those case it ignores them as they are invalid movements
    static boolean validSquare(int x, int y, int boardSize) {

        if (x >= 1 && y >= 1 && x <= boardSize && y <= boardSize) {
            return true;
        } else {
            return false;
        }

    }

}

class chessSquare {

    int x;
    int y;
    chessSquare parent;
    int depth;

    public chessSquare(int x, int y, chessSquare parent, int depth) {

        this.x = x;
        this.y = y;
        this.parent = parent;
        this.depth = depth;

    }

    public chessSquare(int x, int y, chessSquare parent) {

        this.x = x;
        this.y = y;
        this.parent = parent;
        this.depth = 0;

    }
}

class movement {

    chessSquare startingPoint;
    chessSquare finalPoint;

    public movement(chessSquare startingPoint, chessSquare finalPoint) {

        this.startingPoint = startingPoint;
        this.finalPoint = finalPoint;

    }

}
