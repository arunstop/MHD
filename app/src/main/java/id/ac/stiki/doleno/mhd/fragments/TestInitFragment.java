package id.ac.stiki.doleno.mhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import id.ac.stiki.doleno.mhd.R;
import id.ac.stiki.doleno.mhd.models.User;
import id.ac.stiki.doleno.mhd.tools.GlobalTools;
import id.ac.stiki.doleno.mhd.tools.Session;
import id.ac.stiki.doleno.mhd.ui.ConfirmationDialog;
import id.ac.stiki.doleno.mhd.ui.LoadingDialog;
import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;

//This is the main prototype of fragmenting

public class TestInitFragment extends Fragment {

    private View view;
    private LayoutInflater inflater;
    private Context context;
    private Session session;
    private User user;
    private int flContentBnv, flContent;
    private GlobalTools gt;
    private ConfirmationDialog confirmationDialog;
    private LoadingDialog loadingDialog;
    private Button btnNavTestList, btnNavTestExecuteStart;
    private SwipeButton sbNavTestExecute;
    private TextView tvTestInitLabel;
    private Boolean startTest = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_test_init, container, false);
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

        tvTestInitLabel = view.findViewById(R.id.tvTestInitLabel);
        sbNavTestExecute = view.findViewById(R.id.sbNavTestExecute);
        btnNavTestList = view.findViewById(R.id.btnNavTestResultList);
        btnNavTestExecuteStart = view.findViewById(R.id.btnNavTestQStart);

        if (!startTest) {
            tvTestInitLabel.setVisibility(View.GONE);
            sbNavTestExecute.setVisibility(View.GONE);
        } else {
            btnNavTestExecuteStart.setVisibility(View.GONE);
        }

        initActions();

        return view;
    }

    public void initActions() {

        btnNavTestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gt.navigateFragment(flContent, new TestListFragment());
            }
        });

        sbNavTestExecute.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                if (active) {
//                    addTest();
//                    getFragmentManager().beginTransaction().
                    gt.popFragment();
                    gt.navigateFragment(flContent, new TestQFragment());
                } else {
//                    gt.getSession().endTestSession();
//                    String str = gt.getSession().getTestSession().getId_tes()
//                            + "\n"
//                            + gt.getSession().getTestSession().getId_user();
//                    Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnNavTestExecuteStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTest = true;
                tvTestInitLabel.setVisibility(View.VISIBLE);
                sbNavTestExecute.setVisibility(View.VISIBLE);
                btnNavTestExecuteStart.setVisibility(View.GONE);
            }
        });
    }
}
