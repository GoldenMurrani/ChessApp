package edu.msu.murraniy.project1;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Custom view class for Chess.
 */
public class ChessView extends View {

    /**
     * The Chess game
     */
    private Chess chess;

    private ChessActivity myActivity;

    public ChessView(Context context) {
        super(context);
        init(null, 0);
    }

    public ChessView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ChessView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return chess.onTouchEvent(this, event);
    }

    private void init(AttributeSet attrs, int defStyle) {
        chess = new Chess(getContext(), this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        chess.draw(canvas);
    }

    public void setActivity(ChessActivity a){
        myActivity = a;
    }

    public ChessActivity getActivity(){
        return myActivity;
    }

    /**
     * Changes the turn in the Chess Class
     */
    public void changeChessTurn(){
        if(chess.getTurn() == Chess.Team.WHITE){
            chess.setTurn(Chess.Team.BLACK);
        }else{
            chess.setTurn(Chess.Team.WHITE);
        }
        myActivity.changeTurn();
    }

    /**
     * Declares a winner based on player number passed to it from Chess Class
     * @param playerNumber
     */
    public void declareWinner(int playerNumber){
        myActivity.callGame(playerNumber);
    }

    /**
     * Calls the Chess Activity to open the dialog box
     */
    public void promotePawn(){
        myActivity.promotionDialog();
    }

    /**
     * Sends the position number to chess to update the pawn piece
     * @param position
     */
    public void updatePawn(int position){
        chess.updatePiece(position);
    }

    /**
     * Save the Chess board to a bundle
     * @param bundle The bundle we save to
     */
    public void saveInstanceState(Bundle bundle) {
        chess.saveInstanceState(bundle);
    }

    /**
     * Load the Chess Board from a bundle
     * @param bundle The bundle we save to
     */
    public void loadInstanceState(Bundle bundle) {
        chess.loadInstanceState(bundle);
    }

    public void setChessGameId(int gameId) { this.chess.setGameId(gameId);}
    public void setChessSide(int side) { this.chess.setSide(side); }

}