package com.example.makeus.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.makeus.Model.DBHelper;
import com.example.makeus.Model.Soldier;
import com.example.makeus.Model.Squad;
import com.example.makeus.Module.SquadManager;

import java.util.List;

public abstract class AbstractViewModel extends ViewModel {

    private MutableLiveData<List<Squad>> squads;
    private MutableLiveData<List<Soldier>> soldiers;
    private SquadManager squadManager;

    public AbstractViewModel() {
        //NULL 이면 안됨!
        squadManager = new SquadManager(null);
        this.SamleData();
    }

    public MutableLiveData<List<Squad>> getLiveDataSquads() {
        return squads;
    }
    public MutableLiveData<List<Soldier>> getLiveDataSoldiers() {
        return soldiers;
    }

    protected void updateDataFromDB() {
        squads.setValue(squadManager.getAllSquad());
        soldiers.setValue(squadManager.soldierManager.getAllSoldiers());
    }

    //병사
    public List<Soldier> getAllSoldiers() {
        return squadManager.soldierManager.getAllSoldiers();
    }

    public List<Soldier> getSpecificSquadSoldiers(String squadName){
        return squadManager.soldierManager.getSpecificSquadSoldiers(squadName);
    }

    public Soldier readSoldier(String milliNumber) {
        //Soldier 업데이트
        return squadManager.soldierManager.readSoldier(milliNumber);
    }

    public boolean createSoldier(Soldier squad) {
        //Soldier 추가
        boolean ret = squadManager.soldierManager.createSoldier(squad);
        updateDataFromDB();
        return ret;
    }

    public Soldier deleteSoldier(String milliNumber) {
        //Soldier 삭제
        Soldier ret = squadManager.soldierManager.deleteSoldier(milliNumber);
        updateDataFromDB();
        return ret;
    }

    public boolean updateSoldier(String milliNumber, String newName,String rank, String enlistment_Day, String transfer_Day, String discharge_Day, String birth, String specialty, String squad) {
        //Soldier 값변경
        boolean ret = squadManager.soldierManager.updateSoldier(milliNumber, newName, rank, enlistment_Day, transfer_Day, discharge_Day, birth, specialty, squad);
        updateDataFromDB();
        return ret;
    }

    public boolean isExistSoldier(String isExistSoldier) {
        return squadManager.soldierManager.isExistSoldier(isExistSoldier);
    }

    //분대
    public List<Squad> getAllSquad() {
        //모든 스쿼드 반환
        return squadManager.getAllSquad();
    }

    public Squad readSquad(String name) {
        //Sqaud 업데이트
        return squadManager.readSquad(name);
    }

    public boolean createSquad(Squad squad) {
        //Squad 추가
        boolean ret = squadManager.createSquad(squad);
        updateDataFromDB();
        return ret;
    }

    public Squad deleteSquad(String name) {
        //Squad 삭제
        Squad ret = squadManager.deleteSquad(name);
        updateDataFromDB();
        return ret;
    }

    public boolean updateSquad(String oldName, String newName) {
        //Squad 값변경
        boolean ret = squadManager.updateSquad(oldName, newName);
        updateDataFromDB();
        return ret;
    }

    public boolean isExistSquad(String name) {
        return squadManager.isExistSquad(name);
    }

    private void SamleData() {

    }
}
