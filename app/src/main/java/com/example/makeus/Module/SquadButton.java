package com.example.makeus.Module;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;

import com.example.makeus.Model.DBHelper;
import com.example.makeus.Model.Squad;
import com.example.makeus.R;

public class SquadButton extends AppCompatButton {

    public Squad squad;

    public SquadButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        // TODO Auto-generated constructor stub
    }
}
