package com.jco.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        teamFuture = new CompletableFuture<>();

        teamSpinner = findViewById(R.id.team_contact_select);
        taskCategorySpinner = findViewById(R.id.TaskSpinnerSelector);

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
                Log.e(TAG, "Interupted exception while getting contacts");
                Thread.currentThread().interrupt();
            }catch (ExecutionException ee){
                Log.e(TAG, "Execution exeption while getting contacts");
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
}