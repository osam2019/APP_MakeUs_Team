package com.example.makeus.Module;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.navigation.Navigation;

import com.example.makeus.Model.Soldier;
import com.example.makeus.R;

import java.util.List;

public class SoldierAdapter extends BaseAdapter {

    private final Context mContext;
    //private Squad mSquad;
    private List<Soldier> soldiers;
    private String squadName;

    public SoldierAdapter(Context context, List<Soldier> soldiers, String squadName) {
        this.mContext = context;
        this.soldiers = soldiers;
        this.squadName = squadName;
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

        TextView soldierRank = convertView.findViewById(R.id.view_soilder_rank);
        soldierRank.setText(soldier.getRank());

        TextView soldierName = convertView.findViewById(R.id.view_soldier_name);
        soldierName.setText(soldier.Name);

        TextView soldierMilNum = convertView.findViewById(R.id.view_soldier_milnum);
        soldierMilNum.setText(soldier.getMilliNumber());

        LinearLayout viewSoldierLayout = convertView.findViewById(R.id.row_view_soldier_layout);
        viewSoldierLayout.setOnClickListener( new View.OnClickListener() {
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