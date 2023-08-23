package com.jco.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TaskCategoryEnum;
import com.google.android.material.snackbar.Snackbar;

import com.jco.taskmaster.R;
import com.jco.taskmaster.models.TaskStatuses;

import java.util.Date;

public class CreateTask extends AppCompatActivity {

    private final String TAG = "Add Product Activity";
    Spinner productCategorySpinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        productCategorySpinner = findViewById(R.id.TaskSpinnerSelector);



        Spinner taskCategorySpinner = findViewById(R.id.TaskSpinnerSelector);

        setupCategorySpinner(taskCategorySpinner);
        setupSaveButton(taskCategorySpinner);
//        addTask();
    }

//    void addTask(){
//        Button addTaskButton = findViewById(R.id.CreateTaskActivitySubmitButton);
//
//        addTaskButton.setOnClickListener(v -> {
//            TextView myTextView = findViewById(R.id.CreateActivityTaskTitle);
//            myTextView.setText("Submitted");
//        });
//    }

    void setupCategorySpinner(Spinner taskCategorySpinner){

        taskCategorySpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                TaskStatuses.values()
        ));
    }

    void setupSaveButton(Spinner taskCategorySpinner){
        Button submitTaskButton = findViewById(R.id.CreateTaskActivitySubmitButton);

        submitTaskButton.setOnClickListener(v -> {
//            Task taskToSave = new Task(
//                    ((EditText)findViewById(R.id.CreateActivityTaskTitleInput)).getText().toString(),
//                    ((EditText)findViewById(R.id.CreateActivityTaskDescriptionInput)).getText().toString(),
//                    new Date(),
//                    TaskStatuses.fromString(taskCategorySpinner.getSelectedItem().toString())
            //TODO: Make a Dynamo DB/GraphQL call
            Task taskToSave = Task.builder()
                    .taskName(((EditText)findViewById(R.id.CreateActivityTaskTitleInput)).getText().toString())
                    .description(((EditText)findViewById(R.id.CreateActivityTaskDescriptionInput)).getText().toString())
                    .dateCreated(new Temporal.DateTime(new Date(),0))
                    .taskCategory((TaskCategoryEnum) taskCategorySpinner.getSelectedItem())
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(taskToSave),     //makes a GraphQL request to cloud
                    successResponse -> Log.i(TAG, "AddProductActivity.setUPSaveButton(): made product successfully"),
                    failureResponse -> Log.i(TAG, "AddProductActivity.setUPSaveButton(): Failed with this response "+ failureResponse)
            );


//            taskDatabase.taskDatabaseDao().insertATask(taskToSave);
            //Notify dataset changes for recycler view
            Snackbar.make(findViewById(R.id.AddTaskActivityPage), "Product Saved", Snackbar.LENGTH_SHORT).show();
        });
    }
}