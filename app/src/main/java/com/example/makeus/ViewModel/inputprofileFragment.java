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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.makeus.R;

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(InputprofileViewModel.class);

        setDatePickerInput();

        Button summit = getView().findViewById(R.id.modify);
        summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TextView nameView = view.findViewById(R.id.name);
//                if(nameView.getText().toString() != null) {
//                    String name = nameView.getText().toString();
//                }
            }
        });

        // TODO: Use the ViewModel

    }

    // 날짜 Input 부분을 DatePicker로 받기
    private void setDatePickerInput() {
        EditText enlistment_day = getView().findViewById(R.id.input_enlistment_Day);
        enlistment_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDatePicker(OPTION_ENLISTMENT_DAY);
            }
        });

        EditText transfer_day = getView().findViewById(R.id.input_transfer_Day);
        transfer_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDatePicker(OPTION_TRANSFER_DAY);
            }
        });

        EditText expeceted_discharge_day  = getView().findViewById(R.id.input_discharge_Day);
        expeceted_discharge_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDatePicker(OPTION_EXPECTED_DISCHARGE_DAY);
            }
        });

        EditText birth  = getView().findViewById(R.id.input_birth);
        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDatePicker(OPTION_BIRTH);
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
