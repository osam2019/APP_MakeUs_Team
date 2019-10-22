package com.example.makeus.Module;

import android.app.Application;

import com.example.makeus.Model.DBHelper;
import com.example.makeus.Model.Squad;

import java.util.List;

public class SquadManager {

    private DBHelper dbHelper;
    public SoldierManager soldierManager;

    public SquadManager(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
        this.soldierManager = new SoldierManager(this.dbHelper);
    }

    public List<Squad> getAllSquad() {
        //모든 스쿼드 반환
        return null;
    }

    public Squad readSquad(String name) {
        //Sqaud 업데이트
        return null;
    }

    public boolean createSquad(Squad squad) {
        //Squad 추가
        return false;
    }

    public Squad deleteSquad(String name) {
        //Squad 삭제
        return null;
    }

    public boolean updateSquad(String oldName, String newName) {
        //Squad 값변경
        return false;
    }

    public boolean isExistSquad(String name) {
        return false;
    }
}
