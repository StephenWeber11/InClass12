package com.uncc.mobileappdev.inclass12;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Stephen on 4/20/2018.
 */

public class MessageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView chatMessage;
    TextView userName;
    TextView time;
    ImageButton delete;
    private RecyclerViewClickListener recyclerViewClickListener;

    public MessageHolder(View itemView, RecyclerViewClickListener recyclerViewClickListener) {
        super(itemView);
        chatMessage = itemView.findViewById(R.id.text_view_chat_message);
        userName = itemView.findViewById(R.id.text_view_user_name);
        time = itemView.findViewById(R.id.text_view_time_from_now);
        delete = itemView.findViewById(R.id.deleteMessageButton);
        this.recyclerViewClickListener = recyclerViewClickListener;
        delete.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(delete)) {
            recyclerViewClickListener.removeItem(v, this.getPosition());
        } else if (recyclerViewClickListener != null) {
            recyclerViewClickListener.recyclerViewListClicked(v, this.getPosition());
        }
    }
}
