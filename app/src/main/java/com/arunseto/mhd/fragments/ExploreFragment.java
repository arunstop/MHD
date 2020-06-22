package com.arunseto.mhd.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_explore, container, false);
        //getting inflater from the parameter is important to preventing a crash caused by switching between fragment too fast
        this.inflater = inflater;

        gt = new GlobalTools(getActivity());
        context = gt.getContext();
        session = gt.getSession();
        user = gt.getUser();
        flContent = gt.getContent();

        llNewsList = view.findViewById(R.id.llNewsList);
        skvLoading = view.findViewById(R.id.skvLoading);
        loadingDialog = new LoadingDialog(context);
        srlRefresher = ((SwipeRefreshLayout) llNewsList.getParent().getParent());

        srlRefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                loadNews();
                gt.refreshFragment(getFragmentManager(), ExploreFragment.this);
            }
        });

        loadNews();


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public void loadNews() {
        skvLoading.setVisibility(View.VISIBLE);
        // calling news api via retrofit
        Call<News> call = NewsClient.getInstance().getApi().showNews();
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News newsResult = response.body();
                if (response.isSuccessful()) {
                    mapNews(newsResult.getArticles());
                } else {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }

        });

    }

    public void mapNews(List<NewsArticle> lna) {
        llNewsList.removeAllViews();
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
            tvArticleExtra.setText(na.getSource().getName() + " - " + na.getPublishedAt().substring(0, 10));

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
