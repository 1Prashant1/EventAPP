package com.eventapp.Dashboard;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.eventapp.Profile.FragmentActivity;
import com.eventapp.Profile.FragmentAlert;
import com.eventapp.Profile.FragmentExplore;
import com.eventapp.Profile.FragmentMyProfile;
import com.eventapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;


public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentMyProfile()).commit();
        }

    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_explore:
                    selectedFragment = new FragmentExplore();
                    break;
                case R.id.nav_activity:
                    selectedFragment = new FragmentActivity();
                    break;
                case R.id.nav_alert:
                    selectedFragment = new FragmentAlert();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new FragmentMyProfile();
                    break;
            }
            Dashboard.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Objects.requireNonNull(selectedFragment)).commit();
            return true;
        }
    };
}


//    @Override
//    public void onBackPressed() {
//        new AlertDialog.Builder(this, R.style.AlertDialogTheme)
//                //.setIcon(R.drawable.greactive)
//                .setTitle("GreActive")
//                .setMessage("Are you sure, Do you want to exit ?")
//                .setNegativeButton("No", null).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Dashboard.this.finish();
//            }
//        }).show();
//    }
//}


//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        if(intent.getExtras()!=null){
//            if(Objects.requireNonNull(intent.getExtras()).getString("whereToNavigate").equalsIgnoreCase("pledge_creation")){
//                startActivity(new Intent(Dashboard.this,ViewAll.class).putExtra("event_type","pledge"));
//            }else if(Objects.requireNonNull(intent.getExtras()).getString("whereToNavigate").equalsIgnoreCase("chat")){
//                startActivity(new Intent(Dashboard.this, Chat.class));
//            }
//        }
//    }
//}
//

