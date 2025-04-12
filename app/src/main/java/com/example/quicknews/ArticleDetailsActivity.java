package com.example.quicknews;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

public class ArticleDetailsActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView titleText, authorText, contentText, dateText, sourceText;
    private Button openInWebBtn;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        imageView = findViewById(R.id.detail_image);
        titleText = findViewById(R.id.detail_title);
        authorText = findViewById(R.id.detail_author);
        contentText = findViewById(R.id.detail_content);
        dateText = findViewById(R.id.detail_date);
        sourceText = findViewById(R.id.detail_source);
        openInWebBtn = findViewById(R.id.open_in_web_button);
        Toolbar toolbar = findViewById(R.id.article_details_toolbar);
        toolbar.setTitle("Article Details");
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");
        String imageUrl = intent.getStringExtra("image");
        String content = intent.getStringExtra("content");
        String date = intent.getStringExtra("date");
        String source = intent.getStringExtra("source");
        url = intent.getStringExtra("url");
        titleText.setText(title);
        authorText.setText(author != null ? author : "Unknown Author");
        contentText.setText(content != null ? content : "No content available.");
        dateText.setText(date);
        sourceText.setText(source);

        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(imageView);
        openInWebBtn.setOnClickListener(v -> {
            Intent intentWeb = new Intent(ArticleDetailsActivity.this, ArticleWebView.class);
            intentWeb.putExtra("url", url);
            startActivity(intentWeb);
        });
    }
}
