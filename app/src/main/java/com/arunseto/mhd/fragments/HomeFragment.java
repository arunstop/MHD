package com.arunseto.mhd.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.arunseto.mhd.R;
import com.arunseto.mhd.models.Article;
import com.arunseto.mhd.models.ArticleResponse;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.models.ytmodels.Item;
import com.arunseto.mhd.models.ytmodels.Snippet;
import com.arunseto.mhd.models.ytmodels.YoutubeResponse;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.List;

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
    private int flContent;
    private ImageView ivProfilePhoto;
    private Button btnNavNote, btnNavNoteList;
    private HorizontalScrollView hsvVidContainer;
    private int countMore = 0;
    private ViewPager vpArticle;
    private DotsIndicator dotsIndicator;
    private LinearLayout llVideoList;

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
        flContent = gt.getContent();

        tvName = view.findViewById(R.id.tvName);
        ivProfilePhoto = view.findViewById(R.id.ivProfilePhoto);
        tvEmail = view.findViewById(R.id.tvEmail);
        btnNavNote = view.findViewById(R.id.btnNavNote);
        btnNavNoteList = view.findViewById(R.id.btnNavNoteList);
        hsvVidContainer = view.findViewById(R.id.hsvVidContainer);
        vpArticle = view.findViewById(R.id.vpArticle);
        dotsIndicator = view.findViewById(R.id.dotsIndicator);
        llVideoList = view.findViewById(R.id.llVideoList);

        gt.loadImgUrl(getString(R.string.dummy_img_url), ivProfilePhoto);

        String greeting = "Halo! "
                + gt.capEachWord(user.getFirst_name().toUpperCase()
                + " "
                + user.getLast_name().toUpperCase());
        tvName.setText(greeting);
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
        loadArticles();


        return view;
    }


    public void loadVids(final int maxResults, final String nextPageToken) {
        Call<YoutubeResponse> call = gt.callYoutubeApi().showVideos("mental health", maxResults, nextPageToken);
        call.enqueue(new Callback<YoutubeResponse>() {
            @Override
            public void onResponse(Call<YoutubeResponse> call, Response<YoutubeResponse> response) {
                final YoutubeResponse result = response.body();

                if (result == null) {
//                    gt.showNotFoundPage(llVideoList);
                    for (int i = 1; i <= 10; i++) {
                        View vVideo = inflater.inflate(R.layout.template_video, null);
                        gt.addViewAnimatedPop(llVideoList, vVideo);
                        vVideo.requestFocus(View.FOCUS_RIGHT);
                    }
//                    return;
                }

                for (Item item : result.getItems()) {
                    View vVideo = inflater.inflate(R.layout.template_video, null);
                    vVideo.setFocusable(true);
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
                            Intent iYoutubeVid = new Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(vidUrl)
                            );
                            startActivity(iYoutubeVid);
                        }
                    });

                    gt.addViewAnimatedPop(llVideoList, vVideo);
                    vVideo.requestFocus();
                }

                final View vMore = inflater.inflate(R.layout.template_more, null);
                gt.addViewAnimatedPop(llVideoList, vMore);

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
//                        gt.removeViewAnimated(llVideoList, vMore);
                        loadVids(5, result.getNextPageToken());
//                        for (int i = 1; i <= 5; i++) {
//                            View vVideo = inflater.inflate(R.layout.template_video, null);
//                            gt.addViewAnimatedPop(llVideoList, vVideo);
//                        }
                        countMore++;
                    }
                });

            }

            @Override
            public void onFailure(Call<YoutubeResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });

//        if (!nextPageToken.isEmpty()) {
//            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    hsvVidContainer.fullScroll(View.FOCUS_RIGHT);
//                }
//            }, maxResults * 300);
//        }
    }

    public void loadArticles() {
        Call<ArticleResponse> call = gt.callApi().showArticle();
        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                ArticleResponse result = response.body();
                vpArticle.setAdapter(paArticle(result));
                vpArticle.setPageTransformer(false, ptArticle());
                dotsIndicator.setViewPager(vpArticle);
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {

            }
        });
    }

    //Adapter for articles
    public PagerAdapter paArticle(final ArticleResponse result) {
        return new PagerAdapter() {
            List<Article> data = result.getData();

            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                //check if view is the same as object
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, final int position) {
                View vArticle = inflater.inflate(R.layout.template_article, null);
                TextView tvTitle = vArticle.findViewById(R.id.tvTitle);
                ImageView ivTitle = vArticle.findViewById(R.id.ivThumbnail);
                LinearLayout llClickable = vArticle.findViewById(R.id.llClickable);

                tvTitle.setText(data.get(position).getJudul());
                gt.loadImgUrl(data.get(position).getImg_url(), ivTitle);
                llClickable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gt.navigateFragment(
                                flContent,
                                new ArticleDetailFragment(data.get(position))
                        );
//                        Toast.makeText(context, "KEKWKWKW", Toast.LENGTH_SHORT).show();
                    }
                });
                container.addView(vArticle, 0);
                return vArticle;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        };
    }

    public ViewPager.PageTransformer ptArticle() {
        return new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                final float normalizedposition = Math.abs(Math.abs(position) - 1);
                page.setScaleX(normalizedposition / 2 + 0.5f);
                page.setScaleY(normalizedposition / 2 + 0.5f);
            }
        };
    }
}

