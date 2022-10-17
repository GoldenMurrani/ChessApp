package edu.msu.murraniy.project1;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Chess {
    // The parent ChessView of this game Chess
    private ChessView parentView;
    // The greenish paint of the board
    private Paint greenPaint;
    // The grayish paint of the board
    private Paint grayPaint;
    // The size of the chess board in pixels
    private int chessSize;
    // Percentage of the display width or height that is occupied by the puzzle
    final static float SCALE_IN_VIEW = 0.9f;
    // The size of the horizontal margin in pixels
    private int marginX;
    // The size of the vertical margin in pixels
    private int marginY;

    public Chess(Context context, ChessView view) {
        parentView = view;

        // Create paints for the green and gray of the board
        greenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        greenPaint.setColor(0xff18453b);
        grayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        grayPaint.setColor(0xffb9c7c4);
    }

    public void draw(Canvas canvas) {
        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        int minDim = Math.min(wid, hit);
        chessSize = (int)(minDim * SCALE_IN_VIEW);

        // Compute the margins so we center the chess board
        marginX = (wid - chessSize) / 2;
        marginY = (hit - chessSize) / 2;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Paint squarePaint = ((i+j)%2)==0 ? grayPaint : greenPaint;
                canvas.drawRect(marginX + (chessSize*j)/8.0f, marginY + (chessSize*i)/8.0f,
                        marginX + (chessSize*(j+1))/8.0f, marginY + (chessSize*(i+1))/8.0f,
                        squarePaint);
            }
        }


    }

    private boolean mTurn = false;
    private ChessPiece[] whitePieces;
    private ChessPiece[] blackPieces;
//    private ChessPiece[] mPieces = [whitePieces, blackPieces];

    private boolean checkWin(){
        return false;
    }
}
