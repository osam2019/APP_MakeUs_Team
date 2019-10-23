package com.example.makeus.ViewModel.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.makeus.MainActivity;
import com.example.makeus.Module.DBHelper;
import com.example.makeus.ViewModel.AbstractViewModel;

public class SquadCreateFragment extends DialogFragment {
    AbstractViewModel mViewModel;
    EditText editText;

    public SquadCreateFragment(AbstractViewModel viewModel) {
        this.mViewModel = viewModel;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final MainActivity activity = (MainActivity)getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        editText = new EditText(activity);

        builder.setTitle("신규 분대 편성");
        builder.setView(editText);
        builder.setPositiveButton("생성", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String squadName = editText.getText().toString();
                if (squadName == null || squadName.isEmpty()) {
                    Toast.makeText(activity, "분대명을 입력해주세요.", Toast.LENGTH_LONG);
                    return;
                }
                DBHelper dbHelper = new DBHelper(getContext());
                if(!dbHelper.isExistSquad(squadName)) {
                    dbHelper.createSquad(squadName);
                    mViewModel.updateDataFromDB(dbHelper);
                }
            }
        });

        builder.setNegativeButton("취소", null);
        AlertDialog alert = builder.create();

        return alert;
    }
}
