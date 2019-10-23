package com.example.makeus.Model;

public class PhysicalScore {
    private int pushUp;
    private int sitUp;
    private long running;

    public PhysicalScore() {
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
}
