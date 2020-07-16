package com.arunseto.mhd.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.arunseto.mhd.R;
import com.arunseto.mhd.api.GoogleAuthClient;
import com.arunseto.mhd.api.MainAPI;
import com.arunseto.mhd.api.MainClient;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.ui.LoadingDialog;
import com.arunseto.mhd.ui.ConfirmationDialog;
import com.arunseto.mhd.ui.PoppingMenu;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GlobalTools {
    private Context context;
    private int animItemCounter;

    public GlobalTools(Context context) {
        this.context = context;
        animItemCounter = 1;
    }

    //Getting Context
    public Context getContext() {
        return context;
    }

    //Getting session
    public Session getSession() {
        return Session.getInstance(context);
    }

    //Getting user
    public User getUser() {
        return getSession().getUser();
    }

    //Getting Bottom Nav Content
    public int getContentBnv() {
        return R.id.flContentBnv;
    }

    //Getting main content
    public int getContent() {
        return R.id.flContent;
    }

    //Getting Main API
    public MainAPI callApi(){
        return MainClient.getInstance().getApi();
    }

    //Getting GoogleAuthClient
    public GoogleAuthClient getGoogleAuthClient() {
        return GoogleAuthClient.getInstance(context);
    }

    //Getting Loading Dialog
    //Must create local dialog with this to prevent window leak
    public LoadingDialog getLoadingDialog() {
        return new LoadingDialog(context);
    }

    //Getting Confirmation Dialog
    public ConfirmationDialog getConfirmationDialog() {
        return new ConfirmationDialog(context);
    }

    //Navigate Fragment inside Bottom Navigation View
    public void navigateFragmentBnv(FragmentManager fragmentManager, int content, Fragment fragment) {
        //Fragment navigator for the main content
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(content, fragment).commit();
    }

    //Navigate Fragment inside Main Content
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
        fragmentTransaction.replace(content, fragment).addToBackStack("fragmentNavigation").commit();
    }

    //Refresh Fragment
    public void refreshFragment(FragmentManager fragmentManager, Fragment fragment) {
        //Fragment navigator for the Bottom Navigation View content
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.detach(fragment).attach(fragment).commit();
    }

    //Load online Image
    public void loadImgUrl(String url, ImageView ivTarget) {
        //.diskCacheStrategy(DiskCacheStrategy.ALL is to cache the downloaded images
        //so the images can be reused in the future, by calling the same image's url
        if (!url.isEmpty()) {
            Glide.with(context).load(url).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(ivTarget);
        } else {
            Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.google_logo)).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(ivTarget);
        }
    }

    //Load online Image, large one
    public void loadImgUrlLarge(String url, ImageView ivTarget) {
        //.diskCacheStrategy(DiskCacheStrategy.ALL is to cache the downloaded images
        //so the images can be reused in the future, by calling the same image's url
        if (!url.isEmpty()) {
            Glide.with(context).load(url).centerInside().diskCacheStrategy(DiskCacheStrategy.ALL).into(ivTarget);
        } else {
            Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.google_logo)).centerInside().diskCacheStrategy(DiskCacheStrategy.ALL).into(ivTarget);
        }
    }

    //Capitalize each word
    public String capEachWord(String string) {
        //If there is no space, return the default string value
        String s = string.toLowerCase();
        String[] sArray = s.split(" ");
        StringBuilder sBuilder = new StringBuilder();
        for (String sNew : sArray) {
            String sCap = sNew.substring(0, 1).toUpperCase() + sNew.substring(1);
            sBuilder.append(sCap + " ");
        }
        return sBuilder.toString();
    }

    //Adding View with slide in animation
    public void addViewAnimated(final ViewGroup container, final View item) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_slide_left_in);
        item.startAnimation(animation);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                container.addView(item);
            }
        }, animItemCounter * 100);
        animItemCounter++;
    }

    //Removing View with fade out animation
    public void removeViewAnimated(final ViewGroup container, final View item) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        item.startAnimation(animation);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                container.removeView(item);
            }
        }, 400);
//        final int duration = 1000;
//        final ValueAnimator vAnim = ValueAnimator.ofInt(item.getMeasuredHeight(), -100);
//        vAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                int val = (Integer) valueAnimator.getAnimatedValue();
//                ViewGroup.LayoutParams layoutParams = item.getLayoutParams();
//                layoutParams.height = val;
//                item.setLayoutParams(layoutParams);
//            }
//        });
////        vAnim.addListener(new AnimatorListenerAdapter() {
////            @Override
////            public void onAnimationEnd(Animator animation) {
////                container.removeView(item);
//////                super.onAnimationEnd(animation);
////
////            }
////        });
//        vAnim.setDuration(duration);
//        vAnim.start();
//
//        //removing view with delay or 1/4 of animation duration
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                container.removeView(item);
//            }
//        }, duration/2);

    }

    //Pop out menu
    public PoppingMenu getPoppingMenu(View anchor) {
        return new PoppingMenu(context, anchor);
    }

    //Getting current Date and time
    public String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateNow = new Date();
        return formatter.format(dateNow);
    }

    //Getting current date(optional)
    public String getCurrentDate(int dateFormat) {
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(dateFormat).format(calendar.getTime());

        return currentDate;
    }

    //Sharing intent
    public void showSharingIntent(String text) {
        Intent iShare = new Intent(android.content.Intent.ACTION_SEND);
        iShare.setType("text/plain");
        iShare.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        iShare.putExtra(android.content.Intent.EXTRA_TEXT, text);

        ((Activity) context).startActivity(Intent.createChooser(iShare, "Share via..."));
    }

    //DateFormatter
    /* FORMAT PATTERNS
       "yyyy.MM.dd G 'at' HH:mm:ss z" ==== 2001.07.04 AD at 12:08:56 PDT

        "EEEE, MMM d, ''yy" ==== Wednesday, Jul 4, '01

        "h:mm a" ==== 12:08 PM

        "hh 'o''clock' a, zzzz" ==== 12 o'clock PM, Pacific Daylight Time

        "K:mm a, z" ==== 0:08 PM, PDT

        "yyyyy.MMMMM.dd GGG hh:mm aaa" ==== 02001.July.04 AD 12:08 PM

        "EEE, d MMM yyyy HH:mm:ss Z" ==== Wed, 4 Jul 2001 12:08:56 -0700

        "yyMMddHHmmssZ" ==== 010704120856-0700

        "yyyy-MM-dd'T'HH:mm:ss.SSSZ" ==== 2001-07-04T12:08:56.235-0700

        "yyyy-MM-dd'T'HH:mm:ss.SSSXXX" ==== 2001-07-04T12:08:56.235-07:00

        "YYYY-'W'ww-u" ==== 2001-W27-3
     */
    public String formatDate(String inPattern, String outPattern, String strDate){

        String strResult = "";
        SimpleDateFormat ip = new SimpleDateFormat(inPattern);
//        DateFormat formatter = DateFormat.getDateInstance(DateFormat.FULL);
        SimpleDateFormat op= new SimpleDateFormat(outPattern);

        try {
            //dateStr example 2020-05-27T19:30:00Z
            Date date = ip.parse(strDate);
            //after formatted with ip 2020-05-27
            strResult = op.format(date);
            //after formatted with formatter 27 Jun 2020
            return strResult;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strResult;
    }

}
