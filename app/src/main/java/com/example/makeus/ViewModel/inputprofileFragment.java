package com.example.makeus.ViewModel;

import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.makeus.Model.Soldier;
import com.example.makeus.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

        View v1 = inflater.inflate(R.layout.inputprofile_fragment, container, false);
        Button summit = v1.findViewById(R.id.modify);
        summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");


                EditText nameView = view.getRootView().findViewById(R.id.input_name);
                String name = nameView.getText().toString();

                EditText rankView = view.getRootView().findViewById(R.id.input_rank);
                String rank = rankView.getText().toString();

                EditText transfer_day_View = getView().findViewById(R.id.input_transfer_Day);
                transfer_day_View.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callDatePicker(OPTION_TRANSFER_DAY);
                    }
                });
                String transfer_day = transfer_day_View.getText().toString();

                EditText milli_numberView = view.getRootView().findViewById(R.id.input_milli_number);
                String milli_number = milli_numberView.getText().toString();

                EditText enlistment_day_View = getView().findViewById(R.id.input_enlistment_Day);
                enlistment_day_View.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callDatePicker(OPTION_ENLISTMENT_DAY);
                    }
                });
                try {
                    date = formatter.parse("20130526160000 ");
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String enlistment_day = dateFormat.parse(enlistment_day_View.getText().toString());

                EditText transfer_DayView = view.getRootView().findViewById(R.id.input_transfer_Day);
                String transfer_Day = transfer_DayView.getText().toString();

                EditText expeceted_discharge_day_View  = getView().findViewById(R.id.input_discharge_Day);
                expeceted_discharge_day_View.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callDatePicker(OPTION_EXPECTED_DISCHARGE_DAY);

                    }
                });
                String expeceted_discharge_day = expeceted_discharge_day_View.get();

                EditText birth_View  = getView().findViewById(R.id.input_birth);
                birth_View.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callDatePicker(OPTION_BIRTH);
                    }
                });
                String birth = birth_View.getText().toString();

                EditText specialtyView = view.getRootView().findViewById(R.id.name);
                String specialty = specialtyView.getText().toString();

                EditText squadView = view.getRootView().findViewById(R.id.input_squad);
                String squad = squadView.getText().toString();

                if(mViewModel.isExistSoldier(milli_number)) {
                    mViewModel.updateSoldier(milli_number, name, rank, expeceted_discharge_day, transfer_Day, expeceted_discharge_day, birth, specialty, squad);
                }
                else {
                    Soldier soldier = new Soldier();
                        soldier.Input_Infomation(name, squad, milli_number, birth, expeceted_discharge_day, transfer_Day, expeceted_discharge_day, false, rank, specialty);
                    mViewModel.createSoldier(soldier);
                }
            }
        });

        return v1;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(InputprofileViewModel.class);

        setDatePickerInput();

        // TODO: Use the ViewModel

    }

    // 날짜 Input 부분을 DatePicker로 받기
    private void setDatePickerInput() {

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
