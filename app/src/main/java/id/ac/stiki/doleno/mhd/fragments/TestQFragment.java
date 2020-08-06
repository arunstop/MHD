package id.ac.stiki.doleno.mhd.fragments;

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

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.ac.stiki.doleno.mhd.R;
import id.ac.stiki.doleno.mhd.models.Question;
import id.ac.stiki.doleno.mhd.models.QuestionResponse;
import id.ac.stiki.doleno.mhd.models.Test;
import id.ac.stiki.doleno.mhd.models.TestDetail;
import id.ac.stiki.doleno.mhd.models.TestDetailResponse;
import id.ac.stiki.doleno.mhd.models.TestResponse;
import id.ac.stiki.doleno.mhd.models.User;
import id.ac.stiki.doleno.mhd.tools.GlobalTools;
import id.ac.stiki.doleno.mhd.tools.Session;
import id.ac.stiki.doleno.mhd.ui.ConfirmationDialog;
import id.ac.stiki.doleno.mhd.ui.LoadingDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//This is the main prototype of fragmenting

public class TestQFragment extends Fragment {

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
    private ViewFlipper vfSymptomsList;
    private SpinKitView skvLoading;
    private TextView tvPageTitle;
    private TextView append;
    private List<TestDetail> tdList;
    private int iNo = 1;
    private HashMap<Integer, Question> mapQ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_test_q, container, false);
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

        tdList = new ArrayList<>();
        mapQ = new HashMap<>();

        vfSymptomsList = view.findViewById(R.id.vfSymptomsList);
        skvLoading = view.findViewById(R.id.skvLoading);

        vfSymptomsList.removeAllViews();
//        loadQuiz(1, 0, 1);
        loadQuizAll();

        return view;
    }

    public void loadQuizAll() {
        Call<QuestionResponse> call = gt.callApi().loadQuizAll();
        call.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if (response.isSuccessful()) {
                    QuestionResponse result = response.body();
                    if (result.isOk()) {
                        List<Question> lq = result.getData();
                        for (Question q : lq) {
                            mapQ.put(q.getId_symptom_detail(), q);
                        }
                        loadQuiz(mapQ.get(1).getId_symptom_detail(), 0, 1);
//                        Toast.makeText(context, mapQ.size() + "", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {
                gt.showSnackbar("Terjadi kesalahan koneksi.", "RETRY", null).show();
            }
        });
    }

    public void loadQuiz(final int idSymptomDetail, final int idSymptom, @Nullable final Integer choice) {
        Question question = mapQ.get(idSymptomDetail);
        skvLoading.setVisibility(View.VISIBLE);
        View vQuiz = inflater.inflate(R.layout.template_test_question, null);
        TextView tvQuizNo = vQuiz.findViewById(R.id.tvQuestionNo);
        TextView tvQuizName = vQuiz.findViewById(R.id.tvQuestionLabel);
        Button btnYes = vQuiz.findViewById(R.id.btnAnswerYes);
        Button btnNo = vQuiz.findViewById(R.id.btnAnswerNo);
        Button btnPrevious = vQuiz.findViewById(R.id.btnPrevious);
//
        String strQuizNo = "Pertanyaan ke - "
                + iNo //question number
                + "";
        tvQuizNo.setText(strQuizNo);

        String strQuizName;
        if (question.getId_symptom() != idSymptom) {
            String strYn = "\n"
                    + "YES : " + question.getYes() + "\n"
                    + "NO : " + question.getNo();
            strQuizName = question.getQuestion();
            vfSymptomsList.addView(vQuiz);
            vfSymptomsList.showNext();
            iNo++;
        } else {
            loadQuiz(question.getYes(), question.getId_symptom(), choice);
            pushDetail(
                    question.getId_symptom_detail(),
                    choice
            );
            strQuizName = "sama " + question.getSymptom_name() + " ?";
        }

        tvQuizName.setText(strQuizName);
        if (tdList.size() == 0) {
            btnPrevious.setVisibility(View.INVISIBLE);
        }
        setButtonOption(question, vfSymptomsList.getChildCount(), vQuiz, btnYes, btnNo, btnPrevious);


        skvLoading.setVisibility(View.GONE);
    }

    public void setButtonOption(final Question question, final int index, final View vSymptom, Button btnYes, Button btnNo, Button btnPrevious) {
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushDetail(
                        question.getId_symptom_detail(),
                        1
                );
                if (question.getYes() == 0) {
//                    vfSymptomsList.removeAllViews();
//                    Toast.makeText(context, tdList.size() + "", Toast.LENGTH_SHORT).show();
                    addTest(question.getId_disorder());
                    return;
                }
                loadQuiz(
                        question.getYes(),
                        question.getId_symptom(),
                        1
                );
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushDetail(
                        question.getId_symptom_detail(),
                        0
                );
                if (question.getNo() == 0) {
//                    vfSymptomsList.removeAllViews();
//                    Toast.makeText(context, tdList.size() + "", Toast.LENGTH_SHORT).show();
                    addTest(question.getId_disorder());
                    return;
                }
                loadQuiz(
                        question.getNo(),
                        question.getId_symptom(),
                        0
                );
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vfSymptomsList.showPrevious();
                vfSymptomsList.removeView(vSymptom);
                tdList.remove((tdList.size() - 1));
                iNo--;
            }
        });
    }

    public void pushDetail(int id_symptom_detail, int choice) {
        tdList.add(new TestDetail(0, id_symptom_detail, 0, choice, "", ""));
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
                                addTestDetail(
                                        testDetail.getId_symptom_detail(),
                                        test.getId_tes(),
                                        testDetail.getChoice()
                                );
                            }
                            gt.popFragment();
                            gt.navigateFragment(flContent, new TestResultFragment(test.getId_tes()));
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
                loadingDialog.dismiss();
            }
        });
//        Toast.makeText(context, call.request().url()+"", Toast.LENGTH_SHORT).show();
    }

    public void addTestDetail(int id_symptom_detail, int id_tes, int choice) {
        Call<TestDetailResponse> call = gt.callApi()
                .addTestDetail(
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
                gt.showSnackbar("Terjadi kesalahan koneksi.", "RETRY", null).show();
                //Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
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
