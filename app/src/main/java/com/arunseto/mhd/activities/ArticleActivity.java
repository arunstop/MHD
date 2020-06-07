package com.arunseto.mhd.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.arunseto.mhd.R;
import com.arunseto.mhd.storage.Session;
import com.github.ybq.android.spinkit.SpinKitView;

public class ArticleActivity extends AppCompatActivity {
    private Context context;
    private Session session;
    private Bundle bundle;
    private String articleUrl;

    private WebView wvArticle;
    private SpinKitView skvLoading;
    private Button btnMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_article);
        context = this;

        session = Session.getInstance(context);
        bundle = getIntent().getExtras();
        articleUrl = bundle.getString("articleUrl");

        wvArticle = findViewById(R.id.wvArticle);
        skvLoading = findViewById(R.id.skvLoading);
        btnMore = findViewById(R.id.btnMore);

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,view );
                //add(groupId, itemId, order, title);
                popupMenu.getMenu().add(0,0,0,"Buka di Browser");
                popupMenu.getMenu().add(0,1,1,"Bagikan");

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getOrder()){
                            case 0:
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(articleUrl));
                                startActivity(i);
                                return true;
                            case 1:
                                Toast.makeText(context, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        wvArticle.loadUrl(articleUrl);
        // Force links and redirects to open in the WebView instead of in a browser
        wvArticle.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                skvLoading.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });

    }

    @Override
    public void onBackPressed() {
        // Check if the key event was the Back button and if there's history
        if (wvArticle.canGoBack()) {
            wvArticle.goBack();
        }
        else {
            // If it wasn't the Back key or there's no web page history, bubble up to the default
            // system behavior (probably exit the activity)
            super.onBackPressed();
        }


    }

    public void navBack(View view){
        finish();
    }
}
