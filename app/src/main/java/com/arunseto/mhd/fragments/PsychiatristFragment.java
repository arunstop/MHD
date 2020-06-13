package com.arunseto.mhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.arunseto.mhd.R;
import com.arunseto.mhd.models.Psychiatrist;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;

import java.util.ArrayList;
import java.util.List;


public class PsychiatristFragment extends Fragment {
    private View view;
    private LayoutInflater inflater;
    private Context context;
    private Session session;
    private LinearLayout llPsychiatristList;
    private int flContent;
    private GlobalTools gt;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_psychiatrist, container, false);
         //getting inflater from the parameter is important to preventing a crash caused by switching between fragment too fast
        this.inflater = inflater;

        gt = new GlobalTools(getActivity());
        context = gt.getContext();
        session = gt.getSession();
        flContent = gt.getContent();

        llPsychiatristList = view.findViewById(R.id.llPsychiatristList);
        llPsychiatristList.removeAllViews();

        List<Psychiatrist> lp = new ArrayList<Psychiatrist>();
        lp.add(new Psychiatrist("Dr. Muna Sihombing", "+62 823 999 890", "Jl. Mawar No 1, Malang", "08:00 - 19:00", "f"));
        lp.add(new Psychiatrist("Dr. Mariyo Kirk", "+62 822 123 642", "Jl. Kasih No 2, Batu", "08:00 - 19:00", "m"));
        lp.add(new Psychiatrist("Dr. Billy Suharja", "+62 811 656 576", "Jl. Ibu No 23, Malang", "08:00 - 19:00", "m"));
        lp.add(new Psychiatrist("Dr. Mustafa Allemania", "+62 823 789 279", "Jl. Yogurt No 6", "08:00 - 19:00", "m"));
        lp.add(new Psychiatrist("Dr. Ma'aruf Mancagit", "+62 811 222 712", "Jl. Setia No 7", "08:00 - 19:00", "m"));
        lp.add(new Psychiatrist("Dr. Durma Durmajana", "+62 823 157 228", "Jl. Melati No 12", "08:00 - 19:00", "f"));
        lp.add(new Psychiatrist("Dr. Prasetiyo", "+62 822 122 561", "Jl. Melijan No 32", "08:00 - 19:00", "m"));
        lp.add(new Psychiatrist("Dr. Enny Malamita", "+62 810 572 550", "Jl. Sukoharjo No 2", "08:00 - 19:00", "f"));


        for (final Psychiatrist psy : lp) {
            View vPsy = getLayoutInflater().inflate(R.layout.template_psychiatrist, null);
            LinearLayout llPsy = vPsy.findViewById(R.id.llPsychiatrist);
            TextView tvPsyName = vPsy.findViewById(R.id.tvPsyName);
            TextView tvPsyNo = vPsy.findViewById(R.id.tvPsyNo);
            ImageView ivPsyImg = vPsy.findViewById(R.id.ivPsyImg);

            tvPsyName.setText(psy.getName());
            tvPsyNo.setText(psy.getNumber());
            if (psy.getImg().equals("f")){
                ivPsyImg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dummy_femdoc));
            }else{
                ivPsyImg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dummy_jrr));
            }


            llPsy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gt.navigateFragment(getFragmentManager(), gt.getContent(), new PsychiatristProfileFragment(psy));
                }
            });

            llPsychiatristList.addView(vPsy);
        }

        return view;
    }
}
