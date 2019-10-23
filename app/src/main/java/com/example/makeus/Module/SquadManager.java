package com.example.makeus.Module;

import android.app.Application;
import android.content.Context;
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
        this.soldierManager = new SoldierManager(dbHelper);
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
        db.close();
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

    public void createSquad(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "insert into " + name + "(name) values (?)";
        String [] arg = {name};
        db.execSQL(sql, arg);
        db.close();
    }

    public void deleteSquad(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "DELETE FROM squads WHERE name = " + name;
        db.execSQL(sql,null);
        db.close();
    }

    public void updateSquad(String oldName, String newName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "UPDATE squads SET name = ? WHERE name = " + oldName;
        String [] arg = { newName };
        db.execSQL(sql, arg);
        db.close();
    }

    public boolean isExistSquad(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor squad = db.rawQuery("SELECT squad FROM squads WHERE name = \'" + name + "\'", null );
        if(squad.getCount() == 0) {
            db.close();
            return false;
        }
        else {
            db.close();
            return true;
        }
    }
}
