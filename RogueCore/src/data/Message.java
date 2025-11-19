package data;

import entity.Player;

import java.io.Serializable;

public class Message implements Serializable {

    private final String data;
    private Player player;

    public Message(String data, Player player) {
        this.data = data;
        this.player = player;
    }

    public Message(Message message, String username) {
        this.data = "[" + username + "]: " + message.getData();
    }

    public String getData() {
        return data;
    }

    public Player getPlayer() {
        return player;
    }

}
