package edu.msu.murraniy.project1;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class Chess {
    // PUBLIC enumeration to represent teams
    public enum Team { WHITE, BLACK }

    // PUBLIC enumeration to represent piece types
    public enum Type { ROOK, KNIGHT, BISHOP, QUEEN, KING, PAWN}

    // The parent ChessView of this game Chess
    private ChessView parentView;
    // The greenish paint of the board
    private Paint greenPaint;
    // The grayish paint of the board
    private Paint grayPaint;
    // The sound player for chess piece placement
    private MediaPlayer mediaPlayer;
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
    /**
     * This variable is set to a piece we are dragging. If
     * we are not dragging, the variable is null.
     */
    private ChessPiece dragging = null;

    private ChessPiece lastMoved = null;
    /**
     * Most recent relative X touch when dragging
     */
    private float lastRelX;

    /**
     * Most recent relative Y touch when dragging
     */
    private float lastRelY;

    /**
     * Count of White pieces on board
     */
    private int whitePieces = 16;

    /**
     * Count of black pieces on board
     */
    private int blackPieces = 16;

    private Context chessContext;

    private ArrayList<BoardSquare> squares = new ArrayList<>();

    /**
     * The name of the bundle keys to save the Chess game
     */
    private final static String LOCATIONS = "Chess.locations";
    private final static String IDS = "Chess.ids";
    private final static String PLAYERTURN = "Chess.PlayerTurn";


    public Chess(Context context, ChessView view) {
        parentView = view;
        chessContext = context;

        // Create paints for the green and gray of the board
        greenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        greenPaint.setColor(0xff18453b);
        grayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        grayPaint.setColor(0xffb9c7c4);

        // Set up the media player
        mediaPlayer = MediaPlayer.create(context, R.raw.place);

        // Give the turn to the white team first
        turn = Team.WHITE;

        // Initialize the chess pieces
        pieces.add(new ChessPiece(context, R.drawable.rook_black, Team.BLACK, Type.ROOK, 0, 0));
        pieces.add(new ChessPiece(context, R.drawable.knight_black, Team.BLACK, Type.KNIGHT, 1, 0));
        pieces.add(new ChessPiece(context, R.drawable.bishop_black, Team.BLACK, Type.BISHOP, 2, 0));
        pieces.add(new ChessPiece(context, R.drawable.queen_black, Team.BLACK, Type.QUEEN, 3, 0));
        pieces.add(new ChessPiece(context, R.drawable.king_black, Team.BLACK, Type.KING, 4, 0));
        pieces.add(new ChessPiece(context, R.drawable.bishop_black, Team.BLACK, Type.BISHOP, 5, 0));
        pieces.add(new ChessPiece(context, R.drawable.knight_black, Team.BLACK, Type.KNIGHT,6, 0));
        pieces.add(new ChessPiece(context, R.drawable.rook_black, Team.BLACK, Type.ROOK, 7, 0));
        for (int i = 0; i < 8; i++) {
            pieces.add(new ChessPiece(context, R.drawable.pawn_black, Team.BLACK, Type.PAWN, i, 1));
            pieces.add(new ChessPiece(context, R.drawable.pawn_white, Team.WHITE, Type.PAWN, i, 6));
        }
        pieces.add(new ChessPiece(context, R.drawable.rook_white, Team.WHITE, Type.ROOK, 0, 7));
        pieces.add(new ChessPiece(context, R.drawable.knight_white, Team.WHITE, Type.KNIGHT, 1, 7));
        pieces.add(new ChessPiece(context, R.drawable.bishop_white, Team.WHITE, Type.BISHOP, 2, 7));
        pieces.add(new ChessPiece(context, R.drawable.queen_white, Team.WHITE, Type.QUEEN, 3, 7));
        pieces.add(new ChessPiece(context, R.drawable.king_white, Team.WHITE, Type.KING, 4, 7));
        pieces.add(new ChessPiece(context, R.drawable.bishop_white, Team.WHITE, Type.BISHOP, 5, 7));
        pieces.add(new ChessPiece(context, R.drawable.knight_white, Team.WHITE, Type.KNIGHT,6, 7));
        pieces.add(new ChessPiece(context, R.drawable.rook_white, Team.WHITE, Type.ROOK, 7, 7));

        // set  unique id for each piece as just a number from 0-31.
        for(int i=0; i<pieces.size();i++){
            ChessPiece piece = pieces.get(i);
            piece.setUnique_id(i);
        }

        placePieces();
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

        if(squares.size() <= 0) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Paint squarePaint = ((i + j) % 2) == 0 ? grayPaint : greenPaint;
                    squares.add(new BoardSquare(marginX + (chessSize * j) / 8.0f, marginY + (chessSize * i) / 8.0f,
                            marginX + (chessSize * (j + 1)) / 8.0f, marginY + (chessSize * (i + 1)) / 8.0f,
                            squarePaint, j, i));

                }
            }
        }

        // Determine scaling for the chess pieces
        scaleFactor = (chessSize/8.0f) / pieces.get(0).getBitmap().getWidth();

        // Draw each board square
        for(BoardSquare square : squares) {
            square.draw(canvas, scaleFactor);
        }

        // Draw each of the chess pieces
        for (ChessPiece piece : pieces) {
            piece.draw(canvas, marginX, marginY, chessSize, scaleFactor);
        }

    }

    // Place the chess pieces in their positions
    public void placePieces() {
        for (int i = 0; i < 32; i++) {
            ChessPiece piece = pieces.get(i);
            int x = piece.getChessX();
            int y = piece.getChessY();
            piece.setX((x*2+1)/16.0f);
            piece.setY((y*2+1)/16.0f);
        }
    }

    /**
     * Handle a touch event from the view.
     * @param view The view that is the source of the touch
     * @param event The motion event describing the touch
     * @return true if the touch is handled.
     */
    public boolean onTouchEvent(View view, MotionEvent event) {
        //
        // Convert an x,y location to a relative location in the
        // puzzle.
        //

        float relX = (event.getX() - marginX) / chessSize;
        float relY = (event.getY() - marginY) / chessSize;

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                return onTouched(relX, relY);

            case MotionEvent.ACTION_UP:
                if(dragging != null && dragging.getTeam() == turn) {
//                    parentView.changeChessTurn();


                    for(BoardSquare square : squares) {
                        float relSquareX = (square.getCenterX() - marginX) / chessSize;
                        float relSquareY = (square.getCenterY() - marginY) / chessSize;

                        if(Math.abs(relSquareX - dragging.getX()) <= 0.08f && Math.abs(relSquareY - dragging.getY()) <= 0.08f) {
                            dragging.setX(relSquareX);
                            dragging.setY(relSquareY);
                            dragging.setChessX(square.getChessX());
                            dragging.setChessY(square.getChessY());
                            // Play placement sound when snapping
                            mediaPlayer.start();
                            view.invalidate();
                            break;
                        }
                    }



                    for(ChessPiece piece : pieces) {
                        if(dragging.getTeam() != piece.getTeam()) {

                            if (Math.abs(dragging.getX() - piece.getX()) <= 0.05f && Math.abs(dragging.getY() - piece.getY()) <= 0.05f ) {

                                if(piece.getTeam() == Team.WHITE){
                                    whitePieces -= 1;
                                    if(piece.getType() == Type.KING){
                                        whitePieces = 0;
                                    }
                                }else{
                                    blackPieces -= 1;
                                    if(piece.getType() == Type.KING){
                                        blackPieces = 0;
                                    }
                                }

                                pieces.remove(piece);

                                if(dragging.getType() == Type.PAWN){

                                    if(dragging.getTeam() == Team.WHITE && dragging.getY() <= 0.0625){
                                        promotePiece();
                                    }
                                    if(dragging.getTeam() == Team.BLACK && dragging.getY() >= 0.9375){
                                        promotePiece();
                                    }

                                }

                                view.invalidate();
                                checkScore();
                                break;
                            }
                        }
                    }

                    parentView.changeChessTurn();
                }

            case MotionEvent.ACTION_CANCEL:
                if(dragging != null) {
                    dragging = null;
                    return true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                // If we are dragging, move the piece and force a redraw
                if(dragging != null && dragging.getTeam() == turn) {
                    dragging.move(relX - lastRelX, relY - lastRelY);
                    lastRelX = relX;
                    lastRelY = relY;
                    view.invalidate();
                    return true;
                }
                break;
        }

        return false;
    }

    /**
     * Handle a touch message. This is when we get an initial touch
     * @param x x location for the touch, relative to the puzzle - 0 to 1 over the puzzle
     * @param y y location for the touch, relative to the puzzle - 0 to 1 over the puzzle
     * @return true if the touch is handled
     */
    private boolean onTouched(float x, float y) {

        // Check each piece to see if it has been hit
        // We do this in reverse order so we find the pieces in front
        for(int p=pieces.size()-1; p>=0;  p--) {
            if(pieces.get(p).hit(x, y, chessSize, scaleFactor)) {
                // We hit a piece!
                dragging = pieces.get(p);
                lastMoved = dragging;
                lastRelX = x;
                lastRelY = y;
                return true;
            }
        }

        return false;
    }

    /**
     * Getter for the turn
     * @return
     */
    public Team getTurn() {
        return turn;
    }

    /**
     * Setter for the turn
     * @param turn
     */
    public void setTurn(Team turn) {
        this.turn = turn;
    }

    /**
     * Checks the current score to declare a winner or not
     */
    public void checkScore(){
        if(whitePieces == 0){
            parentView.declareWinner(2);
        }
        if(blackPieces == 0){
            parentView.declareWinner(1);
        }
    }

    /**
     * Tells chess view to promote the last pawn moved
     */
    public void promotePiece(){
        parentView.promotePawn();
    }

    /**
     * Uses the promotion number to update the pawn to correct piece
     * @param position
     */
    public void updatePiece(int position){
        if(lastMoved.getTeam() == Chess.Team.WHITE){
            if(position == 0){
                lastMoved.setId(R.drawable.queen_white);
                lastMoved.setType(Chess.Type.QUEEN);
            }else if(position == 1){
                lastMoved.setId(R.drawable.rook_white);
                lastMoved.setType(Chess.Type.ROOK);
            }else if(position == 2){
                lastMoved.setId(R.drawable.knight_white);
                lastMoved.setType(Chess.Type.KNIGHT);
            }else if(position == 3){
                lastMoved.setId(R.drawable.bishop_white);
                lastMoved.setType(Chess.Type.BISHOP);
            }
        }
        if(lastMoved.getTeam() == Chess.Team.BLACK){
            if(position == 0){
                lastMoved.setId(R.drawable.queen_black);
                lastMoved.setType(Chess.Type.QUEEN);
            }else if(position == 1){
                lastMoved.setId(R.drawable.rook_black);
                lastMoved.setType(Chess.Type.ROOK);
            }else if(position == 2){
                lastMoved.setId(R.drawable.knight_black);
                lastMoved.setType(Chess.Type.KNIGHT);
            }else if(position == 3){
                lastMoved.setId(R.drawable.bishop_black);
                lastMoved.setType(Chess.Type.BISHOP);
            }
        }
        parentView.invalidate();
    }

    /**
     * Save the puzzle to a bundle
     * @param bundle The bundle we save to
     */
    public void saveInstanceState(Bundle bundle) {
        float [] locations = new float[pieces.size() * 2];
        int [] ids = new int[pieces.size()];

        for(int i=0;  i<pieces.size(); i++) {
            ChessPiece piece = pieces.get(i);
            locations[i*2] = piece.getX();
            locations[i*2+1] = piece.getY();
            ids[i] = piece.getUnique_id();
        }

        bundle.putFloatArray(LOCATIONS, locations);
        // is actually unique_ids not ids
        bundle.putIntArray(IDS,  ids);

        String turnAsString = "";
        if(turn == Team.WHITE){
            turnAsString = "WHITE";
        }
        else{
            turnAsString = "BLACK";
        }
        bundle.putString(PLAYERTURN, turnAsString);
    }

    /**
     * Read the puzzle from a bundle
     * @param bundle The bundle we save to
     */
    public void loadInstanceState(Bundle bundle) {
        float [] locations = bundle.getFloatArray(LOCATIONS);
        int [] ids = bundle.getIntArray(IDS);

        /*for(int i=0; i<ids.length-1; i++) {

            // Find the corresponding piece
            // We don't have to test if the piece is at i already,
            // since the loop below will fall out without it moving anything
            for(int j=i+1;  j<ids.length;  j++) {
                if(ids[i] == pieces.get(j).getUnique_id()) {
                    // We found it
                    // Yah...
                    // Swap the pieces
                    ChessPiece t = pieces.get(i);
                    pieces.set(i, pieces.get(j));
                    pieces.set(j, t);
                }
            }
        }*/

        /// loop to remove captured pieces from pieces before reloading everything.
        ArrayList<ChessPiece> allPieces = new ArrayList<ChessPiece>(pieces);

        for(int i=0;  i<allPieces.size(); i++) {
            ChessPiece piece = allPieces.get(i);

            boolean present = false;
            for(int id : ids){
                if (id == piece.getUnique_id()){
                    present = true;
                    break;
                }
            }
            if (!present){
                pieces.remove(piece);
            }
        }

        for(int i=0;  i<pieces.size(); i++) {
            ChessPiece piece = pieces.get(i);

            piece.setX(locations[i*2]);
            piece.setY(locations[i*2+1]);
        }

        if(bundle.getString(PLAYERTURN) == "WHITE"){
            turn = Team.WHITE;
        }
        else{
            turn = Team.BLACK;
        }
    }

    // Used to update the database when a move is made, called upon snap
    public void makeMove(int pieceId, int pieceX, int pieceY) {

    }

}
