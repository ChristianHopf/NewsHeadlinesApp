package com.example.newsheadlinesapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView headlineRV;
    private List<HeadlineModel> headlineModelList;
    private HeadlineAdapter mAdapter;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        headlineRV = findViewById(R.id.headlineRV);
        mDb = AppDatabase.getInstance(getApplicationContext());
        // set adapter and layout manager
        mAdapter = new HeadlineAdapter(this);
        headlineRV.setLayoutManager(new LinearLayoutManager(this));
        // attach adapter to recyclerview
        headlineRV.setAdapter(mAdapter);
    }

    /**
     * onClick function for buttonGetHeadlines:
     * Gets top articles from NewsAPI which will populate headlineModelArrayList, then
     * builds the RecyclerView with a card for each article
     * @param view
     */
    public void getHeadlines(View view){
        new FetchHeadline(this, headlineModelList).execute();
        // in roomcodelab, retrieveTasks is called in onResume and when a person is deleted
        // with the swipe listener. here, the recyclerview is populated when buttonGetHeadlines
        // is clicked, so retrieveTasks should be called here
        retrieveTasks();
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveTasks();
    }

    private void retrieveTasks() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<HeadlineModel> headlines = mDb.headlineDao().loadAllHeadlines();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setTasks(headlines);
                    }
                });
            }
        });
    }

    /**
     * onClick function for the button in each card:
     * Opens a dialog with the article's information
     * @param view
     */
    public void articleDialog(View view){
        // query db for article with the same title
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        String title = tvTitle.getText().toString();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final HeadlineModel thisHeadline = mDb.headlineDao().loadHeadlineByTitle(title);
                // open dialog with that article's information
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.article_dialog,null);
                TextView dTitle = mView.findViewById(R.id.dialogTitle);
                TextView dAuthor = mView.findViewById(R.id.dialogAuthor);
                TextView dDesc = mView.findViewById(R.id.dialogDescription);
                dTitle.setText(thisHeadline.getTitle());
                dAuthor.setText(thisHeadline.getAuthor());
                dDesc.setText(thisHeadline.getDescription());
                builder.setView(mView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    /**
     * Delete all headlines and reset the adapter's list of headlines
     * @param view
     */
    public void clearHeadlines(View view) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<HeadlineModel> headlines = mDb.headlineDao().loadAllHeadlines();
                for(HeadlineModel model : headlines){
                    mDb.headlineDao().deleteHeadline(model);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setTasks(headlines);
                    }
                });
            }
        });
    }
}