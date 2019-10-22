package com.example.makeus.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.makeus.Module.SquadManager;

import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_squad_table_create = "create table 분대_1 (" +
                "name char(10), " +
                "squad varchar(20), " +
                "rank char(10), " +
                "milli_number char(12), " +
                "specialty varchar(20), " +
                "birthday int, " +
                "enlistment_day int, " +
                "transfer_day int, " +
                "discharge_day int, " +
                "discharge_flag int, " +
                "primary key(milli_number) " +
                ") ";

        db.execSQL(sql_squad_table_create);
    }

    public void createTable(DBHelper helper, String squad) {
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql_squad_table_create = "create table " + squad + " (" +
                "name char(10), " +
                "squad varchar(20), " +
                "rank char(10), " +
                "milli_number char(12), " +
                "specialty varchar(20), " +
                "birthday int, " +
                "enlistment_day int, " +
                "transfer_day int, " +
                "discharge_day int, " +
                "discharge_flag int, " +
                "primary key(milli_number) " +
                ") ";

        db.execSQL(sql_squad_table_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            switch (oldVersion) {
                /* 버전 갱신시 작성 */
            }
        }
    }
}
