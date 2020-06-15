package com.arunseto.mhd.fragments;

import android.content.Context;
import android.content.Intent;
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
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.arunseto.mhd.ui.ConfirmationDialog;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class SettingsFragment extends Fragment {

    private GoogleSignInClient googleSignInClient;
    private View view;
    private LayoutInflater inflater;
    private Context context;
    private Session session;
    private GoogleAPI googleAPI;
    private TextView tvName, tvEmail;
    private ImageView ivProfilePhoto;
    private ConfirmationDialog confirmationDialog;
    private GlobalTools gt;
    private Button btnAbout, btnHelp, btnLogout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_settings, container, false);
        //getting inflater from the parameter is important to preventing a crash caused by switching between fragment too fast
        this.inflater = inflater;


        gt = new GlobalTools(getActivity());
        context = gt.getContext();
        session = gt.getSession();
        googleAPI = gt.getGoogleAPI();

        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        ivProfilePhoto = view.findViewById(R.id.ivProfilePhoto);

        String fName = session.getUser().getFname();
        String fNameCap = fName.substring(0, 1) + fName.substring(1);

        tvName.setText(session.getUser().getFname().toUpperCase() + " " + session.getUser().getLname().toUpperCase());
        tvEmail.setText(session.getUser().getEmail());
        if (!session.getUser().getPhotoUrl().isEmpty()) {
//            Glide.with(context).load(session.getUser().getPhotoUrl()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(ivProfilePhoto);
            gt.loadImgUrl(session.getUser().getPhotoUrl(), ivProfilePhoto);
        }

        btnAbout = view.findViewById(R.id.btnAbout);
        btnHelp = view.findViewById(R.id.btnHelp);
        btnLogout = view.findViewById(R.id.btnLogout);

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gt.navigateFragment(getFragmentManager(),
                        gt.getContent(),
                        new AboutFragment());
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gt.navigateFragment(getFragmentManager(),
                        gt.getContent(),
                        new HelpFragment());
            }
        });

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

        confirmationDialog = new ConfirmationDialog(context);
        confirmationDialog.setTitle("Apakah Anda yakin ingin keluar akun ?");
        confirmationDialog.setYLabel("KELUAR");
        confirmationDialog.setNLabel("BATAL");
        confirmationDialog.show();
        //getting button must be declared after .show()
        //otherwise it's going to crash the app
        confirmationDialog.getBtnYes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmationDialog.dismiss();
                execLogout();
            }
        });

        confirmationDialog.getBtnNo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmationDialog.dismiss();
            }
        });

    }
}
