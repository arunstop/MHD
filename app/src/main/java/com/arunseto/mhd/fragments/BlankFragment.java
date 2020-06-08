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
import com.arunseto.mhd.ui.LoadingDialog;
import com.arunseto.mhd.ui.OptionDialog;


public class BlankFragment extends Fragment {
    private View view;
    private Context context;
    private Session session;
    private int flContent,flContentSub;
    private GlobalTools gt;
    private OptionDialog optionDialog;
    private LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_blank, container, false);

        gt = new GlobalTools(getActivity());
        context = gt.getContext();
        session = gt.getSession();
        flContent = gt.getContent();
        flContentSub = gt.getContentSub();
        loadingDialog = gt.getLoadingDialog();
        optionDialog = gt.getOptionDialog();


        return view;
    }
}
