package com.example.makeus.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.makeus.Module.SoldierManager;

import java.util.List;


public class DBHelper extends SQLiteOpenHelper {
    String SquadName;
    List<String>SquadList = null;
    SoldierManager sm = new SoldierManager();

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, String squadName) {
        // 추가시
        super(context, "SoldierDB", null, 1);
        if(SquadList != null) {
            for (int i = 0; i<SquadList.size(); i++) {
                if (SquadList.get(i) != squadName) {
                    SquadList.add(squadName);
                }
            }
        } else {
            this.SquadName = squadName;
            SquadList.add(squadName);
        }
        createTable(squadName);
        SquadList = sm.getTableList();
    }

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        // 평상시
        super(context, "SoldierDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_squad_table_create = "create table " + sm.getTableList().get(0) + " (" +
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
