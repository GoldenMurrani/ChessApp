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
    private String user1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return user1;
    }

    public void setName(String name) {
        this.user1 = name;
    }

    public Game(String id, String name) {
        this.user1 = name;
        this.id = id;
    }

    public Game() {}

}