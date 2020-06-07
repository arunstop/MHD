package com.arunseto.mhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arunseto.mhd.R;
import com.arunseto.mhd.models.NewsArticle;
import com.arunseto.mhd.storage.Session;
import com.squareup.picasso.Picasso;


public class ArticleFragment extends Fragment {

    private View view;
    private Context context;
    private Session session;
    private NewsArticle na;
    private WebView wvArticle;

    public ArticleFragment(NewsArticle na) {
        this.na = na;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_article, container, false);

        context = getActivity();
        session = Session.getInstance(context);

        wvArticle = view.findViewById(R.id.wvArticle);

//        TextView tvArticleTitle = view.findViewById(R.id.tvArticleTitle);
//        TextView tvArticleContent = view.findViewById(R.id.tvArticleContent);
//        TextView tvArticleExtra = view.findViewById(R.id.tvArticleExtra);
//        ImageView ivArticleImage = view.findViewById(R.id.ivArticleImage);
//
//        tvArticleTitle.setText(na.getTitle());
//        tvArticleContent.setText(na.getContent());
//        tvArticleExtra.setText(na.getSource().getName() + " - " + na.getPublishedAt().substring(0, 10));

//        if (!na.getUrlToImage().isEmpty()) {
//            Picasso.with(context).load(na.getUrlToImage()).fit().into(ivArticleImage);
//        }

        loadArticle();

        return view;
    }

    public void loadArticle() {
        wvArticle.loadUrl(na.getUrl());
        // Force links and redirects to open in the WebView instead of in a browser
        wvArticle.setWebViewClient(new WebViewClient());
    }


}
