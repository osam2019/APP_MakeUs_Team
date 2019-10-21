package com.example.makeus.Model;

public class Soldier {
    public String Name;
    public String Squad;
    public Rank Rank;
    private String Millitary_Number;
    private long Birthday;
    private long Enlistment_Day;
    private long Transfer_Day;
    private long Discharge_Day;
    private boolean Discharge_Flag;
    private Specialty Specialty;

    public void Input_Infomation(String name, String squad, String millitary_Number, long birthday, long enlistment_Day, long transfer_Day, long discharge_Day, boolean discharge_Flag, Rank rank, Specialty specialty){
        this.Name = name;
        this.Squad = squad;
        this.Millitary_Number = millitary_Number;
        this.Birthday = birthday;
        this.Enlistment_Day = enlistment_Day;
        this.Transfer_Day = transfer_Day;
        this.Discharge_Day = discharge_Day;
        this.Discharge_Flag = discharge_Flag;
        this.Rank = rank;
        this.Specialty = specialty;
    }
}
