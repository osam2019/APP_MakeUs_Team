package com.example.makeus.ViewModel;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.makeus.R;

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


        // --
        // GridView gridView = getView().findViewById(R.id.grid);
        // --
    }

    public SoliderViewModel getViewModel() {
        return mViewModel;
    }

}
