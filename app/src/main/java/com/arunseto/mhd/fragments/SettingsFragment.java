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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arunseto.mhd.activities.LoginActivity;
import com.arunseto.mhd.R;
import com.arunseto.mhd.api.GoogleAPI;
import com.arunseto.mhd.storage.Session;
import com.arunseto.mhd.ui.OptionDialog;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;


public class SettingsFragment extends Fragment {

    private GoogleSignInClient googleSignInClient;
    private View view;
    private Context context;
    private Session session;
    private GoogleAPI googleAPI;
    private TextView tvName, tvEmail;
    private ImageView ivProfilePhoto;
    private OptionDialog optionDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_settings, container, false);
        context = getActivity();
        session = Session.getInstance(context);
        googleAPI = GoogleAPI.getInstance(context);

        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        ivProfilePhoto = view.findViewById(R.id.ivProfilePhoto);

        tvName.setText(session.getUser().getFname().toUpperCase() + " " + session.getUser().getLname().toUpperCase());
        tvEmail.setText(session.getUser().getEmail());
        if (!session.getUser().getPhotoUrl().isEmpty()) {
            Picasso.with(context).load(session.getUser().getPhotoUrl()).centerInside().fit().into(ivProfilePhoto);
        }

        Button btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commitLogout();
            }
        });

        return view;
    }

    private void execLogout() {
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

        optionDialog = new OptionDialog(context, "Apakah Anda yakin ingin keluar akun ?", "KELUAR", "BATAL");
        optionDialog.show();
        optionDialog.getBtnYes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionDialog.dismiss();
                execLogout();
            }
        });

        optionDialog.getBtnNo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionDialog.dismiss();
            }
        });

    }
}
