package com.uncc.mobileappdev.inclass12;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

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
    private ArrayList<String> keyList;

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_threads);
        setTitle("Message Threads");

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

        if(userInfo != null){
            userName.setText(userInfo.get(0));
            uid = userInfo.get(1).toString();
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageThreads.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String threadText = inputTopic.getText().toString();

                Thread thread = new Thread();
                thread.setThreadName(threadText);
                thread.setUid(uid);
                thread.setMessages(new HashMap<String, Message>());

                pushThreadData(thread);
                inputTopic.setText("");
                hideKeyboard(v);
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
                buildThreadList();
                buildKeyList();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Thread thread = postSnapshot.getValue(Thread.class);
                    threads.add(thread);
                    keyList.add(postSnapshot.getKey());

                }
                buildAdapterIfNecessary();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("DB", "Failed to read value.", error.toException());
            }
        });
    }

    private void buildKeyList() {
        if(keyList == null) {
            keyList = new ArrayList<>();
        } else {
            keyList.clear();
        }
    }

    private void buildThreadList() {
        if(threads == null) {
            threads = new ArrayList<>();
        } else {
            threads.clear();
        }
    }

    private void buildAdapterIfNecessary() {
        if(adapter == null || (threads == null || threads.size() == 0)) {
            adapter = new ThreadsAdapter(threads, MessageThreads.this, MessageThreads.this, uid);
            recyclerView.setAdapter(adapter);
            LinearLayoutManager horizontalLayoutManager
                    = new LinearLayoutManager(MessageThreads.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(horizontalLayoutManager);
            recyclerView.setNestedScrollingEnabled(false);
            adapter.notifyDataSetChanged();
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void recyclerViewListClicked(View v, int position){
        String threadKey = keyList.get(position);
        String threadName = threads.get(position).getThreadName();

        ArrayList<String> threadInfo = new ArrayList<>();
        threadInfo.add(threadKey);
        threadInfo.add(uid);
        threadInfo.add(threadName);

        Intent intent = new Intent(this, MessageView.class);
        intent.putStringArrayListExtra(Constants.INTENT_KEY, threadInfo);
        startActivity(intent);
    }

    @Override
    public void removeItem(View v, int position) {
        mDatabase.child("threads").child(keyList.get(position)).removeValue();
    }

}
