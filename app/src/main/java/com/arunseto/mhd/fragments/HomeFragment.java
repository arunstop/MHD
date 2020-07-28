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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.arunseto.mhd.R;
import com.arunseto.mhd.models.Article;
import com.arunseto.mhd.models.ArticleResponse;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.models.Video;
import com.arunseto.mhd.models.VideoResponse;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.github.ybq.android.spinkit.SpinKitView;
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
    private int offset = 0;
    private SpinKitView skvLoadingArticle, skvLoadingVideo;

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
        skvLoadingArticle = view.findViewById(R.id.skvLoadingArticle);
        vpArticle = view.findViewById(R.id.vpArticle);
        dotsIndicator = view.findViewById(R.id.dotsIndicator);

        skvLoadingVideo = view.findViewById(R.id.skvLoadingVideo);
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

        loadArticles();
        loadVids(10);

        return view;
    }

    public void loadArticles() {
        skvLoadingArticle.setVisibility(View.VISIBLE);
        Call<ArticleResponse> call = gt.callApi().showArticles(4);
        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                ArticleResponse result = response.body();
                vpArticle.setAdapter(paArticle(result));
                vpArticle.setPageTransformer(false, ptArticle());
                dotsIndicator.setViewPager(vpArticle);
                skvLoadingArticle.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadVids(final int limit) {
        //showing first 10 data at first
        // limit = limit of output data
        // offset = index start of data selection
        skvLoadingVideo.setVisibility(View.VISIBLE);
        Call<VideoResponse> call = gt.callApi().showVideos(limit, offset);
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                final VideoResponse result = response.body();

                if (result == null) {
                    gt.showNotFoundPage(llVideoList);
//                    for (int i = 1; i <= 10; i++) {
//                        View vVideo = inflater.inflate(R.layout.template_video, null);
//                        gt.addViewAnimatedPop(llVideoList, vVideo);
//                        vVideo.requestFocus(View.FOCUS_RIGHT);
//                    }
                    return;
                }

                for (final Video video : result.getData()) {
                    View vVideo = inflater.inflate(R.layout.template_video, null);
                    vVideo.setFocusable(true);
                    ImageView ivVidThumbnail = vVideo.findViewById(R.id.ivVidThumbnail);
                    TextView tvVidTitle = vVideo.findViewById(R.id.tvVidTitle);
                    TextView tvVidChannel = vVideo.findViewById(R.id.tvVidChannel);
                    TextView tvVidDate = vVideo.findViewById(R.id.tvVidDate);

                    String strOutputDate = gt.formatDate(
                            "yyyy-MM-dd'T'HH:mm:ss'Z'",
                            "EEEE, dd MMMM yyyy, HH:mm",
                            video.getPublished_at());

                    gt.loadImgUrl(video.getThumbnail() + "", ivVidThumbnail);
                    tvVidTitle.setText(video.getTitle());
                    tvVidChannel.setText(video.getChannel());
                    tvVidDate.setText(strOutputDate);

                    vVideo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent iYoutubeVid = new Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(video.getUrl())
                            );
                            startActivity(iYoutubeVid);
                        }
                    });

                    gt.addViewAnimatedPop(llVideoList, vVideo);
//                    ivVidThumbnail.requestFocus();
                }

                final View vMore = inflater.inflate(R.layout.template_more, null);
                gt.addViewAnimatedPop(llVideoList, vMore);

                vMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (countMore == 3) {
                            Intent iYoutubeSearch = new Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://www.youtube.com/results?search_query=kesehatan+mental")
                            );
                            startActivity(iYoutubeSearch);
                            return;
                        }
                        gt.removeViewAnimated(llVideoList, vMore);
                        int newLimit = 5;
                        loadVids(newLimit);
//                        for (int i = 1; i <= 5; i++) {
//                            View vVideo = inflater.inflate(R.layout.template_video, null);
//                            gt.addViewAnimatedPop(llVideoList, vVideo);
//                        }
                        countMore++;
                    }
                });
                skvLoadingVideo.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });

        if (offset != 0) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    hsvVidContainer.fullScroll(View.FOCUS_RIGHT);
                }
            }, offset * 200);
        }
        offset += limit;
//        Toast.makeText(context, hsvVidContainer.getWidth()+"\n"+hsvVidContainer.get(), Toast.LENGTH_SHORT).show();
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
                gt.addViewAnimatedPop(container, vArticle,true);
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

