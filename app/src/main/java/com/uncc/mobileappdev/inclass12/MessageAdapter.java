package com.uncc.mobileappdev.inclass12;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;

/**
 * Created by Stephen on 4/20/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter {

    private ArrayList<Message> messages;
    private User user;
    private Activity activity;
    private RecyclerViewClickListener recyclerViewClickListener;
    private Message message;

    public MessageAdapter(ArrayList<Message> messages, User user, Activity activity, RecyclerViewClickListener recyclerViewClickListener) {
        this.messages = messages;
        this.user = user;
        this.activity = activity;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatroom_recycle_view, parent, false);
        return new MessageHolder(v, recyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(messages != null) {
            final MessageHolder messageHolder = (MessageHolder) holder;
            message = messages.get(position);
            messageHolder.chatMessage.setText(message.getMessageContent());
            messageHolder.userName.setText(message.getFullName());

            PrettyTime p = new PrettyTime();
            messageHolder.time.setText(p.format(message.getDate()));
            if(message.getUid().equals(user.getUid())) {
                messageHolder.delete.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public int getItemCount() {
        if(messages != null ) {
            return messages.size();
        }
        return 0;
    }
}
