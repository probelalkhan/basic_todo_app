package net.simplifiedcoding.taskorganizer.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import net.simplifiedcoding.taskorganizer.models.Todo;

import java.util.List;

@Dao
public interface TodoDao {

    @Query("SELECT * FROM Todo")
    List<Todo> getAll();

    @Insert
    void insert(Todo todo);

    @Delete
    void delete(Todo todo);

    @Update
    void update(Todo todo);
}
