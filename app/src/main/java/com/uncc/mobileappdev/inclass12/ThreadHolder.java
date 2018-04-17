package com.uncc.mobileappdev.inclass12;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Stephen on 4/16/2018.
 */

public class ThreadHolder extends RecyclerView.ViewHolder {

    TextView threadName;
    ImageButton removeThread;

    public ThreadHolder(View itemView) {
        super(itemView);

        threadName = (TextView) itemView.findViewById(R.id.MessageThread);
        removeThread = (ImageButton) itemView.findViewById(R.id.imageButtonDelete);
    }
}
