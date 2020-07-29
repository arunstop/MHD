package com.arunseto.mhd.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.arunseto.mhd.R;
import com.arunseto.mhd.activities.LoginActivity;
import com.arunseto.mhd.api.GoogleAuthClient;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.arunseto.mhd.ui.ConfirmationDialog;
import com.arunseto.mhd.ui.LoadingDialog;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class SettingsFragment extends Fragment {

    //storage permission properties
    private GoogleSignInClient googleSignInClient;
    private View view;
    private LayoutInflater inflater;
    private Context context;
    private Session session;
    private User user;
    private GoogleAuthClient googleAuthClient;
    private TextView tvName, tvEmail;
    private ImageView ivProfilePhoto;
    private ConfirmationDialog confirmationDialog;
    private GlobalTools gt;
    private Button btnEditProfile, btnAbout, btnHelp, btnFeedback, btnLogout;
    private LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_settings, container, false);
        //getting inflater from the parameter is important to preventing a crash caused by switching between fragment too fast
        this.inflater = inflater;


        gt = new GlobalTools(this);
        context = gt.getContext();
        session = gt.getSession();
        user = gt.getUser();
        googleAuthClient = gt.getGoogleAuthClient();
        loadingDialog = gt.getLoadingDialog();

        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        ivProfilePhoto = view.findViewById(R.id.ivProfilePhoto);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnAbout = view.findViewById(R.id.btnAbout);
        btnHelp = view.findViewById(R.id.btnHelp);
        btnFeedback = view.findViewById(R.id.btnFeedback);
        btnLogout = view.findViewById(R.id.btnLogout);

        String displayName = user.getFirst_name() + " " + user.getLast_name();
        displayName = gt.capEachWord(displayName);
        tvName.setText(displayName);
        tvEmail.setText(user.getEmail());
        if (!user.getPhoto_url().isEmpty()) {
//            Glide.with(context).load(user.getPhoto_url()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(ivProfilePhoto);
            gt.loadImgUrl(user.getPhoto_url(), ivProfilePhoto);
        }

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gt.navigateFragment(
                        gt.getContent(),
                        new UserProfileFragment());
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gt.navigateFragment(
                        gt.getContent(),
                        new AboutFragment());
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gt.navigateFragment(
                        gt.getContent(),
                        new HelpFragment());
            }
        });

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", getResources().getString(R.string.email), null));
                emailIntent.putExtra(
                        Intent.EXTRA_SUBJECT,
                        "[MHD APP] Feedabck from " +
                                (user.getFirst_name() + " " + user.getLast_name()).toUpperCase()
                );
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initLogout();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!user.getPhoto_url().isEmpty()) {
//            Glide.with(context).load(user.getPhoto_url()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(ivProfilePhoto);
            gt.loadImgUrl(user.getPhoto_url(), ivProfilePhoto);
        }
    }

    public void refresh() {
        gt.refreshFragment();
    }

    public void initLogout() {

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
                loadingDialog.show();
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


    private void execLogout() {
        /*
          Sign-out is initiated by simply calling the googleSignInClient.signOut NewsAPI. We add a
          listener which will be invoked once the sign out is the successful
           */
        googleAuthClient.gsi().signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //On Succesfull signout we navigate the user back to LoginActivity
                session.clear();
                Log.w("Google Logout", "NICE");
                startActivity(new Intent(context, LoginActivity.class));
                getActivity().finish();
                confirmationDialog.dismiss();
                loadingDialog.dismiss();
                Toast.makeText(context, "Log out success", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
