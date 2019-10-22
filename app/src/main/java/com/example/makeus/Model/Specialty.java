package com.example.makeus.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Specialty implements Parcelable {
    public String Name;

    public Specialty(String name){ Name = name; }

    public static final Creator<Specialty> CREATOR = new Creator<Specialty>() {
        // 객체 복원
        @Override
        public Specialty createFromParcel(Parcel source) {
            Specialty t1 = new Specialty(source.readString());

            return t1;
        }

        @Override
        public Specialty[] newArray(int size) {
            return new Specialty[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
