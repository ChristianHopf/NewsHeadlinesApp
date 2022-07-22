package com.example.newsheadlinesapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class FetchHeadline extends AsyncTask<String, Void, String> {
    private WeakReference<TextView> mTitleText;
    private WeakReference<TextView> mAuthorText;
    private WeakReference<TextView> mDescriptionText;
    private List<HeadlineModel> headlineModelList;
    private Context context;
    private AppDatabase mDb;

    FetchHeadline(Context context, List<HeadlineModel> headlineModelArrayList) {
        /*this.mTitleText = new WeakReference<>(titleText);
        this.mAuthorText = new WeakReference<>(authorText);
        this.mDescriptionText = new WeakReference<>(descriptionText);*/
        this.context = context;
        this.headlineModelList = headlineModelList;
        mDb = AppDatabase.getInstance(context);
    }

    /**
     * Invoke getHeadlineInfo in the NetworkUtils class to retrieve and build the JSON of
     * article information from NewsAPI, then pass the JSON to onPostExecute
     * @param strings
     * @return
     */
    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getHeadlineInfo(context);
    }

    /**
     * Turn the JSON from doInBackground into a JSONObject, then get the title, author, and
     * description from each article and create a HeadlineModel for each article.
     * Populates the ArrayList headlineModelArrayList
     * @param s
     */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            int numberOfArticles = (int) jsonObject.get("totalResults");
            JSONArray articlesArray = jsonObject.getJSONArray("articles");
            int i = 0;
            String title = null;
            String author = null;
            String description = null;
            while (i < articlesArray.length() &&
                    (author == null && title == null && description == null)) {
                // Get the current item information.
                JSONObject article = articlesArray.getJSONObject(i);

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    title = article.getString("title");
                    author = article.getString("author");
                    description = article.getString("description");
                    if (title != null && author != null && description != null) {
                        Log.d(FetchHeadline.class.getSimpleName(), "New headline");
                        // add article information to a headlinemodel,
                        // and append the headlinemodel to the arraylist
                        HeadlineModel model = new HeadlineModel(title, author, description);
                        //headlineModelArrayList.add(model);
                        // instead of populating arraylist, populate room db
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                mDb.headlineDao().insertHeadline(model);
                            }
                        });
                        title = null;
                        author = null;
                        description = null;
                    } else {
                        /*mTitleText.get().setText(R.string.no_results);
                        mAuthorText.get().setText(R.string.no_results);
                        mDescriptionText.get().setText(R.string.no_results);*/
                    }
                } catch (Exception e) {
                    // If onPostExecute does not receive a proper JSON string,
                    // update the UI to show failed results.
                    mTitleText.get().setText(R.string.no_results);
                    mAuthorText.get().setText("");
                    title = null;
                    author = null;
                    description = null;
                    e.printStackTrace();
                }

                // Move to the next item.
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
