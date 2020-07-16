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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.arunseto.mhd.R;
import com.arunseto.mhd.models.TestResult;
import com.arunseto.mhd.models.TestResultResponse;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.arunseto.mhd.ui.ConfirmationDialog;
import com.arunseto.mhd.ui.LoadingDialog;
import com.ebanx.swipebtn.SwipeButton;
import com.github.ybq.android.spinkit.SpinKitView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//This is the main prototype of fragmenting

public class TestResultListFragment extends Fragment {

    private View view;
    private LayoutInflater inflater;
    private Context context;
    private Session session;
    private User user;
    private int flContentBnv, flContent;
    private GlobalTools gt;
    private ConfirmationDialog confirmationDialog;
    private LoadingDialog loadingDialog;
    private SwipeButton sbNavDiagnoseExecute;
    private LinearLayout llTestResultList;
    private Button btnMore;
    private SpinKitView skvLoading;
    private SwipeRefreshLayout srlRefresher;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_test_result_list, container, false);
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

        llTestResultList = view.findViewById(R.id.llTestResultList);
        skvLoading = view.findViewById(R.id.skvLoading);
        btnMore = view.findViewById(R.id.btnMore);
        srlRefresher = ((SwipeRefreshLayout) llTestResultList.getParent().getParent());

        loadTest();

        srlRefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                loadNotes();
                gt.refreshFragment(getFragmentManager(), TestResultListFragment.this);
            }
        });

        return view;
    }

    public void loadTest() {
        llTestResultList.removeAllViews();
        skvLoading.setVisibility(View.VISIBLE);

        Call<TestResultResponse> call = gt.callApi().showTest(gt.getUser().getId_user());
        call.enqueue(new Callback<TestResultResponse>() {
            @Override
            public void onResponse(Call<TestResultResponse> call, Response<TestResultResponse> response) {
                if (response.isSuccessful()) {
                    TestResultResponse result = response.body();
                    if (result.isOk()) {
                        int i = 1;
                        for (TestResult testResult : result.getData()) {
                            View vTestResult = inflater.inflate(R.layout.template_test_result, null);
                            TextView tvTestResultLabel = vTestResult.findViewById(R.id.tvTestResultLabel);
                            TextView tvTestResultDate = vTestResult.findViewById(R.id.tvTestResultDate);

                            tvTestResultDate.setText("Test #" + i);
                            tvTestResultDate.setText(gt.formatDate(
                                    "yyyy-MM-dd HH:mm:ss",
                                    "dd MMMM yyyy, HH:mm",
                                    testResult.getDate()));

                            llTestResultList.addView(vTestResult);
                            i++;
                        }
                    }
                    skvLoading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TestResultResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
