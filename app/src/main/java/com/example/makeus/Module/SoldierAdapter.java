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
    private List<Soldier> mSoldier;
    private String squadName;

    public SoldierAdapter(Context context, List<Soldier> soldier, String squadName) {
        this.mContext = context;
        this.mSoldier = soldier;
        this.squadName = squadName;
    }

    @Override
    public int getCount() {
        return mSoldier.size();
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
            convertView = layoutInflater.inflate(R.layout.squad, null);
        }

        //TextView toolBarTitle = convertView.findViewById(R.id.toolbar);
        //toolBarTitle.setText(squadName);

        TextView soldierRank = convertView.findViewById(R.id.view_squad_rank);
        // soldierRank.setText();
        soldierRank.setOnClickListener(new soldierListener());

        TextView soldierName = convertView.findViewById(R.id.view_squad_name);
        // soldierName.setText();
        soldierRank.setOnClickListener(new soldierListener());

        TextView soldierMilNum = convertView.findViewById(R.id.view_squad_milnum);
        // soliderMilNum.setText();
        soldierMilNum.setOnClickListener(new soldierListener());

        return convertView;
    }

        class soldierListener implements View.OnClickListener{
            @Override   // 클릭시 용사 개개인의 정보 보기로 이동. ViewSolider
            public void onClick(View view){

            }
        }
}