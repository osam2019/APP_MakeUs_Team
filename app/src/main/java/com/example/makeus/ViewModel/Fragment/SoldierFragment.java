package com.example.makeus.ViewModel.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.makeus.Module.DBHelper;
import com.example.makeus.Model.Soldier;
import com.example.makeus.Model.Squad;
import com.example.makeus.ViewModel.Adapter.SoldierAdapter;
import com.example.makeus.R;
import com.example.makeus.ViewModel.SoldierViewModel;

import java.util.List;

public class SoldierFragment extends Fragment {

    private SoldierViewModel mViewModel;
    private SoldierAdapter mSoldierAdapter;

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
        mViewModel.updateDataFromDB(new DBHelper(getContext()));

        if(mSoldierAdapter == null) {
            mSoldierAdapter = new SoldierAdapter(this.getContext(), mViewModel.getLiveDataSoldiers().getValue());
        }

        GridView gridView = getView().findViewById(R.id.grid);
        gridView.setAdapter(mSoldierAdapter);

        mViewModel.getLiveDataSoldiers().observe(this, new Observer<List<Soldier>>(){
            @Override
            public void onChanged(@Nullable List<Soldier> soldier) {
                mSoldierAdapter.soldiers = soldier;
                mSoldierAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SoldierViewModel.class);

        Squad squad = (Squad)getArguments().get("squad");
        mViewModel.setSquad(new DBHelper(getContext()), squad);
        Toast.makeText(getContext(), squad.Name, Toast.LENGTH_SHORT).show();
    }
}
