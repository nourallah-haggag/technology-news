package com.example.android.a7gag_news;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    // create the list components
    static ListView listView;
    //  ArrayList<String> titlesList;
    //  ArrayList<String> byList;
    // ArrayList<String> datesList;
    static CustomAdapter customAdapter;
    ArrayAdapter arrayAdapter;
    static ArrayList<String> idsList;
    static ArrayList<String> urlsList;
    Intent intent;
    static ArrayList<ListItem> storiesList;
    static ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize list components
        listView = (ListView) findViewById(R.id.listView);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        //  titlesList = new ArrayList<>();
        //  byList = new ArrayList<>();
        idsList = new ArrayList<>();
        //  datesList = new ArrayList<>();
        storiesList = new ArrayList<>();

        urlsList = new ArrayList<>();
       /* titlesList.add("kjndavljnvlav");
        titlesList.add("kjndavljnvlav");
        titlesList.add("kjndavljnvlav");
        byList.add("kjndavljnvlav");
        byList.add("kjndavljnvlav");
        byList.add("kjndavljnvlav");
        dateslist.add("kjndavljnvlav");
        dateslist.add("kjndavljnvlav");
        dateslist.add("kjndavljnvlav");*/
        customAdapter = new CustomAdapter(this, storiesList);
        //   arrayAdapter = new ArrayAdapter(this , android.R.layout.simple_list_item_1 , titlesList);
        listView.setAdapter(customAdapter);
        DownloadStories downloadStories = new DownloadStories();
        downloadStories.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url", urlsList.get(i));
                startActivity(intent);

            }
        });


    }
}

class DownloadItem extends AsyncTask<String , Void , String>
{

    @Override
    protected String doInBackground(String... urls) {
        String result = ""; // store the html content -> JSON
        URL url;
        HttpURLConnection urlConnection;
        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            int data = reader.read();
            while (data != -1) {
                char current = (char) data;
                result += current;
                data = reader.read();
            }  Log.i("story" , result);

            return result;
        }catch (Exception e)
        {
            e.printStackTrace();
            return "failed";
        }

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        JSONObject storyJson = null;
        try {
            storyJson = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if(storyJson.getString("title") !=null && storyJson.getString("by") !=null && storyJson.getString("url")!=null && storyJson.getString("time")!=null) {
                       /* titlesList.add(storyJson.getString("title"));
                        byList.add(storyJson.getString("by"));
                        datesList.add(storyJson.getString("time"));*/
                MainActivity.storiesList.add(new ListItem(storyJson.getString("title") ,storyJson.getString("by") ,storyJson.getString("time")) );
                MainActivity.urlsList.add(storyJson.getString("url"));
                MainActivity.customAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}







class DownloadStories extends AsyncTask<String , Void , String> {
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);


    }



    @Override
    protected String doInBackground(String... urls) {
        // code to download html data from passed urls
        String result = ""; // store the html content -> JSON
        URL url;
        HttpURLConnection urlConnection;
        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            int data = reader.read();

            while (data != -1) {
                char current = (char) data;
                result += current;
                data = reader.read();
            }
            return result;


        } catch (Exception e) {
            e.printStackTrace();
            return "failed to get data";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

       // DownloadStories downloadStories = new DownloadStories();
        try {
           // String storyIds = result;
            Log.i("story ids" , result);

            //get those ids indiviually to be able to use the
            JSONArray idsJson = new JSONArray(result);

            //storiesDB.execSQL("DELETE FROM storiesDB");
            for(int i =0 ; i<idsJson.length() ; i++)
            {
                String id = idsJson.getString(i);
                MainActivity.idsList.add(id);
                DownloadItem dStory =  new DownloadItem();
                dStory.execute("https://hacker-news.firebaseio.com/v0/item/"+id+".json?print=pretty");



                //   String sql = "INSERT INTO storiesTB ( title , url ) VALUES ( ? , ? , ? )";
             /*   SQLiteStatement statement = storiesDB.compileStatement(sql);
                statement.bindString(1 ,storyJson.getString("title") );
                statement.bindString(2 ,storyJson.getString("url") );
                statement.execute();*/
                MainActivity.progressBar.setVisibility(View.INVISIBLE);

            }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}




