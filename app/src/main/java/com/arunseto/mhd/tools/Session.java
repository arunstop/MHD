package com.arunseto.mhd.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.arunseto.mhd.models.User;


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

    public void saveUser(User user) {
        SharedPreferences session = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = session.edit();

        editor.putString("id_user", user.getId_user());
        editor.putString("email", user.getEmail());
        editor.putString("password", user.getPassword());
        editor.putString("first_name", user.getFirst_name());
        editor.putString("last_name", user.getLast_name());
        editor.putString("no_telp", user.getNo_telp());
        editor.putInt("sex", user.getSex());
        editor.putString("birth_date", user.getBirth_date());
        editor.putString("city", user.getCity());
        editor.putString("photo_url", user.getPhoto_url());
        editor.putInt("role", user.getRole());
        editor.putString("last_login", user.getLast_login());
        editor.putInt("type_login", user.getType_login());
        editor.putString("created_at", user.getCreated_at());



        editor.apply();
    }

    //logging check
    public boolean isLoggedIn() {
        SharedPreferences session = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return session.getString("email", null) != null;
    }

    public User getUser() {
        SharedPreferences session = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                session.getString("id_user", null),
                session.getString("email", null),
                session.getString("password", null),
                session.getString("first_name", null),
                session.getString("last_name", null),
                session.getString("no_telp", null),
                session.getInt("sex", 0),
                session.getString("birth_date", null),
                session.getString("city", null),
                session.getString("photo_url", null),
                session.getInt("role", 0),
                session.getString("last_login", null),
                session.getInt("type_login", 0),
                session.getString("created_at", null)
                );
    }

    public void clear() {
        SharedPreferences session = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = session.edit();
        editor.clear();
        editor.apply();
    }
}
