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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arunseto.mhd.R;
import com.arunseto.mhd.activities.ArticleActivity;
import com.arunseto.mhd.api.NewsClient;
import com.arunseto.mhd.models.News;
import com.arunseto.mhd.models.NewsArticle;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.arunseto.mhd.ui.LoadingDialog;
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
    private LoadingDialog loadingDialog;
    private GlobalTools gt;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_explore, container, false);

        gt = new GlobalTools(getActivity());
        context = gt.getContext();
        session = gt.getSession();
        flContentSub = gt.getContentSub();

        llNewsList = view.findViewById(R.id.llNewsList);
        skvLoading = view.findViewById(R.id.skvLoading);
        loadingDialog = new LoadingDialog(context);


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
                        tvArticleExtra.setText(na.getSource().getName() + " - " + na.getPublishedAt().substring(0, 10));

                        vArticle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                loadingDialog.show();
                                //Open article  thru fragment
//                                getFragmentManager().beginTransaction().replace(flContentSub,
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
