package com.example.makeus.ViewModel.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.makeus.MainActivity;
import com.example.makeus.Model.Soldier;
import com.example.makeus.Module.DBHelper;
import com.example.makeus.R;
import com.example.makeus.ViewModel.AbstractViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddFragment extends DialogFragment {

    private Context mContext;
    private FragmentManager fragmentManager;
    private AbstractViewModel mViewModel;

    public AddFragment(Context context, FragmentManager fragmentManager, AbstractViewModel viewModel) {
        this.mContext = context;
        this.fragmentManager = fragmentManager;
        this.mViewModel = viewModel;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View fragment = inflater.inflate(R.layout.fragment_add, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(fragment);

        final Button squad = fragment.findViewById(R.id.add_squad);
        squad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SquadCreateFragment scf = new SquadCreateFragment(mViewModel);
                scf.show(fragmentManager, "createSquad");
                AddFragment.this.dismiss();
            }
        });

        final Button soldier = fragment.findViewById(R.id.add_soldier);
        soldier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController((getActivity()), R.id.nav_host_fragment).navigate(R.id.nav_inputprofile, null);
                AddFragment.this.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        return dialog;
    }
}