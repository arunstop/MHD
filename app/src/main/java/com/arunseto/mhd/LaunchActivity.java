package com.arunseto.mhd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LaunchActivity extends AppCompatActivity {

    private GoogleSignInClient googleSignInClient;
    private Button btnLogin, btnLogout;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        context = LaunchActivity.this;
        btnLogin = findViewById(R.id.btnLogin);
        btnLogout = findViewById(R.id.btnLogout);

        //btnLogin to log in to google account
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginGoogle();
            }
        });

        //btnLogout to log out google account
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    logoutGoogle();
                } catch (Exception e) {
                    Log.w("Google Logout", "signInResult:failed code=" + e.getMessage());
                }
            }
        });
    }

    // google login
    private void loginGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        startActivityForResult(googleSignInClient.getSignInIntent(), 101);
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

                        startActivity(new Intent(context, MainActivity.class));
//                        finish();
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        Log.w("Google Login", "signInResult:failed code=" + e.getStatusCode());
                        Toast.makeText(this, "Sign In failed, error code = " + e.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        } else {
            Toast.makeText(context, "Sign In failed", Toast.LENGTH_SHORT).show();
        }

    }

    private void logoutGoogle() {
        /*
          Sign-out is initiated by simply calling the googleSignInClient.signOut API. We add a
          listener which will be invoked once the sign out is the successful
           */
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //On Succesfull signout we navigate the user back to LoginActivity
                Toast.makeText(context, "Log out success", Toast.LENGTH_SHORT).show();
                Log.w("Google Logout", "NICE");

            }
        });

    }


}
