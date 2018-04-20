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

    private ArrayList<Thread> threads;
    private Activity activity;
    private Thread thread;
    private static RecyclerViewClickListener recyclerViewClickListener;
    private String uid;

    public ThreadsAdapter(ArrayList<Thread> threads, Activity activity, RecyclerViewClickListener recyclerViewClickListener, String uid)
    {
        this.threads = threads;
        this.activity = activity;
        this.recyclerViewClickListener = recyclerViewClickListener;
        this.uid = uid;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_thread_message, parent, false);
        return new ThreadHolder(v, recyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(threads != null) {
            final ThreadHolder threadHolder = (ThreadHolder) holder;
            thread = threads.get(position);
            threadHolder.threadName.setText(thread.getThreadName());
            if(thread.getUid().equals(uid)) {
                threadHolder.removeThread.setVisibility(View.VISIBLE);
            }
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
