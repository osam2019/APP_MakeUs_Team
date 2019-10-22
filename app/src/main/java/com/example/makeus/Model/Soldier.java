package com.example.makeus.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Soldier {
    public String Name; // 성명
    public String Squad; // 분대
    private String Rank; // 계급
    private String Milli_Number; // 군번
    private long Birthday; // 생년월일
    private long Enlistment_Day; // 입대일
    private long Transfer_Day; // 전입일
    private long Discharge_Day; // 전역예정일
    private boolean Discharge_Flag; // 전역여부
    private Specialty Specialty; // 주특기

    public Soldier(String Name, String Squad, String Rank, String Milli_Number){
        this.Name = Name;
        this.Squad = Squad;
        this.Rank = Rank;
        this.Milli_Number = Milli_Number;
    }

    public void Input_Infomation(String name, String squad, String milli_Number, long birthday, long enlistment_Day, long transfer_Day, long discharge_Day, boolean discharge_Flag, String rank, Specialty specialty){
        this.Name = name;
        this.Squad = squad;
        this.Milli_Number = milli_Number;
        this.Birthday = birthday;
        this.Enlistment_Day = enlistment_Day;
        this.Transfer_Day = transfer_Day;
        this.Discharge_Day = discharge_Day;
        this.Discharge_Flag = discharge_Flag;
        this.Rank = rank;
        this.Specialty = specialty;
    }

    public Soldier Output_Infomation(){
        return this;
    }

    /*
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

        public Rank Rank; // 계급
        private Specialty Specialty; // 주특기

        dest.writeString(Squad);
        dest.writeString(Milli_Number);
        dest.writeLong(Birthday);
        dest.writeLong(Enlistment_Day);
        dest.writeLong(Transfer_Day);
        dest.writeLong(Discharge_Day);
        dest.writeByte((byte) (Discharge_Flag ? 1 : 0));


    }

    @Override
    public int describeContents() {
        return 0;
    }
    */
}
