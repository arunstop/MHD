package com.arunseto.mhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.arunseto.mhd.R;
import com.arunseto.mhd.models.Psychiatrist;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.arunseto.mhd.ui.LoadingDialog;
import com.arunseto.mhd.ui.OptionDialog;


public class PsychiatristProfileFragment extends Fragment {
    private View view;
    private LayoutInflater inflater;
    private Context context;
    private Session session;
    private int flContent,flContentSub;
    private GlobalTools gt;
    private OptionDialog optionDialog;
    private LoadingDialog loadingDialog;
    private Psychiatrist psychiatrist;
    private TextView tvPsyName, tvPsyNo, tvPsyAddress, tvPsyExtra;
    private ImageView ivPsyImg;

    public PsychiatristProfileFragment(Psychiatrist psychiatrist) {
        this.psychiatrist = psychiatrist;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_psychiatrist_profile, container, false);
         //getting inflater from the parameter is important to preventing a crash caused by switching between fragment too fast
        this.inflater = inflater;

        gt = new GlobalTools(getActivity());
        context = gt.getContext();
        session = gt.getSession();
        flContent = gt.getContent();
        flContentSub = gt.getContentSub();
        loadingDialog = gt.getLoadingDialog();
        optionDialog = gt.getOptionDialog();

        tvPsyName = view.findViewById(R.id.tvPsyName);
        tvPsyNo = view.findViewById(R.id.tvPsyNo);
        tvPsyAddress = view.findViewById(R.id.tvPsyAddress);
        tvPsyExtra = view.findViewById(R.id.tvPsyExtra);
        ivPsyImg = view.findViewById(R.id.ivPsyImg);

        tvPsyName.setText(psychiatrist.getName());
        tvPsyNo.setText(psychiatrist.getNumber());
        tvPsyAddress.setText(psychiatrist.getAddress());
        tvPsyExtra.setText(psychiatrist.getExtra());
        if (psychiatrist.getImg().equals("f")){
            ivPsyImg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dummy_femdoc));
        }else{
            ivPsyImg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dummy_jrr));
        }


        return view;
    }
}
