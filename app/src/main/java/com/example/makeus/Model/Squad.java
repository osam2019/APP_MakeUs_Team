package com.example.makeus.Model;

import java.util.ArrayList;

public class Squad {
    public String Name;
    private ArrayList<Soldier> SoldierList;

    public Squad(String name){
        Name = name;
    }

    public void Organize_Squad(Soldier soldier) {
        if ( Name == soldier.Squad) {
            SoldierList.add(soldier);
        }
    }
}
