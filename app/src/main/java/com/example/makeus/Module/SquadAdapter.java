package com.example.makeus.Module;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.makeus.Model.Squad;
import com.example.makeus.R;
import com.example.makeus.ViewModel.SoldierFragment;
import com.example.makeus.ViewModel.SoldierViewModel;

import java.util.List;

public class SquadAdapter extends BaseAdapter {

    private final Context mContext;
    private List<Squad> mSquads;

    public SquadAdapter(Context context, List<Squad> squads) {
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

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.squad, null);
        }

        final SquadButton squadButton = convertView.findViewById(R.id.squad_button);
        squadButton.squad = mSquads.get(position);
        squadButton.setText(String.valueOf(mSquads.get(position).Name));
        squadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SquadButton button = v.findViewById(R.id.squad_button);
                SoldierFragment fragment = new SoldierFragment();
                SoldierViewModel viewModel = ViewModelProviders.of(fragment).get(SoldierViewModel.class);

                FragmentManager manager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.commit();
            }
        });

        return convertView;
    }

}
