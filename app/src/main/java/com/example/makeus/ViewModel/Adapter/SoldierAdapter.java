package com.example.makeus.ViewModel.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.navigation.Navigation;

import com.example.makeus.Model.Soldier;
import com.example.makeus.R;

import java.util.List;

public class SoldierAdapter extends BaseAdapter {

    private final Context mContext;
    //private squad mSquad;
    public List<Soldier> soldiers;

    public SoldierAdapter(Context context, List<Soldier> soldiers) {
        this.mContext = context;
        this.soldiers = soldiers;
    }

    @Override
    public int getCount() {
        // 분대의 용사 리스트 사이즈 반환
        return soldiers.size();
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

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.row_view_soldier, null);
        }

        final Soldier soldier = soldiers.get(position);


        Button button = convertView.findViewById(R.id.soldier_button);
        button.setText(soldier.rank +"\n\n" + soldier.name);
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("soldier", soldier);

                Navigation.findNavController(v).navigate(R.id.nav_inputprofile, bundle);
            }
        });

        return convertView;
    }
}