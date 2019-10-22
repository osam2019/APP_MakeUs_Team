package com.example.makeus.Module;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.makeus.Model.DBHelper;
import com.example.makeus.Model.Soldier;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import java.util.List;

public class SoldierManager {

    private DBHelper dbHelper;
    public SoldierManager(DBHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public Soldier getSoldierFromCursor(Cursor cursor) {

        Soldier soldier = new Soldier();

        soldier.Discharge_Flag = cursor.getInt(cursor.getColumnIndex("discharge_flag")) == 0 ? false : true;
        soldier.Name = cursor.getString(cursor.getColumnIndex("name"));
        soldier.Squad = cursor.getString(cursor.getColumnIndex("squad"));
        soldier.Rank = cursor.getString(cursor.getColumnIndex("rank"));
        soldier.Milli_Number = cursor.getString(cursor.getColumnIndex("milli_number"));
        soldier.Specialty = cursor.getString(cursor.getColumnIndex("specialty"));
        soldier.Birthday = cursor.getLong(cursor.getColumnIndex("birthday"));
        soldier.Enlistment_Day = cursor.getLong(cursor.getColumnIndex("enlistment_day"));
        soldier.Transfer_Day = cursor.getLong(cursor.getColumnIndex("transfer_day"));
        soldier.Discharge_Day = cursor.getLong(cursor.getColumnIndex("discharge_day"));

        return soldier;
    }

    public List<Soldier> getAllSoldiers() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Soldier> soldiers = new ArrayList<>();
        Cursor solider = db.rawQuery("SELECT * FROM soldiers", null );
        while(solider.moveToNext()) {
            soldiers.add(this.getSoldierFromCursor(solider));
        }
        db.close();
        return soldiers;
    }

    public List<Soldier> getSpecificSquadSoldiers(String squadName){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Soldier> soldiers = new ArrayList<>();
        Cursor solider = db.rawQuery("SELECT * FROM soldiers WHERE squad = \'" + squadName +"\'", null );
        while(solider.moveToNext()) {
            soldiers.add(this.getSoldierFromCursor(solider));
        }
        db.close();
        return soldiers;
    }

    public Soldier readSoldier(String milliNumber) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Soldier s = null;
        if(isExistSoldier(milliNumber)) {
            Cursor soldier = db.rawQuery("SELECT * FROM soldiers WHERE milli_number = " + milliNumber, null );
            soldier.moveToNext();
            s = this.getSoldierFromCursor(soldier);
        }
        db.close();
        return s;
    }

    public void createSoldier(Soldier squad) {
        //Soldier 추가
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "insert into soldiers (name, squad, rank, milli_number, specialty, birthday, " +
                "enlistment_day, transfer_day, discharge_day, discharge_flag) values (?,?,?,?,?,?,?,?,?,?)";

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String Discharge = "0";

        String [] arg = {squad.Name, squad.Squad, squad.Rank, squad.Milli_Number, squad.Specialty, sdf.format(squad.Birthday),
                sdf.format(squad.Enlistment_Day), sdf.format(squad.Transfer_Day), sdf.format(squad.Discharge_Day),
                Discharge};

        db.execSQL(sql, arg);
        db.close();
    }

    public void deleteSoldier(String milliNumber) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "DELETE FROM * WHERE milli_number = " + milliNumber;
        db.execSQL(sql,null);
        db.close();
    }

    public void updateSoldier(String milliNumber, String newName, String rank, long enlistment_Day, long transfer_Day, long discharge_Day, long birth, String specialty, String squad) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = "UPDATE * SET name = ?, squad = ?, rank = ?, milli_number = ?, specialty = ?, birthday = ?," +
                "enlistment_day = ?, transfer_day = ?, discharge_day = ? WHERE milli_number = " + milliNumber;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String [] arg = {newName, squad, rank,  milliNumber, specialty, sdf.format(birth), sdf.format(enlistment_Day), sdf.format(transfer_Day), sdf.format(discharge_Day)};

        db.execSQL(sql, arg);
        db.close();
    }

    public void updateSoldier(String milliNumber, boolean disFlag) {
        //Soldier 전역 플래그값 변경
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = "UPDATE * SET discharge_flag = ? WHERE milli_number = " + milliNumber;

        // 전역자는 1, 복무중인 사람은 0
        String [] arg = new String[1];
        if (disFlag == true) {
            arg[0] = "0";
        }else {
            arg[0] = "1";
        }

        db.execSQL(sql, arg);
        db.close();
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
