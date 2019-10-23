package com.example.makeus.ViewModel.Fragment;

import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.makeus.Module.DBHelper;
import com.example.makeus.Model.Soldier;
import com.example.makeus.Model.Squad;
import com.example.makeus.R;
import com.example.makeus.ViewModel.InputprofileViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static androidx.databinding.DataBindingUtil.setContentView;


public class inputprofileFragment extends Fragment { //fragment class 선언

    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    final int OPTION_ENLISTMENT_DAY = 0;  //입대일 선언
    final int OPTION_TRANSFER_DAY = 1;  //전입일 선언
    final int OPTION_EXPECTED_DISCHARGE_DAY = 2;    //
    final int OPTION_BIRTH = 3;

    EditText inputName;
    Spinner inputRank;
    EditText inputMilNum;
    Spinner inputSquad;
    EditText inputEnlistmentDay;
    EditText inputTransferDay;
    EditText inputDischargeDay;
    EditText inputBirthday;
    EditText inputSpecialty;

    private InputprofileViewModel mViewModel;

    public static inputprofileFragment newInstance() {
        return new inputprofileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.inputprofile_fragment, container, false);

        inputName = fragment.findViewById(R.id.input_name);
        inputTransferDay = fragment.findViewById(R.id.input_transfer_Day);
        inputMilNum = fragment.findViewById(R.id.input_milli_number);
        inputSquad = fragment.findViewById(R.id.input_squad);

        inputDischargeDay  = fragment.findViewById(R.id.input_discharge_Day);
        inputDischargeDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDatePicker(OPTION_EXPECTED_DISCHARGE_DAY);
            }
        });
        inputTransferDay = fragment.findViewById(R.id.input_transfer_Day);
        inputTransferDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDatePicker(OPTION_TRANSFER_DAY);
            }
        });
        inputBirthday  = fragment.findViewById(R.id.input_birth);
        inputBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDatePicker(OPTION_BIRTH);
            }
        });
        inputEnlistmentDay = fragment.findViewById(R.id.input_enlistment_Day);
        inputEnlistmentDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDatePicker(OPTION_ENLISTMENT_DAY);
            }
        });
        inputSpecialty = fragment.findViewById(R.id.input_specialty);
        inputSquad = fragment.findViewById(R.id.input_squad);
        inputRank = fragment.findViewById(R.id.input_rank);
        return fragment;
    }

    private List<String> getSquadNames(List<Squad> squads) {
        List<String> squadNames = new ArrayList<>();
        for(int i=0;i<squads.size();i++){
            squadNames.add(squads.get(i).Name);
        }
        return squadNames;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(InputprofileViewModel.class);
        mViewModel.updateDataFromDB(new DBHelper(getContext(), mViewModel));

        List<String> squadNames = getSquadNames(mViewModel.getLiveDataSquads().getValue());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, squadNames);
        inputSquad.setAdapter(arrayAdapter);

        Button summit = getView().findViewById(R.id.modify);
        summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Soldier soldier = new Soldier();
                    soldier.name  = inputName.getText().toString();
                    soldier.rank = inputRank.getSelectedItem().toString();
                    soldier.transferDay = dateFormat.parse(inputTransferDay.getText().toString()).getTime();
                    soldier.milliNumber = inputMilNum.getText().toString();
                    soldier.enlistmentDay = dateFormat.parse(inputEnlistmentDay.getText().toString()).getTime();
                    soldier.dischargeDay = dateFormat.parse(inputDischargeDay.getText().toString()).getTime();
                    soldier.birthday = dateFormat.parse(inputBirthday.getText().toString()).getTime();
                    soldier.specialty = inputSpecialty.getText().toString();
                    soldier.Squad = inputSquad.getSelectedItem().toString();
                    soldier.dischargeFlag = false;

                    DBHelper dbHelper = new DBHelper(getContext(), mViewModel);
                    if(!dbHelper.isExistSquad(soldier.Squad)) {
                        dbHelper.createSquad(soldier.Squad);
                    }

                    if(dbHelper.isExistSoldier(soldier.milliNumber)) {
                        dbHelper.updateSoldier(soldier);
                    }
                    else {
                        dbHelper.createSoldier(soldier);
                    }
                    Toast.makeText(getContext(), soldier.name +" 용사가 추가되었습니다.", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(InputprofileViewModel.class);

        if( getArguments() != null ) {
            Soldier soldier = (Soldier) getArguments().get("soldier");
            setForProfileModification(soldier);
        }
    }

    private void setForProfileModification(Soldier soldier) {
        inputName.setText(soldier.name);
        inputMilNum.setText(soldier.getMilliNumber());
        inputEnlistmentDay.setText( dateFormat.format(new Date(soldier.getEnlistmentDay())) );
        inputTransferDay.setText( dateFormat.format(new Date(soldier.getTransferDay())) );
        inputDischargeDay.setText( dateFormat.format(new Date(soldier.getDischargeDay())) );
        inputBirthday.setText( dateFormat.format(new Date(soldier.getBirthday())));
        inputSpecialty.setText(soldier.getSpecialty());

        inputSquad.setSelection(getIndex(inputSquad, soldier.getSquad()));
        inputRank.setSelection(getIndex(inputRank, soldier.getRank()));

    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

}

