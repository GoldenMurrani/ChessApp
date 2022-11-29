package edu.msu.murraniy.project1.Cloud.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Nested class to store one catalog row
 */
@Root(name = "chessgames")
public final class Game {
    @Attribute
    private String id;

    @Attribute
    private String player1id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return player1id;
    }

    public void setName(String name) {
        this.player1id = name;
    }

    public Game(String id, String name) {
        this.player1id = name;
        this.id = id;
    }

    public Game() {}

}