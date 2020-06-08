package com.arunseto.mhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arunseto.mhd.R;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;


public class DiagnoseFragment extends Fragment {
    private View view;
    private Context context;
    private Session session;
    private LinearLayout llPsikiater;
    private int flContentSub;
    private GlobalTools gt;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_diagnose, container, false);

        gt = new GlobalTools(getActivity());
        context = gt.getContext();
        session = gt.getSession();
        flContentSub = gt.getContentSub();

        llPsikiater = view.findViewById(R.id.llNavPsychiatrist);
        llPsikiater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(flContentSub, new PsychiatristFragment()).addToBackStack("1")
                        .commit();
            }
        });


        return view;
    }
}
