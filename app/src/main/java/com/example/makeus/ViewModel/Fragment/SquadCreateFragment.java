package com.example.makeus.ViewModel.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.makeus.MainActivity;
import com.example.makeus.Module.DBHelper;
import com.example.makeus.ViewModel.SquadViewModel;

public class SquadCreateFragment extends DialogFragment {
    private SquadViewModel mViewModel;

    public SquadCreateFragment(SquadViewModel viewmodel) {
        this.mViewModel = viewmodel;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity)getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        DBHelper dbHelper = new DBHelper(getContext(), mViewModel);
        final EditText editText = new EditText(getActivity());
        final String[] squadName = new String[1];
        final boolean[] saveFlag = new boolean[1];

        builder.setTitle("신규 분대 편성");
        builder.setView(editText);
        builder.setPositiveButton("생성", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE :
                        if(editText.getText().toString() != null) {
                            System.out.println("DB 저장 성공 :" + editText.getText().toString());

                            squadName[0] = editText.getText().toString();
                            saveFlag[0] = true;
                        }else {
                            System.out.println("DB 저장 실패 :" + editText.getText().toString());
                            saveFlag[0] = false;
                        }
                }
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 취소버튼 클릭시 특정 이벤트 필요하면 생성
            }
        });

        AlertDialog alert = builder.create();


        if(dbHelper.isExistSquad(squadName[0]) == false) {
            System.out.println(squadName[0]);
            dbHelper.createSquad(squadName[0]);
        }

        return alert;
    }
}
