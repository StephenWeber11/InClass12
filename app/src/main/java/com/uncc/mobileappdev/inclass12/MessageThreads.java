package com.uncc.mobileappdev.inclass12;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Stephen on 4/16/2018.
 */

public class MessageThreads extends AppCompatActivity implements RecyclerViewClickListener{

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private TextView userName;
    private ImageButton logout;
    private ImageButton addButton,removeButton;
    private EditText inputTopic;
    private RecyclerView recyclerView;
    private ThreadsAdapter adapter;

    private ArrayList<User> users;
    private ArrayList<Thread> threads;
    private ArrayList<String> userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_threads);

        mAuth = MainActivity.getmAuth();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        getUserData();
        getMessageData();

        userName = findViewById(R.id.UsernameMessage);
        logout = findViewById(R.id.logoutButton);
        addButton = findViewById(R.id.imageButtonAdd);
        removeButton = findViewById(R.id.imageButtonDelete);
        inputTopic = findViewById(R.id.inputTopic);

        recyclerView = findViewById(R.id.threadRecycleListView);

        Intent intent = getIntent();
        userInfo = intent.getStringArrayListExtra(Constants.INTENT_KEY);
        userName.setText(userInfo.get(0));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageThreads.this, MainActivity.class);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String threadText = inputTopic.getText().toString();

                Thread thread = new Thread();
                thread.setThreadName(threadText);
                thread.setUid(userInfo.get(1));

                String uid = String.valueOf(createThreadID());
                thread.setThreadID(uid);

                ArrayList<Message> messages = new ArrayList<>();
                messages.add(new Message(uid, "Some Message", "TodaysDate"));
                messages.add(new Message(uid, "Some Message", "TodaysDate"));
                messages.add(new Message(uid, "Some Message", "TodaysDate"));
                thread.setMessages(messages);

                pushThreadData(thread);
            }
        });


    }

    private void getUserData() {
        users = null;
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users = new ArrayList<>();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("DB", "Failed to read value.", error.toException());
            }
        });
    }

    private void pushThreadData(Thread thread) {
        mDatabase.child("threads").push().setValue(thread);
    }

    private void getMessageData() {
        threads = null;
        mDatabase.child("threads").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                threads = new ArrayList<>();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Thread thread = postSnapshot.getValue(Thread.class);
                    threads.add(thread);

                    if(adapter == null) {
                        adapter = new ThreadsAdapter(threads, MessageThreads.this, MessageThreads.this);
                        recyclerView.setAdapter(adapter);
                        LinearLayoutManager horizontalLayoutManager
                                = new LinearLayoutManager(MessageThreads.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(horizontalLayoutManager);
                        recyclerView.setNestedScrollingEnabled(false);
                        adapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("DB", "Failed to read value.", error.toException());
            }
        });
    }

    protected int createThreadID() {
        Double randomID = Math.random() * 64;
        return randomID.intValue();
    }

    @Override
    public void recyclerViewListClicked(View v, int position){
        String threadID = threads.get(position).getThreadID();
//        Intent intent = new Intent(this, MessageView.class);
//        intent.putExtra(Constants.INTENT_KEY, threadID);
//        startActivity(intent);

        Toast.makeText(this, "Clicked position: " + position, Toast.LENGTH_SHORT).show();
    }
}
