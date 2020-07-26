package com.arunseto.mhd.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arunseto.mhd.R;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.models.ytmodels.Item;
import com.arunseto.mhd.models.ytmodels.Snippet;
import com.arunseto.mhd.models.ytmodels.YoutubeResponse;
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
    private User user;
    private TextView tvName, tvEmail;
    private GlobalTools gt;
    private Button btnNavNote, btnNavNoteList;
    private HorizontalScrollView hsvVidContainer;
    private LinearLayout llVideoList;
    private int countMore = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        //getting inflater from the parameter is important to preventing a crash caused by switching between fragment too fast
        this.inflater = inflater;

        gt = new GlobalTools(this);
        context = gt.getContext();
        session = gt.getSession();
        user = gt.getUser();

        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        btnNavNote = view.findViewById(R.id.btnNavNote);
        btnNavNoteList = view.findViewById(R.id.btnNavNoteList);
        hsvVidContainer = view.findViewById(R.id.hsvVidContainer);
        llVideoList = view.findViewById(R.id.llVideoList);

        tvName.setText(user.getFirst_name().toUpperCase() + " " + user.getLast_name().toUpperCase());
        tvEmail.setText(user.getEmail());

        btnNavNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gt.navigateFragment(gt.getContent(), new NoteFragment());
            }
        });
        btnNavNoteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gt.navigateFragment(gt.getContent(), new NoteListFragment());
            }
        });

        loadVids(10, "");

        return view;
    }


    public void loadVids(final int maxResults, final String nextPageToken) {
        Call<YoutubeResponse> call = gt.callYoutubeApi().showVideos("mental health", maxResults, nextPageToken);
        call.enqueue(new Callback<YoutubeResponse>() {
            @Override
            public void onResponse(Call<YoutubeResponse> call, Response<YoutubeResponse> response) {
                final YoutubeResponse result = response.body();
//                et.setText(call.request().url()+"");
                if (result == null) {
                    gt.showNotFoundPage(llVideoList);
                    return;
                }
                for (Item item : result.getItems()) {
                    View vVideo = inflater.inflate(R.layout.template_video, null);
                    ImageView ivVidThumbnail = vVideo.findViewById(R.id.ivVidThumbnail);
                    TextView tvVidTitle = vVideo.findViewById(R.id.tvVidTitle);
                    TextView tvVidChannel = vVideo.findViewById(R.id.tvVidChannel);
                    TextView tvVidDate = vVideo.findViewById(R.id.tvVidDate);
//                    Toast.makeText(context, item.getSnippet().getTitle() + "", Toast.LENGTH_SHORT).show();

                    final String vidUrl = "https://www.youtube.com/watch?v=" + item.getId().getVideoId();
                    Snippet sVideo = item.getSnippet();

                    gt.loadImgUrl(sVideo.getThumbnails().getMedium().getUrl() + "", ivVidThumbnail);
                    tvVidTitle.setText(item.getSnippet().getTitle());
                    tvVidChannel.setText(sVideo.getChannelTitle());
                    tvVidDate.setText(sVideo.getPublishedAt());

                    vVideo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent iYoutubeVid= new Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(vidUrl)
                            );
                            startActivity(iYoutubeVid);
                        }
                    });

                    gt.addViewAnimated(llVideoList, vVideo);
                }
                final View vMore = inflater.inflate(R.layout.template_more, null);
                gt.addViewAnimated(llVideoList, vMore);

                vMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (countMore == 2) {
                            Intent iYoutubeSearch = new Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://www.youtube.com/results?search_query=kesehatan+mental")
                            );
                            startActivity(iYoutubeSearch);
                            return;
                        }
                        gt.removeViewAnimated(llVideoList, vMore);
                        loadVids(5, result.getNextPageToken());
                        countMore++;
                    }
                });

            }

            @Override
            public void onFailure(Call<YoutubeResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
        if (!nextPageToken.isEmpty()) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    hsvVidContainer.fullScroll(View.FOCUS_RIGHT);
                }
            }, maxResults * (maxResults * 0));

        }
    }
}
