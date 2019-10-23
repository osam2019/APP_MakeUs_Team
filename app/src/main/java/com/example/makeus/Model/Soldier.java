package com.example.makeus.Model;

import android.os.Parcel;
import android.os.Parcelable;

/* 용사정보를 만드는 클래스, 효율적인 데이터 전송을 위한 parcelable 상속*/
public class Soldier implements Parcelable {
    public String name; // 성명
    public String Squad; // 분대
    public String rank; // 계급
    public String milliNumber; // 군번
    public String specialty; // 주특기
    public long birthday; // 생년월일
    public long enlistmentDay; // 입대일
    public long transferDay; // 전입일
    public long dischargeDay; // 전역예정일

    public PhysicalScore physicalScore;

    public Soldier() {
        this.name = null;
        this.Squad = null;
        this.rank = null;
        this.milliNumber = null;
        this.birthday = 0;
        this.enlistmentDay = 0;
        this.transferDay = 0;
        this.dischargeDay = 0;
        this.specialty = null;
        this.physicalScore = new PhysicalScore();
    }


    public Soldier(String Name, String Rank, String milliNumber, PhysicalScore physicalScore) {
        this.name = Name;
        this.rank = Rank;
        this.milliNumber = milliNumber;
        this.physicalScore = physicalScore;
    }

    /* 용사정보 입력 메소드*/
    public void Input_Infomation(String name, String squad,  String rank, String milli_Number, String specialty, long birthday, long enlistment_Day, long transfer_Day, long discharge_Day){
        this.name = name;
        this.Squad = squad;
        this.milliNumber = milli_Number;
        this.birthday = birthday;
        this.enlistmentDay = enlistment_Day;
        this.transferDay = transfer_Day;
        this.dischargeDay = discharge_Day;
        this.rank = rank;
        this.specialty = specialty;
    }

    public String getRank(){
        return this.rank;
    }

    public String getMilliNumber(){
        return this.milliNumber;
    }

    public String getSquad() {
        return this.Squad;
    }

    public PhysicalScore getPhysicalScore() {
        return this.physicalScore;
    }

    public void setPhysicalScore(PhysicalScore physicalScore) {
        this.physicalScore = physicalScore;
    }

    public void setPushUp(int pushUp) {
        this.physicalScore.setPushUp(pushUp);
    }

    public void setSitUp(int sitUp) {
        this.physicalScore.setSitUp(sitUp);
    }

    public void setRunning(long running) {
        this.physicalScore.setRunning(running);
    }

    /*parcelable 사용을 위한 creator 객체 생성, 생성자 Soldier*/
    public static final Creator<Soldier> CREATOR = new Creator<Soldier>() {
        // 객체 복원
        @Override
        public Soldier createFromParcel(Parcel source) {
            Soldier ret = new Soldier();
            ret.name = source.readString();
            ret.Squad = source.readString();
            ret.rank = source.readString();
            ret.milliNumber = source.readString();
            ret.specialty = source.readString();

            ret.birthday = source.readLong();
            ret.enlistmentDay = source.readLong();
            ret.transferDay = source.readLong();
            ret.dischargeDay = source.readLong();

            ret.physicalScore = source.readParcelable(PhysicalScore.class.getClassLoader());
            return ret;
        }

        @Override
        public Soldier[] newArray(int size) {
            return new Soldier[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(Squad);
        dest.writeString(rank);
        dest.writeString(milliNumber);
        dest.writeString(specialty);
        dest.writeLong(birthday);
        dest.writeLong(enlistmentDay);
        dest.writeLong(transferDay);
        dest.writeLong(dischargeDay);

        dest.writeParcelable(physicalScore, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
