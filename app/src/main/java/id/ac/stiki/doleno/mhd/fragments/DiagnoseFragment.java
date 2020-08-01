package id.ac.stiki.doleno.mhd.fragments;

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

import id.ac.stiki.doleno.mhd.R;
import id.ac.stiki.doleno.mhd.models.User;
import id.ac.stiki.doleno.mhd.tools.GlobalTools;
import id.ac.stiki.doleno.mhd.tools.Session;


public class DiagnoseFragment extends Fragment {
    private View view;
    private LayoutInflater inflater;
    private Context context;
    private Session session;
    private User user;
    private LinearLayout llNavTestExecute, llNavPsychiatrist, llNavPsychiatristMap;
    private int flContent;
    private GlobalTools gt;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_diagnose, container, false);
        //getting inflater from the parameter is important to preventing a crash caused by switching between fragment too fast
        this.inflater = inflater;

        gt = new GlobalTools(this);
        context = gt.getContext();
        session = gt.getSession();
        user = gt.getUser();
        flContent = gt.getContent();

        llNavTestExecute = view.findViewById(R.id.llNavTestExecute);
        llNavPsychiatrist = view.findViewById(R.id.llNavPsychiatrist);
        llNavPsychiatristMap = view.findViewById(R.id.llNavPsychiatristMap);


        llNavTestExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gt.navigateFragment(flContent, new TestInitFragment());
            }
        });

        llNavPsychiatrist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gt.navigateFragment(flContent, new PsychiatristFragment());
            }
        });


        llNavPsychiatristMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to google map
                Intent iGoogleMap = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=psikiater+terdekat"));
                startActivity(iGoogleMap);
            }
        });


        return view;
    }
}
