package com.example.makeus.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/* 부대를 만드는 클래스, 효율적인 데이터 전송을 위한 parcelable 상속*/
public class Squad implements Parcelable {

    public String Name;
    public List<Soldier> SoldierList;

    public Squad(String name) {
        Name = name;
        SoldierList = new ArrayList<>();
    }

    public Squad(String name, ArrayList<Soldier> soldierList) {
        Name = name;
        SoldierList = soldierList;
    }

    public static final Creator<Squad> CREATOR = new Creator<Squad>() {
        // 객체 복원
        @Override
        public Squad createFromParcel(Parcel source) {
            Squad t1 = new Squad(source.readString());
            t1.SoldierList = source.readArrayList(Soldier.class.getClassLoader());

            return t1;
        }

        @Override
        public Squad[] newArray(int size) {
            return new Squad[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeList(SoldierList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
