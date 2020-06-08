package com.arunseto.mhd.tools;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.arunseto.mhd.R;
import com.arunseto.mhd.api.GoogleAPI;
import com.arunseto.mhd.ui.LoadingDialog;
import com.arunseto.mhd.ui.OptionDialog;

public class GlobalTools {
    private Context context;

    public GlobalTools(Context context) {
        this.context = context;
    }

    public Session getSession() {

        return Session.getInstance(context);
    }

    public int getContent() {
        return R.id.flContent;
    }

    public int getContentSub() {
        return R.id.flContentSub;
    }

    public Context getContext() {
        return context;
    }

    public GoogleAPI getGoogleAPI() {
        return GoogleAPI.getInstance(context);
    }

    public LoadingDialog getLoadingDialog() {
        return new LoadingDialog(context);
    }

    public OptionDialog getOptionDialog() {
        return new OptionDialog(context);
    }
}
