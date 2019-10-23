package com.example.makeus.Module;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.makeus.Model.PhysicalScore;
import com.example.makeus.Model.Soldier;
import com.example.makeus.Model.Squad;
import com.example.makeus.ViewModel.AbstractViewModel;

import java.text.ParseException;
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

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //용사 테이블 생성
        String create_soldiers =
                "create table " + TABLE_SOLDIERS + " (" +
                "name char(60), " +
                "squad varchar(60), " +
                "rank char(60), " +
                "milli_number char(60), " +
                "specialty varchar(60), " +
                "birthday int, " +
                "enlistment_day int, " +
                "transfer_day int, " +
                "discharge_day int, " +
                "push_up int, " +
                "sit_up int, " +
                "running int, " +
                "primary key(milli_number) " +
                ") ";
        db.execSQL(create_soldiers);

        //분대 테이블 생성
        String create_squads =
                "create table "+ TABLE_SQUADS+" (" +
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
    }

    public void updateSquad(String oldName, String newName) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE "+TABLE_SQUADS+" SET name = ? WHERE name = " + oldName;
        String [] arg = { newName };
        db.execSQL(sql, arg);
        db.close();
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

        soldier.name = cursor.getString(cursor.getColumnIndex("name"));
        soldier.Squad = cursor.getString(cursor.getColumnIndex("squad"));
        soldier.rank = cursor.getString(cursor.getColumnIndex("rank"));
        soldier.milliNumber = cursor.getString(cursor.getColumnIndex("milli_number"));
        soldier.specialty = cursor.getString(cursor.getColumnIndex("specialty"));
        soldier.birthday = cursor.getLong(cursor.getColumnIndex("birthday"));
        soldier.enlistmentDay = cursor.getLong(cursor.getColumnIndex("enlistment_day"));
        soldier.transferDay = cursor.getLong(cursor.getColumnIndex("transfer_day"));
        soldier.dischargeDay = cursor.getLong(cursor.getColumnIndex("discharge_day"));
        soldier.dischargeDay = cursor.getLong(cursor.getColumnIndex("push_up"));
        soldier.dischargeDay = cursor.getLong(cursor.getColumnIndex("sit_up"));
        soldier.dischargeDay = cursor.getLong(cursor.getColumnIndex("running"));

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
                "enlistment_day, transfer_day, discharge_day, push_up, sit_up, running) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

        String [] arg =
                {
                    soldier.name, soldier.Squad, soldier.rank,
                    soldier.milliNumber, soldier.specialty,
                    String.valueOf(soldier.birthday),
                    String.valueOf(soldier.enlistmentDay),
                    String.valueOf(soldier.transferDay),
                    String.valueOf(soldier.dischargeDay),
                    String.valueOf(soldier.physicalScore.getPushUp()),
                    String.valueOf(soldier.physicalScore.getSitUp()),
                    String.valueOf(soldier.physicalScore.getRunning()),
                };
        db.execSQL(sql, arg);
        db.close();
    }

    public void deleteSoldier(String milliNumber) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM "+TABLE_SOLDIERS+" WHERE milli_number = ?";
        String[] args = {milliNumber};
        db.execSQL(sql, args);
        db.close();
    }

    public void updateSoldier(String milliNumber, String newName, String rank, long enlistment_Day, long transfer_Day, long discharge_Day, long birth, String specialty, String squad, PhysicalScore physicalScore) {
        SQLiteDatabase db = getWritableDatabase();

        String sql = "UPDATE " + TABLE_SOLDIERS + " SET name = ?, squad = ?, rank = ?, specialty = ?, birthday = ?," +
                "enlistment_day = ?, transfer_day = ?, discharge_day = ?, push_up = ?, sit_up = ?, running = ? WHERE milli_number = ?";

        String [] arg = {newName, squad, rank, specialty,
                String.valueOf(birth),
                String.valueOf(enlistment_Day),
                String.valueOf(transfer_Day),
                String.valueOf(discharge_Day),
                String.valueOf(physicalScore.getPushUp()),
                String.valueOf(physicalScore.getSitUp()),
                String.valueOf(physicalScore.getRunning()),
                String.valueOf(milliNumber)
        };

        db.execSQL(sql, arg);
        db.close();
    }

    public void updateSoldier(Soldier soldier) {
        updateSoldier(soldier.milliNumber, soldier.name, soldier.rank, soldier.enlistmentDay, soldier.transferDay, soldier.dischargeDay, soldier.birthday, soldier.specialty, soldier.Squad, soldier.physicalScore);
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        try{
            Soldier a = new Soldier();
            a.name = "산타" ;
            a.rank = "일병";
            a.milliNumber = "13-45687542";
            a.Squad = "1분대";
            a.enlistmentDay = new Date(1990, 3, 14).getTime();
            a.transferDay =  new Date(1990, 4, 14).getTime();
            a.dischargeDay =  new Date(1992, 1, 1).getTime();
            a.birthday = new Date(1970, 11, 11).getTime();
            a.specialty ="화학병";
            a.physicalScore = new PhysicalScore(59, 70, dateFormat.parse("48:56").getTime());

            Soldier b = new Soldier();
            b.name = "마리아" ;
            b.rank = "상병";
            b.milliNumber = "14-45618526";
            b.Squad = "2분대";
            b.enlistmentDay = new Date(1991, 4, 15).getTime();
            b.transferDay =  new Date(1991, 5, 15).getTime();
            b.dischargeDay =  new Date(1993, 2, 2).getTime();
            b.birthday = new Date(1971, 12, 12).getTime();
            b.specialty ="상황병";
            b.physicalScore = new PhysicalScore(78, 35, dateFormat.parse("9:20").getTime());

            Soldier c = new Soldier();
            c.name = "요셉" ;
            c.rank = "병장";
            c.milliNumber = "15-78987542";
            c.Squad = "3분대";
            c.enlistmentDay = new Date(1992, 5, 16).getTime();
            c.transferDay =  new Date(1992, 6, 16).getTime();
            c.dischargeDay =  new Date(1994, 3, 3).getTime();
            c.birthday = new Date(1973, 1, 14).getTime();
            c.specialty ="전술기 관리병";
            c.physicalScore = new PhysicalScore(10, 10, dateFormat.parse("10:20").getTime());

            Soldier d = new Soldier();
            d.name = "로빈" ;
            d.rank = "이병";
            d.milliNumber = "16-45688434";
            d.Squad = "1분대";
            d.enlistmentDay = new Date(1994, 6, 20).getTime();
            d.transferDay =  new Date(1994, 7, 22).getTime();
            d.dischargeDay =  new Date(1996, 2, 7).getTime();
            d.birthday = new Date(1974, 10, 9).getTime();
            d.specialty ="해적";
            d.physicalScore = new PhysicalScore(10, 10, dateFormat.parse("10:20").getTime());

            Soldier e = new Soldier();
            e.name = "루피" ;
            e.rank = "일병";
            e.milliNumber = "17-45681134";
            e.Squad = "2분대";
            e.enlistmentDay = new Date(1998, 7, 14).getTime();
            e.transferDay =  new Date(1998, 8, 18).getTime();
            e.dischargeDay =  new Date(2000, 1, 5).getTime();
            e.birthday = new Date(1980, 1, 1).getTime();
            e.specialty ="탐정";
            e.physicalScore = new PhysicalScore(10, 10, dateFormat.parse("10:20").getTime());

            Soldier f = new Soldier();
            f.name = "조로" ;
            f.rank = "상병";
            f.milliNumber = "14-49685421";
            f.Squad = "3분대";
            f.enlistmentDay = new Date(2000, 3, 14).getTime();
            f.transferDay =  new Date(2000, 5, 14).getTime();
            f.dischargeDay =  new Date(2002, 8, 7).getTime();
            f.birthday = new Date(1980, 3, 25).getTime();
            f.specialty ="해적사냥꾼";
            f.physicalScore = new PhysicalScore(10, 10, dateFormat.parse("10:20").getTime());

            Soldier g = new Soldier();
            g.name = "나미" ;
            g.rank = "병장";
            g.milliNumber = "14-45452242";
            g.Squad = "1분대";
            g.enlistmentDay = new Date(2000, 3, 14).getTime();
            g.transferDay =  new Date(2000, 4, 14).getTime();
            g.dischargeDay =  new Date(2002, 1, 1).getTime();
            g.birthday = new Date(1970, 11, 11).getTime();
            g.specialty ="고고학자";
            g.physicalScore = new PhysicalScore(10, 10, dateFormat.parse("10:20").getTime());

            Soldier h = new Soldier();
            h.name = "우솝" ;
            h.rank = "일병";
            h.milliNumber = "18-45644442";
            h.Squad = "2분대";
            h.enlistmentDay = new Date(1990, 3, 14).getTime();
            h.transferDay =  new Date(1990, 4, 14).getTime();
            h.dischargeDay =  new Date(1992, 1, 1).getTime();
            h.birthday = new Date(1970, 11, 11).getTime();
            h.specialty ="바보";
            h.physicalScore = new PhysicalScore(10, 10, dateFormat.parse("10:20").getTime());

            Soldier i = new Soldier();
            i.name = "루키아" ;
            i.rank = "상병";
            i.milliNumber = "13-45686542";
            i.Squad = "3분대";
            i.enlistmentDay = new Date(1990, 03, 14).getTime();
            i.transferDay =  new Date(1990, 04, 14).getTime();
            i.dischargeDay =  new Date(1992, 01, 01).getTime();
            i.birthday = new Date(1970, 11, 11).getTime();
            i.specialty ="칼잡이";
            i.physicalScore = new PhysicalScore(10, 10, dateFormat.parse("10:20").getTime());

            Soldier j = new Soldier();
            j.name = "나르코스" ;
            j.rank = "병장";
            j.milliNumber = "15-45687542";
            j.Squad = "1분대";
            j.enlistmentDay = new Date(1990, 03, 14).getTime();
            j.transferDay =  new Date(1990, 04, 14).getTime();
            j.dischargeDay =  new Date(1992, 01, 01).getTime();
            j.birthday = new Date(1970, 11, 11).getTime();
            j.specialty ="환자";
            j.physicalScore = new PhysicalScore(10, 10, dateFormat.parse("10:20").getTime());

            Soldier k = new Soldier();
            k.name = "쵸파" ;
            k.rank = "이등병";
            k.milliNumber = "13-45617542";
            k.Squad = "2분대";
            k.enlistmentDay = new Date(1990, 03, 14).getTime();
            k.transferDay =  new Date(1990, 04, 14).getTime();
            k.dischargeDay =  new Date(1992, 01, 01).getTime();
            k.birthday = new Date(1970, 11, 11).getTime();
            k.specialty ="의사";
            k.physicalScore = new PhysicalScore(10, 10, dateFormat.parse("10:20").getTime());

            Soldier l = new Soldier();
            l.name = "그랜라간" ;
            l.rank = "일병";
            l.milliNumber = "13-45687742";
            l.Squad = "3분대";
            l.enlistmentDay = new Date(1990, 03, 14).getTime();
            l.transferDay =  new Date(1990, 04, 14).getTime();
            l.dischargeDay =  new Date(1992, 01, 01).getTime();
            l.birthday = new Date(1970, 11, 11).getTime();
            l.specialty ="기계병";
            l.physicalScore = new PhysicalScore(10, 10, dateFormat.parse("10:20").getTime());

            Soldier m = new Soldier();
            m.name = "나이스" ;
            m.rank = "상병";
            m.milliNumber = "13-4568c542";
            m.Squad = "1분대";
            m.enlistmentDay = new Date(1990, 03, 14).getTime();
            m.transferDay =  new Date(1990, 04, 14).getTime();
            m.dischargeDay =  new Date(1992, 01, 01).getTime();
            m.birthday = new Date(1970, 11, 11).getTime();
            m.specialty ="학부모";
            m.physicalScore = new PhysicalScore(10, 10, dateFormat.parse("10:20").getTime());

            Soldier n = new Soldier();
            n.name = "맨홀" ;
            n.rank = "병장";
            n.milliNumber = "13-45687541";
            n.Squad = "2분대";
            n.enlistmentDay = new Date(1990, 03, 14).getTime();
            n.transferDay =  new Date(1990, 04, 14).getTime();
            n.dischargeDay =  new Date(1992, 01, 01).getTime();
            n.birthday = new Date(1970, 11, 11).getTime();
            n.specialty ="뚜껑";
            n.physicalScore = new PhysicalScore(10, 10, dateFormat.parse("10:20").getTime());

            Soldier o = new Soldier();
            o.name = "블리치" ;
            o.rank = "이등병";
            o.milliNumber = "12-45689542";
            o.Squad = "2분대";
            o.enlistmentDay = new Date(1990, 03, 14).getTime();
            o.transferDay =  new Date(1990, 04, 14).getTime();
            o.dischargeDay =  new Date(1992, 01, 01).getTime();
            o.birthday = new Date(1970, 11, 11).getTime();
            o.specialty ="화학병";
            o.physicalScore = new PhysicalScore(4, 146, dateFormat.parse("10:23").getTime());

            Soldier p = new Soldier();
            p.name = "우루과이" ;
            p.rank = "일병";
            p.milliNumber = "19-45627542";
            p.Squad = "1분대";
            p.enlistmentDay = new Date(1990, 03, 14).getTime();
            p.transferDay =  new Date(1990, 04, 14).getTime();
            p.dischargeDay =  new Date(1992, 01, 01).getTime();
            p.birthday = new Date(1970, 11, 11).getTime();
            p.specialty ="지구본";
            p.physicalScore = new PhysicalScore(50, 60, dateFormat.parse("19:20").getTime());

            Soldier q = new Soldier();
            q.name = "이집트" ;
            q.rank = "일병";
            q.milliNumber = "10-45681542";
            q.Squad = "1분대";
            q.enlistmentDay = new Date(1990, 03, 14).getTime();
            q.transferDay =  new Date(1990, 04, 14).getTime();
            q.dischargeDay =  new Date(1992, 01, 01).getTime();
            q.birthday = new Date(1970, 11, 11).getTime();
            q.specialty ="축구병";
            q.physicalScore = new PhysicalScore(15, 17, dateFormat.parse("03:20").getTime());

            Soldier r = new Soldier();
            r.name = "브라질" ;
            r.rank = "일병";
            r.milliNumber = "02-45686542";
            r.Squad = "1분대";
            r.enlistmentDay = new Date(1990, 03, 14).getTime();
            r.transferDay =  new Date(1990, 04, 14).getTime();
            r.dischargeDay =  new Date(1992, 01, 01).getTime();
            r.birthday = new Date(1970, 11, 11).getTime();
            r.specialty ="미라병";
            r.physicalScore = new PhysicalScore(13, 34, dateFormat.parse("12:00").getTime());

            Soldier s = new Soldier();
            s.name = "한국";
            s.rank = "일병";
            s.milliNumber = "20-45680542";
            s.Squad = "1분대";
            s.enlistmentDay = new Date(1990, 03, 14).getTime();
            s.transferDay =  new Date(1990, 04, 14).getTime();
            s.dischargeDay =  new Date(1992, 01, 01).getTime();
            s.birthday = new Date(1970, 11, 11).getTime();
            s.specialty ="화생방병";
            s.physicalScore = new PhysicalScore(10, 10, dateFormat.parse("10:20").getTime());

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
        }catch (ParseException e){
            Log.d("makeus", e.getStackTrace().toString());
        }

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
                "enlistment_day, transfer_day, discharge_day) VALUES (?,?,?,?,?,?,?,?,?)";

        String [] arg =
                {
                        soldier.name, soldier.Squad, soldier.rank,
                        soldier.milliNumber, soldier.specialty,
                        String.valueOf(soldier.birthday),
                        String.valueOf(soldier.enlistmentDay),
                        String.valueOf(soldier.transferDay),
                        String.valueOf(soldier.dischargeDay),
                };
        db.execSQL(sql, arg);
    }
}
