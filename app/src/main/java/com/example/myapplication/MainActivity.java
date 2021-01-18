package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String  News_url= "http://newsapi.org/v2/top-headlines?country=in&apiKey=8ca4accee6204001b4e3288238914448";
    ArrayList<Event> NewsList = new ArrayList<Event>();
    public static final String LOG_TAG = MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new NewsAsyncTask().execute(News_url);

    }

    @SuppressLint("StaticFieldLeak")
    public class NewsAsyncTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... urls) {
            URL url;
            String jsonResponse="";
                    try {
                        url = new URL(News_url);
                            jsonResponse = makeHttpRequest(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        Log.e(LOG_TAG, "Error handling Json Response", e);
                    }
             return jsonResponse;
        }
        protected void onPostExecute(String jsonResponse) {
            try {
                JSONObject Response = new JSONObject(jsonResponse);
                JSONArray articles = Response.getJSONArray("articles");


                for(int i=0;i<articles.length();i++)
                {
                    JSONObject article = articles.getJSONObject(i);

               String title = article.getString("title");
                String  description= article.getString("description");
                String author = article.getString("author");
                String url=article.getString("url");
                 NewsList.add(new Event(title,description,author,url));

                     // Create a new {@link Event} object
                    Log.i(LOG_TAG,title);

                }
            } catch (JSONException e) {
              e.printStackTrace();}
            ListView newsListView = findViewById(R.id.lvmain);
            NewsAdapter adapter = new NewsAdapter(
                    MainActivity.this, NewsList);
            newsListView.setAdapter(adapter);
            Log.d("recived",jsonResponse);
        }
    }

    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if(url==null)
            return jsonResponse;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Chrome");
            urlConnection.setRequestMethod("GET");
         urlConnection.connect();
         inputStream=urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
             Log.i(LOG_TAG,jsonResponse);
        } catch (IOException e) {
            // TODO: Handle the exception
            e.printStackTrace();

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


}