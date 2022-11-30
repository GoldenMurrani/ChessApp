package edu.msu.murraniy.project1.Cloud.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "chessgame")
public class CheckJoin {

    @Attribute
    private String status;

    @Attribute(name = "msg", required = false)
    private String message;

    @Attribute(name = "player2", required = false)
    private String player2;

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
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

