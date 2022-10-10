package com.example.roomapp;

import android.content.ContentValues;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.roomapp.Room.Visitor;
import com.example.roomapp.Room.VisitorDao;
import com.example.roomapp.Room.VisitorDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class VisitorRepository {

    private final VisitorDao mVisitorDao;
    private LiveData<List<Visitor>> mAllVisitors = new MutableLiveData<>();


    public VisitorRepository(Context context, String dbName) {

        VisitorDatabase visitorDatabase = VisitorDatabase.getClearDatabase(context, dbName);
        this.mVisitorDao = visitorDatabase.visitorDao();
        this.mAllVisitors = mVisitorDao.getAll();
    }


    public void insert(Visitor visitor) {
        VisitorDatabase.databaseWriteExecutor.execute(() -> mVisitorDao.insert(visitor));
    }

    public void delete(Visitor visitor) {
        VisitorDatabase.databaseWriteExecutor.execute(() -> mVisitorDao.delete(visitor));
    }

    public List<Visitor> getVisitorsById(Integer ID) {
        Future<List<Visitor>> future = VisitorDatabase.databaseWriteExecutor.submit(() -> mVisitorDao.findVisitorById(ID));
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<Visitor>> getAllVisitors() {
        return mAllVisitors;
    }
}
