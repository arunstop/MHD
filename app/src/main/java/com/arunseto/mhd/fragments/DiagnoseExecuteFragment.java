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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.arunseto.mhd.R;
import com.arunseto.mhd.models.Symptom;
import com.arunseto.mhd.models.SymptomResponse;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.arunseto.mhd.ui.ConfirmationDialog;
import com.arunseto.mhd.ui.LoadingDialog;
import com.github.ybq.android.spinkit.SpinKitView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//This is the main prototype of fragmenting

public class DiagnoseExecuteFragment extends Fragment {

    private View view;
    private LayoutInflater inflater;
    private Context context;
    private Session session;
    private User user;
    private int flContentBnv, flContent;
    private GlobalTools gt;
    private ConfirmationDialog confirmationDialog;
    private LoadingDialog loadingDialog;
    private SwipeRefreshLayout srlRefresher;
    private LinearLayout llSymptomsList;
    private Button btnNext;
    private SpinKitView skvLoading;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_diagnose_execute, container, false);
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
//        srlRefresher = ((SwipeRefreshLayout) llList.getParent().getParent());

        llSymptomsList = view.findViewById(R.id.llSymptomsList);
        btnNext = view.findViewById(R.id.btnNext);
        skvLoading = view.findViewById(R.id.skvLoading);

//        loadSymptom();
        loadSymptomQuiz(1);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gt.navigateFragment(getFragmentManager(), flContent, new DiagnoseResultFragment());
            }
        });

        return view;
    }

    public void loadSymptom() {
        llSymptomsList.removeAllViews();

        Call<SymptomResponse> call = gt.callApi().showSymptom();
        call.enqueue(new Callback<SymptomResponse>() {
            @Override
            public void onResponse(Call<SymptomResponse> call, Response<SymptomResponse> response) {
                if (response.isSuccessful()) {
                    SymptomResponse result = response.body();
                    if (result.isOk()) {
                        for (final Symptom symptom : result.getData()) {
                            View vSymptom = inflater.inflate(R.layout.template_symptom, null);
                            TextView tvSymptomName = vSymptom.findViewById(R.id.tvSymptomName);
                            Button btnYes = vSymptom.findViewById(R.id.btnYes);
                            Button btnNo = vSymptom.findViewById(R.id.btnNo);

                            //Button behaviour
                            btnBehaviour(btnYes, btnNo);

                            tvSymptomName.setText(symptom.getNama_gejala() + " ?");

                            llSymptomsList.addView(vSymptom);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SymptomResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadSymptomQuiz(int id) {
        skvLoading.setVisibility(View.VISIBLE);

        Call<SymptomResponse> call = gt.callApi().showSymptomQuiz(id);
        call.enqueue(new Callback<SymptomResponse>() {
            @Override
            public void onResponse(Call<SymptomResponse> call, Response<SymptomResponse> response) {
                llSymptomsList.removeAllViews();
                if (response.isSuccessful()) {
                    SymptomResponse result = response.body();
                    if (result.isOk()) {
                        for (final Symptom symptom : result.getData()) {
                            View vSymptom = inflater.inflate(R.layout.template_diagnose_symptom, null);
                            TextView tvSymptomName = vSymptom.findViewById(R.id.tvSymptomName);
                            Button btnYes = vSymptom.findViewById(R.id.btnYes);
                            Button btnNo = vSymptom.findViewById(R.id.btnNo);

                            btnYes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    loadSymptomQuiz(symptom.getYes());
                                }
                            });

                            btnNo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    loadSymptomQuiz(symptom.getNo());
                                }
                            });

                            tvSymptomName.setText(symptom.getId_gejala_detail() +" "+ symptom.getNama_gejala() + " ?");

                            llSymptomsList.addView(vSymptom);
                            skvLoading.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SymptomResponse> call, Throwable t) {

            }
        });
    }

    public void btnBehaviour(final Button btnYes, final Button btnNo) {
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSexChecked(btnYes);
                btnSexUnchecked(btnNo);
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSexChecked(btnNo);
                btnSexUnchecked(btnYes);
            }
        });
    }

    //If the button clicked the background and text color wil be changed
    public void btnSexChecked(Button button) {
        button.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorPrimary));
        button.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
    }

    //If the other button clicked by the function above,
    //the background and text color wil be changed as well
    public void btnSexUnchecked(Button button) {
        button.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBackground));
        button.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

    }

}
