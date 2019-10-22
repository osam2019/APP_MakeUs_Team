package com.example.makeus.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.makeus.Model.Soldier;
import com.example.makeus.Model.Squad;

import java.util.ArrayList;
import java.util.List;


public class SquadViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<List<Squad>> mSquads;

    public SquadViewModel() {
        ArrayList<Squad> squads = new ArrayList<>();
        ArrayList<Soldier> soldiers1 = new ArrayList<>();

        soldiers1.add(new Soldier());
        soldiers1.add(new Soldier());
        soldiers1.add(new Soldier());
        soldiers1.add(new Soldier());

        squads.add(new Squad("1분대", soldiers1));

        ArrayList<Soldier> soldiers2 = new ArrayList<>();

        soldiers2.add(new Soldier());
        soldiers2.add(new Soldier());

        squads.add(new Squad("2분대", soldiers2));

        ArrayList<Soldier> soldiers3 = new ArrayList<>();

        soldiers3.add(new Soldier("홍길동", "3분대", "이등병", "00-102304"));

        squads.add(new Squad("3분대", soldiers3));

        mSquads = new MutableLiveData<>();
        mSquads.setValue(squads);
    }

    public MutableLiveData<List<Squad>> getSquads() {
        return mSquads;
    }
}
