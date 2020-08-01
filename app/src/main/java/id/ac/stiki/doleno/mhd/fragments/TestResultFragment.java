package id.ac.stiki.doleno.mhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import id.ac.stiki.doleno.mhd.R;
import id.ac.stiki.doleno.mhd.models.TestDetail;
import id.ac.stiki.doleno.mhd.models.TestDetailResponse;
import id.ac.stiki.doleno.mhd.models.TestResult;
import id.ac.stiki.doleno.mhd.models.TestResultResponse;
import id.ac.stiki.doleno.mhd.models.User;
import id.ac.stiki.doleno.mhd.tools.GlobalTools;
import id.ac.stiki.doleno.mhd.tools.Session;
import id.ac.stiki.doleno.mhd.ui.ConfirmationDialog;
import id.ac.stiki.doleno.mhd.ui.LoadingDialog;
import com.github.ybq.android.spinkit.SpinKitView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//This is the main prototype of fragmenting

public class TestResultFragment extends Fragment {

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
    private LinearLayout llTestResultPercentageList, llTestQuestionList;
    private SpinKitView skvLoadingPercentage, skvLoadingQuestion;

    public TestResultFragment(int id_test) {
        this.id_test = id_test;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_test_result, container, false);
        //getting inflater from the parameter is important to preventing a crash caused by switching between fragment too fast
        this.inflater = inflater;

        gt = new GlobalTools(this);
        context = gt.getContext();
        session = gt.getSession();
        user = gt.getUser();
        flContentBnv = gt.getContentBnv();
        flContent = gt.getContent();
        loadingDialog = gt.getLoadingDialog();
        confirmationDialog = gt.getConfirmationDialog();
//        srlRefresher = ((SwipeRefreshLayout) llList.getParent().getParent());

        llTestResultPercentageList = view.findViewById(R.id.llTestResultPercentageList);
        llTestQuestionList = view.findViewById(R.id.llTestQuestionList);
        skvLoadingPercentage = view.findViewById(R.id.skvLoadingPercentage);
        skvLoadingQuestion = view.findViewById(R.id.skvLoadingQuestion);

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
                            View vPercentage = inflater.inflate(R.layout.template_test_result_percentage, null);
                            TextView tvTestResultPercentage = vPercentage.findViewById(R.id.tvTestResultPercentageLabel);

                            double percentage = testResult.getSymptom_percentage();
                            percentage = percentage * 100;
                            percentage = Math.round(percentage);
                            percentage = percentage / 100;
                            String strLabel = testResult.getDisorder_name() + " : " + percentage + "%";
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
                        skvLoadingPercentage.setVisibility(View.GONE);
                        loadTestDetail();
                    }
                }
            }

            @Override
            public void onFailure(Call<TestResultResponse> call, Throwable t) {
                gt.showSnackbar(
                        "Terjadi kesalahan koneksi.",
                        "RETRY",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                gt.refreshFragment();
                            }
                        }).show();
                //Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadTestDetail() {
        Call<TestDetailResponse> call = gt.callApi().showTestResultDetail(id_test);
        call.enqueue(new Callback<TestDetailResponse>() {
            @Override
            public void onResponse(Call<TestDetailResponse> call, Response<TestDetailResponse> response) {
                if (response.isSuccessful()) {
                    TestDetailResponse result = response.body();
                    if (result.isOk()) {
                        int iNo = 1;
                        for (TestDetail testDetail : result.getData()) {
                            View vDetail = inflater.inflate(R.layout.template_test_result_detail, null);
                            TextView tvTestQuestionNo = vDetail.findViewById(R.id.tvTestQuestionNo);
                            TextView tvTestQuestion = vDetail.findViewById(R.id.tvTestQuestion);
                            TextView tvTestDetailChoice = vDetail.findViewById(R.id.tvTestQuestionChoice);

                            tvTestQuestionNo.setText(iNo + ". ");
                            tvTestQuestion.setText(testDetail.getQuestion());
                            if (testDetail.getChoice() == 1) {
                                tvTestDetailChoice.setText("YA");
                                textStyling(tvTestDetailChoice, R.color.colorSuccess, R.drawable.bg_round_corner_border_success);

                            } else {
                                tvTestDetailChoice.setText("TIDAK");
                                textStyling(tvTestDetailChoice, R.color.colorDanger, R.drawable.bg_round_corner_border_danger);
                            }

                            gt.addViewAnimated(llTestQuestionList, vDetail);
                            iNo++;
                        }
                        skvLoadingQuestion.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<TestDetailResponse> call, Throwable t) {
                gt.showSnackbar(
                        "Terjadi kesalahan koneksi.",
                        "RETRY",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                gt.refreshFragment();
                            }
                        }).show();
                //Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
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
