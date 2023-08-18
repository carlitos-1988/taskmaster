package com.jco.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.jco.taskmaster.MainActivity;
import com.jco.taskmaster.R;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        setupTaskNameTextView();
    }

    //TODO: Method from the TaskListRecyclerView sends intent to this page to display data of the task Clicked
    void setupTaskNameTextView(){
        Intent callingIntent = getIntent();
        String taskNameString = null;

        if(callingIntent != null){
            taskNameString = callingIntent.getStringExtra(MainActivity.TASK_TITLE_TAG);
        }

        TextView taskNameTextView = (TextView) findViewById(R.id.TaskDetailsActivityTitle);
        if(taskNameString != null && !taskNameTextView.equals("")){
            taskNameTextView.setText(taskNameString);
        }else{
            taskNameTextView.setText((R.string.blank_product_name));
        }
    }
}