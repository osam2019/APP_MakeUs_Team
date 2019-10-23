package com.example.makeus.ViewModel.Fragment;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
        inputName.setOnFocusChangeListener(new MyFocusChangeListener());
        inputTransferDay = fragment.findViewById(R.id.input_transfer_Day);
        inputMilNum = fragment.findViewById(R.id.input_milli_number);
        inputMilNum.setOnFocusChangeListener(new MyFocusChangeListener());
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
        inputSpecialty.setOnFocusChangeListener(new MyFocusChangeListener());
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
        mViewModel.updateDataFromDB(new DBHelper(getContext()));

        List<String> squadNames = getSquadNames(mViewModel.getLiveDataSquads().getValue());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, squadNames);
        inputSquad.setAdapter(arrayAdapter);

        Button summit = getView().findViewById(R.id.modify);
        summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Soldier soldier = new Soldier();

                    soldier.name  = inputName.getText().toString();
                    soldier.rank = inputRank.getSelectedItem().toString();
                    soldier.milliNumber = inputMilNum.getText().toString();
                    if(soldier.milliNumber == null || soldier.milliNumber.isEmpty()) {
                        Toast.makeText(getContext(), "군번은 반드시 입력해주셔야 합니다.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    soldier.specialty = inputSpecialty.getText().toString();
                    soldier.Squad = inputSquad.getSelectedItem().toString();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String s = inputTransferDay.getText().toString();
                    if(s != null && !s.isEmpty()) {
                        soldier.transferDay = dateFormat.parse(s).getTime();
                    }
                    s = inputEnlistmentDay.getText().toString();
                    if(s != null && !s.isEmpty()) {
                        soldier.enlistmentDay = dateFormat.parse(s).getTime();
                    }
                    s = inputDischargeDay.getText().toString();
                    if(s != null && !s.isEmpty()) {
                        soldier.dischargeDay = dateFormat.parse(s).getTime();
                    }
                    s  = inputBirthday.getText().toString();
                    if(s != null && !s.isEmpty()) {
                        soldier.birthday = dateFormat.parse(s).getTime();
                    }

                    DBHelper dbHelper = new DBHelper(getContext());
                    if(!dbHelper.isExistSquad(soldier.Squad)) {
                        dbHelper.createSquad(soldier.Squad);
                    }

                    if(dbHelper.isExistSoldier(soldier.milliNumber)) {
                        dbHelper.updateSoldier(soldier);
                    }
                    else {
                        dbHelper.createSoldier(soldier);
                    }
                    Toast.makeText(getContext(), soldier.name +"완료", Toast.LENGTH_SHORT).show();
                }
                catch(ParseException e) {
                    Log.d("makeus", e.getStackTrace().toString());
                }

                getFragmentManager().popBackStack();
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
                inputEnlistmentDay.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                break;
            case OPTION_TRANSFER_DAY:
                inputTransferDay.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                break;
            case OPTION_EXPECTED_DISCHARGE_DAY:
                inputDischargeDay.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                break;
            case OPTION_BIRTH:
                inputBirthday.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
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
            inputMilNum.setEnabled(false);
            inputMilNum.setTextColor(Color.BLACK);
        }
    }

    private void setForProfileModification(Soldier soldier) {
        inputName.setText(soldier.name);
        inputMilNum.setText(soldier.getMilliNumber());
        inputEnlistmentDay.setText( dateFormat.format(new Date(soldier.enlistmentDay)) );
        inputTransferDay.setText( dateFormat.format(new Date(soldier.transferDay)) );
        inputDischargeDay.setText( dateFormat.format(new Date(soldier.dischargeDay)) );
        inputBirthday.setText( dateFormat.format(new Date(soldier.birthday)));
        inputSpecialty.setText(soldier.specialty);

        inputSquad.setSelection(getIndex(inputSquad, soldier.Squad));
        inputRank.setSelection(getIndex(inputRank, soldier.rank));

    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    private class MyFocusChangeListener implements View.OnFocusChangeListener {
        public void onFocusChange(View v, boolean hasFocus){
            if(!hasFocus) {
                InputMethodManager imm =  (InputMethodManager)v.getRootView().getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
    }

}

