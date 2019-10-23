package com.example.makeus.ViewModel.Adapter;

import android.content.Context;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.Navigation;

import com.example.makeus.Model.Squad;
import com.example.makeus.Module.SquadButton;
import com.example.makeus.R;


import java.util.List;

public class SquadAdapter extends BaseAdapter {

    private final Context mContext;
    public List<Squad> mSquads;

    public SquadAdapter(Context context, List<Squad> squads) {
        this.mContext = context;
        this.mSquads = squads;
    }

    @Override
    public int getCount() {
        return mSquads.size() + 1;
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

        if(position == 0) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.add_squad, null);
            final SquadButton squadButton = view.findViewById(R.id.squad_button);
            squadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText((AppCompatActivity) mContext, "새로운 분대 추가 버튼", Toast.LENGTH_LONG).show();
                }
            });
            return view;
        } else {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.squad, null);
        }

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.squad, null);
        }

        final SquadButton squadButton = convertView.findViewById(R.id.squad_button);
        squadButton.squad = mSquads.get( position - 1 );
        squadButton.setText(String.valueOf(mSquads.get( position - 1 ).Name));
        squadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SquadButton button = v.findViewById(R.id.squad_button);

                Bundle bundle = new Bundle();
                bundle.putParcelable("squad", button.squad);
                Navigation.findNavController(v).navigate(R.id.nav_soilder, bundle);

                //Toast.makeText((AppCompatActivity) mContext, button.squad.name + " 이벤트발생", Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }

}
