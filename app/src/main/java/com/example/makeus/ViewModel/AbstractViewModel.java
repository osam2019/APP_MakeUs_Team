package com.example.makeus.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.makeus.Model.DBHelper;
import com.example.makeus.Model.Soldier;
import com.example.makeus.Model.Squad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractViewModel extends ViewModel {

    private MutableLiveData<List<Squad>> squads;
    private MutableLiveData<List<Soldier>> soldiers;

    public AbstractViewModel() {
        this.squads = new MutableLiveData<>();
        squads.setValue(new ArrayList<Squad>());
        this.soldiers = new MutableLiveData<>();
        soldiers.setValue(new ArrayList<Soldier>());
        this.SampleData();
    }


    public MutableLiveData<List<Squad>> getLiveDataSquads() {
        return squads;
    }
    public MutableLiveData<List<Soldier>> getLiveDataSoldiers() {
        return soldiers;
    }

    public void updateDataFromDB(DBHelper dbHelper) {
        this.squads.setValue(dbHelper.getAllSquad());
        this.soldiers.setValue(dbHelper.getAllSoldiers());
    }

    private void SampleData() {

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
        b.milliNumber = "14-45688526";
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
        c.milliNumber = "15-78967542";
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
        d.milliNumber = "16-45688431";
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
        e.milliNumber = "17-45681234";
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
        f.milliNumber = "14-45685421";
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
        i.milliNumber = "13-45687542";
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
        j.milliNumber = "13-45687542";
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
        k.milliNumber = "13-45687542";
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
        l.milliNumber = "13-45687542";
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
        m.milliNumber = "13-45687542";
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
        n.milliNumber = "13-45687542";
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
        o.milliNumber = "13-45687542";
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
        p.milliNumber = "13-45687542";
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
        q.milliNumber = "13-45687542";
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
        r.milliNumber = "13-45687542";
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
        s.milliNumber = "13-45687542";
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

        Squad alpha = new Squad("a", x);

        ArrayList<Soldier> y = new ArrayList<>();
        y.add(b);
        y.add(e);
        y.add(h);
        y.add(k);
        y.add(n);
        y.add(o);

        Squad beta = new Squad("b", y);

        ArrayList<Soldier> z = new ArrayList<>();
        z.add(c);
        z.add(f);
        z.add(i);
        z.add(l);

        Squad gamma = new Squad("c", y);

        this.squads.getValue().add(alpha);
        this.squads.getValue().add(beta);
        this.squads.getValue().add(gamma);

        this.soldiers.getValue().add(a);
        this.soldiers.getValue().add(b);
        this.soldiers.getValue().add(c);
        this.soldiers.getValue().add(d);
        this.soldiers.getValue().add(e);
        this.soldiers.getValue().add(f);
        this.soldiers.getValue().add(g);
        this.soldiers.getValue().add(h);
        this.soldiers.getValue().add(i);
        this.soldiers.getValue().add(j);
        this.soldiers.getValue().add(k);
        this.soldiers.getValue().add(l);
        this.soldiers.getValue().add(m);
        this.soldiers.getValue().add(n);
        this.soldiers.getValue().add(o);
        this.soldiers.getValue().add(p);
        this.soldiers.getValue().add(q);
        this.soldiers.getValue().add(r);
        this.soldiers.getValue().add(s);

    }
}
