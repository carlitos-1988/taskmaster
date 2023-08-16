package com.jco.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jco.taskmaster.activities.AllTasks;
import com.jco.taskmaster.activities.CreateTask;
import com.jco.taskmaster.activities.SettingsPage;
import com.jco.taskmaster.activities.TaskDetailActivity;

public class MainActivity extends AppCompatActivity {

    public static final String USERNAME_SET = "definedUserName";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        viewAllTasks();
        addTaskButton();
        viewTaskDetails();
        viewSettings();
    }

    @Override
    protected void onResume(){
        super.onResume();

        viewAllTasks();
        addTaskButton();
        viewTaskDetails();
        viewSettings();
        setupUsernameTextView();
    }

    void setupUsernameTextView(){
        String username = preferences.getString(USERNAME_SET, "No Username");
        ((TextView)findViewById(R.id.MainActivityUsernameTextView)).setText(username);
    }

    void viewAllTasks(){
        Button viewAllTasksButton = findViewById(R.id.mainActivityAllTask);

        viewAllTasksButton.setOnClickListener(v -> {

            System.out.println("Button Clicked");

            //grab button that will trigger the intent
            //set an onClick listener
            //create intent .. tell intent where you came from and where you're headed
            Intent goToOrderFormIntent = new Intent(MainActivity.this, AllTasks.class);
            //Start the intent (or trigger it)
            startActivity(goToOrderFormIntent);
            //may also be written as MainActivity.this.starActivity(go to OrderForm intent)<-- don't need where you came from in the intent
        });
    }

    void addTaskButton(){
        Button addTaskButton = findViewById(R.id.MainActivityAddTask);

        //v lambda is View view same thing
        addTaskButton.setOnClickListener(v -> {
            System.out.println("add task button clicked");
            Intent goToCreateFormIntent = new Intent(MainActivity.this, CreateTask.class);
            startActivity(goToCreateFormIntent);
        });
    }

    void viewTaskDetails(){
        Button viewTaskDetailsTaskButton = findViewById(R.id.AllActivitiesTaskDetailActivities);

        viewTaskDetailsTaskButton.setOnClickListener(v -> {
            Intent goToTaskDetailsIntent = new Intent(MainActivity.this, TaskDetailActivity.class);
            startActivity(goToTaskDetailsIntent);
        });
    }

    void viewSettings(){
        ImageButton viewSettingsTaskButton = findViewById(R.id.SetUsernameActivityButton);

        viewSettingsTaskButton.setOnClickListener(v -> {
            Intent goToViewSettingActivity = new Intent(MainActivity.this, SettingsPage.class);
            startActivity(goToViewSettingActivity);
        });
    }

}