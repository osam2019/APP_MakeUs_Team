package com.example.makeus.ViewModel.Fragment;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.makeus.MainActivity;
import com.example.makeus.R;
import com.example.makeus.ViewModel.PhysicinputViewModel;

public class PhysicalInputFragment extends DialogFragment {
    TextView confirm;

    private PhysicinputViewModel mViewModel;

    public static PhysicalInputFragment newInstance() {
        return new PhysicalInputFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MainActivity avtivity = (MainActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(avtivity);

        AlertDialog physicAlert = builder.create();

        return super.onCreateDialog(savedInstanceState);
    }

    class DialogListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            MainActivity activity = (MainActivity)getActivity();

        }
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {



        return inflater.inflate(R.layout.physical_input_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PhysicinputViewModel.class);
        // TODO: Use the ViewModel
    }

}


