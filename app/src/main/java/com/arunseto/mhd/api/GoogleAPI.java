package com.arunseto.mhd.api;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import com.arunseto.mhd.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


public class GoogleAPI {
//    private String idToken = Resources.getSystem().getString(R.string.client_id);
    private static GoogleAPI mInstance;
    private Context context;
    GoogleSignInOptions gso;

    //Setting up google API

    public GoogleAPI(Context context) {
        this.context = context;
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("52023918840-usq2mt0bjs6jkrncin52gno943csjasf.apps.googleusercontent.com")
                .requestEmail()
                .build();
        gsi();
    }

    public static synchronized GoogleAPI getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new GoogleAPI(mCtx);
        }
        return mInstance;
    }

    public GoogleSignInClient gsi(){
        return GoogleSignIn.getClient(context, gso);
    }

    public Intent getIntent(){
        return gsi().getSignInIntent();
    }


}
