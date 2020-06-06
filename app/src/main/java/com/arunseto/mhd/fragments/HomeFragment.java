package com.arunseto.mhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arunseto.mhd.R;
import com.arunseto.mhd.storage.Session;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;


public class HomeFragment extends Fragment {

    private View view;
    private Context context;
    private Session session;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_home, container, false);
        context = getActivity();
        session = Session.getInstance(context);


        return view;
    }


}
