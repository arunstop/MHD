package com.arunseto.mhd.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arunseto.mhd.R;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.arunseto.mhd.ui.ConfirmationDialog;
import com.arunseto.mhd.ui.LoadingDialog;


public class MoreInfoFragment extends Fragment {
    private View view;
    private LayoutInflater inflater;
    private Context context;
    private Session session;
    private User user;
    private int flContentBnv, flContent;
    private GlobalTools gt;
    private ConfirmationDialog confirmationDialog;
    private LoadingDialog loadingDialog;
    private LinearLayout llStiki, ll000webhost, llFlaticon, llImgur;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_more_info, container, false);
        //getting inflater from the parameter is important to preventing a crash caused by switching between fragment too fast
        this.inflater = inflater;

        gt = new GlobalTools(this);
        context = gt.getContext();
        session = gt.getSession();
        user = gt.getUser();
        flContentBnv = gt.getContentBnv();
        flContent = gt.getContent();
        loadingDialog = gt.getLoadingDialog();
        confirmationDialog = gt.getConfirmationDialog();

        llStiki = view.findViewById(R.id.llStiki);
        ll000webhost = view.findViewById(R.id.ll000webhost);
        llFlaticon = view.findViewById(R.id.llFlaticon);
        llImgur = view.findViewById(R.id.llImgur);


        llStiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.stiki.ac.id/"));
                startActivity(i);
            }
        });
        ll000webhost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.000webhost.com/"));
                startActivity(i);
            }
        });
        llFlaticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.flaticon.com/"));
                startActivity(i);
            }
        });
        llImgur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.imgur.com/"));
                startActivity(i);
            }
        });


        return view;
    }
}
