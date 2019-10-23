package com.example.makeus.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class PhysicalScore implements Parcelable {
    private int pushUp;
    private int sitUp;
    private long running;

    public PhysicalScore() {
        pushUp = 0;
        sitUp = 0;
        running = 0;
    }

    public PhysicalScore(int pushUp, int sitUp, long running) {
        this.pushUp = pushUp;
        this.sitUp = sitUp;
        this.running = running;
    }

    public int getPushUp() {
        return pushUp;
    }

    public int getSitUp() {
        return sitUp;
    }

    public long getRunning() {
        return running;
    }

    public void setPushUp(int pushUp) {
        this.pushUp = pushUp;
    }

    public void setSitUp(int sitUp) {
        this.sitUp = sitUp;
    }

    public void setRunning(long running) {
        this.running = running;
    }

    public static final Creator<PhysicalScore> CREATOR = new Creator<PhysicalScore>() {
        // 객체 복원
        @Override
        public PhysicalScore createFromParcel(Parcel source) {
            PhysicalScore ret = new PhysicalScore();
            ret.pushUp = source.readInt();
            ret.sitUp = source.readInt();
            ret.running = source.readLong();

            return ret;
        }

        @Override
        public PhysicalScore[] newArray(int size) {
            return new PhysicalScore[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(pushUp);
        parcel.writeInt(sitUp);
        parcel.writeLong(running);
    }
}
