package com.example.makeus.Module;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.makeus.Model.DBHelper;
import com.example.makeus.Model.Soldier;
import com.example.makeus.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

import java.util.List;

public class SoldierManager {

    private DBHelper dbHelper;
    public SoldierManager(DBHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public List<Soldier> getAllSoldiers() {
        return null;
    }

    public List<Soldier> getSpecificSquadSoldiers(String squadName){
        return null;
    }

    public Soldier readSoldier(String milliNumber) {
        DBHelper helper = new DBHelper(null, null, null, 0);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM * WHERE milli_number = " + milliNumber;
        Cursor c = db.rawQuery(sql, null);

        String Name = c.getString(c.getColumnIndex("name"));
        String Squad = c.getString(c.getColumnIndex("squad"));
        String Rank = c.getString(c.getColumnIndex("rank"));
        String Milli_Number = c.getString(c.getColumnIndex("milli_number"));
        String Specialty = c.getString(c.getColumnIndex("specialty"));
        long Birthday = c.getLong(c.getColumnIndex("birthday"));
        long Enlistment_Day = c.getLong(c.getColumnIndex("enlistment_day"));
        long Transfer_Day = c.getLong(c.getColumnIndex("transfer_day"));
        long Discharge_Day = c.getLong(c.getColumnIndex("discharge_day"));
        int Discharge_Flag = c.getInt(c.getColumnIndex("discharge_flag"));

        boolean Discharge = true;
        if (Discharge_Flag == 0) {
            Discharge = false;
        }

        Soldier soldier = new Soldier();
        soldier.Input_Infomation(Name, Squad, Rank, Milli_Number, Specialty, Birthday, Enlistment_Day, Transfer_Day,
                Discharge_Day, Discharge);

        return soldier;
    }

    public boolean createSoldier(Soldier squad) {
        //Soldier 추가
        DBHelper helper = new DBHelper(null, null, null, 0, squad.Squad);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "insert into " + squad.Squad + "(name, squad, rank, milli_number, specialty, birthday, " +
                "enlistment_day, transfer_day, discharge_day, discharge_flag) values (?,?,?,?,?,?,?,?,?,?)";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyy-MM-dd", Locale.getDefault());
        String Discharge = "0";

        String [] arg = {squad.Name, squad.Squad, squad.Rank, squad.Milli_Number, sdf.format(squad.Birthday),
                sdf.format(squad.Enlistment_Day), sdf.format(squad.Transfer_Day), sdf.format(squad.Discharge_Day),
                Discharge};

        db.execSQL(sql, arg);
        db.close();
        if (db.isOpen() == false){
            return true;
        }else{
            return false;
        }
    }

    public Soldier deleteSoldier(String milliNumber) {
        //Soldier 삭제
        DBHelper helper = new DBHelper(null, null, null, 0);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "DELETE FROM * WHERE milli";
        return null;
    }

    public boolean updateSoldier(String milliNumber, String newName,String rank, String enlistment_Day, String transfer_Day, String discharge_Day, String birth, String specialty, String squad) {
        //Soldier 값변경
        return false;
    }

    public boolean isExistSoldier(String milliNumber) {
        return false;
    }
}
