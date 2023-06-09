package edu.msu.murraniy.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class ChessPiece{

	// The image for the chess piece
	private Bitmap piece;
	// X location of the chess piece in pixels
	private float x;
	// Y location of the chess piece in pixels
	private float y;
	// Chess board grid X (leftmost is 0)
	private int chessX;
	// Chess board grid Y (topmost is 0)
	private int chessY;
	// Team of this chess piece
	private Chess.Team team;
	// The type of the chess piece
	private Chess.Type type;
	// The id of this chess piece
	private int id;
	// Snap piece into place if within this distance
	final static float SNAP_DISTANCE = 0.05f;
	// unique id used to save and load state correctly.
	private int unique_id;

	public int getUnique_id() {
		return unique_id;
	}

	public void setUnique_id(int unique_id) {
		this.unique_id = unique_id;
	}

	private Context pieceContext;


	public ChessPiece(Context context, int id, Chess.Team team, Chess.Type type, int x, int y) {
		this.id = id;
		this.team = team;
		this.type = type;
		this.chessX = x;
		this.chessY = y;

		piece = BitmapFactory.decodeResource(context.getResources(), id);

		pieceContext = context;
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
	public int getChessX() { return chessX; }
	public void setChessX(int chessX) { this.chessX = chessX; }
	public int getChessY() { return chessY; }
	public void setChessY(int chessY) { this.chessY = chessY; }

	/**
	 * Test to see if we have touched a puzzle piece
	 * @param testX X location as a normalized coordinate (0 to 1)
	 * @param testY Y location as a normalized coordinate (0 to 1)
	 * @param chessSize the size of the puzzle in pixels
	 * @param scaleFactor the amount to scale a piece by
	 * @return true if we hit the piece
	 */
	public boolean hit(float testX, float testY,
					   int chessSize, float scaleFactor) {

		// Make relative to the location and size to the piece size
		int pX = (int)((testX - x) * chessSize / scaleFactor) +
				piece.getWidth() / 2;
		int pY = (int)((testY - y) * chessSize / scaleFactor) +
				piece.getHeight() / 2;

		if(pX < 0 || pX >= piece.getWidth() ||
				pY < 0 || pY >= piece.getHeight()) {
			return false;
		}

		// We are within the rectangle of the piece.
		// Are we touching actual picture?
		return (piece.getPixel(pX, pY) & 0xff000000) != 0;
	}

	/**
	 * Move the puzzle piece by dx, dy
	 * @param dx x amount to move
	 * @param dy y amount to move
	 */
	public void move(float dx, float dy) {
		x += dx;
		y += dy;
	}

	public Chess.Type getType() {
		return type;
	}
	public void setType(Chess.Type type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		piece = BitmapFactory.decodeResource(pieceContext.getResources(), id);
	}

}