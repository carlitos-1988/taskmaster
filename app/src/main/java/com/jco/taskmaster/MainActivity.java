package com.jco.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.jco.taskmaster.activities.AllTasks;
import com.jco.taskmaster.activities.CreateTask;
import com.jco.taskmaster.activities.SettingsPage;
import com.jco.taskmaster.activities.TaskDetailActivity;
import com.jco.taskmaster.adapters.TaskListRecyclerViewAdapter;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    public static final String USERNAME_SET = "definedUserName";
    public static final String TASK_TITLE_TAG = "taskTitle";
    SharedPreferences preferences;
    TaskListRecyclerViewAdapter adapter;

    List<Task>taskItems =  new ArrayList<>();

    public static final String DATABASE_NAME = "juan_task_database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);


        setupDatabase();
        viewAllTasks();
        addTaskButton();
        viewTaskDetails();
        viewSettings();
        // TODO: Task instances must be created before we hand the products into the RecyclerView
//        createTaskInstance();
        setupRecyclerView();
        queryForAWSDatabase();
        //akeTeamInstances();


    }

    @Override
    protected void onResume(){
        super.onResume();

        queryForAWSDatabase();
        setupUsernameTextView();
        updateTasksFromDatabase();
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
                    runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                            });
                },
                failure -> Log.i(TAG, "did not read products successfully")
        );
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

    void updateTasksFromDatabase(){

//        taskItems.clear();
//        taskItems.addAll(taskDatabase.taskDatabaseDao().findAll());
//        adapter.notifyDataSetChanged();//tells recycler view that there is data and to update it


    }

    void makeTeamInstances(){


        Team team2 = Team.builder()
                .name("La Onda")
                .build();
        Amplify.API.mutate(
                ModelMutation.create(team2),
                successResponse -> Log.i(TAG, "Main Activity CreatedContactInstances() made a contact successfully"),
                failureResponse -> Log.i(TAG, "Main Activity CreatedContactInstances() FAILED"+ failureResponse)
        );
    }



//    void createTaskInstance(){
//
//        // TODO: Step 2-2  cont: fill list with data
//        taskItems.add(new Task("go to school", "Get Good Grades", TaskClass.TaskState.NEW));
//        taskItems.add(new Task("Take Medicine", "Take all medicine", TaskClass.TaskState.NEW));
//        taskItems.add(new Task("Finish Homework", "Complete all math exercises", TaskClass.TaskState.NEW));
//        taskItems.add(new Task("Buy Groceries", "Purchase fruits, vegetables, and bread", TaskClass.TaskState.NEW));
//        taskItems.add(new Task("Call Mom", "Give Mom a call to catch up", TaskClass.TaskState.NEW));
//        taskItems.add(new Task("Write Blog Post", "Compose an article about technology trends", TaskClass.TaskState.NEW));
//        taskItems.add(new Task("Go for a Run", "Run for 30 minutes in the park", TaskClass.TaskState.NEW));
//        taskItems.add(new Task("Clean the Garage", "Organize tools and clear clutter", TaskClass.TaskState.NEW));
//        taskItems.add(new Task("Prepare Presentation", "Gather data and create slides for meeting", TaskClass.TaskState.NEW));
//        taskItems.add(new Task("Read a Book", "Read the first three chapters of 'The Great Novel'", TaskClass.TaskState.NEW));
//        taskItems.add(new Task("Plan Weekend Trip", "Research destinations and make travel itinerary", TaskClass.TaskState.NEW));
//        taskItems.add(new Task("Practice Guitar", "Play scales and learn a new chord progression", TaskClass.TaskState.NEW));
//
//    }


}