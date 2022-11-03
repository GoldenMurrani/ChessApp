package edu.msu.murraniy.project1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BoardSquare {

    // left margin
    private float left;
    // top margin
    private float top;
    // right margin
    private float right;
    // bottom margin
    private float bottom;
    // Color of square (green or grey)
    private Paint color;

    private boolean drawn = false;


    public BoardSquare(float left, float top, float right, float bottom, Paint color) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.color = color;
    }

    public void draw(Canvas canvas, float scaleFactor) {
        canvas.save();
        canvas.drawRect(this.left, this.top, this.right, this.bottom, this.color);
        canvas.restore();
        this.drawn = true;
    }

    public float getLeft() {return this.left;}
    public float getTop() {return this.top;}
    public float getRight() {return this.right;}
    public float getBottom() {return this.bottom;}
    public void setLeft(float margin) {this.left = margin;}
    public void setRight(float margin) {this.right = margin;}
    public void setTop(float margin) {this.top = margin;}
    public void setBottom(float margin) {this.bottom = margin;}

}
