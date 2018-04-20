package com.uncc.mobileappdev.inclass12;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Stephen on 4/16/2018.
 */

public class ThreadHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView threadName;
    ImageButton removeThread;
    private RecyclerViewClickListener recyclerViewClickListener;

    public ThreadHolder(View itemView, RecyclerViewClickListener recyclerViewClickListener) {
        super(itemView);

        threadName = (TextView) itemView.findViewById(R.id.MessageThread);
        removeThread = (ImageButton) itemView.findViewById(R.id.imageButtonDelete);
        this.recyclerViewClickListener = recyclerViewClickListener;
        removeThread.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.equals(removeThread)) {
            recyclerViewClickListener.removeItem(v, this.getPosition());
        } else if (recyclerViewClickListener != null) {
            recyclerViewClickListener.recyclerViewListClicked(v, this.getPosition());
        }
    }
}
