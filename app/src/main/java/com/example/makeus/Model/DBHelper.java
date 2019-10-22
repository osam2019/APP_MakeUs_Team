package com.example.makeus.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "SoldierDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table soldierList (" +
                "name char(10)" +
                "squad varchar(20)" +
                "rank char(10)" +
                "millitary_number char(12)" +
                "birthday int" +
                "enlistment_day int" +
                "transfer_day int" +
                ")";
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
