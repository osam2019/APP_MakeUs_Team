package com.example.makeus.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.makeus.Module.DBHelper;
import com.example.makeus.Model.Soldier;
import com.example.makeus.Model.Squad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractViewModel extends ViewModel {

    private MutableLiveData<List<Squad>> squads;
    private MutableLiveData<List<Soldier>> soldiers;

    public AbstractViewModel() {
        this.squads = new MutableLiveData<>();
        squads.setValue(new ArrayList<Squad>());
        this.soldiers = new MutableLiveData<>();
        soldiers.setValue(new ArrayList<Soldier>());
    }

    public MutableLiveData<List<Squad>> getLiveDataSquads() {
        return squads;
    }
    public MutableLiveData<List<Soldier>> getLiveDataSoldiers() {
        return soldiers;
    }

    public void updateDataFromDB(DBHelper dbHelper) {
        this.squads.setValue(dbHelper.getAllSquad());
        this.soldiers.setValue(dbHelper.getAllSoldiers());
    }
}
