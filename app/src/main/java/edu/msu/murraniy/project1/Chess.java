package edu.msu.murraniy.project1;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class Chess {
    // PUBLIC enumeration to represent teams
    public enum Team { WHITE, BLACK }

    // The parent ChessView of this game Chess
    private ChessView parentView;
    // The greenish paint of the board
    private Paint greenPaint;
    // The grayish paint of the board
    private Paint grayPaint;
    // The size of the chess board in pixels
    private int chessSize;
    // The scaling of the chess pieces
    private float scaleFactor;
    // Percentage of the display width or height that is occupied by the chess board
    final static float SCALE_IN_VIEW = 0.9f;
    // The size of the horizontal margin in pixels
    private int marginX;
    // The size of the vertical margin in pixels
    private int marginY;
    // Represents which team has the turn
    private Team turn;
    // Collection of chess pieces
    private ArrayList<ChessPiece> pieces = new ArrayList<>();

    public Chess(Context context, ChessView view) {
        parentView = view;

        // Create paints for the green and gray of the board
        greenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        greenPaint.setColor(0xff18453b);
        grayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        grayPaint.setColor(0xffb9c7c4);

        // Give the turn to the white team first
        turn = Team.WHITE;

        // Initialize the chess pieces
        pieces.add(new ChessPiece(context, R.drawable.rook_black, Team.BLACK));
        pieces.add(new ChessPiece(context, R.drawable.knight_black, Team.BLACK));
        pieces.add(new ChessPiece(context, R.drawable.bishop_black, Team.BLACK));
        pieces.add(new ChessPiece(context, R.drawable.queen_black, Team.BLACK));
        pieces.add(new ChessPiece(context, R.drawable.king_black, Team.BLACK));
        pieces.add(new ChessPiece(context, R.drawable.bishop_black, Team.BLACK));
        pieces.add(new ChessPiece(context, R.drawable.knight_black, Team.BLACK));
        pieces.add(new ChessPiece(context, R.drawable.rook_black, Team.BLACK));
        pieces.add(new ChessPiece(context, R.drawable.pawn_black, Team.BLACK));
        pieces.add(new ChessPiece(context, R.drawable.pawn_black, Team.BLACK));
        pieces.add(new ChessPiece(context, R.drawable.pawn_black, Team.BLACK));
        pieces.add(new ChessPiece(context, R.drawable.pawn_black, Team.BLACK));
        pieces.add(new ChessPiece(context, R.drawable.pawn_black, Team.BLACK));
        pieces.add(new ChessPiece(context, R.drawable.pawn_black, Team.BLACK));
        pieces.add(new ChessPiece(context, R.drawable.pawn_black, Team.BLACK));
        pieces.add(new ChessPiece(context, R.drawable.pawn_black, Team.BLACK));
        pieces.add(new ChessPiece(context, R.drawable.pawn_white, Team.WHITE));
        pieces.add(new ChessPiece(context, R.drawable.pawn_white, Team.WHITE));
        pieces.add(new ChessPiece(context, R.drawable.pawn_white, Team.WHITE));
        pieces.add(new ChessPiece(context, R.drawable.pawn_white, Team.WHITE));
        pieces.add(new ChessPiece(context, R.drawable.pawn_white, Team.WHITE));
        pieces.add(new ChessPiece(context, R.drawable.pawn_white, Team.WHITE));
        pieces.add(new ChessPiece(context, R.drawable.pawn_white, Team.WHITE));
        pieces.add(new ChessPiece(context, R.drawable.pawn_white, Team.WHITE));
        pieces.add(new ChessPiece(context, R.drawable.rook_white, Team.WHITE));
        pieces.add(new ChessPiece(context, R.drawable.knight_white, Team.WHITE));
        pieces.add(new ChessPiece(context, R.drawable.bishop_white, Team.WHITE));
        pieces.add(new ChessPiece(context, R.drawable.queen_white, Team.WHITE));
        pieces.add(new ChessPiece(context, R.drawable.king_white, Team.WHITE));
        pieces.add(new ChessPiece(context, R.drawable.bishop_white, Team.WHITE));
        pieces.add(new ChessPiece(context, R.drawable.knight_white, Team.WHITE));
        pieces.add(new ChessPiece(context, R.drawable.rook_white, Team.WHITE));

        startingPlacement();
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

        // Determine scaling for the chess pieces
        scaleFactor = (chessSize/8.0f) / pieces.get(0).getBitmap().getWidth();

        // Draw each of the chess pieces
        for (ChessPiece piece : pieces) {
            piece.draw(canvas, marginX, marginY, chessSize, scaleFactor);
        }

    }

    // Place the chess pieces in their starting positions (assuming properly ordered array)
    public void startingPlacement() {
        for (int i = 0; i < 32; i++) {
            ChessPiece piece = pieces.get(i);
            int offset_team = i < 16 ? 0 : 12;
            int offset_row = (i%16) < 8 ? 0 : 2;
            piece.setX(((i%8)*2+1)/16.0f);
            piece.setY((offset_team+offset_row+1)/16.0f);
        }
    }
}
