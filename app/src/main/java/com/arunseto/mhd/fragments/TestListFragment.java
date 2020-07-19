package com.arunseto.mhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.arunseto.mhd.R;
import com.arunseto.mhd.models.Test;
import com.arunseto.mhd.models.TestResponse;
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

public class TestListFragment extends Fragment {

    private View view;
    private LayoutInflater inflater;
    private Context context;
    private Session session;
    private User user;
    private int flContentBnv, flContent;
    private GlobalTools gt;
    private ConfirmationDialog confirmationDialog;
    private LoadingDialog loadingDialog;
    private SwipeButton sbNavTestExecute;
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

        gt = new GlobalTools(this);
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

        loadQuestion();

        srlRefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                loadNotes();
                gt.refreshFragment();
            }
        });

        return view;
    }

    public void loadQuestion() {
        llTestResultList.removeAllViews();
        skvLoading.setVisibility(View.VISIBLE);

        Call<TestResponse> call = gt.callApi().showTest(gt.getUser().getId_user());
        call.enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                if (response.isSuccessful()) {
                    TestResponse result = response.body();
                    if (result.isOk()) {
                        int i = result.getData().size();
                        for (final Test test : result.getData()) {
                            View vTestResult = inflater.inflate(R.layout.template_test_result, null);
                            TextView tvTestLabel = vTestResult.findViewById(R.id.tvTestLabel);
                            TextView tvTestDate = vTestResult.findViewById(R.id.tvTestDate);

                            tvTestLabel.setText("Test #" + (i));
                            tvTestDate.setText(gt.formatDate(
                                    "yyyy-MM-dd HH:mm:ss",
                                    "dd MMMM yyyy, HH:mm",
                                    test.getDate()));
                            vTestResult.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    gt.navigateFragment( flContent, new TestResultFragment(test.getId_tes()));
                                }
                            });
                            gt.addViewAnimated(llTestResultList, vTestResult);
                            i--;
                        }
                    }
                    skvLoading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
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
}
