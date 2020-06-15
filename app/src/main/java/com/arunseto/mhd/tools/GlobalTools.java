package com.arunseto.mhd.tools;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.arunseto.mhd.R;
import com.arunseto.mhd.api.GoogleAPI;
import com.arunseto.mhd.ui.LoadingDialog;
import com.arunseto.mhd.ui.ConfirmationDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GlobalTools {
    private Context context;
    private int animItemCounter;

    public GlobalTools(Context context) {
        this.context = context;
        animItemCounter = 1;
    }

    public Session getSession() {

        return Session.getInstance(context);
    }

    public int getContentBnv() {
        return R.id.flContentBnv;
    }

    public int getContent() {
        return R.id.flContent;
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

    public ConfirmationDialog getConfirmationDialog() {
        return new ConfirmationDialog(context);
    }

    public void navigateFragmentBnv(FragmentManager fragmentManager, int content, Fragment fragment) {
        //Fragment navigator for the main content
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(content, fragment).commit();
    }

    public void navigateFragment(FragmentManager fragmentManager, int content, Fragment fragment) {
        //Fragment navigator for the Bottom Navigation View content
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_left_in,
                        0,
                        0,
                        R.anim.slide_right_out
                );
        fragmentTransaction.replace(content, fragment).addToBackStack("1").commit();
    }

    public void loadImgUrl(String url, ImageView ivTarget) {
        //.diskCacheStrategy(DiskCacheStrategy.ALL is to cache the downloaded images
        //so the images can be reused in the future, by calling the same image's url
        if (!url.isEmpty()) {
            Glide.with(context).load(url).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(ivTarget);
        } else {
            Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.google_logo)).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(ivTarget);
        }
    }

    public void loadImgUrlLarge(String url, ImageView ivTarget) {
        //.diskCacheStrategy(DiskCacheStrategy.ALL is to cache the downloaded images
        //so the images can be reused in the future, by calling the same image's url
        if (!url.isEmpty()) {
            Glide.with(context).load(url).centerInside().diskCacheStrategy(DiskCacheStrategy.ALL).into(ivTarget);
        } else {
            Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.google_logo)).centerInside().diskCacheStrategy(DiskCacheStrategy.ALL).into(ivTarget);
        }
    }

    public String capEachWord(String string) {
        //Capitalize Each Word
        String s = string.toLowerCase();
        String[] sArray = s.split(" ");
        StringBuilder sBuilder = new StringBuilder();
        for (String sNew : sArray) {
            String sCap = sNew.substring(0, 1).toUpperCase() + sNew.substring(1);
            sBuilder.append(sCap + " ");
        }
        return sBuilder.toString();
    }

    public void loadAnimateView(final ViewGroup container, final View item) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                container.addView(item);
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_slide_left_in_);
                item.startAnimation(animation);
            }
        }, animItemCounter * 100);
        animItemCounter++;
    }

}
