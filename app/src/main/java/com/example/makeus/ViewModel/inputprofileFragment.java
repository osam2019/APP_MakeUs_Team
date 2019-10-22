package com.example.makeus.ViewModel;

import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.NoCopySpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makeus.Model.Soldier;
import com.example.makeus.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class inputprofileFragment extends Fragment {

    final int OPTION_ENLISTMENT_DAY = 0;
    final int OPTION_TRANSFER_DAY = 1;
    final int OPTION_EXPECTED_DISCHARGE_DAY = 2;
    final int OPTION_BIRTH = 3;

    private InputprofileViewModel mViewModel;

    public static inputprofileFragment newInstance() {
        return new inputprofileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.inputprofile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(InputprofileViewModel.class);

        EditText expeceted_discharge_day_View  = getView().findViewById(R.id.input_discharge_Day);
        expeceted_discharge_day_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDatePicker(OPTION_EXPECTED_DISCHARGE_DAY);
            }
        });
        EditText transfer_day_View = getView().findViewById(R.id.input_transfer_Day);
        transfer_day_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDatePicker(OPTION_TRANSFER_DAY);
            }
        });
        EditText birth_day_view  = getView().findViewById(R.id.input_birth);
        birth_day_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDatePicker(OPTION_BIRTH);
            }
        });
        EditText enlistment_day_View = getView().findViewById(R.id.input_enlistment_Day);
        enlistment_day_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDatePicker(OPTION_ENLISTMENT_DAY);
            }
        });

        Button summit = getView().findViewById(R.id.modify);
        summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                    EditText nameView = view.getRootView().findViewById(R.id.input_name);
                    String name = nameView.getText().toString();

                    EditText rankView = view.getRootView().findViewById(R.id.input_rank);
                    String rank = rankView.getText().toString();

                    EditText transfer_day_View = view.getRootView().findViewById(R.id.input_transfer_Day);
                    Date transfer_day = dateFormat.parse(transfer_day_View.getText().toString());

                    EditText milli_numberView = view.getRootView().findViewById(R.id.input_milli_number);
                    String milli_number = milli_numberView.getText().toString();

                    EditText enlistment_day_View = view.getRootView().findViewById(R.id.input_enlistment_Day);
                    Date enlistment_day = dateFormat.parse(enlistment_day_View.getText().toString());

                    EditText expeceted_discharge_day_View  = view.getRootView().findViewById(R.id.input_discharge_Day);
                    Date expeceted_discharge_day = dateFormat.parse(expeceted_discharge_day_View.getText().toString());

                    EditText birth_day_View  = view.getRootView().findViewById(R.id.input_birth);
                    Date birth = dateFormat.parse(birth_day_View.getText().toString());

                    EditText specialtyView = view.getRootView().findViewById(R.id.input_specialty);
                    String specialty = specialtyView.getText().toString();

                    EditText squadView = view.getRootView().findViewById(R.id.input_squad);
                    String squad = squadView.getText().toString();

                    if(mViewModel.isExistSoldier(milli_number)) {
                        mViewModel.updateSoldier(milli_number, name, rank, enlistment_day.getTime(), transfer_day.getTime(), expeceted_discharge_day.getTime(), birth.getTime(), specialty, squad);
                    }
                    else {
                        Soldier soldier = new Soldier();
                        soldier.Input_Infomation(name, squad, milli_number, rank, specialty, birth.getTime(), expeceted_discharge_day.getTime(), transfer_day.getTime(), expeceted_discharge_day.getTime(), false);
                        mViewModel.createSoldier(soldier);
                    }
                }
                catch(ParseException e) {
                    Toast.makeText(getContext(), "용사가 추가되지 못했습니다.", Toast.LENGTH_LONG);
                }
            }
        });

    }

    private void callDatePicker(final int option) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                receiveDate(option, year, monthOfYear, dayOfMonth);
            }
        }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    private void receiveDate(int option, int year, int monthOfYear, int dayOfMonth) {
        switch(option) {
            case OPTION_ENLISTMENT_DAY:
                EditText enlistment_day = getView().findViewById(R.id.input_enlistment_Day);
                enlistment_day.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                break;
            case OPTION_TRANSFER_DAY:
                EditText transfer_day = getView().findViewById(R.id.input_transfer_Day);
                transfer_day.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                break;
            case OPTION_EXPECTED_DISCHARGE_DAY:
                EditText expeceted_discharge_day = getView().findViewById(R.id.input_discharge_Day);
                expeceted_discharge_day.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                break;
            case OPTION_BIRTH:
                EditText birth = getView().findViewById(R.id.input_birth);
                birth.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                break;
        }
    }

}
