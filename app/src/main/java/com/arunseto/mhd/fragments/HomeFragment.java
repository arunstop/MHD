package com.arunseto.mhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arunseto.mhd.R;
import com.arunseto.mhd.api.GoogleAPI;
import com.arunseto.mhd.storage.Session;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;


public class HomeFragment extends Fragment {

    private View view;
    private Context context;
    private Session session;
    private TextView tvName, tvEmail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = getActivity();
        session = Session.getInstance(context);

        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);

        tvName.setText(session.getUser().getFname().toUpperCase() + " " + session.getUser().getLname().toUpperCase());
        tvEmail.setText(session.getUser().getEmail());

        return view;
    }


}
