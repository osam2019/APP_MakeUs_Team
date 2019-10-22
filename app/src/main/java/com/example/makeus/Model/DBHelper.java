package com.example.makeus.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    String SquadName;
    public String [] SquadList = null;

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, String squadName) {
        // 추가시
        super(context, "SoldierDB", null, 1);
        if(SquadList != null) {
            for (int i = 0; i<SquadList.length; i++) {
                if (SquadList[i] == squadName) {
                    continue;
                }
                if (i == SquadList.length - 1) {
                    SquadList[SquadList.length] = squadName;
                    createTable(squadName);
                }
            }
        } else {
            this.SquadName = squadName;
            SquadList[0] = squadName;
        }
    }

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        // 평상시
        super(context, "SoldierDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_squad_table_create = "create table " + SquadName + " (" +
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

    public void createTable(String squad) {
        DBHelper helper = new DBHelper(null, null, null, 0);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql_squad_table_create = "create table " + SquadName + " (" +
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
