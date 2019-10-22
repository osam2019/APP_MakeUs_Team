package com.example.makeus.Module;

import com.example.makeus.Model.Soldier;

public class DateCalculator {
    SoldierManager sm = new SoldierManager();

    public void Tenacity_Test(String milnum) {
        Soldier soldier = sm.readSoldier(milnum);

        if(soldier != null) {

        }
    }

    public long Discharge_Day_Calc(String milnum) {
        Soldier soldier = sm.readSoldier(milnum);

        if(soldier != null) {

        }

        return 1;
    }
}
