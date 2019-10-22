package com.example.makeus.Module;

import com.example.makeus.Model.DBHelper;
import com.example.makeus.Model.Soldier;

public class SoldierManager {

    private DBHelper dbHelper;
    public SoldierManager(DBHelper dbHelper){
        this.dbHelper = dbHelper;
    }
    public Soldier readSoldier(String milliNumber) {
        //Soldier 업데이트
        return null;
    }

    public boolean createSoldier(Soldier squad) {
        //Soldier 추가
        return false;
    }

    public Soldier deleteSoldier(String milliNumber) {
        //Soldier 삭제
        return null;
    }

    public boolean updateSoldier(String milliNumber, String newName) {
        //Soldier 값변경
        return false;
    }
}
