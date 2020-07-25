package com.arunseto.mhd.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.arunseto.mhd.R;
import com.arunseto.mhd.activities.ArticleActivity;
import com.arunseto.mhd.api.NewsClient;
import com.arunseto.mhd.models.News;
import com.arunseto.mhd.models.NewsArticle;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.arunseto.mhd.ui.LoadingDialog;
import com.arunseto.mhd.ui.PoppingMenu;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExploreFragment extends Fragment {

    private View view;
    private LayoutInflater inflater;
    private Context context;
    private Session session;
    private User user;
    private int flContent;
    private LinearLayout llNewsList;
    private SpinKitView skvLoading;
    private LoadingDialog loadingDialog;
    private GlobalTools gt;
    private SwipeRefreshLayout srlRefresher;
    private Button btnMore;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_explore, container, false);
        //getting inflater from the parameter is important to preventing a crash caused by switching between fragment too fast
        this.inflater = inflater;

        gt = new GlobalTools(this);
        context = gt.getContext();
        session = gt.getSession();
        user = gt.getUser();
        flContent = gt.getContent();

        btnMore = view.findViewById(R.id.btnMore);
        llNewsList = view.findViewById(R.id.llNewsList);
        skvLoading = view.findViewById(R.id.skvLoading);
        loadingDialog = new LoadingDialog(context);
        srlRefresher = ((SwipeRefreshLayout) llNewsList.getParent().getParent());

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPoppingMenu(view);
            }
        });

        srlRefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                loadNews();
                gt.refreshFragment();
            }
        });

        llNewsList.removeAllViews();
        try {
            loadNews("kesehatan mental");
        } finally {
            if (session.getEngArticle()) {
                loadNews("mental health");
            }
        }


        return view;
    }

    public void setPoppingMenu(View view) {
        PoppingMenu poppingMenu = gt.getPoppingMenu(view);
        if (session.getEngArticle()) {
            poppingMenu.addItem("Hide English articles");
        } else {
            poppingMenu.addItem("Show English articles");
        }

        poppingMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getOrder()) {
                    case 0:
                        if (session.getEngArticle()) {
                            session.engArticleOn(false);
                            gt.refreshFragment();
                        } else {
                            session.engArticleOn(true);
                        }

                        if (session.getEngArticle()) {
                            gt.refreshFragment();
                        }
                        return true;

                }
                return false;
            }
        });
        poppingMenu.show();
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//    }

    public void loadNews(String query) {
        skvLoading.setVisibility(View.VISIBLE);
        // calling news api via retrofit
        Call<News> call = NewsClient.getInstance().getApi().showNews(query);
//        Toast.makeText(context, call.request().url().toString()+"", Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News newsResult = response.body();
                if (response.isSuccessful()) {
                    if (newsResult.getArticles().size() <= 0) {
                        gt.showNotFoundPage(llNewsList);
                        return;
                    }
                    mapNews(newsResult.getArticles());
                } else {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
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

    public void mapNews(List<NewsArticle> lna) {
//        llNewsList.removeAllViews();


        //removing all contents inside news list container
        for (final NewsArticle na : lna) {
            // calling news template
            final View vArticle = inflater.inflate(R.layout.template_news, null);
            // declaring article content
            TextView tvArticleTitle = vArticle.findViewById(R.id.tvArticleTitle);
            TextView tvArticleContent = vArticle.findViewById(R.id.tvArticleContent);
            TextView tvArticleExtra = vArticle.findViewById(R.id.tvArticleExtra);
            ImageView ivArticleImage = vArticle.findViewById(R.id.ivArticleImage);

            tvArticleTitle.setText(na.getTitle());
            tvArticleContent.setText(na.getContent());
//            tvArticleExtra.setText(na.getSource().getName() + " - " + na.getPublishedAt().substring(0, 10));
            String strOutputDate = gt.formatDate(
                    "yyyy-MM-dd'T'HH:mm:ss",
                    "dd MMMM yyyy",
                    na.getPublishedAt());
            String extra = na.getSource().getName() + " - " + strOutputDate;
            tvArticleExtra.setText(extra);

            vArticle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadingDialog.show();
                    //Open article  thru fragment
//                                getFragmentManager().beginTransaction().replace(flContent,
//                                        new ArticleFragment(na)).addToBackStack("1").commit();

                    //Opening url in Browser
//                                Intent i = new Intent(Intent.ACTION_VIEW);
//                                i.setData(Uri.parse(na.getUrl()));
//                                startActivity(i);

                    //Open url in dedicated browser
                    Intent iArticle = new Intent(context, ArticleActivity.class);
                    iArticle.putExtra("articleUrl", na.getUrl());
                    startActivity(iArticle);
                    loadingDialog.dismiss();
                }
            });

            if (!na.getUrlToImage().isEmpty()) {
//                            Glide.with(context).load(na.getUrlToImage()).centerCrop().into(ivArticleImage);
                gt.loadImgUrl(na.getUrlToImage(), ivArticleImage);
            }

            gt.addViewAnimated(llNewsList, vArticle);
        }
        skvLoading.setVisibility(View.GONE);
        srlRefresher.setRefreshing(false);
    }
}
