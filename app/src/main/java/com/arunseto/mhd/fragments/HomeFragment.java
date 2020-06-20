package com.arunseto.mhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arunseto.mhd.R;
import com.arunseto.mhd.activities.LoginActivity;
import com.arunseto.mhd.api.MainClient;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private View view;
    private LayoutInflater inflater;
    private Context context;
    private Session session;
    private TextView tvName, tvEmail;
    private GlobalTools gt;
    private Button btnNavNote,btnNavNoteList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
         //getting inflater from the parameter is important to preventing a crash caused by switching between fragment too fast
        this.inflater = inflater;

        gt = new GlobalTools(getActivity());
        context = gt.getContext();
        session = gt.getSession();

        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        btnNavNote = view.findViewById(R.id.btnNavNote);
        btnNavNoteList = view.findViewById(R.id.btnNavNoteList);

        tvName.setText(session.getUser().getFirst_name().toUpperCase() + " " + session.getUser().getLast_name().toUpperCase());
        tvEmail.setText(session.getUser().getEmail());

        btnNavNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gt.navigateFragment(getFragmentManager(), gt.getContent(), new NoteFragment());
            }
        });
        btnNavNoteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gt.navigateFragment(getFragmentManager(), gt.getContent(), new NoteListFragment());
            }
        });

        return view;
    }


}
