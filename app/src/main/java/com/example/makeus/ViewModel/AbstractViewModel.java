package com.example.makeus.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.makeus.Model.DBHelper;
import com.example.makeus.Model.Squad;
import com.example.makeus.Module.SquadManager;

import java.util.List;

public abstract class AbstractViewModel extends ViewModel {

    private List<Squad> squads;
    public SquadManager squadManager;

    public AbstractViewModel() {
        //NULL 이면 안됨!
        squadManager = new SquadManager(null);
    }

    public List<Squad> getSquads() {
        updateDataFromDB();
        return squads;
    }

    private void updateDataFromDB() {
        squads = squadManager.getAllSquad();
    }
}
