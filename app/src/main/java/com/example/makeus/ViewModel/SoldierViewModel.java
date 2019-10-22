package com.example.makeus.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.makeus.Model.Soldier;
import com.example.makeus.Model.Squad;

import java.util.ArrayList;
import java.util.List;

public class SoldierViewModel extends AbstractViewModel {
    // TODO: Implement the ViewModel
    private Squad mSquad;
    private MutableLiveData<List<Soldier>> mSoldiers;

    public SoldierViewModel() {
        mSoldiers = new MutableLiveData<>();
        mSoldiers.setValue(new ArrayList<Soldier>());

        //샘플 데이터
        final String squadName = "1소대 1분대";
        ArrayList<Soldier> soldiers = new ArrayList<>();
        soldiers.add(new Soldier("육군12", squadName,"병장","18-98765432"));
        soldiers.add(new Soldier("육군13", squadName,"병장","18-78787878"));
        soldiers.add(new Soldier("육군14", squadName,"일병","19-75357535"));
        soldiers.add(new Soldier("육군15", squadName,"상병","18-45654565"));
        soldiers.add(new Soldier("육군16", squadName,"이병","19-99999999"));
        mSoldiers.setValue(soldiers);
    }

    @Override
    protected void updateDataFromDB() {
        //super.updateDataFromDB();
        //mSoldiers.setValue(getSpecificSquadSoldiers(mSquad.Name));
    }

    @Override
    public MutableLiveData<List<Soldier>> getLiveDataSoldiers(){
        return mSoldiers;
    }

    public void setSquad(Squad squad) {
        mSquad = squad;
        updateDataFromDB();
    }
}
