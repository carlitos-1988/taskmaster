package com.jco.taskmaster.models;
// TODO: Create model subpackage and create new class for each of the items in the Recycler, also crete data class


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
        public long id;
    String taskName;
    String description;

    TaskStatuses status;
    java.util.Date dateCreated;

    public Task(String taskName, String description, TaskStatuses status) {
        this.taskName = taskName;
        this.description = description;
        this.status = status;
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

    public TaskStatuses getStatus() {
        return this.status;
    }

    public void setStatus(TaskStatuses status) {
        this.status = status;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
