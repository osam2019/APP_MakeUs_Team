package com.example.makeus.ViewModel;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.makeus.Model.Soldier;
import com.example.makeus.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.jar.Attributes;

public class inputprofileFragment extends Fragment {

    private InputprofileViewModel mViewModel;

    public static inputprofileFragment newInstance() {
        return new inputprofileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v1 = inflater.inflate(R.layout.inputprofile_fragment, container, false);
        Button summit = v1.findViewById(R.id.modify);

        summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText nameView = view.getRootView().findViewById(R.id.input_name);
                String name = nameView.getText().toString();

                EditText rankView = view.getRootView().findViewById(R.id.input_rank);
                String rank = rankView.getText().toString();

                EditText milli_numberView = view.getRootView().findViewById(R.id.input_milli_number);
                String milli_number = milli_numberView.getText().toString();

                EditText enlistment_DayView = view.getRootView().findViewById(R.id.input_enlistment_Day);
                //long enlistment_Day = enlistment_DayView.getText();


                EditText transfer_DayView = view.getRootView().findViewById(R.id.input_transfer_Day);
                //transfer_Day = transfer_DayView.getText();

                EditText discharge_DayView = view.getRootView().findViewById(R.id.input_discharge_Day);
                //discharge_Day = discharge_DayView.getText();

                EditText birthView = view.getRootView().findViewById(R.id.input_specialty);
                // birth = birthView.getText();

                EditText specialtyView = view.getRootView().findViewById(R.id.input_specialty);
                String specialty = specialtyView.getText().toString();

                EditText squadView = view.getRootView().findViewById(R.id.input_squad);
                String squad = squadView.getText().toString();
            }
        });


        return v1;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(InputprofileViewModel.class);


        // TODO: Use the ViewModel
    }

}
