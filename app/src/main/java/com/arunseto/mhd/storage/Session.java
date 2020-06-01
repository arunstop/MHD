package com.arunseto.mhd.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.arunseto.mhd.models.ListUser;


/**
 * Created by Arunstop on 25-May-19.
 */

public class Session {

    private static final String SHARED_PREF_NAME = "my_session";
    private static Session mInstance;
    private Context mCtx;

    public Session(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized Session getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new Session(mCtx);
        }
        return mInstance;
    }

    public void saveUser(ListUser user) {
        SharedPreferences session = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = session.edit();

        editor.putString("email", user.getEmail());
        editor.putString("password", user.getPassword());


        editor.apply();
    }

    //logging check
    public boolean isLoggedIn() {
        SharedPreferences session = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return session.getString("email", null) != null;
    }

    public ListUser getUser() {
        SharedPreferences session = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new ListUser(
                session.getString("email", null),
                session.getString("password", null)
        );
    }

    public void clear() {
        SharedPreferences session = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = session.edit();
        editor.clear();
        editor.apply();
    }
}
