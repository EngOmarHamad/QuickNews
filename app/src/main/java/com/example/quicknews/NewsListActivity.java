package com.example.quicknews;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quicknews.adapters.NewsAdapter;
import com.example.quicknews.models.Article;
import com.example.quicknews.models.Response;
import com.example.quicknews.network.NewsApiService;
import com.example.quicknews.network.RetrofitInstance;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
public class NewsListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView errorMessage;
    private NewsAdapter newsAdapter;
    private String countryCode;
    private ArrayList<Article> articleList = new ArrayList<>();
    private TabLayout tabLayout;
    private List<String> categories = Arrays.asList(
          "All",  "business", "entertainment", "health", "science", "sports", "technology"
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        tabLayout = findViewById(R.id.tab_layout);

        // إضافة التابات
        for (String category : categories) {
            tabLayout.addTab(tabLayout.newTab().setText(category));
        }

        // الاستماع لتغيير التابات
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String  selectedCategory = Objects.requireNonNull(tab.getText()).toString();
                if (selectedCategory.equals("All")){loadNews("");}else {
                loadNews(selectedCategory);}
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // عند إلغاء تحديد التاب، لا شيء نحتاج لفعله هنا
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // عند إعادة تحديد التاب، لا شيء نحتاج لفعله هنا
            }
        });
        // استقبال الكود من الدولة
        countryCode = getIntent().getStringExtra("countryCode");
        if (countryCode == null || countryCode.isEmpty()) {
            countryCode = "us";
        }

        Toolbar toolbar = findViewById(R.id.news_list_toolbar);
        toolbar.setTitle("News List");
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.news_recycler);
        progressBar = findViewById(R.id.progress_bar);
        errorMessage = findViewById(R.id.error_message);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newsAdapter = new NewsAdapter(this, articleList, article -> {
            if (article != null) {
                try {
                    Intent intent = new Intent(this, ArticleDetailsActivity.class);
                    intent.putExtra("title", article.getTitle());
                    intent.putExtra("author", article.getAuthor());
                    intent.putExtra("image", article.getUrlToImage());
                    intent.putExtra("content", article.getContent());
                    intent.putExtra("date", article.getPublishedAt());
                    intent.putExtra("source", article.getSource().getName());
                    intent.putExtra("url", article.getUrl());
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("NewsListActivity", "Error opening article: " + e.getMessage());
                }
            }
        });

        recyclerView.setAdapter(newsAdapter);

        loadNews("");  // تحميل الأخبار عند فتح النشاط
    }

    private void loadNews(String category) {
        progressBar.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.GONE);  // إخفاء رسالة الخطأ

        // استخدم Retrofit لتحميل الأخبار
        NewsApiService newsApiService = RetrofitInstance.getApiService();
        Call<Response> call = newsApiService.getTopHeadlines(countryCode,category, "4582772f362e407b9210629773a6674f"); // ضع مفتاح الـ API هنا

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<Article> articles = response.body().getArticles();
                    if (articles.isEmpty()){showError( "No Data");}
                    // تأكد من طريقة الوصول إلى البيانات
                    newsAdapter.setArticles(articles);  // تحديث الـ adapter مع المقالات الجديدة
                } else {
                    showError("Failed to load news");
                }
            }


            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                showError("Error loading news: " + t.getMessage());
            }
        });
    }

    private void showError(String message) {
        errorMessage.setVisibility(View.VISIBLE);
        errorMessage.setText(message);
    }
}
