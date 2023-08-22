package com.jco.taskmaster.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jco.taskmaster.MainActivity;
import com.jco.taskmaster.R;
import com.jco.taskmaster.activities.TaskClass;
import com.jco.taskmaster.activities.TaskDetailActivity;
import com.jco.taskmaster.models.Task;

import java.util.List;

// TODO: Step 1-4: Make a customs RecyclerView class which  extends RecyclerView.Adapter
public class TaskListRecyclerViewAdapter extends RecyclerView.Adapter{

    //TODO: Step 2-3 cont: Create a Task List variable and constructor within the adapter
    //TODO: 3-2a cont Create a task list variable and constructor within the adapter
    List<Task> tasks;
    //TODO 3-2b: Create Contex variable and update constructor
    Context callingActivity;

    //TODO: 3-1 Refactor code so that it works with the custom Task model
    public  TaskListRecyclerViewAdapter(List<Task> tasks, Context context){
        this.tasks = tasks;
        this.callingActivity = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // TODO: step 1-7: Inflate the fragment( add the fragment to the viewHolder)
        // code below adds fragment to view holder
        View taskFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task_list,parent, false);
        //TODO: step 1-9 Attach fragment to the viewHolder
        return new TaskListViewHolder(taskFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //TODO: Step 2-4: Bind data items to Fragments inside of ViewHolders
        TextView taskFragmentTextView = (TextView) holder.itemView.findViewById(R.id.taskFragmentTextView);
        String itemFragmentText = (position+1)+ " " + tasks.get(position).getTaskName()
                + "\n" + tasks.get(position).getStatus();
        taskFragmentTextView.setText(itemFragmentText);

        //TODO: 3-3 Create onClick listener, make an intent inside of it with an extrato go to a new activity
        View productViewHolder = holder.itemView;
        productViewHolder.setOnClickListener(v->{
            Intent goToTaskDetailActivity = new Intent(callingActivity, TaskDetailActivity.class);
            goToTaskDetailActivity.putExtra(MainActivity.TASK_TITLE_TAG, tasks.get(position).getTaskName());
            callingActivity.startActivity(goToTaskDetailActivity);
        });
    }

    @Override
    public int getItemCount() {

        //TODO: 2-5: Make the size of the list dynamic based on the size of the product list
        return tasks.size();
    }

    //TODO: Step 1-8: Make a ViewHolder class to hold our fragments(nested within ProductListRecyclerViewAdapter
    public static class TaskListViewHolder extends RecyclerView.ViewHolder{

        public TaskListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
