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
import com.arunseto.mhd.models.Article;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.arunseto.mhd.ui.ConfirmationDialog;
import com.arunseto.mhd.ui.LoadingDialog;

//This is the main prototype of fragmenting

public class ArticleDetailFragment extends Fragment {

    private View view;
    private LayoutInflater inflater;

    private Context context;
    private Session session;
    private User user;
    private int flContentBnv, flContent;
    private GlobalTools gt;
    private ConfirmationDialog confirmationDialog;
    private LoadingDialog loadingDialog;
    private Article article;
    private ImageView ivArticleThumbnail;
    private TextView tvArticleTitle, tvArticleDate, tvArticleContent;

    public ArticleDetailFragment(Article article) {
        this.article = article;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_article_detail, container, false);
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

        ivArticleThumbnail = view.findViewById(R.id.ivArticleThumbnail);
        tvArticleTitle = view.findViewById(R.id.tvArticleTitle);
        tvArticleDate = view.findViewById(R.id.tvArticleDate);
        tvArticleContent = view.findViewById(R.id.tvArticleContent);

        gt.loadImgUrl(article.getImg_url(), ivArticleThumbnail);
        tvArticleTitle.setText(article.getJudul());
        tvArticleDate.setText(article.getCreated_at());
        tvArticleContent.setText(article.getIsi());


        return view;
    }
}
