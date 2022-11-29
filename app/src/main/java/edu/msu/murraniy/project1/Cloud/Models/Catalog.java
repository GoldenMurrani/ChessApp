package edu.msu.murraniy.project1.Cloud.Models;

import androidx.annotation.Nullable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "chessuser")
public final class Catalog {
    @Attribute
    private String status;

    @ElementList(name = "chessgames", inline = true, required = false, type = Game.class)
    private List<Game> games;

    @Attribute(name = "msg", required = false)
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Game> getItems() {
        return games;
    }

    public void setItems(List<Game> items) {
        this.games = items;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Catalog(String status, ArrayList<Game> games, @Nullable String msg) {
        this.status = status;
        this.games = games;
        this.message = msg;
    }

    public Catalog() {
    }
}
