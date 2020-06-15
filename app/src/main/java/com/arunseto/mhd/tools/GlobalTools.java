package com.arunseto.mhd.tools;

import android.content.Context;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.arunseto.mhd.R;
import com.arunseto.mhd.api.GoogleAPI;
import com.arunseto.mhd.models.Note;
import com.arunseto.mhd.ui.LoadingDialog;
import com.arunseto.mhd.ui.ConfirmationDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GlobalTools {
    private Context context;

    public GlobalTools(Context context) {
        this.context = context;
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
        fragmentManager.beginTransaction().replace(content, fragment).commit();
    }
    public void navigateFragment(FragmentManager fragmentManager, int content, Fragment fragment) {
        fragmentManager.beginTransaction().replace(content, fragment).addToBackStack("1").commit();
    }

    public void loadImgUrl(String url, ImageView ivTarget){
        //.diskCacheStrategy(DiskCacheStrategy.ALL is to cache the downloaded images
        //so the images can be reused in the future, by calling the same image's url
        Glide.with(context).load(url).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(ivTarget);
    }
    public void loadImgUrlLarge(String url, ImageView ivTarget){
        //.diskCacheStrategy(DiskCacheStrategy.ALL is to cache the downloaded images
        //so the images can be reused in the future, by calling the same image's url
        Glide.with(context).load(url).centerInside().diskCacheStrategy(DiskCacheStrategy.ALL).into(ivTarget);
    }

}
