package com.arunseto.mhd.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.arunseto.mhd.R;
import com.arunseto.mhd.fragments.DiagnoseExecuteFragment;
import com.arunseto.mhd.fragments.DiagnoseFragment;
import com.arunseto.mhd.fragments.ExploreFragment;
import com.arunseto.mhd.fragments.HomeFragment;
import com.arunseto.mhd.fragments.SettingsFragment;
import com.arunseto.mhd.models.DummyListNote;
import com.arunseto.mhd.models.Note;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private boolean doubleBackToExitPressedOnce;
    private Session session;
    private User user;
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
        user = gt.getUser();
        flContentBnv = gt.getContentBnv();

        bnvNav = findViewById(R.id.nav_view);
        tvToolbar = findViewById(R.id.tvToolbar);

//        gt.navigateFragmentBnv(getSupportFragmentManager(),
//                gt.getContentBnv(),
//                new HomeFragment());
        bnvNav.setOnNavigationItemSelectedListener(bnvListener());
        bnvNav.setSelectedItemId(R.id.navigation_home);

        List<Note> ln = new ArrayList<>();


        for (int i = 1; i <= 10; i++) {
            ln.add(new Note(i, "Feels Good Man " + i, "Feels Good super Good Man", "12 May 2020"));
        }

        DummyListNote dln = DummyListNote.getInstance();
        dln.setDln(ln);


        // snackbar color
//        Snackbar snackbar =
//        Snackbar.make(findViewById(android.R.id.content), "Welcome back!", Snackbar.LENGTH_LONG);
//        View snackbarView = snackbar.getView();
//        snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
//        snackbar.show();

        //snackbar color alternative
        String fName = user.getFirst_name();
        Snackbar.make(findViewById(android.R.id.content), "Welcome ! " + gt.capEachWord(fName), Snackbar.LENGTH_SHORT)
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
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        //checking if fragment has stacks or not
        final Fragment fragmentInFrame = getSupportFragmentManager().findFragmentById(R.id.flContent);

        if (getSupportFragmentManager().getBackStackEntryCount() == 0 || fragmentInFrame instanceof DiagnoseExecuteFragment) {
            //if fragment has stack 0, user will be asked to click back twice to exit
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            if (fragmentInFrame instanceof DiagnoseExecuteFragment) {
//                Snackbar snackbar = Snackbar
//                        .make(
//                                findViewById(android.R.id.content),
//                                "Apakah anda ingin mengakhiri proses diagnosa ?\n",
//                                Snackbar.LENGTH_LONG
//                        );
//                snackbar.setAction("Kembali", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        MainActivity.super.onBackPressed();
//                    }
//                });
//
//                snackbar.show();
                Toast.makeText(this, "Tekan sekali lagi untuk kembali", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this, "Tekan sekali lagi untuk keluar aplikasi", Toast.LENGTH_SHORT).show();

            }
            //snack bar
//        Snackbar.make(findViewById(android.R.id.content), "Please click BACK again to exit", Snackbar.LENGTH_LONG).show();

            //giving user 3 seconds to click back again
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 3000);
        }  else {
            super.onBackPressed();
        }
    }
}
