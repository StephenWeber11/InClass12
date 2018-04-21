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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static FirebaseAuth mAuth;
    private static DatabaseReference mDatabase;
    private ValueEventListener listener;

    private EditText userNameField;
    private EditText passwordField;
    private Button login;
    private Button signup;

    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Login");

        userNameField = findViewById(R.id.loginemail);
        passwordField = findViewById(R.id.loginpassword);
        login = findViewById(R.id.loginbutton);
        signup = findViewById(R.id.signupButton);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        createListener();
        getUserData();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userNameField.getText().toString();
                String password = passwordField.getText().toString();
                login(email, password);

                Intent intent = new Intent(MainActivity.this, MessageThreads.class);
                ArrayList<String> userInfo = new ArrayList<>();
                userInfo.add(getUserFullName(email));
                userInfo.add(getUserID(email));
                intent.putStringArrayListExtra(Constants.INTENT_KEY, userInfo);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login(String email, String password) {
        final String emailAddr = email;
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AUTH", "signInWithEmail:success");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AUTH", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    protected void createListener() {
        listener = new ValueEventListener() {
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
        };
    }


    private void getUserData() {
        users = null;
        mDatabase.child("users").addValueEventListener(listener);
    }


    public static FirebaseAuth getmAuth() {
        return mAuth;
    }

    private String getUserFullName(String email) {
        String fullName = "";
        for(User user : users) {
            if(email.equals(user.getEmail())) {
                fullName = user.getFirstName() + " " + user.getLastName();
            }
        }

        return fullName;
    }

    private String getUserID(String email) {
        String uid = "";
        for(User user : users) {
            if(email.equals(user.getEmail())) {
                uid = user.getUid();
            }
        }

        return uid;
    }

}
