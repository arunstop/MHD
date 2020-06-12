package com.arunseto.mhd.activities;

import android.os.Bundle;

import com.arunseto.mhd.R;
import com.arunseto.mhd.fragments.DiagnoseFragment;
import com.arunseto.mhd.fragments.ExploreFragment;
import com.arunseto.mhd.fragments.HelpFragment;
import com.arunseto.mhd.fragments.HomeFragment;
import com.arunseto.mhd.fragments.SettingsFragment;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private boolean doubleBackToExitPressedOnce;
    private Session session;
    private int flContent;
    private TextView tvToolbar;
    private BottomNavigationView bnvNav;
    private GlobalTools gt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flContent = R.id.flContent;
        gt = new GlobalTools(this);
        session = gt.getSession();

        bnvNav = findViewById(R.id.nav_view);
        tvToolbar = findViewById(R.id.tvToolbar);

        bnvNav.setOnNavigationItemSelectedListener(bnvListener());
        bnvNav.setSelectedItemId(R.id.navigation_home);

        // snackbar color
//        Snackbar snackbar =
//        Snackbar.make(findViewById(android.R.id.content), "Welcome back!", Snackbar.LENGTH_LONG);
//        View snackbarView = snackbar.getView();
//        snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
//        snackbar.show();

        //snackbar color alternative
        Snackbar.make(findViewById(android.R.id.content), "Welcome back! " + session.getUser().getFname(), Snackbar.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
                .show();

    }

    public BottomNavigationView.OnNavigationItemSelectedListener bnvListener(){
        return new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        gt.navigateFragment(getSupportFragmentManager(),
                                gt.getContent(),
                                new HomeFragment());
                        return true;
                    case R.id.navigation_diagnose:
                        gt.navigateFragment(getSupportFragmentManager(),
                                gt.getContent(),
                                new DiagnoseFragment());
                        return true;
                    case R.id.navigation_explore:
                        gt.navigateFragment(getSupportFragmentManager(),
                                gt.getContent(),
                                new ExploreFragment());
                        return true;
                    case R.id.navigation_settings:
                        gt.navigateFragment(getSupportFragmentManager(),
                                gt.getContent(),
                                new SettingsFragment());
                        return true;
                }
                return false;
            }
        };
    }

    // back for level 2 fragment
    public void navBack(View view) {
        super.onBackPressed();
    }

    //
//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//        //snack bar
//        Snackbar.make(findViewById(android.R.id.content), "Please click BACK again to exit", Snackbar.LENGTH_LONG).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce = false;
//            }
//        }, 2000);
//    }

}
