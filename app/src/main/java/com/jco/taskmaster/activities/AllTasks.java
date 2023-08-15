package com.jco.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jco.taskmaster.R;

//implements view.OnClickListener allows for @Override onClick if else if to be used
public class AllTasks extends AppCompatActivity implements View.OnClickListener {


    //Variables to be used in the methods outside of the OnCreate function
    private TextView myCounter;
    private int count =0;

    @Override
    public void  onClick(View v) {
        if (v.getId() == R.id.AllTasksToastButton) {
            Toast.makeText(this, "Button Show Toast Clicked", Toast.LENGTH_SHORT).show();
            count++;
            myCounter.setText("Counter: "+ count);
        }else if(v.getId() == R.id.AllTasksShowToastButton2){
            Toast.makeText(this, "Second Toast Clicked", Toast.LENGTH_SHORT).show();
        }else{
            System.out.println("Did not work");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_activities);

        Button showToastButton = findViewById(R.id.AllTasksToastButton);
        showToastButton.setOnClickListener(this);

         //initialize the myCounter variable here if not done it will remain null
        myCounter = findViewById(R.id.AllActivitiesCounter);

        //Long click implementation will not work on onClick void method above different type
        Button showToastButton2 = findViewById(R.id.AllTasksShowToastButton2);
        showToastButton2.setOnLongClickListener(view -> {
            Toast.makeText(this, "Some Long Click", Toast.LENGTH_SHORT).show();
            return true;
        });
    }



}