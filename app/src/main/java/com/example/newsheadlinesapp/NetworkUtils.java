package com.example.newsheadlinesapp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    // Base URL for Books API.
    private static final String ARTICLES_BASE_URL = "https://newsapi.org/v2/top-headlines?country=us";
    // Parameter that limits search results.
    private static final String MAX_RESULTS = "maxResults";
    // Parameter to filter by print type.
    private static final String PRINT_TYPE = "printType";

    static String getHeadlineInfo(){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String articleJSONString = null;
        try {
            // use URIBuilder to build URI
            Uri builtURI = Uri.parse(ARTICLES_BASE_URL).buildUpon()
                    .appendQueryParameter("apiKey", "7e459eb1e1ab4e2399fc6ca47720528e")
                    .build();
            // convert URI to request string
            URL requestURL = new URL(builtURI.toString());
            //URL testURL = new URL("https://jsonplaceholder.typicode.com/todos/1");
            Log.d(LOG_TAG, requestURL.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            //urlConnection = (HttpURLConnection) testURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            Log.d(LOG_TAG, String.valueOf(urlConnection.getResponseCode()));
            Log.d(LOG_TAG, urlConnection.getResponseMessage());

            // set up connection response
            InputStream inputStream = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                builder.append(line);
                builder.append("\n");
                Log.d(LOG_TAG, line);
            }
            if (builder.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }
            // convert stringbuilder to string and store in bookJSONString
            articleJSONString = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // close connection and bufferedreader
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (reader != null){
                try{
                    reader.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        // log json string value
        Log.d(LOG_TAG, articleJSONString);

        return articleJSONString;
    }
}
