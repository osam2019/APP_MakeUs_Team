package com.example.makeus.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Soldier implements Parcelable {
    public String Name; // 성명
    public String Squad; // 분대
    public String Rank; // 계급
    public String Milli_Number; // 군번
    public String Specialty; // 주특기
    public long Birthday; // 생년월일
    public long Enlistment_Day; // 입대일
    public long Transfer_Day; // 전입일
    public long Discharge_Day; // 전역예정일
    public boolean Discharge_Flag; // 전역여부

    public Soldier() {
        this.Name = "Test Name";
        this.Squad = "Squad Test";
        this.Rank = "Test Rank";
        this.Milli_Number = "00-12345678";

        this.Birthday = 1999080800;
        this.Enlistment_Day = 2019010100;
        this.Transfer_Day = 2019022000;
        this.Discharge_Day = 2020070100;
        this.Discharge_Flag = false;
        Specialty = "화학병";
    }
    public  Soldier(String name) {
        Name = name;
    }
    public Soldier(String Name, String Squad, String Rank, String Milli_Number) {
        this.Name = Name;
        this.Squad = Squad;
        this.Rank = Rank;
        this.Milli_Number = Milli_Number;
    }

    public void Input_Infomation(String name, String squad,  String rank, String milli_Number, String specialty, long birthday, long enlistment_Day, long transfer_Day, long discharge_Day, boolean discharge_Flag){
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

    public String getRank(){
        return this.Rank;
    }

    public String getMilli_Number(){
        return this.Milli_Number;
    }


    public static final Creator<Soldier> CREATOR = new Creator<Soldier>() {
        // 객체 복원
        @Override
        public Soldier createFromParcel(Parcel source) {
            String Name = source.readString();
            String Squad = source.readString();
            String Rank = source.readString();
            String Milli_Number = source.readString();
            String Specialty = source.readString();
            long Birthday = source.readLong();
            long Enlistment_Day = source.readLong();
            long Transfer_Day = source.readLong();
            long Discharge_Day = source.readLong();
            boolean Discharge_Flag = source.readByte() != 0;
            Soldier t1 = new Soldier();
            t1.Input_Infomation(Name, Squad, Rank, Milli_Number, Specialty, Birthday, Enlistment_Day, Transfer_Day, Discharge_Day, Discharge_Flag);

            return t1;
        }

        @Override
        public Soldier[] newArray(int size) {
            return new Soldier[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(Squad);
        dest.writeString(Rank);
        dest.writeString(Milli_Number);
        dest.writeString(Specialty);
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

}
