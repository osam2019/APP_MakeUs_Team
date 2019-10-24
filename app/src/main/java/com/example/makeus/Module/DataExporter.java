package com.example.makeus.Module;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Toast;

import com.example.makeus.Model.Soldier;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DataExporter {

    public static boolean Export(DBHelper dbHelper) {
        List<Soldier> soldierList = dbHelper.getAllSoldiers();

        File root = Environment.getExternalStorageDirectory();
        File dir = new File (root.getAbsolutePath() + "/makeus");
        if(!dir.exists()) {
            dir.mkdirs();
        }
        String currentDateString = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(new Date());
        File file = new File(dir, "makeus_"+currentDateString+".csv");
        try {
            FileOutputStream stream = new FileOutputStream(file);
            Writer writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8);

            CSVPrinter csvPrinter = new CSVPrinter(writer,
                CSVFormat.DEFAULT.withHeader("분대", "계급", "이름", "군번", "주특기", "생일", "입대일", "전입일", "전역일", "팔굽혀펴기", "윗몸일으키기", "뜀걸음"));

            SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat time = new SimpleDateFormat("mm:ss");
            for(int i = 0 ; i < soldierList.size(); i++) {
                Soldier soldier = soldierList.get(i);
                csvPrinter.printRecord(soldier.Squad, soldier.rank, soldier.name,
                        soldier.milliNumber, soldier.specialty, day.format(soldier.birthday),
                        day.format(soldier.enlistmentDay), day.format(soldier.transferDay),
                        day.format(soldier.dischargeDay), soldier.physicalScore.getPushUp(),
                        soldier.physicalScore.getSitUp(), time.format(soldier.physicalScore.getRunning()));
            }
            csvPrinter.flush();
        }
        catch (FileNotFoundException e) {
            Log.d("makeus", e.getStackTrace().toString());
            return false;
        } catch (IOException e) {
            Log.d("makeus", e.getStackTrace().toString());
            return false;
        }
        return true;
    }
}