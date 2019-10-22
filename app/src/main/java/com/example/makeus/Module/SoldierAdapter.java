package com.example.makeus.Module;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.makeus.Model.Soldier;
import com.example.makeus.Model.Squad;
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
            convertView = layoutInflater.inflate(R.layout.row_view_squad, null);
        }

        Soldier soldier = soldiers.get(position);

        TextView soldierRank = convertView.findViewById(R.id.view_squad_rank);
        soldierRank.setText(soldier.getRank());
        soldierRank.setOnClickListener(new soldierListener());

        TextView soldierName = convertView.findViewById(R.id.view_squad_name);
        soldierName.setText(soldier.Name);
        //soldierRank.setOnClickListener(new soldierListener());

        TextView soldierMilNum = convertView.findViewById(R.id.view_squad_milnum);
        soldierMilNum.setText(soldier.getMilli_Number());
        soldierMilNum.setOnClickListener(new soldierListener());

        return convertView;
    }

        class soldierListener implements View.OnClickListener{
            @Override   // 클릭시 용사 개개인의 정보 보기로 이동. ViewSolider
            public void onClick(View v){

            }
        }
}