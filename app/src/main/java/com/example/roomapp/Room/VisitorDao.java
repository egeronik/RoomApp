package com.example.roomapp.Room;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface VisitorDao {
    @Query("SELECT * from Visitor")
    LiveData<List<Visitor>> getAll();

    @Query("SELECT * from Visitor WHERE vID =:visitorID")
    List<Visitor> findVisitorById(int visitorID);

    @Query("DELETE from Visitor")
    void nukeTable();

    @Insert
    void insert(Visitor visitor);

    @Delete
    void delete(Visitor visitor);

}

