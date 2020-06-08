package com.arunseto.mhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.arunseto.mhd.R;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;


public class PsychiatristFragment extends Fragment {
    private View view;
    private Context context;
    private Session session;
    private LinearLayout llPsychiatristList;
    private int flContentSub;
    private GlobalTools gt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_psychiatrist, container, false);

        gt = new GlobalTools(getActivity());
        context = gt.getContext();
        session = gt.getSession();
        flContentSub = gt.getContentSub();

        llPsychiatristList = view.findViewById(R.id.llPsychiatristList);
        llPsychiatristList.removeAllViews();

        for (int i = 0; i < 15; i++) {
            View vPsychiatrist = getLayoutInflater().inflate(R.layout.template_psychiatrist, null);
            LinearLayout llPsy = vPsychiatrist.findViewById(R.id.llPsychiatrist);
            llPsy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(flContentSub, new PsychiatristProfileFragment()).addToBackStack("1")
                            .commit();
                }
            });

            llPsychiatristList.addView(vPsychiatrist);
        }

        return view;
    }
}
