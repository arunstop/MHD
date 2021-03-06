package com.arunseto.mhd.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class News {
    @SerializedName("articles")
    List<NewsArticle> articles;
    @SerializedName("status")
    private String status;
    @SerializedName("totalResults")
    private float totalResults;


    public News(String status, float totalResults, List<NewsArticle> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public float getTotalResults() {
        return totalResults;
    }

    public List<NewsArticle> getArticles() {
        return articles;
    }
}
