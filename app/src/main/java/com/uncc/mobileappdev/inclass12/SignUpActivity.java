package com.uncc.mobileappdev.inclass12;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Stephen on 4/17/2018.
 */

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private EditText fNameField;
    private EditText lNameField;
    private EditText passField;
    private EditText passConfirmField;
    private EditText emailField;
    private Button cancelButon;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = MainActivity.getmAuth();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        fNameField = findViewById(R.id.FirstName);
        lNameField = findViewById(R.id.LastName);
        passField = findViewById(R.id.SignupPassword);
        passConfirmField = findViewById(R.id.SignupRePassword);
        emailField = findViewById(R.id.SignupEmail);
        cancelButon = findViewById(R.id.CancelButton);
        submitButton = findViewById(R.id.registerButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = fNameField.getText().toString();
                String lastName = lNameField.getText().toString();
                String password = passField.getText().toString();
                String passwordConfirmed = passConfirmField.getText().toString();
                String email = emailField.getText().toString();

                if(doPasswordsMatch(password, passwordConfirmed)) {
                    signup(email, password);
                    String uid = String.valueOf(generateUID());
                    pushUserData(firstName, lastName, uid, email);

                    Intent intent = new Intent(SignUpActivity.this, MessageThreads.class);
                    ArrayList<String> userInfo = new ArrayList<>();
                    userInfo.add(firstName);
                    userInfo.add(uid);
                    intent.putStringArrayListExtra(Constants.INTENT_KEY, userInfo);
                } else {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
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
                            Toast.makeText(SignUpActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void pushUserData(String firstName, String lastName, String uid, String email) {
        mDatabase.child("users").push().setValue(new User(firstName, lastName, uid, email));
    }

    private boolean doPasswordsMatch(String passOne, String passTwo) {
        return passOne.equals(passTwo)
                && (passOne != null || passTwo != null)
                && (!Constants.EMPTY_STRING.equals(passOne) || !Constants.EMPTY_STRING.equals(passTwo));
    }

    private int generateUID() {
        Double randomID = Math.random() * 64;
        return randomID.intValue();

    }
}
