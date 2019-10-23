package com.example.makeus.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.makeus.Model.PhysicalScore;
import com.example.makeus.Model.Soldier;

import java.util.ArrayList;
import java.util.List;

public class PhysicalmanageViewModel extends AbstractViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<List<Soldier>> mSoldierWithPhysicalScore;

    public PhysicalmanageViewModel() {

        PhysicalScore score1 = new PhysicalScore(78, 81, 19700);
        Soldier soldier1 = new Soldier("Jack O Lantern", "일병", "19-13467946", score1);

        PhysicalScore score2 = new PhysicalScore(90, 90, 15700);
        Soldier soldier2 = new Soldier("테스트", "병장", "18-18181818", score2);

        PhysicalScore score3 = new PhysicalScore(45, 62, 50700);
        Soldier soldier3 = new Soldier("집체교육", "상병", "19-87654321", score3);

        PhysicalScore score4 = new PhysicalScore(30, 51, 14700);
        Soldier soldier4 =  new Soldier("이일상병장", "이병", "19-13467946", score4);

        List<Soldier> soldiers = new ArrayList<>();
        soldiers.add(soldier1);
        soldiers.add(soldier2);
        soldiers.add(soldier3);
        soldiers.add(soldier4);

        mSoldierWithPhysicalScore = new MutableLiveData<>();
        mSoldierWithPhysicalScore.setValue(soldiers);
    }

    public MutableLiveData<List<Soldier>> getLiveDataSoldierWithPhysicalScore() {
        return this.mSoldierWithPhysicalScore;
    }

}
