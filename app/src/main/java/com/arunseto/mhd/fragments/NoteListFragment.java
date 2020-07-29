package com.arunseto.mhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.arunseto.mhd.R;
import com.arunseto.mhd.api.MainClient;
import com.arunseto.mhd.models.DefaultResponse;
import com.arunseto.mhd.models.Note;
import com.arunseto.mhd.models.NoteResponse;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.arunseto.mhd.ui.ConfirmationDialog;
import com.arunseto.mhd.ui.LoadingDialog;
import com.arunseto.mhd.ui.PoppingMenu;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//This is the main prototype of fragmenting

public class NoteListFragment extends Fragment {

    private View view;
    private LayoutInflater inflater;

    private Context context;
    private Session session;
    private User user;
    private int flContentBnv, flContent;
    private GlobalTools gt;
    private ConfirmationDialog confirmationDialog;
    private LoadingDialog loadingDialog;
    private LinearLayout llNoteList;
    private Button btnMore;
    private SpinKitView skvLoading;
    private SwipeRefreshLayout srlRefresher;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_note_list, container, false);
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

        llNoteList = view.findViewById(R.id.llNoteList);
        skvLoading = view.findViewById(R.id.skvLoading);
        btnMore = view.findViewById(R.id.btnMore);
        srlRefresher = ((SwipeRefreshLayout) llNoteList.getParent().getParent());

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PoppingMenu poppingMenu = gt.getPoppingMenu(view);
                poppingMenu.addItem("Refresh");
                poppingMenu.addItem("Delete All");

                poppingMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getOrder()) {
                            case 0:
                                Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                                return true;
                            case 1:
                                Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        return false;
                    }
                });

                poppingMenu.show();
            }
        });

        srlRefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                loadNotes();
                gt.refreshFragment();
            }
        });

        loadNotes();


//
        return view;
    }

    public void loadNotes() {
        skvLoading.setVisibility(View.VISIBLE);
        Call<NoteResponse> call = MainClient.getInstance().getApi().showNotes(user.getId_user());
        call.enqueue(new Callback<NoteResponse>() {
            @Override
            public void onResponse(Call<NoteResponse> call, Response<NoteResponse> response) {
                if (response.isSuccessful()) {
                    NoteResponse result = response.body();
                    if (result.getData() == null) {
                        gt.showNotFoundPage(llNoteList);
                        skvLoading.setVisibility(View.GONE);
                        srlRefresher.setRefreshing(false);
                        return;
                    } else if (result.isOk()) {
//                        if (result.getData()==null){
//                            gt.showNotFoundPage(llNoteList);
//                            return;
//                        }
                        mapNote(result.getData());
                        skvLoading.setVisibility(View.GONE);
                        srlRefresher.setRefreshing(false);
//                        Toast.makeText(context, result.getMessage()+"", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<NoteResponse> call, Throwable t) {
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

    public void mapNote(List<Note> ln) {
        llNoteList.removeAllViews();
        for (final Note note : ln) {
            final View vNote = inflater.inflate(R.layout.template_note, null);
            TextView tvNoteTitle = vNote.findViewById(R.id.tvNoteTitle);
            TextView tvNoteContent = vNote.findViewById(R.id.tvNoteContent);
            TextView tvNoteDate = vNote.findViewById(R.id.tvNoteDate);

            tvNoteTitle.setText(note.getTitle());
            tvNoteContent.setText(note.getContent());
            String strOutputDate = gt.formatDate(
                    "yyyy-MM-dd HH:mm:ss",
                    "EEEE, dd MMMM yyyy, HH:mm",
                    note.getDate());

            tvNoteDate.setText(strOutputDate);

            vNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gt.navigateFragment(gt.getContent(), new NoteDetailFragment(note));
                }
            });

            vNote.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    confirmationDialog = new ConfirmationDialog(context);
                    confirmationDialog.setTitle("Apakah Anda yakin ingin menghapus catatan?");
                    confirmationDialog.setYLabel("HAPUS");
                    confirmationDialog.setNLabel("BATAL");
                    confirmationDialog.show();
                    confirmationDialog.getBtnYes().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            deleteNote(note.getId(), vNote);
                        }
                    });
                    return false;
                }
            });

            gt.addViewAnimated(llNoteList, vNote);
        }
    }

    public void deleteNote(int id, final View vNote) {
        Call<DefaultResponse> call = MainClient.getInstance().getApi().deleteNote(id);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    DefaultResponse result = response.body();
                    if (result.isOk()) {
                        gt.removeViewAnimated(llNoteList, vNote);
                        confirmationDialog.dismiss();
                        Toast.makeText(context, result.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, response.message() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                gt.showSnackbar("Terjadi kesalahan koneksi.", "RETRY", null).show();
                //Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
