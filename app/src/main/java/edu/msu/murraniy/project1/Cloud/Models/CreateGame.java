package edu.msu.murraniy.project1.Cloud.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Model for creating a new game service.
 */
@Root(name = "chessgames")
public class CreateGame {
    @Attribute
    private String status;

    @Attribute(name = "msg", required = false)
    private String message;

    @Attribute(name = "game", required = false)
    private int game;

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
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
