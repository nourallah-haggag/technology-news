package com.example.android.a7gag_news;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewActivity extends AppCompatActivity {
    WebView  webView;

    boolean loadingFinished = true;
    boolean redirect = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        String url = getIntent().getStringExtra("url");

        // web view
         webView = (WebView)findViewById(R.id.web_view);
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar2);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;
                view.loadUrl(urlNewString);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap facIcon) {
                loadingFinished = false;
                //SHOW LOADING IF IT ISNT ALREADY VISIBLE
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if(!redirect){
                    loadingFinished = true;


                }

                if(loadingFinished && !redirect){
                    //HIDE LOADING IT HAS FINISHED
                    progressBar.setVisibility(View.INVISIBLE);
                } else{
                    redirect = false;
                }

            }
        });


        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);


    }
}
