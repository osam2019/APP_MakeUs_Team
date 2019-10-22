package com.example.makeus.Module;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.makeus.Model.DBHelper;
import com.example.makeus.Model.Soldier;

import java.text.SimpleDateFormat;
import java.util.Locale;

import java.util.List;

public class SoldierManager {
    List<Soldier> SoldierList = this.getAllSoldiers();

    private DBHelper dbHelper;
    public SoldierManager(DBHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public List<Soldier> getAllSoldiers() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "SELECT * FROM all_tables";
        Cursor c = db.rawQuery(sql, null);
        while(c.moveToNext()) {
            int Discharge_Flag = c.getInt(c.getColumnIndex("discharge_flag"));
            if( Discharge_Flag == 1) {
                continue;
            }
            String Name = c.getString(c.getColumnIndex("name"));
            String Squad = c.getString(c.getColumnIndex("squad"));
            String Rank = c.getString(c.getColumnIndex("rank"));
            String Milli_Number = c.getString(c.getColumnIndex("milli_number"));
            String Specialty = c.getString(c.getColumnIndex("specialty"));
            long Birthday = c.getLong(c.getColumnIndex("birthday"));
            long Enlistment_Day = c.getLong(c.getColumnIndex("enlistment_day"));
            long Transfer_Day = c.getLong(c.getColumnIndex("transfer_day"));
            long Discharge_Day = c.getLong(c.getColumnIndex("discharge_day"));
            boolean Discharge = false;

            Soldier soldier = new Soldier();
            soldier.Input_Infomation(Name, Squad, Rank, Milli_Number, Specialty, Birthday, Enlistment_Day, Transfer_Day,
                    Discharge_Day, Discharge);

            SoldierList.add(soldier);
        }

        return SoldierList;
    }

    public List<Soldier> getSpecificSquadSoldiers(String squadName){
        List<Soldier>specificSquad = null;

        for (int i = 0; i < SoldierList.size(); i ++) {
            if(SoldierList.get(i).Squad == squadName) {
                specificSquad.add(SoldierList.get(i));
            }
        }

        return specificSquad;
    }

    public Soldier readSoldier(String milliNumber) {
        Soldier soldier = new Soldier();
        for (int i = 0; i < SoldierList.size(); i++) {
            if(SoldierList.get(i).Milli_Number == milliNumber) {
                soldier = SoldierList.get(i);
            }
        }
        return soldier;
    }

    public boolean createSoldier(Soldier squad) {
        //Soldier 추가
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "insert into " + squad.Squad + "(name, squad, rank, milli_number, specialty, birthday, " +
                "enlistment_day, transfer_day, discharge_day, discharge_flag) values (?,?,?,?,?,?,?,?,?,?)";

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String Discharge = "0";

        String [] arg = {squad.Name, squad.Squad, squad.Rank, squad.Milli_Number, squad.Specialty, sdf.format(squad.Birthday),
                sdf.format(squad.Enlistment_Day), sdf.format(squad.Transfer_Day), sdf.format(squad.Discharge_Day),
                Discharge};

        Soldier soldier = new Soldier();
        soldier.Input_Infomation(squad.Name, squad.Squad, squad.Rank, squad.Milli_Number, squad.Specialty, squad.Birthday,
                squad.Enlistment_Day, squad.Transfer_Day, squad.Discharge_Day, squad.Discharge_Flag);
        SoldierList.add(soldier);

        getAllSoldiers();
        db.execSQL(sql, arg);
        db.close();

        if (db.isOpen() == false){
            return true;
        }else{
            return false;
        }
    }

    public void deleteSoldier(String milliNumber) {
        //Soldier 삭제
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for(int i = 0; i < SoldierList.size(); i++) {
            if(SoldierList.get(i).Milli_Number == milliNumber) {
                SoldierList.remove(i);
            }
        }

        String sql = "DELETE FROM * WHERE milli_number = " + milliNumber;
        db.execSQL(sql,null);
        getAllSoldiers();
    }

    public boolean updateSoldier(String milliNumber, String newName, String rank, long enlistment_Day, long transfer_Day, long discharge_Day, long birth, String specialty, String squad) {
        //Soldier 일반 상태값 변경
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = "UPDATE * SET name = ?, squad = ?, rank = ?, milli_number = ?, specialty = ?, birthday = ?," +
                "enlistment_day = ?, transfer_day = ?, discharge_day = ? WHERE milli_number = " + milliNumber;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String [] arg = {newName, squad, rank,  milliNumber, specialty, sdf.format(birth), sdf.format(enlistment_Day), sdf.format(transfer_Day), sdf.format(discharge_Day)};

        db.execSQL(sql, arg);
        db.close();

        if (db.isOpen() == false){
            return true;
        }else{
            return false;
        }
    }

    public boolean updateSoldier(String milliNumber, boolean disFlag) {
        //Soldier 전역 플래그값 변경
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = "UPDATE * SET discharge_flag = ? WHERE milli_number = " + milliNumber;

        // 전역자는 1, 복무중인 사람은 0
        String [] arg = null;
        if (disFlag == true) {
            arg[0] = "0";
        }else {
            arg[0] = "1";
        }

        getAllSoldiers();
        db.execSQL(sql, arg);
        db.close();

        if (db.isOpen() == false){
            return true;
        }else{
            return false;
        }
    }

    public boolean isExistSoldier(String milliNumber) {
        // 존재하면 true, 없으면 false
        if(SoldierList.size() == 0) { return false; }
        for(int i = 0; i < SoldierList.size(); i++) {
            if(SoldierList.get(i).Milli_Number == milliNumber) {
                return true;
            }
        }
        return false;
    }
}
