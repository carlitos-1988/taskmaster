package com.jco.taskmaster.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TaskCategoryEnum;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.material.snackbar.Snackbar;

import com.jco.taskmaster.R;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CreateTask extends AppCompatActivity {

    CompletableFuture<List<Team>> teamFuture = null;

    private final String TAG = "ADD_PRODUCT_ACTIVITY";
    Spinner teamSpinner;
    Spinner taskCategorySpinner;
    ImageView taskImageView;
    ActivityResultLauncher<Intent> activityResultLauncher;
    private String s3ImageKey = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        teamFuture = new CompletableFuture<>();

        activityResultLauncher = getImagePickingActivityResultLauncher();
        teamSpinner = findViewById(R.id.team_contact_select);
        taskCategorySpinner = findViewById(R.id.TaskSpinnerSelector);
        taskImageView = findViewById(R.id.AddProductActivityProductImageView);

        setupTaskImageView();
        setupCategorySpinner(taskCategorySpinner);
        setupTeamSpinner();
        setupSaveButton(taskCategorySpinner);
//        addTask();
    }


    void setupTeamSpinner(){
        Amplify.API.query(
                ModelQuery.list(Team.class),
                success -> {
                   Log.i(TAG, "Read Teams from AWS Successfully");
                   ArrayList<String> teamNames = new ArrayList<>();
                   ArrayList<Team> teams = new ArrayList<>();

                    for(Team team: success.getData()){
                        teamNames.add(team.getName());
                        teams.add(team);
                    }

                    teamFuture.complete(teams);

                    runOnUiThread(()->{
                        teamSpinner.setAdapter(new ArrayAdapter<>(
                                this,
                                android.R.layout.simple_spinner_item,
                                teamNames
                        ));
                    });
                },
                failure -> {
                    teamFuture.complete(null);
                    Log.i(TAG, "Did not read contacts successfully!!");
                }
        );
    }

    void setupCategorySpinner(Spinner taskCategorySpinner){

        taskCategorySpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                TaskCategoryEnum.values()
        ));
    }

    void setupSaveButton(Spinner taskCategorySpinner){
        Button submitTaskButton = findViewById(R.id.CreateTaskActivitySubmitButton);

        submitTaskButton.setOnClickListener(v -> {

            String selectedTeamString = teamSpinner.getSelectedItem().toString();
            List<Team> teams = null;
            try{
                teams = teamFuture.get();
            } catch (InterruptedException ie){
                Log.e(TAG, "Interrupted exception while getting contacts");
                Thread.currentThread().interrupt();
            }catch (ExecutionException ee){
                Log.e(TAG, "Execution exception while getting contacts");
            }

            assert teams != null;
            Team selectedTeam = teams.stream().filter(c -> c.getName().equals(selectedTeamString)).findAny().orElseThrow(RuntimeException::new);

            //TODO: Make a Dynamo DB/GraphQL call
            Task taskToSave = Task.builder()
                    .taskName(((EditText)findViewById(R.id.CreateActivityTaskTitleInput)).getText().toString())
                    .description(((EditText)findViewById(R.id.CreateActivityTaskDescriptionInput)).getText().toString())
                    .dateCreated(new Temporal.DateTime(new Date(),0))
                    .taskCategory((TaskCategoryEnum) taskCategorySpinner.getSelectedItem())
                    .contactTeam(selectedTeam)
                    .taskImageS3Key(s3ImageKey)
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(taskToSave),     //makes a GraphQL request to cloud
                    successResponse -> Log.i(TAG, "AddProductActivity.setUPSaveButton(): made product successfully" + successResponse),
                    failureResponse -> Log.i(TAG, "AddProductActivity.setUPSaveButton(): Failed with this response "+ failureResponse)
            );


//            taskDatabase.taskDatabaseDao().insertATask(taskToSave);
            //Notify dataset changes for recycler view
            Snackbar.make(findViewById(R.id.AddTaskActivityPage), "Product Saved", Snackbar.LENGTH_SHORT).show();
        });
    }

    void setupTaskImageView(){
        taskImageView.setOnClickListener(v ->{
            launchImageSelectionIntent();
        });
    }

    void launchImageSelectionIntent(){
        // Part 1: Launch Android's activity to pick an image file
        Intent imageFilePickingIntent = new Intent(Intent.ACTION_GET_CONTENT); // one of several file picking activities built into Android
        imageFilePickingIntent.setType("*/*"); // only allow one kind or category of file; if you don't have this, you get a very cryptic error about "No activity found to handle Intent"
        imageFilePickingIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg", "image/png"}); // only pick JPEG and PNG images

        // Launch Android's built-in file picking activity using our newly created ActivityResultLauncher below
        activityResultLauncher.launch(imageFilePickingIntent);
    }

    ActivityResultLauncher<Intent> getImagePickingActivityResultLauncher() {
        ActivityResultLauncher<Intent> imagePickingActivityResultLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            // Part 2: Android calls your callback with the picked image
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                Uri pickedImageFileUri = result.getData().getData();
                                try {
                                    InputStream pickedImageInputStream = getContentResolver().openInputStream(pickedImageFileUri);
                                    String pickedImageFilename = getFileNameFromUri(pickedImageFileUri); // nicer way of extracting filename from a Uri
                                    Log.i(TAG, "Succeeded in getting input stream from file on phone! Filename is: " + pickedImageFilename);
                                    uploadInputStreamToS3(pickedImageInputStream, pickedImageFilename, pickedImageFileUri);
                                } catch (FileNotFoundException fnfe) {
                                    Log.e(TAG, "Could not get file form file picker! " + fnfe.getMessage(), fnfe);
                                }
                            }
                        }
                );
        return imagePickingActivityResultLauncher;
    }

    void uploadInputStreamToS3(InputStream pickedImageInputStream, String pickedImageFilename, Uri pickedImageFileUri) {
        // Part 3: Use our InputStream to upload file to S3

        Amplify.Storage.uploadInputStream(
                pickedImageFilename, // S3 key
                pickedImageInputStream,
                success -> {
                    // confirm that we succeeded our upload and grab the key
                    Log.i(TAG, "Succeeded in getting file uploaded to S3! Key is: " + success.getKey());
                    s3ImageKey = success.getKey(); // non-empty s3ImageKey indicates an image was picked in this activity
                    // TODO: update our saveProduct to include the s3 key

                    // grabbing a new input stream since we used the original for uploading to S3
                    InputStream pickedImageInputStreamCopy = null; // need to make a copy because InputStreams cannot be reused!
                    try {
                        pickedImageInputStreamCopy = getContentResolver().openInputStream(pickedImageFileUri);
                    } catch (FileNotFoundException fnfe) {
                        Log.e(TAG, "Could not get file stream from URI! " + fnfe.getMessage(), fnfe);
                    }

                    // set te imageView with the selected image
                    taskImageView.setImageBitmap(BitmapFactory.decodeStream(pickedImageInputStreamCopy));
                },
                failure -> {
                    Log.e(TAG, "Failure in uploading file to S3 with filename: " + pickedImageFilename + " with error: " + failure.getMessage());
                }
        );
    }

        // Taken from https://stackoverflow.com/a/25005243/16889809
        @SuppressLint("Range")
        public String getFileNameFromUri(Uri uri) {
            String result = null;
            if (uri.getScheme().equals("content")) {
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            }
            if (result == null) {
                result = uri.getPath();
                int cut = result.lastIndexOf('/');
                if (cut != -1) {
                    result = result.substring(cut + 1);
                }
            }
            return result;
        }



}