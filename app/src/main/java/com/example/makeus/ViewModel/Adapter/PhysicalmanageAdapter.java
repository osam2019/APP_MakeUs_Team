package com.example.makeus.ViewModel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makeus.Model.PhysicalScore;
import com.example.makeus.Model.Soldier;
import com.example.makeus.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PhysicalmanageAdapter extends BaseAdapter {

    private final Context mContext;
    private List<Soldier> soldiers;

    public PhysicalmanageAdapter(Context context, List<Soldier> soldiers) {
        this.mContext = context;
        this.soldiers = soldiers;
    }

    @Override
    public int getCount() {
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
        }

        Soldier soldier = soldiers.get(position);

        TextView physicalRank = convertView.findViewById(R.id.in_physic_rank);
        physicalRank.setText(soldier.getRank());

        TextView physicalName = convertView.findViewById(R.id.in_physic_name);
        physicalName.setText(soldier.name);

        PhysicalScore physicalScore = soldier.getPhysicalScore();

        TextView physicalPushUp = convertView.findViewById(R.id.in_physic_pushUp);
        physicalPushUp.setText(Integer.toString(physicalScore.getPushUp()));

        TextView physicalSitUp = convertView.findViewById(R.id.in_physic_sitUp);
        physicalSitUp.setText(Integer.toString(physicalScore.getSitUp()));

        TextView physicalRunning = convertView.findViewById(R.id.in_physic_running);
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        physicalRunning.setText( dateFormat.format(new Date(physicalScore.getRunning())) );

        return convertView;
    }

}
