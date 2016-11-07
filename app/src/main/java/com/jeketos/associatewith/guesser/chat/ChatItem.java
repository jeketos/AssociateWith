package com.jeketos.associatewith.guesser.chat;

/**
 * Created by eugene.kotsogub on 11/7/16.
 *
 */

public class ChatItem implements IChatItem {

    String name;
    String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
