package com.example.roomapp.Room;

import androidx.annotation.NonNull;
import androidx.room.*;

@Entity
public class Visitor {
    public Visitor() {
    }

    @PrimaryKey(autoGenerate = true)
    public int vID;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "table")
    public int table;

    @ColumnInfo(name = "phone")
    public String phone;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "book_time")
    public String bookTime;

    @ColumnInfo(name = "img")
    public String imgUri;

    public Visitor( String visitorName, int table, String phone, String email, String bookTime, String imgUri) {
        this.name = visitorName;
        this.table = table;
        this.phone = phone;
        this.email = email;
        this.bookTime = bookTime;
        this.imgUri = imgUri;

    }

    @NonNull
    @Override
    public String toString() {
        return "Visitor{" +
                "vID=" + vID +
                ", name='" + name + '\'' +
                ", table=" + table +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", bookTime='" + bookTime + '\'' +
                '}';
    }
}
