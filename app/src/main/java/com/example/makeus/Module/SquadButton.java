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

    class SquadBtnListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            Button squadBtn = (Button)findViewById(v.getId());
            String squadName = (String)squadBtn.getText();

            DBHelper helper = new DBHelper(null, null, null, 0, squadName);
            SQLiteDatabase db = helper.getReadableDatabase();
            String sql = "select * from " + squadName + "squadTable";
            Cursor c = db.rawQuery(sql, null);
            while(c.moveToNext()) {
                // Column의 Index 추출
                int name_pos = c.getColumnIndex("name");
                int squad_pos = c.getColumnIndex("squad");
                int rank_pos = c.getColumnIndex("rank");
                int milli_numberr_pos = c.getColumnIndex("milli_number");
                int birthday_pos = c.getColumnIndex("birthday");
                int enlistment_day_pos = c.getColumnIndex("enlistment_day");
                int transfer_day_pos = c.getColumnIndex("transfer_day");
                int discharge_day_pos = c.getColumnIndex("discharge_day");
                int discharge_flag_pos = c.getColumnIndex("discharge_flag");
                int specialty_pos = c.getColumnIndex("specialty");

                // Index로 Data 가져오기
                String Name = c.getString(name_pos);
                String Squad = c.getString(squad_pos);
                String Rank = c.getString(rank_pos);
                String Milli_Number = c.getString(milli_numberr_pos);
            }
        }
    }
}
