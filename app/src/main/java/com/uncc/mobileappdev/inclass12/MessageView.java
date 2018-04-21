package com.uncc.mobileappdev.inclass12;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class MessageView extends AppCompatActivity implements RecyclerViewClickListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private TextView threadName;
    private ImageButton add;
    private ImageButton back;
    private EditText newMessage;
    private RecyclerView recyclerView;
    private MessageAdapter adapter;

    private String threadKey;
    private String uid;
    private ArrayList<Message> messages;
    private ArrayList<User> users;
    private ArrayList<String> keyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        setTitle("Chat Room");

        threadName = findViewById(R.id.ThreadName);
        add = findViewById(R.id.imageButtonAddMessage);
        back = findViewById(R.id.imageButtonReturnToThreads);
        newMessage = findViewById(R.id.messageEntry);

        Intent intent = getIntent();
        ArrayList<String> threadInfo = intent.getStringArrayListExtra(Constants.INTENT_KEY);
        threadKey = threadInfo.get(0);
        uid = threadInfo.get(1);
        threadName.setText(threadInfo.get(2));

        mAuth = MainActivity.getmAuth();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        getUserData();
        getMessageData();

        recyclerView = findViewById(R.id.messageRecycleListView);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = newMessage.getText().toString();
                Message message = new Message(uid, messageText, new Date(), getUserFullName());
                pushMessage(message);
                newMessage.setText("");
                hideKeyboard(v);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageView.this, MessageThreads.class);
                ArrayList<String> userInfo = new ArrayList<>();
                userInfo.add(getUserFullName());
                userInfo.add(uid);
                intent.putStringArrayListExtra(Constants.INTENT_KEY, userInfo);
                startActivity(intent);
            }
        });
    }

    private void getMessageData() {
        messages = null;
        mDatabase.child("threads").child(threadKey).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                buildMessages();
                buildKeyList();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Message message = postSnapshot.getValue(Message.class);
                    messages.add(message);
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

    private void buildMessages() {
        if(messages == null) {
            messages = new ArrayList<>();
        } else {
            messages.clear();
        }
    }

    private void buildKeyList() {
        if(keyList == null) {
            keyList = new ArrayList<>();
        } else {
            keyList.clear();
        }
    }

    private void buildAdapterIfNecessary() {
        if(adapter == null || (messages == null || messages.size() == 0)) {
            adapter = new MessageAdapter(messages, getUser(), MessageView.this, MessageView.this);
            recyclerView.setAdapter(adapter);
            LinearLayoutManager horizontalLayoutManager
                    = new LinearLayoutManager(MessageView.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(horizontalLayoutManager);
            recyclerView.setNestedScrollingEnabled(false);
            adapter.notifyDataSetChanged();
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void pushMessage(Message message) {
        mDatabase.child("threads").child(threadKey).child("messages").push().setValue(message);
    }

    private User getUser() {
        User loggedInUser = null;
        for(User user : users) {
            if(user.getUid().equals(uid)){
                loggedInUser = user;
                break;
            }
        }

        return loggedInUser;
    }

    private String getUserFullName() {
        User user = getUser();

        StringBuilder sb = new StringBuilder();
        sb.append(user.getFirstName());
        sb.append(" ");
        sb.append(user.getLastName());

        return sb.toString();
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }

    @Override
    public void removeItem(View v, int position) {
        mDatabase.child("threads").child(threadKey).child("messages").child(keyList.get(position)).removeValue();
    }
}
