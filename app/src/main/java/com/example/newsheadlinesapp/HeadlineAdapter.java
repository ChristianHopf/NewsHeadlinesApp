package com.example.newsheadlinesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HeadlineAdapter extends RecyclerView.Adapter<HeadlineAdapter.Viewholder>{
    private Context context;
    private ArrayList<HeadlineModel> headlineModelArrayList;

    public HeadlineAdapter(Context context, ArrayList<HeadlineModel> headlineModelArrayList) {
        this.context = context;
        this.headlineModelArrayList = headlineModelArrayList;
    }

    @NonNull
    @Override
    public HeadlineAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadlineAdapter.Viewholder holder, int position) {
        // bind data to each card layout
        HeadlineModel model = headlineModelArrayList.get(position);
        holder.headlineTV.setText(model.getTitle());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView headlineTV;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            headlineTV = itemView.findViewById(R.id.tvTitle);
        }
    }
}
