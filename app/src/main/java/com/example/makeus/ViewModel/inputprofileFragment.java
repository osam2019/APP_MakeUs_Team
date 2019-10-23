package com.example.makeus.ViewModel;

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

import com.example.makeus.Model.DBHelper;
import com.example.makeus.Model.Soldier;
import com.example.makeus.Model.Squad;
import com.example.makeus.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static androidx.databinding.DataBindingUtil.setContentView;


public class inputprofileFragment extends Fragment { //fragment class 선언

    final int OPTION_ENLISTMENT_DAY = 0;  //입대일 선언
    final int OPTION_TRANSFER_DAY = 1;  //전입일 선언
    final int OPTION_EXPECTED_DISCHARGE_DAY = 2;    //
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

        Spinner input_squadView = getView().findViewById(R.id.input_squad);
        ArrayList<Squad> squads = (ArrayList)mViewModel.getLiveDataSquads().getValue();
        ArrayList<String> squadsName = new ArrayList<>();
        for(int i=0;i<squads.size();i++){
            squadsName.add(squads.get(i).Name);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, squadsName);
        input_squadView.setAdapter(arrayAdapter);

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

                    Spinner spinnerView = view.getRootView().findViewById(R.id.input_rank);
                    String rank = spinnerView.getSelectedItem().toString();

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

                    DBHelper dbHelper = new DBHelper(getContext(), mViewModel);
                    if(!dbHelper.isExistSquad(squad)) {
                        dbHelper.createSquad(squad);
                    }
                    if(dbHelper.isExistSoldier(milli_number)) {
                        dbHelper.updateSoldier(milli_number, name, rank, enlistment_day.getTime(), transfer_day.getTime(), expeceted_discharge_day.getTime(), birth.getTime(), specialty, squad);
                    }
                    else {
                        Soldier soldier = new Soldier();
                        soldier.Name = name;
                        soldier.Squad = squad;
                        soldier.milliNumber =milli_number;
                        soldier.Rank = rank;
                        soldier.Specialty = specialty;
                        soldier.Birthday = birth.getTime();
                        soldier.Discharge_Day = expeceted_discharge_day.getTime();
                        soldier.Transfer_Day = transfer_day.getTime();
                        soldier.Enlistment_Day = enlistment_day.getTime();
                        soldier.Discharge_Flag = false;
                        dbHelper.createSoldier(soldier);
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        if(soldier.Name != null) {
            EditText inputName = getView().findViewById(R.id.input_name);
            inputName.setText(soldier.Name);
        }

        if(soldier.getRank() != null) {
            EditText inputRank = getView().findViewById(R.id.input_rank);
            inputRank.setText(soldier.getRank());
        }

        if(soldier.getMilliNumber() != null) {
            EditText inputMilNum = getView().findViewById(R.id.input_milli_number);
            inputMilNum.setText(soldier.getMilliNumber());
        }

        if(soldier.getEnlistment_Day() != 0) {
            EditText inputEnlistmentDay = getView().findViewById(R.id.input_enlistment_Day);
            inputEnlistmentDay.setText( dateFormat.format(new Date(soldier.getEnlistment_Day())) );
        }

        if(soldier.getTransfer_Day() != 0) {
            EditText inputTransferDay = getView().findViewById(R.id.input_transfer_Day);
            inputTransferDay.setText( dateFormat.format(new Date(soldier.getTransfer_Day())) );
        }

        if(soldier.getDischarge_Day() != 0) {
            EditText inputDischargeDay = getView().findViewById(R.id.input_discharge_Day);
            inputDischargeDay.setText( dateFormat.format(new Date(soldier.getDischarge_Day())) );
        }

        if(soldier.getBirthday() != 0) {
            EditText inputBirthday = getView().findViewById(R.id.input_birth);
            inputBirthday.setText( dateFormat.format(new Date(soldier.getBirthday())));
        }

        if(soldier.getSpecialty() != null) {
            EditText inputSpecialty = getView().findViewById(R.id.input_specialty);
            inputSpecialty.setText(soldier.getSpecialty());
        }

        if(soldier.getSquad() != null) {
            EditText inputSquad = getView().findViewById(R.id.input_squad);
            inputSquad.setText(soldier.getSquad());
        }
    }

}

