package com.example.makeus;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.makeus.Model.Soldier;
import com.example.makeus.Module.DBHelper;
import com.example.makeus.Module.DataExporter;
import com.example.makeus.Module.DateCalculator;
import com.example.makeus.Module.Notifier;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_squad, R.id.nav_inputprofile, R.id.nav_physical)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        checkPermission();
        releaseNotificationMessage();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_action, menu) ;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // 클릭시 엑셀 내보내기 기능 발생
        if(item.getItemId() == R.id.extraction_to_xls) {
            if(DataExporter.Export(new DBHelper(this))){
                Toast.makeText(this, "엑셀 파일이 외부저장소의 makeus 폴더로 출력되었습니다.", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, "출력에 문제가 발생했습니다. 외부저장소에 문제가 있나요?", Toast.LENGTH_LONG).show();
            }
        }


        return super.onOptionsItemSelected(item);
    }

    // 체크할 권한 목록
    String [] permission_list = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    // 권한 체크 메서드
    public void checkPermission(){
        // 현재 안드로이드 버전이 6.0 미만이면 메서드를 종료한다.
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return;
        }
        // 각 권한의 허용 여부를 확인한다.
        for(String permission : permission_list){
            // 권한 허용 여부를 확인한다.
            int chk = checkCallingOrSelfPermission(permission);
            // 거부 상태라고 한다면..
            if(chk == PackageManager.PERMISSION_DENIED){
                // 사용자에게 권한 허용여부를 확인하는 창을 띄운다.
                requestPermissions(permission_list, 0);
            }
        }
    }

    public void releaseNotificationMessage() {
        DBHelper dbHelper = new DBHelper(this);
        List<Soldier> soldiers = dbHelper.getAllSoldiers();

        DateCalculator dateUtil = new DateCalculator();

        for(int i=0; i<soldiers.size(); i++) {
            long personalityDueDate = dateUtil.getPersonalityTestDueDate(soldiers.get(i).transferDay, soldiers.get(i).rank);
            //String dueDateStr = dateFormat.format(dueDateVal);
            if(personalityDueDate != 0) {
                new Notifier(this).Notify(soldiers.get(i), "인성검사 만료일 ", new Date(personalityDueDate));
            }

            long screeningDueDate = dateUtil.getHealthScreeningDueDate(soldiers.get(i));
            if(screeningDueDate != 0) {
                new Notifier(this).Notify(soldiers.get(i), "신체검사 만료일 ", new Date(screeningDueDate));
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int i = 0; i < grantResults.length; i++){
            if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                this.finish();
                System.exit(0);
            }
        }
    }
}