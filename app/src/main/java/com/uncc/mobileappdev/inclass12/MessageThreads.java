package com.uncc.mobileappdev.inclass12;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

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

public class MessageThreads extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private TextView userName;
    private ImageButton logout;
    private ImageButton addButton,removeButton;
    private EditText inputTopic;
    private ListView listView;

    private ArrayList<User> users;
    private ArrayList<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_threads);

        mAuth = MainActivity.getmAuth();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        userName = findViewById(R.id.UsernameMessage);
        logout = findViewById(R.id.logoutButton);
        addButton = findViewById(R.id.imageButtonAdd);
        removeButton = findViewById(R.id.imageButtonDelete);
        inputTopic = findViewById(R.id.inputTopic);

        Intent intent = getIntent();
        String name = intent.getStringExtra(Constants.INTENT_KEY);
        userName.setText(name);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageThreads.this, MainActivity.class);
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

    private void pushMessageData() {
        mDatabase.child("messages").push().setValue(new Message(null, 0, null));
    }

    private void getMessageData() {
        messages = null;
        mDatabase.child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messages = new ArrayList<>();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Message value = postSnapshot.getValue(Message.class);
                    messages.add(value);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("DB", "Failed to read value.", error.toException());
            }
        });
    }
}
