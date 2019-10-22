package com.example.makeus.ViewModel;

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

import com.example.makeus.Model.Squad;
import com.example.makeus.Module.SquadAdapter;
import com.example.makeus.R;

import java.util.List;

public class SquadFragment extends Fragment {

    private SquadViewModel mViewModel;
    private SquadAdapter mSquadAdapter;

    public static SquadFragment newInstance() {
        return new SquadFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_squad, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SquadViewModel.class);

        if(mSquadAdapter == null) {
            mSquadAdapter = new SquadAdapter(this.getContext(), mViewModel.getLiveDataSquads().getValue());
        }

        // TODO: Use the ViewModel
        GridView gridView = getView().findViewById(R.id.grid);
        gridView.setAdapter(mSquadAdapter);

        mViewModel.getLiveDataSquads().observe(this, new Observer<List<Squad>>() {
            @Override
            public void onChanged(@Nullable List<Squad> squads) {
                mSquadAdapter.notifyDataSetChanged();
            }
        });
    }

}
