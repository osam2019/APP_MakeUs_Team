package com.example.makeus.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.makeus.ViewModel.AbstractViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "makeus.db";
    public static final String TABLE_SOLDIERS = "soldiers";
    public static final String TABLE_SQUADS = "squads";
    private AbstractViewModel viewModel;

    public DBHelper(Context context, AbstractViewModel viewModel) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.viewModel = viewModel;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //용사 테이블 생성
        String create_soldiers =
                "create table soldiers (" +
                "name char(60), " +
                "squad varchar(60), " +
                "rank char(60), " +
                "milli_number char(60), " +
                "specialty varchar(60), " +
                "birthday int, " +
                "enlistment_day int, " +
                "transfer_day int, " +
                "discharge_day int, " +
                "discharge_flag int, " +
                "primary key(milli_number) " +
                ") ";
        db.execSQL(create_soldiers);

        //분대 테이블 생성
        String create_squads =
                "create table squads (" +
                "name char(60), " +
                "primary key(name) " +
                ") ";

        db.execSQL(create_squads);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            switch (oldVersion) {
                /* 버전 갱신시 작성 */
            }
        }
    }

    public List<Squad> getAllSquad() {
        List<Squad> squads = new ArrayList<>();
        //모든 스쿼드 반환
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT name FROM " + TABLE_SQUADS;
        Cursor squad = db.rawQuery(sql, null);
        while(squad.moveToNext()) {
            Squad s = new Squad(squad.getString(squad.getColumnIndex("name")));
            s.SoldierList = getSpecificSquadSoldiers(s.Name);
            squads.add(s);
        }
        db.close();
        return squads;
    }

    public Squad readSquad(String name) {
        if(isExistSquad(name)) {
            return null;
        }
        else {
            Squad s = new Squad(name);
            s.SoldierList = getSpecificSquadSoldiers(s.Name);
            return s;
        }
    }

    public void createSquad(String name) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO " + name + " (name) VALUES ?";
        String [] arg = {name};
        db.execSQL(sql, arg);
        db.close();
        viewModel.updateDataFromDB(this);
    }

    public void deleteSquad(String name) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM "+TABLE_SQUADS+" WHERE name = " + name;
        db.execSQL(sql,null);
        db.close();
        viewModel.updateDataFromDB(this);
    }

    public void updateSquad(String oldName, String newName) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE "+TABLE_SQUADS+" SET name = ? WHERE name = " + oldName;
        String [] arg = { newName };
        db.execSQL(sql, arg);
        db.close();
        viewModel.updateDataFromDB(this);
    }

    public boolean isExistSquad(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor squad = db.rawQuery("SELECT * FROM  "+TABLE_SQUADS+"  WHERE name = \'" + name + "\'", null );
        if(squad.getCount() == 0) {
            db.close();
            return false;
        }
        else {
            db.close();
            return true;
        }
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
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Soldier> soldiers = new ArrayList<>();
        Cursor solider = db.rawQuery("SELECT * FROM "+ TABLE_SOLDIERS, null );
        while(solider.moveToNext()) {
            soldiers.add(this.getSoldierFromCursor(solider));
        }
        db.close();
        return soldiers;
    }

    public List<Soldier> getSpecificSquadSoldiers(String squadName){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Soldier> soldiers = new ArrayList<>();
        Cursor solider = db.rawQuery("SELECT * FROM " + TABLE_SOLDIERS + " WHERE squad = \'" + squadName +"\'", null );
        while(solider.moveToNext()) {
            soldiers.add(this.getSoldierFromCursor(solider));
        }
        db.close();
        return soldiers;
    }

    public Soldier readSoldier(String milliNumber) {
        SQLiteDatabase db = getReadableDatabase();
        Soldier s = null;
        if(isExistSoldier(milliNumber)) {
            Cursor soldier = db.rawQuery("SELECT * FROM " + TABLE_SOLDIERS +" WHERE milli_number = " + milliNumber, null );
            soldier.moveToNext();
            s = this.getSoldierFromCursor(soldier);
        }
        db.close();
        return s;
    }

    public void createSoldier(Soldier soldier) {
        if(!isExistSquad(soldier.Squad)) {
            createSquad(soldier.Squad);
        }
        //Soldier 추가
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO "+ TABLE_SOLDIERS +" (name, squad, rank, milli_number, specialty, birthday, " +
                "enlistment_day, transfer_day, discharge_day, discharge_flag) VALUES (?,?,?,?,?,?,?,?,?,?)";

        String df = (soldier.Discharge_Flag ? String.valueOf(0) : String.valueOf(0));
        String [] arg =
                {
                    soldier.Name, soldier.Squad, soldier.Rank,
                    soldier.Milli_Number, soldier.Specialty,
                    String.valueOf(soldier.Birthday),
                    String.valueOf(soldier.Enlistment_Day),
                    String.valueOf(soldier.Transfer_Day),
                    String.valueOf(soldier.Discharge_Day),
                    df
                };
        db.execSQL(sql, arg);
        db.close();
        viewModel.updateDataFromDB(this);
    }

    public void deleteSoldier(String milliNumber) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM "+TABLE_SOLDIERS+" WHERE milli_number = " + milliNumber;
        db.execSQL(sql,null);
        db.close();
        viewModel.updateDataFromDB(this);
    }

    public void updateSoldier(String milliNumber, String newName, String rank, long enlistment_Day, long transfer_Day, long discharge_Day, long birth, String specialty, String squad) {
        SQLiteDatabase db = getWritableDatabase();

        String sql = "UPDATE " + TABLE_SOLDIERS + " SET name = ?, squad = ?, rank = ?, specialty = ?, birthday = ?," +
                "enlistment_day = ?, transfer_day = ?, discharge_day = ? WHERE milli_number = " + milliNumber;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String [] arg = {newName, squad, rank, specialty, sdf.format(birth), sdf.format(enlistment_Day), sdf.format(transfer_Day), sdf.format(discharge_Day)};

        db.execSQL(sql, arg);
        db.close();
        viewModel.updateDataFromDB(this);
    }

    public void updateSoldier(String milliNumber, boolean disFlag) {
        //Soldier 전역 플래그값 변경
        SQLiteDatabase db = getWritableDatabase();

        String sql = "UPDATE " + TABLE_SOLDIERS + " SET discharge_flag = ? WHERE milli_number = " + milliNumber;

        // 전역자는 1, 복무중인 사람은 0
        String [] arg = new String[1];
        if (disFlag == true) {
            arg[0] = "0";
        }else {
            arg[0] = "1";
        }

        db.execSQL(sql, arg);
        db.close();
        viewModel.updateDataFromDB(this);
    }

    public boolean isExistSoldier(String milliNumber) {
        // 존재하면 true, 없으면 false
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT name FROM " + TABLE_SOLDIERS + " WHERE milli_number = ?";
        String[] args = {milliNumber};
        Cursor data = db.rawQuery(sql, args);
        if(data.getCount() > 0) {
            db.close();
            return true;
        } else {
            db.close();
            return false;
        }
    }
}
