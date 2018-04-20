package com.uncc.mobileappdev.inclass12;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Stephen on 4/16/2018.
 */

public class ThreadsAdapter extends RecyclerView.Adapter{

    ArrayList<Thread> threads;
    Activity activity;
    Thread thread;
    private static RecyclerViewClickListener recyclerViewClickListener;

    public ThreadsAdapter(ArrayList<Thread> threads, Activity activity, RecyclerViewClickListener recyclerViewClickListener)
    {
        this.threads = threads;
        this.activity = activity;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_thread_message, parent, false);
        return new ThreadHolder(v, recyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(threads != null) {
            ThreadHolder threadHolder = (ThreadHolder) holder;
            thread = threads.get(position);
            threadHolder.threadName.setText(thread.getThreadName());
        }

    }



    @Override
    public int getItemCount() {
        if(threads != null ) {
            return threads.size();
        }
        return 0;
    }
}
