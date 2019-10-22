package com.example.makeus.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.makeus.Model.Soldier;

public class InputprofileViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<Soldier> mSoldier;

    public InputprofileViewModel() {
        Soldier soldier = new Soldier();
        mSoldier = new MutableLiveData<>();
        mSoldier.setValue(soldier);
    }

    public MutableLiveData<Soldier> getSoldier(){
        return mSoldier;
    }

}
