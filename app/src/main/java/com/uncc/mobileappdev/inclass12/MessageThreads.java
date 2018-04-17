package com.uncc.mobileappdev.inclass12;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Stephen on 4/16/2018.
 */

public class MessageThreads extends AppCompatActivity {

    TextView userName;
    ImageButton logout;
    ImageButton addButton,removeButton;
    EditText inputTopic;
    ListView listView;
    private static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_threads);

        userName = findViewById(R.id.text_view_user_name);
        logout = findViewById(R.id.logoutButton);
        addButton = findViewById(R.id.imageButtonAdd);
        removeButton = findViewById(R.id.imageButtonDelete);
        inputTopic = findViewById(R.id.inputTopic);



    }
}
