package com.example.newsheadlinesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView headlineRV;
    private ArrayList<HeadlineModel> headlineModelArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        headlineRV = findViewById(R.id.headlineRV);
        // get headlines
        // populate arraylist of headlines
    }

    /**
     * onClick function for buttonGetHeadlines:
     * Gets top articles from NewsAPI which will populate headlineModelArrayList, then
     * builds the RecyclerView with a card for each article
     * @param view
     */
    public void getHeadlines(View view){
        new FetchHeadline(this, headlineModelArrayList).execute();
    }

    /**
     * onClick function for the button in each card:
     * Opens a dialog with the article's information
     * @param view
     */
    public void articleDialog(View view){

    }
}