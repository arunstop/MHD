package com.arunseto.mhd.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arunseto.mhd.activities.LoginActivity;
import com.arunseto.mhd.R;
import com.arunseto.mhd.api.GoogleAPI;
import com.arunseto.mhd.storage.Session;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class SettingsFragment extends Fragment {

    private GoogleSignInClient googleSignInClient;
    private View view;
    private Context context;
    private Session session;
    private GoogleAPI googleAPI;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_settings, container, false);
        context = getActivity();
        session = Session.getInstance(context);
        googleAPI = GoogleAPI.getInstance(context);

        Button btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commitLogout();
            }
        });

        return view;
    }

    private void logoutGoogle() {
        /*
          Sign-out is initiated by simply calling the googleSignInClient.signOut API. We add a
          listener which will be invoked once the sign out is the successful
           */
        googleAPI.gsi().signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //On Succesfull signout we navigate the user back to LoginActivity
                session.clear();
                Toast.makeText(context, "Log out success", Toast.LENGTH_SHORT).show();
                Log.w("Google Logout", "NICE");
                startActivity(new Intent(context, LoginActivity.class));
                getActivity().finish();

            }
        });

    }

    public void commitLogout() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View layoutLogout = getLayoutInflater().inflate(R.layout.template_dialog_confirmation, null);
        // yes == log out
        // no == cancel
        Button btnYes = layoutLogout.findViewById(R.id.btnYes);
        Button btnNo = layoutLogout.findViewById(R.id.btnNo);
        TextView tvTitle = layoutLogout.findViewById(R.id.tvTitle);
        builder.setView(layoutLogout);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();// to resize alert dialogMoreMenu, this command line should be below the alert.show()
        dialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);// here i have fragment height 30% of window's height you can set it as per your requirement


        tvTitle.setText("Apakah Anda yakin ingin keluar akun ?");
        btnYes.setText("KELUAR");
        btnNo.setText("BATAL");
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                logoutGoogle();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
}
