package com.uncc.mobileappdev.inclass12;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private EditText userNameField;
    private EditText passwordField;
    private Button login;
    private Button signup;

    private ArrayList<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameField = findViewById(R.id.loginemail);
        passwordField = findViewById(R.id.loginpassword);
        login = findViewById(R.id.loginbutton);
        signup = findViewById(R.id.signupbutton);

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userNameField.getText().toString();
                String password = passwordField.getText().toString();
                login(email, password);

                Intent intent = new Intent(MainActivity.this, MessageThreads.class);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AUTH", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AUTH", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signup(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d("AUTH", "createUserWithEmail:SUCCESS");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            Log.d("AUTH", "createUserWithEmail:FAILURE", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void pushUserData(String firstName, String lastName, String uid) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").push().setValue(new User(firstName, lastName, uid));
    }

    private void pushMessageData() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
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

    private boolean doPasswordsMatch(String passOne, String passTwo) {
        return passOne.equals(passTwo);
    }
}
