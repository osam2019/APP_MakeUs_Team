package com.example.makeus.ViewModel.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import com.example.makeus.Model.Squad;
import com.example.makeus.Module.SquadButton;
import com.example.makeus.R;
import com.example.makeus.ViewModel.AbstractViewModel;
import com.example.makeus.ViewModel.Fragment.SquadCreateFragment;

import java.util.List;

public class SquadAdapter extends BaseAdapter {

    private AbstractViewModel mViewModel;
    private static final int DIALOG_REQUEST_CODE = 2150;
    private final Context mContext;
    private FragmentManager fragmentManager;
    public List<Squad> mSquads;

    public SquadAdapter(Context context, List<Squad> squads, AbstractViewModel viewModel, FragmentManager fragmentManager) {
        this.mContext = context;
        this.mSquads = squads;
        this.mViewModel = viewModel;
        this.fragmentManager = fragmentManager;
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
                    SquadCreateFragment scf = new SquadCreateFragment(mViewModel);
                    scf.show(fragmentManager, "createSquad");
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
