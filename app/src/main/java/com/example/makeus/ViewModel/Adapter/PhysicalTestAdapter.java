package com.example.makeus.ViewModel.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;

import com.example.makeus.Model.PhysicalScore;
import com.example.makeus.Model.Soldier;
import com.example.makeus.R;
import com.example.makeus.ViewModel.AbstractViewModel;
import com.example.makeus.ViewModel.Fragment.PhysicalInputFragment;
import com.example.makeus.ViewModel.Fragment.PhysicalTestFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PhysicalTestAdapter extends BaseAdapter {

    final FragmentTransaction tran;
    private final Context mContext;
    public List<Soldier> soldiers;
    private AbstractViewModel viewModel;
    private FragmentManager fragmentManager;
    private PhysicalTestFragment prev;

    public PhysicalTestAdapter(Context context, List<Soldier> soldiers, AbstractViewModel viewModel, FragmentManager fragmentManager, PhysicalTestFragment ptf) {
        this.mContext = context;
        this.soldiers = soldiers;
        this.viewModel = viewModel;
        this.prev = ptf;
        this.fragmentManager = fragmentManager;
        this.tran = fragmentManager.beginTransaction();
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
            convertView = layoutInflater.inflate(R.layout.item_physical_test, null);
        }

        final Soldier soldier = soldiers.get(position);

        ImageView imageView = convertView.findViewById(R.id.physical_soldier);
        switch (new Random().nextInt(5 - 1) + 1){
            case 1:
                imageView.setImageResource(R.drawable.avatars1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.avatars2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.avatars4);
                break;
            case 4:
                imageView.setImageResource(R.drawable.avatars5);
                break;
            case 5:
                imageView.setImageResource(R.drawable.avatars6);
                break;
        }

        TextView physicalRank = convertView.findViewById(R.id.physical_rank);
        physicalRank.setText(soldier.getRank());

        TextView physicalName = convertView.findViewById(R.id.physical_name);
        physicalName.setText(soldier.name);

        PhysicalScore physicalScore = soldier.getPhysicalScore();

        TextView physicalPushUp = convertView.findViewById(R.id.physical_pushUp);
        physicalPushUp.setText(Integer.toString(physicalScore.getPushUp()));

        TextView physicalSitUp = convertView.findViewById(R.id.physical_sitUp);
        physicalSitUp.setText(Integer.toString(physicalScore.getSitUp()));

        TextView physicalRunning = convertView.findViewById(R.id.physical_running);
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        physicalRunning.setText( dateFormat.format(new Date(physicalScore.getRunning())) );
        
        LinearLayout viewPhysicalScoreLayout = convertView.findViewById(R.id.physical_test_layout);
        viewPhysicalScoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhysicalInputFragment pif = new PhysicalInputFragment(mContext, viewModel, soldier);
                pif.show(fragmentManager, "InputPhysicalData");
            }
        });

        return convertView;
    }

}
