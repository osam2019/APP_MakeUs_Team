package com.example.makeus.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.makeus.Module.DBHelper;
import com.example.makeus.Model.Soldier;
import com.example.makeus.Model.Squad;

import java.util.List;

public class SoldierViewModel extends AbstractViewModel {
    // TODO: Implement the ViewModel
    private Squad mSquad;
    private MutableLiveData<List<Soldier>> mSoldiers;

    public SoldierViewModel() {
        mSoldiers = new MutableLiveData<>();
    }

    @Override
    public void updateDataFromDB(DBHelper dbHelper) {
        super.updateDataFromDB(dbHelper);
        mSoldiers.setValue(dbHelper.getSpecificSquadSoldiers(mSquad.Name));
    }

    @Override
    public MutableLiveData<List<Soldier>> getLiveDataSoldiers(){
        return mSoldiers;
    }

    public void setSquad(DBHelper dbHelper, Squad squad) {
        mSquad = squad;
        updateDataFromDB(dbHelper);
    }
}
