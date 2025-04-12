package com.example.quicknews;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.LinearLayout;
import  androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ArticleWebView  extends AppCompatActivity {
    private WebView webView;
    private ProgressBar progressBar;
    private LinearLayout progressBarParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_web_view);

        webView = findViewById(R.id.web_view);
        progressBarParent = findViewById(R.id.progressBarParent);
        progressBar = findViewById(R.id.progressBar);
        Toolbar toolbar = findViewById(R.id.articles_web_view_toolbar);
        toolbar.setTitle("Article Web View");
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);


        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // عند الانتهاء من التحميل
                progressBarParent.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        });

        // استلام الرابط
        String url = getIntent().getStringExtra("url");
        Log.e("url", url);
        if (url != null) {
            try {
                webView.loadUrl(url);
            } catch (Exception e) {
                Log.e("Exception url ", e.getMessage());            }

        }
    }
}
