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
import com.arunseto.mhd.models.ListUser;
import com.arunseto.mhd.storage.Session;
import com.arunseto.mhd.ui.LoadingDialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {


    private LinearLayout llBtnLogin;
    private Context context;
    private Session session;
    private LoadingDialog loadingDialog;
    private GoogleAPI googleAPI;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadingDialog = new LoadingDialog(this);
        context = LoginActivity.this;
        session = Session.getInstance(this);
        googleAPI = GoogleAPI.getInstance(this);

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

                        session.saveUser(new ListUser("test@test.com","test"));
                        startActivity(new Intent(context, MainActivity.class));
//                        finish();
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
