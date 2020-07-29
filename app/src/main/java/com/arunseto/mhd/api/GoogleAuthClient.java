package com.arunseto.mhd.api;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


public class GoogleAuthClient {
    private static GoogleAuthClient mInstance;
    GoogleSignInOptions gso;
    //    private String idToken = Resources.getSystem().getString(R.string.client_id);
    private String idToken = "52023918840-usq2mt0bjs6jkrncin52gno943csjasf.apps.googleusercontent.com";
    private Context context;

    public GoogleAuthClient(Context context) {
        this.context = context;
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("52023918840-usq2mt0bjs6jkrncin52gno943csjasf.apps.googleusercontent.com")
                .requestEmail()
                .build();
        gsi();
    }

    public static synchronized GoogleAuthClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new GoogleAuthClient(mCtx);
        }
        return mInstance;
    }

    public GoogleSignInClient gsi() {
        return GoogleSignIn.getClient(context, gso);
    }

    public Intent getIntent() {
        return gsi().getSignInIntent();
    }

}
