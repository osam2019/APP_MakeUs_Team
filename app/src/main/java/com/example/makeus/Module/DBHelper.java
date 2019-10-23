package com.example.makeus.Module;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.makeus.Model.Soldier;
import com.example.makeus.Model.Squad;
import com.example.makeus.ViewModel.AbstractViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        SampleData(db);
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
        String sql = "INSERT INTO " + TABLE_SQUADS + " VALUES (?)";
        String [] arg = {name};
        db.execSQL(sql, arg);
        db.close();
        viewModel.updateDataFromDB(this);
    }

    public void createSquad(Squad squad) {
        if(!isExistSquad(squad.Name)) {
            createSquad(squad.Name);
        }

        for(int i = 0; i < squad.SoldierList.size(); i++){
            if(isExistSoldier(squad.SoldierList.get(i).milliNumber)) {
                updateSoldier(squad.SoldierList.get(i));
            }
            else {
                createSoldier(squad.SoldierList.get(i));
            }
        }
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
        soldier.milliNumber = cursor.getString(cursor.getColumnIndex("milli_number"));
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
                    soldier.milliNumber, soldier.Specialty,
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

    public void updateSoldier(Soldier soldier) {
        updateSoldier(soldier.milliNumber, soldier.Name, soldier.Rank, soldier.Enlistment_Day, soldier.Transfer_Day, soldier.Discharge_Day, soldier.Birthday, soldier.Specialty, soldier.Squad);
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

    private void SampleData(SQLiteDatabase db) {
        Soldier a = new Soldier();
        a.Name = "산타" ;
        a.Rank = "일병";
        a.milliNumber = "13-45687542";
        a.Squad = "1분대";
        a.Enlistment_Day = new Date(1990, 3, 14).getTime();
        a.Transfer_Day =  new Date(1990, 4, 14).getTime();
        a.Discharge_Day =  new Date(1992, 1, 1).getTime();
        a.Discharge_Flag = false;
        a.Birthday = new Date(1970, 11, 11).getTime();
        a.Specialty ="화학병";

        Soldier b = new Soldier();
        b.Name = "마리아" ;
        b.Rank = "상병";
        b.milliNumber = "14-45618526";
        b.Squad = "2분대";
        b.Enlistment_Day = new Date(1991, 4, 15).getTime();
        b.Transfer_Day =  new Date(1991, 5, 15).getTime();
        b.Discharge_Day =  new Date(1993, 2, 2).getTime();
        b.Discharge_Flag = false;
        b.Birthday = new Date(1971, 12, 12).getTime();
        b.Specialty ="상황병";

        Soldier c = new Soldier();
        c.Name = "요셉" ;
        c.Rank = "병장";
        c.milliNumber = "15-78987542";
        c.Squad = "3분대";
        c.Enlistment_Day = new Date(1992, 5, 16).getTime();
        c.Transfer_Day =  new Date(1992, 6, 16).getTime();
        c.Discharge_Day =  new Date(1994, 3, 3).getTime();
        c.Discharge_Flag = true;
        c.Birthday = new Date(1973, 1, 14).getTime();
        c.Specialty ="전술기 관리병";

        Soldier d = new Soldier();
        d.Name = "로빈" ;
        d.Rank = "이병";
        d.milliNumber = "16-45688434";
        d.Squad = "1분대";
        d.Enlistment_Day = new Date(1994, 6, 20).getTime();
        d.Transfer_Day =  new Date(1994, 7, 22).getTime();
        d.Discharge_Day =  new Date(1996, 2, 7).getTime();
        d.Discharge_Flag = false;
        d.Birthday = new Date(1974, 10, 9).getTime();
        d.Specialty ="해적";

        Soldier e = new Soldier();
        e.Name = "루피" ;
        e.Rank = "일병";
        e.milliNumber = "17-45681134";
        e.Squad = "2분대";
        e.Enlistment_Day = new Date(1998, 7, 14).getTime();
        e.Transfer_Day =  new Date(1998, 8, 18).getTime();
        e.Discharge_Day =  new Date(2000, 1, 5).getTime();
        e.Discharge_Flag = false;
        e.Birthday = new Date(1980, 1, 1).getTime();
        e.Specialty ="탐정";

        Soldier f = new Soldier();
        f.Name = "조로" ;
        f.Rank = "상병";
        f.milliNumber = "14-49685421";
        f.Squad = "3분대";
        f.Enlistment_Day = new Date(2000, 3, 14).getTime();
        f.Transfer_Day =  new Date(2000, 5, 14).getTime();
        f.Discharge_Day =  new Date(2002, 8, 7).getTime();
        f.Discharge_Flag = false;
        f.Birthday = new Date(1980, 3, 25).getTime();
        f.Specialty ="해적사냥꾼";

        Soldier g = new Soldier();
        g.Name = "나미" ;
        g.Rank = "병장";
        g.milliNumber = "14-45452242";
        g.Squad = "1분대";
        g.Enlistment_Day = new Date(2000, 3, 14).getTime();
        g.Transfer_Day =  new Date(2000, 4, 14).getTime();
        g.Discharge_Day =  new Date(2002, 1, 1).getTime();
        g.Discharge_Flag = true;
        g.Birthday = new Date(1970, 11, 11).getTime();
        g.Specialty ="고고학자";

        Soldier h = new Soldier();
        h.Name = "우솝" ;
        h.Rank = "일병";
        h.milliNumber = "18-45644442";
        h.Squad = "2분대";
        h.Enlistment_Day = new Date(1990, 3, 14).getTime();
        h.Transfer_Day =  new Date(1990, 4, 14).getTime();
        h.Discharge_Day =  new Date(1992, 1, 1).getTime();
        h.Discharge_Flag = false;
        h.Birthday = new Date(1970, 11, 11).getTime();
        h.Specialty ="바보";

        Soldier i = new Soldier();
        i.Name = "루키아" ;
        i.Rank = "상병";
        i.milliNumber = "13-45686542";
        i.Squad = "3분대";
        i.Enlistment_Day = new Date(1990, 03, 14).getTime();
        i.Transfer_Day =  new Date(1990, 04, 14).getTime();
        i.Discharge_Day =  new Date(1992, 01, 01).getTime();
        i.Discharge_Flag = false;
        i.Birthday = new Date(1970, 11, 11).getTime();
        i.Specialty ="칼잡이";

        Soldier j = new Soldier();
        j.Name = "나르코스" ;
        j.Rank = "병장";
        j.milliNumber = "15-45687542";
        j.Squad = "1분대";
        j.Enlistment_Day = new Date(1990, 03, 14).getTime();
        j.Transfer_Day =  new Date(1990, 04, 14).getTime();
        j.Discharge_Day =  new Date(1992, 01, 01).getTime();
        j.Discharge_Flag = true;
        j.Birthday = new Date(1970, 11, 11).getTime();
        j.Specialty ="환자";

        Soldier k = new Soldier();
        k.Name = "쵸파" ;
        k.Rank = "이등병";
        k.milliNumber = "13-45617542";
        k.Squad = "2분대";
        k.Enlistment_Day = new Date(1990, 03, 14).getTime();
        k.Transfer_Day =  new Date(1990, 04, 14).getTime();
        k.Discharge_Day =  new Date(1992, 01, 01).getTime();
        k.Discharge_Flag = false;
        k.Birthday = new Date(1970, 11, 11).getTime();
        k.Specialty ="의사";

        Soldier l = new Soldier();
        l.Name = "그랜라간" ;
        l.Rank = "일병";
        l.milliNumber = "13-45687742";
        l.Squad = "3분대";
        l.Enlistment_Day = new Date(1990, 03, 14).getTime();
        l.Transfer_Day =  new Date(1990, 04, 14).getTime();
        l.Discharge_Day =  new Date(1992, 01, 01).getTime();
        l.Discharge_Flag = false;
        l.Birthday = new Date(1970, 11, 11).getTime();
        l.Specialty ="기계병";

        Soldier m = new Soldier();
        m.Name = "나이스" ;
        m.Rank = "상병";
        m.milliNumber = "13-4568c542";
        m.Squad = "1분대";
        m.Enlistment_Day = new Date(1990, 03, 14).getTime();
        m.Transfer_Day =  new Date(1990, 04, 14).getTime();
        m.Discharge_Day =  new Date(1992, 01, 01).getTime();
        m.Discharge_Flag = false;
        m.Birthday = new Date(1970, 11, 11).getTime();
        m.Specialty ="학부모";

        Soldier n = new Soldier();
        n.Name = "맨홀" ;
        n.Rank = "병장";
        n.milliNumber = "13-45687541";
        n.Squad = "2분대";
        n.Enlistment_Day = new Date(1990, 03, 14).getTime();
        n.Transfer_Day =  new Date(1990, 04, 14).getTime();
        n.Discharge_Day =  new Date(1992, 01, 01).getTime();
        n.Discharge_Flag = true;
        n.Birthday = new Date(1970, 11, 11).getTime();
        n.Specialty ="뚜껑";

        Soldier o = new Soldier();
        o.Name = "블리치" ;
        o.Rank = "이등병";
        o.milliNumber = "12-45689542";
        o.Squad = "2분대";
        o.Enlistment_Day = new Date(1990, 03, 14).getTime();
        o.Transfer_Day =  new Date(1990, 04, 14).getTime();
        o.Discharge_Day =  new Date(1992, 01, 01).getTime();
        o.Discharge_Flag = false;
        o.Birthday = new Date(1970, 11, 11).getTime();
        o.Specialty ="화학병";

        Soldier p = new Soldier();
        p.Name = "우루과이" ;
        p.Rank = "일병";
        p.milliNumber = "19-45627542";
        p.Squad = "1분대";
        p.Enlistment_Day = new Date(1990, 03, 14).getTime();
        p.Transfer_Day =  new Date(1990, 04, 14).getTime();
        p.Discharge_Day =  new Date(1992, 01, 01).getTime();
        p.Discharge_Flag = false;
        p.Birthday = new Date(1970, 11, 11).getTime();
        p.Specialty ="지구본";

        Soldier q = new Soldier();
        q.Name = "이집트" ;
        q.Rank = "일병";
        q.milliNumber = "10-45681542";
        q.Squad = "1분대";
        q.Enlistment_Day = new Date(1990, 03, 14).getTime();
        q.Transfer_Day =  new Date(1990, 04, 14).getTime();
        q.Discharge_Day =  new Date(1992, 01, 01).getTime();
        q.Discharge_Flag = false;
        q.Birthday = new Date(1970, 11, 11).getTime();
        q.Specialty ="축구병";

        Soldier r = new Soldier();
        r.Name = "브라질" ;
        r.Rank = "일병";
        r.milliNumber = "02-45686542";
        r.Squad = "1분대";
        r.Enlistment_Day = new Date(1990, 03, 14).getTime();
        r.Transfer_Day =  new Date(1990, 04, 14).getTime();
        r.Discharge_Day =  new Date(1992, 01, 01).getTime();
        r.Discharge_Flag = false;
        r.Birthday = new Date(1970, 11, 11).getTime();
        r.Specialty ="미라병";

        Soldier s = new Soldier();
        s.Name = "한국";
        s.Rank = "일병";
        s.milliNumber = "20-45680542";
        s.Squad = "1분대";
        s.Enlistment_Day = new Date(1990, 03, 14).getTime();
        s.Transfer_Day =  new Date(1990, 04, 14).getTime();
        s.Discharge_Day =  new Date(1992, 01, 01).getTime();
        s.Discharge_Flag = false;
        s.Birthday = new Date(1970, 11, 11).getTime();
        s.Specialty ="화생방병";

        ArrayList<Soldier> x = new ArrayList<>();
        x.add(a);
        x.add(d);
        x.add(g);
        x.add(j);
        x.add(m);
        x.add(p);
        x.add(q);
        x.add(r);
        x.add(s);

        Squad alpha = new Squad("1분대", x);

        ArrayList<Soldier> y = new ArrayList<>();
        y.add(b);
        y.add(e);
        y.add(h);
        y.add(k);
        y.add(n);
        y.add(o);

        Squad beta = new Squad("2분대", y);

        ArrayList<Soldier> z = new ArrayList<>();
        z.add(c);
        z.add(f);
        z.add(i);
        z.add(l);

        Squad gamma = new Squad("3분대", z);

        createSquad(db, alpha);
        createSquad(db, beta);
        createSquad(db, gamma);
    }

    private void createSquad(SQLiteDatabase db, Squad squad) {
        createSquad(db, squad.Name);
        for(int i = 0; i < squad.SoldierList.size(); i++){
            createSoldier(db, squad.SoldierList.get(i));
        }
    }

    private void createSquad(SQLiteDatabase db, String name) {
        String sql = "INSERT INTO " + TABLE_SQUADS + " VALUES (?)";
        String [] arg = {name};
        db.execSQL(sql, arg);;
    }
    private void createSoldier(SQLiteDatabase db, Soldier soldier) {
        //Soldier 추가
        String sql = "INSERT INTO "+ TABLE_SOLDIERS +" (name, squad, rank, milli_number, specialty, birthday, " +
                "enlistment_day, transfer_day, discharge_day, discharge_flag) VALUES (?,?,?,?,?,?,?,?,?,?)";

        String df = (soldier.Discharge_Flag ? String.valueOf(0) : String.valueOf(0));
        String [] arg =
                {
                        soldier.Name, soldier.Squad, soldier.Rank,
                        soldier.milliNumber, soldier.Specialty,
                        String.valueOf(soldier.Birthday),
                        String.valueOf(soldier.Enlistment_Day),
                        String.valueOf(soldier.Transfer_Day),
                        String.valueOf(soldier.Discharge_Day),
                        df
                };
        db.execSQL(sql, arg);
    }
}
