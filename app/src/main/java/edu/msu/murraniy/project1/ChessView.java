package edu.msu.murraniy.project1;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Custom view class for Chess.
 */
public class ChessView extends View {

    /**
     * The Chess game
     */
    private Chess chess;

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

    private void init(AttributeSet attrs, int defStyle) {
        chess = new Chess(getContext(), this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        chess.draw(canvas);
    }
}