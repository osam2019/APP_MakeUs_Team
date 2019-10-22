package com.example.makeus.Module;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.makeus.Model.DBHelper;
import com.example.makeus.Model.Soldier;
import com.example.makeus.Model.Squad;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SquadManager {
    private DBHelper dbHelper;
    public SoldierManager soldierManager;

    public SquadManager(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
        this.soldierManager = new SoldierManager(this.dbHelper);
    }

    public List<Squad> getAllSquad() {
        List<Squad> squads = new ArrayList<>();
        //모든 스쿼드 반환
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT squad FROM squads";
        Cursor squad = db.rawQuery(sql, null);
        while(squad.moveToNext()) {
            Squad s = new Squad(squad.getString(squad.getColumnIndex("name")));
            s.SoldierList = soldierManager.getSpecificSquadSoldiers(s.Name);
            squads.add(s);
        }
        return squads;
    }

    public Squad readSquad(String name) {
        if(isExistSquad(name)) {
            return null;
        }
        else {
            Squad s = new Squad(name);
            s.SoldierList = soldierManager.getSpecificSquadSoldiers(s.Name);
            return s;
        }
    }

    public boolean createSquad(Squad squad) {
        //Squad 추가
        SquadList.add(squad);

        if(SquadList.get(SquadList.size()-1) == squad) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            dbHelper.createTable(squad.Name);
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
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor squad = db.rawQuery("SELECT squad FROM squads WHERE name = \'" + name + "\'", null );
        if(squad.getCount() == 0) {
            return false;
        }
        else {
            return true;
        }
    }
}
