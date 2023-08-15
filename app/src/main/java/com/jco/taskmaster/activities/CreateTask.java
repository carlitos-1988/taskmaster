package com.jco.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.jco.taskmaster.R;

public class CreateTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Button addTaskButton = findViewById(R.id.CreateTaskActivitySubmitButton);

        addTaskButton.setOnClickListener(v -> {
            TextView myTextView = findViewById(R.id.CreateActivityTaskTitle);
            myTextView.setText("Submitted");
        });
    }
}