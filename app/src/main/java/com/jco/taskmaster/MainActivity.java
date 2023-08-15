package com.jco.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.jco.taskmaster.activities.AllTasks;
import com.jco.taskmaster.activities.CreateTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button viewAllTasksButton = findViewById(R.id.mainActivityAllTask);

        viewAllTasksButton.setOnClickListener(v -> {
            System.out.println("Button Clicked");

            //grab button that will trigger the intent
            //set an onClick listener
            //create intent .. tell intent where you came from and where youre headed
            Intent goToOrderFormIntent = new Intent(MainActivity.this, AllTasks.class);
            //Start the intent (or trigger it)
            startActivity(goToOrderFormIntent);
            //may also be written as MainActivity.this.starActivity(go to OrderForm intent)<-- dont need where you came from in the intent
        });

        Button addTaskButton = findViewById(R.id.MainActivityAddTask);

        addTaskButton.setOnClickListener(v -> {
            System.out.println("add task button clicked");
            Intent goToCreateFormIntent = new Intent(MainActivity.this, CreateTask.class);
            startActivity(goToCreateFormIntent);
        });




    }
}