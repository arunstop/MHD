package com.arunseto.mhd.activities;

import android.os.Bundle;

import com.arunseto.mhd.R;
import com.arunseto.mhd.fragments.DiagnoseFragment;
import com.arunseto.mhd.fragments.ExploreFragment;
import com.arunseto.mhd.fragments.HomeFragment;
import com.arunseto.mhd.fragments.SettingsFragment;
import com.arunseto.mhd.models.DummyListNote;
import com.arunseto.mhd.models.Note;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private boolean doubleBackToExitPressedOnce;
    private Session session;
    private int flContentBnv;
    private TextView tvToolbar;
    private BottomNavigationView bnvNav;
    private GlobalTools gt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gt = new GlobalTools(this);
        session = gt.getSession();
        flContentBnv = gt.getContentBnv();

        bnvNav = findViewById(R.id.nav_view);
        tvToolbar = findViewById(R.id.tvToolbar);

//        gt.navigateFragmentBnv(getSupportFragmentManager(),
//                gt.getContentBnv(),
//                new HomeFragment());
        bnvNav.setOnNavigationItemSelectedListener(bnvListener());
        bnvNav.setSelectedItemId(R.id.navigation_home);

        List<Note> ln = new ArrayList<>();

        ln.add(new Note("Feels Good Man 1", "Feels Good super Good Man", "12 May 2020"));
        ln.add(new Note("Feels Good Man 2", "Feels Good super Good Man", "12 May 2020"));
        ln.add(new Note("Feels Good Man 3", "Feels Good super Good Man", "12 May 2020"));
        ln.add(new Note("Feels Good Man 4", "Feels Good super Good Man", "12 May 2020"));
        ln.add(new Note("Feels Good Man 5", "Feels Good super Good Man", "12 May 2020"));
        ln.add(new Note("Feels Good Man 6", "Feels Good super Good Man", "12 May 2020"));
        ln.add(new Note("Feels Good Man 7", "Feels Good super Good Man", "12 May 2020"));
        ln.add(new Note("Feels Good Man 8", "Feels Good super Good Man", "12 May 2020"));
        ln.add(new Note("Feels Good Man 9", "Feels Good super Good Man", "12 May 2020"));

        DummyListNote dln = DummyListNote.getInstance();
        dln.setDln(ln);


        // snackbar color
//        Snackbar snackbar =
//        Snackbar.make(findViewById(android.R.id.content), "Welcome back!", Snackbar.LENGTH_LONG);
//        View snackbarView = snackbar.getView();
//        snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
//        snackbar.show();

        //snackbar color alternative
        String fName = session.getUser().getFname();
        String fNameCap = fName.substring(0, 1).toUpperCase() + fName.substring(1);
        Snackbar.make(findViewById(android.R.id.content), "Welcome back! " + fNameCap, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
                .show();

    }

    public BottomNavigationView.OnNavigationItemSelectedListener bnvListener() {
        return new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        gt.navigateFragmentBnv(getSupportFragmentManager(),
                                gt.getContentBnv(),
                                new HomeFragment());
                        return true;
                    case R.id.navigation_diagnose:
                        gt.navigateFragmentBnv(getSupportFragmentManager(),
                                gt.getContentBnv(),
                                new DiagnoseFragment());
                        return true;
                    case R.id.navigation_explore:
                        gt.navigateFragmentBnv(getSupportFragmentManager(),
                                gt.getContentBnv(),
                                new ExploreFragment());
                        return true;
                    case R.id.navigation_settings:
                        gt.navigateFragmentBnv(getSupportFragmentManager(),
                                gt.getContentBnv(),
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


    @Override
    public void onBackPressed() {
        //checking if fragment has stacks or not
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            //if fragment has stack 0, user will be asked to click back twice to exit
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_LONG).show();
            //snack bar
//        Snackbar.make(findViewById(android.R.id.content), "Please click BACK again to exit", Snackbar.LENGTH_LONG).show();

            //giving user 3 seconds to click back again
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 3000);
        } else {
            super.onBackPressed();
        }
    }
}
