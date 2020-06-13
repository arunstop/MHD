package com.arunseto.mhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arunseto.mhd.R;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.arunseto.mhd.ui.LoadingDialog;
import com.arunseto.mhd.ui.OptionDialog;

//This is the main prototype of fragmenting

public class PsychiatristProfileImgFragment extends Fragment {

    private View view;
    private LayoutInflater inflater;

    private Context context;
    private Session session;
    private int flContentBnv, flContent;
    private GlobalTools gt;
    private OptionDialog optionDialog;
    private LoadingDialog loadingDialog;
    private ImageView ivPsyImg;
    private String imgUrl;

    public PsychiatristProfileImgFragment(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_psychiatrist_profile_image, container, false);
        //getting inflater from the parameter is important to preventing a crash caused by switching between fragment too fast
        this.inflater = inflater;

        gt = new GlobalTools(getActivity());
        context = gt.getContext();
        session = gt.getSession();
        flContentBnv = gt.getContentBnv();
        flContent = gt.getContent();
        loadingDialog = gt.getLoadingDialog();
        optionDialog = gt.getOptionDialog();

        ivPsyImg = view.findViewById(R.id.ivPsyImg);
        gt.loadImgUrlLarge(imgUrl, ivPsyImg);

        return view;
    }
}
