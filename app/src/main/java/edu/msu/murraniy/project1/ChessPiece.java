package edu.msu.murraniy.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class ChessPiece{

	// The image for the chess piece
	private final Bitmap piece;
	// X location of the chess piece in pixels
	private float x;
	// Y location of the chess piece in pixels
	private float y;
	// Team of this chess piece
	private Chess.Team team;
	// The id of this chess piece
	private final int id;


	public ChessPiece(Context context, int id, Chess.Team team) {
		this.id = id;
		this.team = team;

		piece = BitmapFactory.decodeResource(context.getResources(), id);
	}

	public void draw(Canvas canvas, int marginX, int marginY,
					 int chessSize, float scaleFactor) {
		canvas.save();

		// Convert x,y to pixels and add the margin, then draw
		canvas.translate(marginX + x * chessSize, marginY + y * chessSize);

		// Scale it to the right size
		canvas.scale(scaleFactor, scaleFactor);

		// This magic code makes the center of the piece at 0, 0
		canvas.translate(-piece.getWidth() / 2f, -piece.getHeight() / 2f);

		// Draw the bitmap
		canvas.drawBitmap(piece, 0, 0, null);
		canvas.restore();
	}

	public Bitmap getBitmap() { return piece; }
	public Chess.Team getTeam() { return team; }
	public float getX() { return x; }
	public void setX(float x) { this.x = x; }
	public float getY() { return y; }
	public void setY(float y) { this.y = y; }

}