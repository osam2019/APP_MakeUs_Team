package com.example.makeus.ViewModel.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.makeus.Model.Soldier;
import com.example.makeus.Module.DBHelper;
import com.example.makeus.ViewModel.Adapter.PhysicalTestAdapter;
import com.example.makeus.R;
import com.example.makeus.ViewModel.PhysicalTestViewModel;

import java.util.List;

public class PhysicalTestFragment extends Fragment {

    private PhysicalTestViewModel mViewModel;
    public PhysicalTestAdapter mPhysicalTestAdapter;

    public static PhysicalTestFragment newInstance() {
        return new PhysicalTestFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.physical_test_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        ListView listView = getView().findViewById(R.id.physicalTestListView);
        listView.setAdapter(mPhysicalTestAdapter);

        mViewModel.getLiveDataSoldiers().observe(this, new Observer<List<Soldier>>(){
            @Override
            public void onChanged(@Nullable List<Soldier> soldiers) {
                mPhysicalTestAdapter.soldiers = soldiers;
                mPhysicalTestAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PhysicalTestViewModel.class);
        mViewModel.updateDataFromDB(new DBHelper(getContext()));
        // TODO: Use the ViewModel
        if(mPhysicalTestAdapter == null) {
            mPhysicalTestAdapter = new PhysicalTestAdapter(this.getContext(), mViewModel.getLiveDataSoldiers().getValue(), mViewModel, getFragmentManager(), PhysicalTestFragment.this);
        }
    }

}
