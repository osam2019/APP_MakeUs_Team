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
import android.widget.TextView;

import com.example.makeus.Model.Soldier;
import com.example.makeus.R;

public class inputprofileFragment extends Fragment {

    private InputprofileViewModel mViewModel;

    public static inputprofileFragment newInstance() {
        return new inputprofileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Button summit = getView().findViewById(R.id.modify);
        summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Soldier soldier = new Soldier();

                TextView nameView = view.findViewById(R.id.name);
                String name = nameView.getText().toString();


                Bundle bundle = new Bundle();
                bundle.putParcelable("soldier", soldier);
                Navigation.findNavController(view).navigate(R.id.nav_soilder, bundle);
            }
        });

        return inflater.inflate(R.layout.inputprofile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(InputprofileViewModel.class);


        // TODO: Use the ViewModel
    }

}
