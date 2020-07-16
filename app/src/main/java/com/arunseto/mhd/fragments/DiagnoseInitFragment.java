package com.arunseto.mhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arunseto.mhd.R;
import com.arunseto.mhd.models.DefaultResponse;
import com.arunseto.mhd.models.TestResultResponse;
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
    private Button btnNavTestList;
    private SwipeButton sbNavDiagnoseExecute;


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

        btnNavTestList = view.findViewById(R.id.btnNavTestList);
        sbNavDiagnoseExecute = view.findViewById(R.id.sbNavDiagnoseExecute);

        btnNavTestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gt.navigateFragment(getFragmentManager(), flContent, new TestResultListFragment());
            }
        });

        sbNavDiagnoseExecute.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                if (active) {
                    addTest();
                }
            }
        });

        return view;
    }

    public void addTest() {
        loadingDialog.show();
        Call<TestResultResponse> call = gt.callApi().addTest(gt.getUser().getId_user());
        call.enqueue(new Callback<TestResultResponse>() {
            @Override
            public void onResponse(Call<TestResultResponse> call, Response<TestResultResponse> response) {
                if (response.isSuccessful()) {
                    TestResultResponse result = response.body();
                    if (result.isOk()) {
                        gt.navigateFragment(getFragmentManager(), flContent, new DiagnoseExecuteFragment());
                    }
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<TestResultResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
            }
        });
//        Toast.makeText(context, call.request().url()+"", Toast.LENGTH_SHORT).show();
    }
}
