package com.example.makeus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    // 프로토타입 테스팅을 위한 프로토타입 아이디 / 패스워드
    final String[] prototype_loginId = {"18-13608", "18-76071154", "18-76061119", "19-71304443"};
    final String[] prototype_loginPwd = {"haneum", "0000", "password", "p@ssword"};

    final int SIGNUPACTIVITY = 11;
    final int SEARCHACTIVITY = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAuthentication();
            }
        });

        Button searchBtn = findViewById(R.id.search_user);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Move to Search User
                callSearchUserActivity();
            }
        });

        Button signUpBtn = findViewById(R.id.signup);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Move to SignUp
                callSignUpActivity();
            }
        });

        CheckBox isAutoLogin = findViewById(R.id.autoLogin_flag);
        if(isAutoLogin.isChecked()) {
            loginAuthentication();
        }
    }

    private void loginAuthentication() {
        EditText loginId = findViewById(R.id.input_loginID);
        String loginIdStr = loginId.getText().toString();

        EditText loginPwd = findViewById(R.id.input_loginPwd);
        String loginPwdStr = loginPwd.getText().toString();

        if( (loginIdStr != null && loginPwdStr != null) && (!loginIdStr.isEmpty() && !loginPwdStr.isEmpty()) ) {
            if(isAuthenticatedUser(loginIdStr, loginPwdStr)) {
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
            } else {
                // Wrong Input between Id or pwd
                Toast.makeText(this, "잘못된 군번 혹은 패스워드입니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Empty Input
            Toast.makeText(this, "군번 혹은 비밀번호 필드가 비어있습니다.", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean isAuthenticatedUser(String loginId, String loginPwd) {
        for(int i=0; i<prototype_loginId.length; i++) {
            if(loginId.equals(prototype_loginId[i]) && loginPwd.equals(prototype_loginPwd[i])) {
                return true;
            }
        }
        return false;
    }

    private void callSignUpActivity() {
        Intent signUpIntent = new Intent(this, SignUpActivity.class);
        startActivityForResult(signUpIntent, SIGNUPACTIVITY);
    }

    private void callSearchUserActivity() {
        Intent searchUserIntent = new Intent(this, SearchUserActivity.class);
        startActivityForResult(searchUserIntent, SIGNUPACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGNUPACTIVITY) {
            if(resultCode == Activity.RESULT_OK) {

            }
        } else if(requestCode == SEARCHACTIVITY) {
            if(resultCode == Activity.RESULT_OK ){

            }
        }
    }

}
