package com.example.android.a7gag_news;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {
    // we will need to download all of the data in the main thread ui thread initially if it is not available
    // or we will download with async task
    Intent intent;
    ImageView image;
    Button button;

    public void moveOn(View view)
    {
        intent = new Intent(this , MainActivity.class);
        // start transition animation
        Pair[] pairs = new Pair[2];
        pairs[0]= new Pair<View , String>(image , "imageTrans");
        pairs[1]= new Pair<View , String>(button , "btnTrans");
       // pairs[2]= new Pair<View , String>(descText , "descTrans");
        //pairs[3]= new Pair<View , String>(layout1 , "layoutTrans");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this ,pairs );
        startActivity(intent , options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        image = (ImageView)findViewById(R.id.image_1);
        button = (Button)findViewById(R.id.button);
    }
}
