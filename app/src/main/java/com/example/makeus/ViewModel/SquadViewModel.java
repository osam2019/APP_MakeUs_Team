package com.example.makeus.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.makeus.Model.Squad;

import java.util.ArrayList;
import java.util.List;


public class SquadViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<List<Squad>> mSquads;

    public SquadViewModel() {
        ArrayList<Squad> squads = new ArrayList<>();
        squads.add(new Squad("1분대"));
        squads.add(new Squad("2분대"));
        squads.add(new Squad("3분대"));
        squads.add(new Squad("4분대"));
        squads.add(new Squad("5분대"));
        mSquads = new MutableLiveData<>();
        mSquads.setValue(squads);
    }

    public MutableLiveData<List<Squad>> getSquads() {
        return mSquads;
    }
}
