package com.arunseto.mhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arunseto.mhd.R;
import com.arunseto.mhd.models.Test;
import com.arunseto.mhd.models.TestResponse;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.arunseto.mhd.ui.ConfirmationDialog;
import com.arunseto.mhd.ui.LoadingDialog;
import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//This is the main prototype of fragmenting

public class DiagnoseInitFragment extends Fragment {

    private View view;
    private LayoutInflater inflater;
    private Context context;
    private Session session;
    private User user;
    private int flContentBnv, flContent;
    private GlobalTools gt;
    private ConfirmationDialog confirmationDialog;
    private LoadingDialog loadingDialog;
    private Button btnNavTestList, btnNavDiagnoseExecuteStart;
    private SwipeButton sbNavDiagnoseExecute;
    private TextView tvDiagnoseInitLabel;
    private Boolean startDiagnose= false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_diagnose_init, container, false);
        //getting inflater from the parameter is important to preventing a crash caused by switching between fragment too fast
        this.inflater = inflater;

        gt = new GlobalTools(getActivity());
        context = gt.getContext();
        session = gt.getSession();
        user = gt.getUser();
        flContentBnv = gt.getContentBnv();
        flContent = gt.getContent();
        loadingDialog = gt.getLoadingDialog();
        confirmationDialog = gt.getConfirmationDialog();

        tvDiagnoseInitLabel = view.findViewById(R.id.tvDiagnoseInitLabel);
        sbNavDiagnoseExecute = view.findViewById(R.id.sbNavDiagnoseExecute);
        btnNavTestList = view.findViewById(R.id.btnNavTestList);
        btnNavDiagnoseExecuteStart = view.findViewById(R.id.btnNavDiagnoseExecuteStart);

        if (!startDiagnose) {
            tvDiagnoseInitLabel.setVisibility(View.GONE);
            sbNavDiagnoseExecute.setVisibility(View.GONE);
        }else{
            btnNavDiagnoseExecuteStart.setVisibility(View.GONE);
        }

        initActions();

        return view;
    }

    public void initActions() {

        btnNavTestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gt.navigateFragment(getFragmentManager(), flContent, new TestListFragment());
            }
        });

        sbNavDiagnoseExecute.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                if (active) {
//                    addTest();
//                    getFragmentManager().beginTransaction().
                    gt.popFragment(getFragmentManager());
                    gt.navigateFragment(getFragmentManager(), flContent, new DiagnoseExecuteFragment());
                } else {
//                    gt.getSession().endDiagnoseSession();
//                    String str = gt.getSession().getDiagnoseSession().getId_tes()
//                            + "\n"
//                            + gt.getSession().getDiagnoseSession().getId_user();
//                    Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnNavDiagnoseExecuteStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDiagnose=true;
                tvDiagnoseInitLabel.setVisibility(View.VISIBLE);
                sbNavDiagnoseExecute.setVisibility(View.VISIBLE);
                btnNavDiagnoseExecuteStart.setVisibility(View.GONE);
            }
        });
    }
}
