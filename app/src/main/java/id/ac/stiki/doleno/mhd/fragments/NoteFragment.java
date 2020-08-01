package id.ac.stiki.doleno.mhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import id.ac.stiki.doleno.mhd.R;
import id.ac.stiki.doleno.mhd.api.MainClient;
import id.ac.stiki.doleno.mhd.models.DefaultResponse;
import id.ac.stiki.doleno.mhd.models.DummyListNote;
import id.ac.stiki.doleno.mhd.models.User;
import id.ac.stiki.doleno.mhd.tools.GlobalTools;
import id.ac.stiki.doleno.mhd.tools.Session;
import id.ac.stiki.doleno.mhd.ui.ConfirmationDialog;
import id.ac.stiki.doleno.mhd.ui.LoadingDialog;

import java.text.DateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//This is the main prototype of fragmenting

public class NoteFragment extends Fragment {

    private View view;
    private LayoutInflater inflater;

    private Context context;
    private Session session;
    private User user;
    private int flContentBnv, flContent;
    private GlobalTools gt;
    private ConfirmationDialog confirmationDialog;
    private LoadingDialog loadingDialog;
    private Button btnNote;
    private EditText etNoteTitle, etNoteContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_note, container, false);
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

        final DummyListNote dln = DummyListNote.getInstance();


        btnNote = view.findViewById(R.id.btnNote);
        etNoteContent = view.findViewById(R.id.etNoteContent);
        etNoteTitle = view.findViewById(R.id.etNoteTitle);

        etNoteTitle.setHint("Judul : " + gt.getCurrentDate(DateFormat.FULL));

        btnNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dln.getDln().add(new Note(1, "Test", etNote.getText().toString(), date.toString()));
                initAddNote();
            }
        });

        return view;
    }

    public void initAddNote() {

        String title = etNoteTitle.getText().toString().trim();
        //if title is empty note title is set to default format
        // ? is true
        // : is false
        title = (
                !title.equals("")
                        ? title
                        : user.getFirst_name() + "'s Note of " + gt.getCurrentDate(DateFormat.FULL)
        );
        String content = etNoteContent.getText().toString();
        if (content.isEmpty()) {
            etNoteContent.setError("Catatan tidak boleh kosong");
            etNoteContent.requestFocus();
            return;
        }
        execAddNote(title, content);

    }

    public void execAddNote(String title, String content) {
        Call<DefaultResponse> call = MainClient.getInstance().getApi()
                .addNote(
                        user.getId_user(),
                        title,
                        content,
                        gt.getCurrentTime());
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    DefaultResponse result = response.body();
                    if (result.isOk()) {
                        Toast.makeText(context, "Sukses menambah catatan", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                gt.showSnackbar(
                        "Terjadi kesalahan koneksi.",
                        "RETRY",
                        null
                ).show();
//                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
