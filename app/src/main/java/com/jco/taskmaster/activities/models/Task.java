package com.jco.taskmaster.activities.models;
// TODO: Create model subpackage and create new class for each of the items in the Recycler, also crete data class
public class Task {
    String taskName;
    String description;

    public Task(String taskName, String description) {
        this.taskName = taskName;
        this.description = description;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
