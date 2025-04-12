package com.example.quicknews;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quicknews.adapters.CountryAdapter;
import com.example.quicknews.models.Country;
import com.example.quicknews.utils.Data;

import java.util.ArrayList;
import java.util.List;

public class CountryListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText searchEditText;
    private CountryAdapter adapter;
    private List<Country> countryList;
    private List<Country> filteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_list);

        recyclerView = findViewById(R.id.countriesRecyclerView);
        searchEditText = findViewById(R.id.searchEditText);

        countryList = Data.getCountries();
        filteredList = new ArrayList<>(countryList);
        Toolbar toolbar = findViewById(R.id.countriesList_toolbar);
        toolbar.setTitle("Countries List");
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);

        adapter = new CountryAdapter(filteredList, country -> {
            Intent intent = new Intent(CountryListActivity.this, NewsListActivity.class);
            Log.e("country code: ", country.getCode());

            intent.putExtra("countryCode", country.getCode());
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCountries(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void filterCountries(String query) {
        filteredList.clear();
        for (Country country : countryList) {
            if (country.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(country);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
