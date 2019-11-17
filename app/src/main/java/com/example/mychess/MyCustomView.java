package com.example.mychess;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


//This is a custom View used for the implementation of the rendering of the chessboard and the movement of the knights

 public class MyCustomView extends View {

    public int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    public int height;
    private Bitmap myBitmap;
    private Canvas myCanvas;
    private Path myPath;
    Context context;
    private Paint myPaint;
    private Paint myOtherPaint;


    private static int boardSize = 6;

    private float myX, myY;
    int cellPressed[] = {-1, -1};
    ArrayList<chessSquare> knightMoves;

    int pressedCells = 0;
    int originalCell[] = {-1, -1};
    int destinationCell[] = {-1, -1};


    public MyCustomView(Context myContext, AttributeSet attributeSet) {
        super(myContext, attributeSet);
        context = myContext;

        myPaint = new Paint();
        myPaint.setColor(Color.BLACK);
        myPaint.setStrokeWidth(10);

        myOtherPaint = new Paint();
        myOtherPaint.setColor(Color.RED);
        myOtherPaint.setStrokeWidth(10);

    }

    public static void setSize(int size) {

        boardSize = size;
    }

    //onTouch captures the first and second touch on the screen, uses Knightmove to calculate the valid paths
    //and forces a refresh of the screen so that the paths will be rendered
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (pressedCells == 0) {

                cellPressed[0] = (int) (event.getX() / (width / boardSize)) + 1;
                cellPressed[1] = (int) (event.getY() / (width / boardSize)) + 1;

                originalCell[0] = (int) (event.getX() / (width / boardSize)) + 1;
                originalCell[1] = (int) (event.getY() / (width / boardSize)) + 1;

                pressedCells++;
            } else if (pressedCells == 1) {

                destinationCell[0] = (int) (event.getX() / (width / boardSize)) + 1;
                destinationCell[1] = (int) (event.getY() / (width / boardSize)) + 1;
                pressedCells--;


                KnightMove km;

                km = new KnightMove(boardSize, originalCell, destinationCell);
                knightMoves = km.retMoves();


                if (knightMoves.size() == 0) {
                    Toast.makeText(context, "There are no possible moves", 2).show();

                }
                invalidate();
            }

            // cellChecked[column][row] = !cellChecked[column][row];
            //  invalidate();
        }
        System.out.println("cellpressed " + cellPressed[0] + " " + cellPressed[1]);
        return true;
    }

    //onDraw renders the grid for the chessboard (left as a white grid to avoid cluttering the screen)
    //then it draws all the valid paths that were calculated above
    @Override
    protected void onDraw(Canvas canvas) {


        super.onDraw(canvas);

        width = Resources.getSystem().getDisplayMetrics().widthPixels;


        int squareWidth = width / boardSize;


        for (int i = 0; i < boardSize + 1; i++) {
            canvas.drawLine(i * squareWidth, 0, i * squareWidth, width, myPaint);
        }

        for (int i = 0; i < boardSize + 1; i++) {
            canvas.drawLine(0, i * squareWidth, width, i * squareWidth, myPaint);
        }


        if (knightMoves != null) {

            for (int i = 0; i < knightMoves.size(); i++) {
                Random rand = new Random();

                myOtherPaint.setARGB(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

                chessSquare cell = knightMoves.get(i);
                boolean parent = true;
                while (parent) {


                    canvas.drawLine((cell.x) * (width / boardSize) - width / (boardSize * 2), (cell.y) * (width / boardSize) - width / (boardSize * 2), (cell.parent.x) * (width / boardSize) - width / (boardSize * 2), (cell.parent.y) * (width / boardSize) - width / (boardSize * 2), myOtherPaint);

                    cell = cell.parent;

                    if (cell.x == cell.parent.x && cell.y == cell.parent.y) {
                        parent = false;
                    }

                }

            }
        }

    }


}
