package com.jco.taskmaster.activities.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jco.taskmaster.R;
import com.jco.taskmaster.activities.TaskClass;

import java.util.List;

// TODO: Step 1-4: Make a customs RecyclerView class which  extends RecyclerView.Adapter
public class TaskListRecyclerViewAdapter extends RecyclerView.Adapter{

    //TODO: Step 2-3 cont: Create a Task List variable and constructor within the adapter

    List<TaskClass> tasks;

    public  TaskListRecyclerViewAdapter(List<TaskClass> tasks){
        this.tasks = tasks;
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
        
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    //TODO: Step 1-8: Make a ViewHolder class to hold our fragments(nested within ProductListRecyclerViewAdapter
    public static class TaskListViewHolder extends RecyclerView.ViewHolder{

        public TaskListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
