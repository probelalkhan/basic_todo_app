package net.simplifiedcoding.taskorganizer.room;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import net.simplifiedcoding.taskorganizer.models.Todo;

@Database(entities = {Todo.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TodoDao taskDao();
}