package com.jco.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.jco.taskmaster.MainActivity;
import com.jco.taskmaster.R;

import java.io.File;

public class TaskDetailActivity extends AppCompatActivity {

    private static String TAG = "OrderFormActivity";


    Intent callingIntent;
    Task currentTask;
    String s3ImageKey;

    Button announceButton;
    ImageView productImageView;
    TextView taskNameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        callingIntent = getIntent();

//        announceButton = findViewById(R.id.);
        productImageView = findViewById(R.id.TaskActivityProductImageView);
        taskNameView = findViewById(R.id.TaskDetailsActivityTitle);


        setupTaskNameTextView();
        setupTaskImageView();
    }

    //DONE: Method from the TaskListRecyclerView sends intent to this page to display data of the task Clicked
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

    void setupTaskImageView() {
        String taskId = "";

        if(callingIntent != null) {
            taskId = callingIntent.getStringExtra(MainActivity.PRODUCT_ID_EXTRA_TAG);
        }

        if(taskId != null && !taskId.equals("")) {
            // query for for product from dynamoDB
            Amplify.API.query(
                    ModelQuery.get(Task.class, taskId),
                    success -> {
                        Log.i(TAG, "successfully found product with id: " + success.getData().getId());
                        currentTask = success.getData();
                        populateImageView();
                    },
                    failure -> {
                        Log.i(TAG,"Failed to query product from DB: " + failure.getMessage());
                    }
            );
        }
    }

    void populateImageView() {
        // truncate folder name from product's s3key
        currentTask.getTaskImageS3Key();
        if(currentTask.getTaskImageS3Key() != null) {
            int cut = currentTask.getTaskImageS3Key().lastIndexOf('/');
            if(cut != -1) {
                s3ImageKey = currentTask.getTaskImageS3Key().substring(cut + 1);
            }
        }

        if(s3ImageKey != null && !s3ImageKey.isEmpty()) {
            Amplify.Storage.downloadFile(
                    s3ImageKey,
                    new File(getApplication().getFilesDir(), s3ImageKey),
                    success -> {
                        productImageView.setImageBitmap(BitmapFactory.decodeFile(success.getFile().getPath()));
                    },
                    failure -> {
                        Log.e(TAG, "Unable to get image from S3 for the product for S3 key: " + s3ImageKey + " for reason: " + failure.getMessage());
                    }
            );
        }
    }
}