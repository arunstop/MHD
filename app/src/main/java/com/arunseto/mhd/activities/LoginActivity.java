package com.arunseto.mhd.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arunseto.mhd.R;
import com.arunseto.mhd.api.GoogleAPI;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.arunseto.mhd.ui.LoadingDialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {


    private LinearLayout llBtnLogin;
    private Context context;
    private Session session;
    private int flContent,flContentSub;
    private GlobalTools gt;
    private GoogleAPI googleAPI;

    private LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadingDialog = new LoadingDialog(LoginActivity.this);
        gt = new GlobalTools(LoginActivity.this);

        context = gt.getContext();
        session = gt.getSession();
        flContent = gt.getContent();
        flContentSub = gt.getContentSub();
        googleAPI = gt.getGoogleAPI();

        llBtnLogin = findViewById(R.id.llBtnLogin);

        //btnLogin to log getIntent to google account
        llBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginGoogle();
            }
        });

        //btnLogout to log out google account
    }

    // google login
    private void loginGoogle() {
        loadingDialog.show();
        startActivityForResult(googleAPI.getIntent(), 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 101:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);

//                        Toast.makeText(context, account.getGivenName()+"", Toast.LENGTH_SHORT).show();
                        session.saveUser(
                                new User(account.getEmail(),
                                        "",
                                        account.getGivenName(),
                                        account.getFamilyName(),
                                        account.getPhotoUrl().toString())
                        );
                        startActivity(new Intent(context, MainActivity.class));
                        finish();
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        Log.w("Google LoginActivity", "signInResult:failed code=" + e.getStatusCode());
                        Toast.makeText(this, "Sign In failed, error code = " + e.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            loadingDialog.dismiss();
        } else {
            Toast.makeText(context, "Sign In failed", Toast.LENGTH_SHORT).show();
            loadingDialog.dismiss();
        }
    }


}
