package com.arunseto.mhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.arunseto.mhd.R;
import com.arunseto.mhd.models.TestDetail;
import com.arunseto.mhd.models.TestDetailResponse;
import com.arunseto.mhd.models.TestResult;
import com.arunseto.mhd.models.TestResultResponse;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.arunseto.mhd.ui.ConfirmationDialog;
import com.arunseto.mhd.ui.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//This is the main prototype of fragmenting

public class DiagnoseResultFragment extends Fragment {

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
    private int id_test;
    private LinearLayout llTestResultDetailList, llTestResultPercentageList;

    public DiagnoseResultFragment(int id_test) {
        this.id_test = id_test;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_diagnose_result, container, false);
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

        llTestResultPercentageList = view.findViewById(R.id.llTestResultPercentageList);
        llTestResultDetailList = view.findViewById(R.id.llTestResultDetailList);

        llTestResultPercentageList.removeAllViews();

        loadTestResult();

        return view;
    }

    public void loadTestResult() {
        Call<TestResultResponse> call = gt.callApi().showTestResult(id_test);
        call.enqueue(new Callback<TestResultResponse>() {
            @Override
            public void onResponse(Call<TestResultResponse> call, Response<TestResultResponse> response) {
                if (response.isSuccessful()) {
                    TestResultResponse result = response.body();
                    if (result.isOk()) {
                        int iNo = 1;
                        for (TestResult testResult : result.getData()) {
                            View vPercentage = getLayoutInflater().inflate(R.layout.template_diagnose_result_percentage, null);
                            TextView tvTestResultPercentage = vPercentage.findViewById(R.id.tvTestResultPercentageLabel);

                            String strLabel = testResult.getDisorder_name() + " : " + testResult.getSymptom_percentage();
                            if (iNo == 1) {
                                strLabel = "<b>" + strLabel + "</b>";
                                tvTestResultPercentage.setTextSize(18);
                                textStyling(tvTestResultPercentage, R.color.colorPrimaryDark, 0);
                            }
//                            Toast.makeText(context, strLabel + "", Toast.LENGTH_SHORT).show();
                            tvTestResultPercentage.setText(Html.fromHtml(strLabel));

                            gt.addViewAnimated(llTestResultPercentageList, vPercentage);
                            iNo++;
                        }
                        loadTestDetail();
                    }
                }
            }

            @Override
            public void onFailure(Call<TestResultResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadTestDetail() {
        Call<TestDetailResponse> call = gt.callApi().showTestDetail(id_test);
        call.enqueue(new Callback<TestDetailResponse>() {
            @Override
            public void onResponse(Call<TestDetailResponse> call, Response<TestDetailResponse> response) {
                if (response.isSuccessful()) {
                    TestDetailResponse result = response.body();
                    if (result.isOk()) {
                        int iNo = 1;
                        for (TestDetail testDetail : result.getData()) {
                            View vDetail = getLayoutInflater().inflate(R.layout.template_diagnose_result_detail, null);
                            TextView tvTestDetailSymptom = vDetail.findViewById(R.id.tvTestDetailSymptom);
                            TextView tvTestDetailChoice = vDetail.findViewById(R.id.tvTestDetailChoice);

                            tvTestDetailSymptom.setText(iNo + ". " + testDetail.getSymptom_name());
                            if (testDetail.getChoice() == 1) {
                                tvTestDetailChoice.setText("YA");
                                textStyling(tvTestDetailChoice, R.color.colorSuccess, R.drawable.bg_round_corner_border_success);

                            } else {
                                tvTestDetailChoice.setText("TIDAK");
                                textStyling(tvTestDetailChoice, R.color.colorDanger, R.drawable.bg_round_corner_border_danger);
                            }

                            gt.addViewAnimated(llTestResultDetailList, vDetail);
                            iNo++;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TestDetailResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void textStyling(TextView tv, int txtColor, int bg) {
        tv.setTextColor(gt.getColor(txtColor));
        if (bg != 0) {
            tv.setBackground(gt.getDrawable(bg));
        }
    }
}
