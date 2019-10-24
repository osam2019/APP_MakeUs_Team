package com.example.makeus.ViewModel.Fragment;

import androidx.fragment.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.makeus.Model.Soldier;
import com.example.makeus.Module.DBHelper;
import com.example.makeus.R;
import com.example.makeus.ViewModel.AbstractViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhysicalInputFragment extends DialogFragment {
    AbstractViewModel mViewModel;
    final Context context;
    Soldier soldier;

    public PhysicalInputFragment(Context context, AbstractViewModel viewModel, Soldier soldier) {
        this.context = context;
        this.mViewModel = viewModel;
        this.soldier = soldier;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View fragment = inflater.inflate(R.layout.physical_input_fragment,null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(fragment);

        final EditText pushupET = fragment.findViewById(R.id.pushup);
        final EditText situpET = fragment.findViewById(R.id.situp);
        final EditText runningET = fragment.findViewById(R.id.running);

        if(soldier.physicalScore.getPushUp() != 0) {
            pushupET.setText(Integer.toString(soldier.physicalScore.getPushUp()));
        }

        if(soldier.physicalScore.getSitUp() != 0) {
            situpET.setText(Integer.toString(soldier.physicalScore.getSitUp()));
        }

        if(soldier.physicalScore.getRunning() != 0) {
            runningET.setText(convertLongToTime(soldier.physicalScore.getRunning()));
        }

        Button cencelButton = fragment.findViewById(R.id.cencel);
        Button confirmButton = fragment.findViewById(R.id.confirm);

        cencelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhysicalInputFragment.this.dismiss();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(context);
                SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
                long running;
                try {
                    running = dateFormat.parse(runningET.getText().toString()).getTime();
                } catch (ParseException e) {
                    Toast.makeText(getActivity(), "시간이 잘못 입력되었어요.", Toast.LENGTH_LONG);
                    return;
                }
                soldier.physicalScore.setSitUp(Integer.valueOf(situpET.getText().toString()));
                soldier.physicalScore.setPushUp(Integer.valueOf(pushupET.getText().toString()));
                soldier.physicalScore.setRunning(running);
                dbHelper.updateSoldier(soldier);
                mViewModel.updateDataFromDB(dbHelper);

                PhysicalInputFragment.this.dismiss();
            }
        });

        return builder.create();
    }

    private String convertLongToTime(long mills) {
        Date date = new Date(mills);
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        String dateFormatted = formatter.format(date);
        return dateFormatted;
    }

}


