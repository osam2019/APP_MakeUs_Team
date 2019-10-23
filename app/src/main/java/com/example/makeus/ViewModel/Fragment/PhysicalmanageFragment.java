package com.example.makeus.ViewModel.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.makeus.R;
import com.example.makeus.ViewModel.Adapter.PhysicalmanageAdapter;
import com.example.makeus.ViewModel.PhysicalTestViewModel;

public class PhysicalmanageFragment extends Fragment {

    private PhysicalTestViewModel mViewModel;
    private PhysicalmanageAdapter mPhysicalmanageAdapter;

    public static PhysicalmanageFragment newInstance() {
        return new PhysicalmanageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LinearLayout PhysicalTestListFragment = (LinearLayout)inflater.inflate(R.id.physicalTestListFragment, null, false);


        return inflater.inflate(R.layout.physical_test_fragment, container, false);
    }
}
