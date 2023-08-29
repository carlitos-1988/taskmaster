package com.jco.taskmaster.models;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskClass {

    private String title;
    private String body;
    private TaskState state;


    public enum TaskState{
        NEW,
        ASSIGNED,
        IN_PROGRESS,
        COMPLETE
    }

    public TaskClass() {
    }

    public TaskClass(String title, String body, TaskState state) {
        this.title = title;
        this.body = body;
        this.state = state;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public TaskState getState() {
        return this.state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "TaskClass{" +
                "title='" + this.title + '\'' +
                ", body='" + this.body + '\'' +
                ", state=" + this.state +
                '}';
    }
}
