package edu.msu.murraniy.project1.Cloud.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "chessgame")
public class CheckTurn {

    @Attribute
    private String status;

    @Attribute(name = "msg", required = false)
    private String message;

    @Attribute(name = "piece", required = false)
    private int piece;

    @Attribute(name = "x", required = false)
    private int x;

    @Attribute(name = "y", required = false)
    private int y;

    @Attribute(name = "turn", required = false)
    private int turn;

    public int getPiece() {
        return piece;
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
