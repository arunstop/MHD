package com.arunseto.mhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.arunseto.mhd.R;
import com.arunseto.mhd.models.Quiz;
import com.arunseto.mhd.models.QuizResponse;
import com.arunseto.mhd.models.Test;
import com.arunseto.mhd.models.TestDetail;
import com.arunseto.mhd.models.TestDetailResponse;
import com.arunseto.mhd.models.TestResponse;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.arunseto.mhd.ui.ConfirmationDialog;
import com.arunseto.mhd.ui.LoadingDialog;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

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
    private ViewFlipper llSymptomsList;
    private SpinKitView skvLoading;
    private TextView tvPageTitle;
    private TextView append;
    private List<TestDetail> tdList;


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

        tdList = new ArrayList<>();

        llSymptomsList = view.findViewById(R.id.llSymptomsList);
        skvLoading = view.findViewById(R.id.skvLoading);
//        append = view.findViewById(R.id.append);

//        loadSymptom();
//        Call<DisorderResponse> call = gt.callApi().showDisorder(null);
//        call.enqueue(new Callback<DisorderResponse>() {
//            @Override
//            public void onResponse(Call<DisorderResponse> call, Response<DisorderResponse> response) {
//                if (response.isSuccessful()) {
//                    DisorderResponse result = response.body();
//                    if (result.isOk()) {
//                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//
//            @Override
//            public void onFailure(Call<DisorderResponse> call, Throwable t) {
//                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
//            }
//        });


        llSymptomsList.removeAllViews();
        loadQuiz(1, 0, 1);


//        Toast.makeText(context, ""+test.getLast_quiz(), Toast.LENGTH_SHORT).show();


//        btnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                gt.navigateFragment(getFragmentManager(), flContent, new DiagnoseResultFragment());
//            }
//        });

        return view;
    }

    public void loadSymptom() {
        llSymptomsList.removeAllViews();

        Call<QuizResponse> call = gt.callApi().showSymptom();
        call.enqueue(new Callback<QuizResponse>() {
            @Override
            public void onResponse(Call<QuizResponse> call, Response<QuizResponse> response) {
                if (response.isSuccessful()) {
                    QuizResponse result = response.body();
                    if (result.isOk()) {
                        for (final Quiz quiz : result.getData()) {
                            View vSymptom = inflater.inflate(R.layout.template_symptom, null);
                            TextView tvSymptomName = vSymptom.findViewById(R.id.tvSymptomName);
                            Button btnYes = vSymptom.findViewById(R.id.btnYes);
                            Button btnNo = vSymptom.findViewById(R.id.btnNo);

                            //Button behaviour
//                            btnBehaviour(btnYes, btnNo);

                            tvSymptomName.setText(quiz.getSymptom_name() + " ?");

                            llSymptomsList.addView(vSymptom);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<QuizResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadQuiz(final int idSymptomDetail, final int idSymptom, @Nullable final Integer choice) {
        skvLoading.setVisibility(View.VISIBLE);

        Call<QuizResponse> call = gt.callApi().loadQuiz(idSymptomDetail);
        call.enqueue(new Callback<QuizResponse>() {
            @Override
            public void onResponse(Call<QuizResponse> call, Response<QuizResponse> response) {

                if (response.isSuccessful()) {
                    QuizResponse result = response.body();
                    if (result.isOk()) {
                        for (final Quiz quiz : result.getData()) {
                            View vQuiz = inflater.inflate(R.layout.template_diagnose_quiz, null);
                            TextView tvQuizNo = vQuiz.findViewById(R.id.tvSymptomQuizNo);
                            TextView tvQuizName = vQuiz.findViewById(R.id.tvSymptomName);
                            Button btnYes = vQuiz.findViewById(R.id.btnYes);
                            Button btnNo = vQuiz.findViewById(R.id.btnNo);
                            Button btnPrevious = vQuiz.findViewById(R.id.btnPrevious);
//


                            String strQuizNo = "Pertanyaan ke - "
                                    + (tdList.size() + 1)//quiz number
                                    + "";
                            tvQuizNo.setText(strQuizNo);

                            String strQuizName;
                            if (quiz.getId_symptom() != idSymptom) {
                                String strYn = "\n"
                                        + "YES : " + quiz.getYes() + "\n"
                                        + "NO : " + quiz.getNo();
                                strQuizName =
                                        quiz.getId_symptom_detail()
                                                + " " + quiz.getSymptom_name() + " ?";
                                llSymptomsList.addView(vQuiz);
                                llSymptomsList.showNext();
                            } else {
                                loadQuiz(quiz.getYes(), quiz.getId_symptom(),choice);
                                pushDetailTest(
                                        quiz.getId_symptom_detail(),
                                        choice
                                );
                                strQuizName = "sama " + quiz.getSymptom_name() + " ?";
                            }

                            tvQuizName.setText(strQuizName);
                            if (tdList.size() == 0) {
                                btnPrevious.setVisibility(View.INVISIBLE);
                            }
                            setButtonOption(quiz, llSymptomsList.getChildCount(), vQuiz, btnYes, btnNo, btnPrevious);


                            skvLoading.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<QuizResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setButtonOption(final Quiz quiz, final int index, final View vSymptom, Button btnYes, Button btnNo, Button btnPrevious) {
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadQuiz(
                        quiz.getYes(),
                        quiz.getId_symptom(),
                        1
                );
                pushDetailTest(
                        quiz.getId_symptom_detail(),
                        1
                );
                if (quiz.getYes() == 0) {
                    llSymptomsList.removeAllViews();
//                    Toast.makeText(context, tdList.size() + "", Toast.LENGTH_SHORT).show();
                    addTest(quiz.getId_disorder());
                }
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadQuiz(
                        quiz.getNo(),
                        quiz.getId_symptom(),
                        0
                );
                pushDetailTest(
                        quiz.getId_symptom_detail(),
                        0
                );
                if (quiz.getNo() == 0) {
                    llSymptomsList.removeAllViews();
//                    Toast.makeText(context, tdList.size() + "", Toast.LENGTH_SHORT).show();
                    addTest(quiz.getId_disorder());
                }
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llSymptomsList.showPrevious();
                llSymptomsList.removeView(vSymptom);
                tdList.remove((tdList.size() - 1));
            }
        });
    }

    public void pushDetailTest(int id_symptom_detail, int choice) {
        tdList.add(new TestDetail(0, id_symptom_detail, 0, choice));
    }

    public void addTest(final int id_disorder_result) {
        loadingDialog.show();
        Call<TestResponse> call = gt.callApi().addTest(gt.getUser().getId_user(), id_disorder_result);
        call.enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                if (response.isSuccessful()) {
                    TestResponse result = response.body();
                    if (result.isOk()) {
                        for (Test test : result.getData()) {
                            for (TestDetail testDetail : tdList) {
                                addDetailTest(
                                        testDetail.getId_symptom_detail(),
                                        test.getId_tes(),
                                        testDetail.getChoice()
                                );
                            }
                            gt.popFragment(getFragmentManager());
                            gt.navigateFragment(getFragmentManager(), flContent, new DiagnoseResultFragment());
//                            Toast.makeText(context, result.getMessage() + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                    loadingDialog.dismiss();
                } else {
//                    Toast.makeText(context, response.message() + "", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, "id_user " + user.getId_user() + "\n" + "result " + id_disorder_result, Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
            }
        });
//        Toast.makeText(context, call.request().url()+"", Toast.LENGTH_SHORT).show();
    }

    public void addDetailTest(int id_symptom_detail, int id_tes, int choice) {
        Call<TestDetailResponse> call = gt.callApi()
                .addDetailTest(
                        id_symptom_detail,
                        id_tes,
                        choice
                );
//        append.append("("+id_symptom_detail+", "+test.getId_tes()+", "+choice+", "+index+"),\n");
        call.enqueue(new Callback<TestDetailResponse>() {
            @Override
            public void onResponse(Call<TestDetailResponse> call, Response<TestDetailResponse> response) {

            }

            @Override
            public void onFailure(Call<TestDetailResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
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
