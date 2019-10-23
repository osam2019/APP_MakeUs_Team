package com.example.makeus.ViewModel.Fragment;

import androidx.fragment.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.makeus.Model.Soldier;
import com.example.makeus.Module.DBHelper;
import com.example.makeus.R;
import com.example.makeus.ViewModel.AbstractViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PhysicalInputFragment extends DialogFragment {
    AbstractViewModel mViewModel;
    Context context;
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
        View fragment = inflater.inflate(R.layout.physical_input_fragment,null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(fragment);

        final EditText situpET = fragment.findViewById(R.id.situp);
        final EditText pushupET = fragment.findViewById(R.id.pushup);
        final EditText runningET = fragment.findViewById(R.id.running);

        builder.setPositiveButton("제출", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                DBHelper dbHelper = new DBHelper(context);
                SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
                long running;
                try {
                    running = dateFormat.parse(runningET.getText().toString()).getTime();
                } catch (ParseException e) {
                    Toast.makeText(context, "시간이 잘못 입력되었어요.", Toast.LENGTH_LONG);
                    return;
                }
                soldier.physicalScore.setSitUp(Integer.valueOf(situpET.getText().toString()));
                soldier.physicalScore.setPushUp(Integer.valueOf(pushupET.getText().toString()));
                soldier.physicalScore.setRunning(running);
                dbHelper.updateSoldier(soldier);
                mViewModel.updateDataFromDB(dbHelper);
            }
        });
        builder.setNegativeButton("취소", null);

        return builder.create();
    }

}


