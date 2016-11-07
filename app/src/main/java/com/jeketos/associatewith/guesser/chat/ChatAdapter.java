package com.jeketos.associatewith.guesser.chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeketos.associatewith.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by eugene.kotsogub on 11/7/16.
 *
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {

    private List<IChatItem> chatItems;

    public ChatAdapter(){
        chatItems = new ArrayList<>();
    }


    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatHolder(v);
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {
        IChatItem item = chatItems.get(position);
        holder.name.setText(item.getName());
        holder.message.setText(item.getMessage());
    }

    @Override
    public int getItemCount() {
        return chatItems.size();
    }

    public void updateItems(IChatItem item){
        if(item != null) {
            chatItems.add(item);
        } else {
            chatItems = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    class ChatHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.message)
        TextView message;

         ChatHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
