package com.jco.taskmaster.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jco.taskmaster.daos.TaskDao;
import com.jco.taskmaster.models.Task;


@TypeConverters({TaskDatabaseConverter.class})
@Database(entities = {Task.class}, version = 1) //if database number it changed the db will be dropped
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDao taskDatabaseDao();
}
