package com.jco.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.nfc.Tag;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult;
import com.amplifyframework.auth.options.AuthSignOutOptions;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.jco.taskmaster.activities.AllTasks;
import com.jco.taskmaster.activities.CreateTask;
import com.jco.taskmaster.activities.SettingsPage;
import com.jco.taskmaster.activities.TaskDetailActivity;
import com.jco.taskmaster.adapters.TaskListRecyclerViewAdapter;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    public static final String USERNAME_SET = "definedUserName";
    public static final String TASK_TITLE_TAG = "taskTitle";
    public static final String PRODUCT_ID_EXTRA_TAG = "productId";



    List<Task>taskItems =  new ArrayList<>();
    List<Team> teams = new ArrayList<>();

    SharedPreferences preferences;
    TaskListRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        viewAllTasks();
        addTaskButton();
        viewTaskDetails();
        viewSettings();

        setupRecyclerView();
        queryForAWSDatabase();

    }

    @Override
    protected void onResume(){
        super.onResume();

        queryForAWSDatabase();
        setupUsernameTextView();
        //updateTasksFromDatabase();
    }

    void queryForAWSDatabase(){
        //TODO: Make a Dynamo DB/GraphQL call

        Amplify.API.query(
                ModelQuery.list(Task.class),
                success -> {
                    Log.i(TAG, "Read products successfully");
                    taskItems.clear();
                    for (Task task: success.getData()){
                        taskItems.add(task);
                    }
                    runOnUiThread(() -> adapter.notifyDataSetChanged());
                },
                failure -> Log.i(TAG, "did not read products successfully")
        );

//        Amplify.API.query(
//                ModelQuery.list(Team.class),
//                success -> {
//                    Log.i(TAG, "Read products successfully");
//                    taskItems.clear();
//                    for (Team team: success.getData()){
//                        teams.add(team);
//                    }
//                    runOnUiThread(() -> adapter.notifyDataSetChanged());
//                },
//                failure -> Log.i(TAG, "did not read products successfully")
//        );
    }

    void setupDatabase(){

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

    //recycler view
    void setupRecyclerView(){
        // TODO: 1-2 Grab RecyclerView at the same time make sure it is called inside of the onCreateMethod
        RecyclerView taskListRecyclerView = findViewById(R.id.MainActivityTaskRecyclerView);

        //TODO: 1-3: Set the layout manager for the RecyclerView to a linear Layout Manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        taskListRecyclerView.setLayoutManager(layoutManager);

        //Extra RecyclerVie Styling add item Decoration with desired spacing this part can exists in own file
        int spaceInPixels = getResources().getDimensionPixelSize(R.dimen.product_fragment_spacing);
        taskListRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = spaceInPixels;
            }
        });


        // TODO: Step 1-5: Create and attach RecyclerView.Adapter to RecyclerView
//        TaskListRecyclerViewAdapter adapter = new TaskListRecyclerViewAdapter(taskItems, this);

        // TODO: 2-3: Hand data items from main Activity to our RecyclerViewAdapter
        adapter = new TaskListRecyclerViewAdapter(taskItems, this);
        taskListRecyclerView.setAdapter(adapter);

        //After this step created fragments directory and created new fragment from new it comes with prebuilt code

    }

    void updateTasksFromDatabase() {
    }

    void manualS3FileUpload(){
        String testFileName = "Test_File_Name";
        File testUploadFile = new File(getApplicationContext().getFilesDir(), testFileName);

        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(testUploadFile));
            bufferedWriter.append("Some random new text for AWS \n this should be another line");
            bufferedWriter.close();

        }catch (IOException e) {
            Log.e(TAG, "could not write file locally with name: "+ testFileName);
        }

        String testFileKey = "SomeFile.txt";

        Amplify.Storage.uploadFile(
                testFileKey,
                testUploadFile,
                success -> {
                    Log.i(TAG, "S3 uploaded successfully! key is: " + success.getKey());
                },
                failure ->{
                    Log.i(TAG, "S3 upload did not work " + failure.getMessage());
                }
        );
    }
}



//        Amplify.Auth.signUp("juan.c.olmedo@icloud.com",
//                "P@ssword123!",
//                AuthSignUpOptions.builder()
//                        .userAttribute(AuthUserAttributeKey.email(), "juan.c.olmedo@icloud.com")
//                        .userAttribute(AuthUserAttributeKey.nickname(), "Hwan")
//                        .build(),
//                        successResponse -> Log.i(TAG, "Signup succeeded: " + successResponse.toString()),
//                        failureResponse -> Log.i(TAG, "signup Failed: with username " + failureResponse.toString() )
//                );

//        Amplify.Auth.confirmSignUp("juan.c.olmedo@icloud.com",
//                "455619",
//                success -> {
//                        Log.i(TAG, "Verification succeeded");
//                        },
//                failure -> {
//                        Log.i(TAG, "Verification failed"+ failure.toString());
//        });

//        Amplify.Auth.signIn("juan.c.olmedo@icloud.com",
//                "P@ssword123!",
//                success -> Log.i(TAG, "Login Succeeded"+ success.toString()),
//                failure -> Log.i(TAG, "Login Failed" + failure.toString())
//                );


//Sign out Section
//        AuthSignOutOptions signOutOptions = AuthSignOutOptions.builder()
//                        .globalSignOut(true)
//                                .build();
//
//        Amplify.Auth.signOut(signOutOptions,
//                signOutResult -> {
//                    if(signOutResult instanceof AWSCognitoAuthSignOutResult.CompleteSignOut){
//                        Log.i(TAG, "Global signout successful");
//                    } else if (signOutResult instanceof  AWSCognitoAuthSignOutResult.PartialSignOut){
//                        Log.i(TAG, " Partial sign out successful ");
//
//                    } else if ( signOutResult instanceof  AWSCognitoAuthSignOutResult.FailedSignOut) {
//                        Log.i(TAG, "Logout Failed" + signOutResult.toString());
//                    }
//                }
//        );

// TODO: Task instances must be created before we hand the products into the RecyclerView

//        makeTeamInstances();
//        manualS3FileUpload();
//        createTaskInstance();