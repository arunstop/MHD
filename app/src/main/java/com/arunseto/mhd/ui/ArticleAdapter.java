package com.arunseto.mhd.ui;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.arunseto.mhd.R;
import com.arunseto.mhd.models.Article;

import java.util.List;

public class ArticleAdapter extends PagerAdapter {

    LayoutInflater inflater;
    List<Article> data;

    public ArticleAdapter(LayoutInflater inflater, List<Article> data) {
        this.inflater = inflater;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View vArticle = inflater.inflate(R.layout.template_article, null);
        TextView tvTitle = vArticle.findViewById(R.id.tvTitle);
        ImageView ivTitle = vArticle.findViewById(R.id.ivThumbnail);
        tvTitle.setText(data.get(position).getJudul());
        container.addView(vArticle, 0);
        return vArticle;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}