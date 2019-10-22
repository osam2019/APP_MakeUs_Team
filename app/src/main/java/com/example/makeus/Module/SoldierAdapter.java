package com.example.makeus.Module;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.makeus.Model.Squad;
import com.example.makeus.R;
import com.example.makeus.ViewModel.SoliderFragment;

import java.util.List;

public class SoldierAdapter extends BaseAdapter {

    private final Context mContext;
    private List<Squad> mSquads;

    public SoldierAdapter(Context context, List<Squad> squads) {
        this.mContext = context;
        this.mSquads = squads;
    }

    @Override
    public int getCount() {
        return mSquads.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return convertView;
    }
}