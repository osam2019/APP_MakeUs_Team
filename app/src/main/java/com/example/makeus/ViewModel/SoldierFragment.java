package com.example.makeus.ViewModel;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.makeus.Model.Soldier;
import com.example.makeus.Model.Squad;
import com.example.makeus.R;

import java.util.List;

public class SoldierFragment extends Fragment {

    private SoldierViewModel mViewModel;

    public static SoldierFragment newInstance() {
        return new SoldierFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.soldier_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SoldierViewModel.class);
        // TODO: Use the ViewModel
        //mViewModel.squad = savedInstanceState.getParcelable("squad");

    public SoldierViewModel getViewModel() {
        return mViewModel;
    }
}
