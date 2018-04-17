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

    public ThreadsAdapter(ArrayList<Thread> threads, Activity activity)
    {
        this.threads = threads;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_thread_message, parent, false);
        return new ThreadHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ThreadHolder threadHolder = (ThreadHolder) holder;
        thread = threads.get(position);
        threadHolder.threadName.setText(thread.getThreadName());

    }

    @Override
    public int getItemCount() {
        return threads.size();
    }
}
