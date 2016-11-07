package com.jeketos.associatewith.guesser.chat;

/**
 * Created by eugene.kotsogub on 11/7/16.
 *
 */

public class ChatItem implements IChatItem {

    public static final String NAME = "name";
    public static final String MESSAGE = "message";

    private String name;
    private String message;

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
