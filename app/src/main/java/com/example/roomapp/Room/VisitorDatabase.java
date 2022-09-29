package com.example.roomapp.Room;

import android.content.Context;

import androidx.room.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Visitor.class},version = 1)
public abstract class VisitorDatabase extends RoomDatabase{
    public abstract VisitorDao visitorDao();
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    static public VisitorDatabase getDatabase(Context context, String dbName){
        return Room.databaseBuilder(context,VisitorDatabase.class,dbName).build();
    }

}
