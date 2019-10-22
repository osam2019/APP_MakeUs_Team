package com.example.makeus.Module;

import com.example.makeus.Model.DBHelper;
import com.example.makeus.Model.Soldier;

import java.util.List;

public class SoldierManager {

    private DBHelper dbHelper;
    public SoldierManager(DBHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public List<Soldier> getAllSoldiers() {
        return null;
    }

    public List<Soldier> getSpecificSquadSoldiers(String squadName){
        return null;
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
