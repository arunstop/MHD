package com.arunseto.mhd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    public ArticleFragment(NewsArticle na) {
        this.na = na;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_article, container, false);

        context = getActivity();
        session = Session.getInstance(context);

        TextView tvArticleTitle = view.findViewById(R.id.tvArticleTitle);
        TextView tvArticleContent = view.findViewById(R.id.tvArticleContent);
        TextView tvArticleExtra = view.findViewById(R.id.tvArticleExtra);
        ImageView ivArticleImage = view.findViewById(R.id.ivArticleImage);

        tvArticleTitle.setText(na.getTitle());
        tvArticleContent.setText(na.getContent());
        tvArticleExtra.setText(na.getSource().getName() + " - " + na.getPublishedAt().substring(0, 10));

        if (!na.getUrlToImage().isEmpty()) {
            Picasso.with(context).load(na.getUrlToImage()).fit().into(ivArticleImage);
        }

        return view;
    }


}
