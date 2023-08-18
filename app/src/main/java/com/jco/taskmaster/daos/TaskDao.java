package com.jco.taskmaster.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.jco.taskmaster.models.Task;

import java.util.List;

@Dao // think of this as a spring JPA repository but have to implement SQL ourselves
public interface TaskDao {

    @Insert
    public void insertATask(Task task);

    //Task name comes from the @Entity of the model class
    @Query("SELECT * FROM Task")
    public List<Task> findAll();

    @Query("SELECT * FROM Task ORDER BY taskName ASC")
    public List<Task> findAllSortedByName();

    @Query("SELECT * FROM Task WHERE id  = :id")
    Task findByAnId(long id);
}
