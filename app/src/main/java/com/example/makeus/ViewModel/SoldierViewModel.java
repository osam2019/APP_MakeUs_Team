package com.example.makeus.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.makeus.Model.Soldier;
import com.example.makeus.Model.Squad;

import java.util.ArrayList;

public class SoldierViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private Squad mSquad;
    private MutableLiveData<ArrayList<Soldier>> mSoldiers;

    final String squadName = "1소대 1분대";

    public SoldierViewModel() {
        ArrayList<Soldier> soldiers = new ArrayList<>();
        soldiers.add(new Soldier("육군12",squadName,"병장","18-98765432"));
        soldiers.add(new Soldier("육군13",squadName,"병장","18-78787878"));
        soldiers.add(new Soldier("육군14",squadName,"일병","19-75357535"));
        soldiers.add(new Soldier("육군15",squadName,"상병","18-45654565"));
        soldiers.add(new Soldier("육군16",squadName,"이병","19-99999999"));
        mSoldiers = new MutableLiveData<>();
        mSoldiers.setValue(soldiers);
    }

    public MutableLiveData<ArrayList<Soldier>> getSoldiers(){
        return mSoldiers;
    }

    public void SetSquad(Squad squad) {
        mSoldiers.setValue(squad.SoldierList);
    }
}
