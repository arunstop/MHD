package id.ac.stiki.doleno.mhd.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import id.ac.stiki.doleno.mhd.R;

import id.ac.stiki.doleno.mhd.R;
import id.ac.stiki.doleno.mhd.models.User;
import id.ac.stiki.doleno.mhd.tools.GlobalTools;
import id.ac.stiki.doleno.mhd.tools.Session;
import id.ac.stiki.doleno.mhd.ui.PoppingMenu;
import com.github.ybq.android.spinkit.SpinKitView;

public class ArticleActivity extends AppCompatActivity {
    private Context context;
    private Session session;
    private User user;
    private Bundle bundle;
    private String articleUrl;
    private GlobalTools gt;
    private WebView wvArticle;
    private SpinKitView skvLoading;
    private Button btnMore;
    private SwipeRefreshLayout srlRefresher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        gt = new GlobalTools(this);
        context = gt.getContext();
        session = gt.getSession();
        bundle = getIntent().getExtras();
        articleUrl = bundle.getString("articleUrl");

        wvArticle = findViewById(R.id.wvArticle);
        skvLoading = findViewById(R.id.skvLoading);
        btnMore = findViewById(R.id.btnMore);
        srlRefresher = findViewById(R.id.srlRefresher);

        srlRefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                wvArticle.reload();
                srlRefresher.setRefreshing(false);
            }
        });

        wvArticle.loadUrl(articleUrl);
        // Force links and redirects to open in the WebView instead of in a browser
        wvArticle.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                skvLoading.setVisibility(View.GONE);
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPoppingMenu(view);
            }
        });

    }

    public void setPoppingMenu(View view) {
        PoppingMenu poppingMenu = gt.getPoppingMenu(view);
        poppingMenu.addItem("Refresh");
        poppingMenu.addItem("Artikel awal");
        poppingMenu.addItem("Buka di Browser");
        poppingMenu.addItem("Bagikan");

        poppingMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getOrder()) {
                    case 0:
                        wvArticle.reload();
                        return true;
                    case 1:
                        wvArticle.loadUrl(articleUrl);
                        return true;
                    case 2:
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(articleUrl));
                        startActivity(i);
                        return true;
                    case 3:
                        gt.showSharingIntent(articleUrl + "");
                        return true;

                }
                return false;
            }
        });
        poppingMenu.show();
    }

    @Override
    public void onBackPressed() {
        // Check if the key event was the Back button and if there's history
        if (wvArticle.canGoBack()) {
            wvArticle.goBack();
        } else {
            // If it wasn't the Back key or there's no web page history, bubble up to the default
            // system behavior (probably exit the activity)
            super.onBackPressed();
        }


    }

    public void navBack(View view) {
        finish();
    }
}
