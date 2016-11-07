package com.jeketos.associatewith.util;

import com.google.firebase.database.DataSnapshot;
import com.jeketos.associatewith.guesser.chat.ChatItem;
import com.jeketos.associatewith.guesser.chat.IChatItem;

import java.util.HashMap;

/**
 * Created by eugene.kotsogub on 11/7/16.
 *
 */

public class ChatUtils {

    public static IChatItem getChatItem(DataSnapshot dataSnapshot, int position){
        String name = (String) ((HashMap) dataSnapshot.child(Integer.toString(position)).getValue()).get(ChatItem.NAME);
        String message = (String) ((HashMap) dataSnapshot.child(Integer.toString(position)).getValue()).get(ChatItem.MESSAGE);
        IChatItem item = new ChatItem();
        item.setName(name);
        item.setMessage(message);
        return item;
    }

}
