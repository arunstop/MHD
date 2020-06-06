package com.arunseto.mhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arunseto.mhd.R;
import com.arunseto.mhd.api.NewsClient;
import com.arunseto.mhd.models.News;
import com.arunseto.mhd.models.NewsArticle;
import com.arunseto.mhd.storage.Session;
import com.github.ybq.android.spinkit.SpinKitView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExploreFragment extends Fragment {

    private View view;
    private Context context;
    private Session session;
    private int flContentSub;
    private LinearLayout llNewsList;
    private SpinKitView skvLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_explore, container, false);
        context = getActivity();
        session = Session.getInstance(context);
        flContentSub = R.id.flContentSub;
        llNewsList = view.findViewById(R.id.llNewsList);
        skvLoading = view.findViewById(R.id.skvLoading);

        loadNews();

        return view;
    }

    public void loadNews() {
        //removing all contents inside news list container
        llNewsList.removeAllViews();

        // calling news api via retrofit
        Call<News> call = NewsClient.getInstance().getApi().getNews();
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News newsResult = response.body();
                if (response.isSuccessful()) {
                    int i = 1;
                    for (final NewsArticle na : newsResult.getArticles()) {
                        // calling news template
                        View vArticle = getLayoutInflater().inflate(R.layout.template_news, null);
                        // declaring article content
                        TextView tvArticleTitle = vArticle.findViewById(R.id.tvArticleTitle);
                        TextView tvArticleContent = vArticle.findViewById(R.id.tvArticleContent);
                        TextView tvArticleExtra = vArticle.findViewById(R.id.tvArticleExtra);
                        ImageView ivArticleImage = vArticle.findViewById(R.id.ivArticleImage);

                        tvArticleTitle.setText(na.getTitle());
                        tvArticleContent.setText(na.getContent());
                        tvArticleExtra.setText(na.getSource().getName() + " - " + na.getPublishedAt().substring(0,10));

                        vArticle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getFragmentManager().beginTransaction().replace(flContentSub,
                                        new ArticleFragment(na)).addToBackStack("1").commit();
                            }
                        });

                        if (!na.getUrlToImage().isEmpty()) {
                            Picasso.with(context).load(na.getUrlToImage()).fit().into(ivArticleImage);
                        }



                        i++;
                        llNewsList.addView(vArticle);
                    }
//                    Toast.makeText(context, newsResult.getTotalResults() + "", Toast.LENGTH_SHORT).show();
                    skvLoading.setVisibility(View.GONE);
                } else {
                    Toast.makeText(context, "KEKW", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
