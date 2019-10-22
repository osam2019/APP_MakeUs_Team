package com.example.makeus.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.makeus.Module.SquadManager;

import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

    final static String name = "makeus";
    public DBHelper() {
        super(null, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //용사 테이블 생성
        String create_soldiers = "create table soldier (" +
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
        db.execSQL(create_soldiers);

        //분대 테이블 생성
        String create_squads = "create table squads (" +
                "name char(10), " +
                "primary key(name) " +
                ") ";

        db.execSQL(create_squads);
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
