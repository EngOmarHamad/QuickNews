package com.example.quicknews.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quicknews.R;
import com.example.quicknews.models.Country;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Country country);
    }

    private final List<Country> countryList;
    private final OnItemClickListener listener;

    public CountryAdapter(List<Country> countryList, OnItemClickListener listener) {
        this.countryList = countryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_country, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country country = countryList.get(position);
        holder.bind(country, listener);
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    static class CountryViewHolder extends RecyclerView.ViewHolder {
        TextView countryNameText;
        Button viewNewsButton;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            countryNameText = itemView.findViewById(R.id.countryNameText);
            viewNewsButton = itemView.findViewById(R.id.viewNewsButton);
        }

        public void bind(Country country, OnItemClickListener listener) {
            countryNameText.setText(country.getName());
            viewNewsButton.setOnClickListener(v -> listener.onItemClick(country));
        }
    }
}
