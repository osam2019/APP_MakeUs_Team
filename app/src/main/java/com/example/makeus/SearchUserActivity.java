package com.example.makeus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SearchUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        Button searchAuth = findViewById(R.id.search_Auth);
        searchAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callTaostmessage("인증번호는 0000 입니다");
            }
        });

        Button submitBtn = findViewById(R.id.search_submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void callTaostmessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
