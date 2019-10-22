package com.example.makeus.Module;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.makeus.Model.DBHelper;
import com.example.makeus.Model.Soldier;
import com.example.makeus.Model.Squad;

import java.util.List;

public class SquadManager {
    private DBHelper dbHelper;
    public SoldierManager soldierManager;
    public List<Squad> SquadList = null;

    public SquadManager(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
        this.soldierManager = new SoldierManager(this.dbHelper);
    }

    public List<Squad> getAllSquad() {
        //모든 스쿼드 반환
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "SELECT squad FROM all_tables";
        Cursor c = db.rawQuery(sql, null);
        while(c.moveToNext()) {
            int Discharge_Flag = c.getInt(c.getColumnIndex("discharge_flag"));
            if( Discharge_Flag == 1) {
                continue;
            }
            String Squad = c.getString(c.getColumnIndex("squad"));
            if(SquadList.size() == 0) { SquadList.add(new Squad(Squad)); }
            for(int i = 0; i < SquadList.size(); i++) {
                if(SquadList.get(i).Name != Squad && i == SquadList.size() - 1) {
                    SquadList.add(new Squad(Squad));
                }
            }
        }
        return SquadList;
    }

    public Squad readSquad(String name) {
        //Sqaud 불러오기
        for(int i = 0; i < SquadList.size(); i++) {
            if(SquadList.get(i).Name == name) {
                return SquadList.get(i);
            }
        }
        System.out.println("SYSTEM_ERROR : SQMread");
        return null;
    }

    public boolean createSquad(Squad squad) {
        //Squad 추가
        SquadList.add(squad);

        if(SquadList.get(SquadList.size()-1) == squad) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            dbHelper.createTable(dbHelper, squad.Name);
            db.close();
            return true;
        }
        return false;
    }

    public Squad deleteSquad(String name) {
        //Squad 삭제
        return null;
    }

    public boolean updateSquad(String oldName, String newName) {
        //Squad 업데이트
        return false;
    }

    public boolean isExistSquad(String name) {
        // 존재하면 true, 없으면 false
        for(int i = 0; i<SquadList.size(); i++) {
            if(SquadList.get(i).Name == name) {
                return true;
            }
        }
        return false;
    }
}
