package com.arunseto.mhd.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.arunseto.mhd.ui.ConfirmationDialog;


public class PsychiatristProfileFragment extends Fragment {
    private View view;
    private LayoutInflater inflater;
    private Context context;
    private Session session;
    private int flContentBnv,flContent;
    private GlobalTools gt;
    private ConfirmationDialog confirmationDialog;
    private LoadingDialog loadingDialog;
    private Psychiatrist psychiatrist;
    private TextView tvPsyName, tvPsyNo, tvPsyAddress, tvPsyExtra;
    private ImageView ivPsyImg;
    private Button btnPsyImg;
    private ImageButton btnPsyAddress;

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
        flContentBnv = gt.getContentBnv();
        flContent = gt.getContent();
        loadingDialog = gt.getLoadingDialog();
        confirmationDialog = gt.getConfirmationDialog();

        tvPsyName = view.findViewById(R.id.tvPsyName);
        tvPsyNo = view.findViewById(R.id.tvPsyNo);
        tvPsyAddress = view.findViewById(R.id.tvPsyAddress);
        tvPsyExtra = view.findViewById(R.id.tvPsyExtra);
        ivPsyImg = view.findViewById(R.id.ivPsyImg);
        btnPsyImg= view.findViewById(R.id.btnPsyImg);
        btnPsyAddress= view.findViewById(R.id.btnPsyAddress);

        tvPsyName.setText(psychiatrist.getName());
        tvPsyNo.setText(psychiatrist.getNumber());
        tvPsyAddress.setText(psychiatrist.getAddress());
        tvPsyExtra.setText(psychiatrist.getExtra());

        if (psychiatrist.getImg().equals("f")){
            gt.loadImgUrl(getString(R.string.img_url), ivPsyImg);
        }else{
            ivPsyImg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dummy_jrr));
        }
        btnPsyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gt.navigateFragment(getFragmentManager(), gt.getContent(), new PsychiatristProfileImgFragment(getString(R.string.img_url)));
            }
        });

        btnPsyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGoogleMap = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query="+psychiatrist.getAddress()+""));
                startActivity(iGoogleMap);
            }
        });



        return view;
    }
}
