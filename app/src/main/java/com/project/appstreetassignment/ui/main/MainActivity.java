package com.project.appstreetassignment.ui.main;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.project.appstreetassignment.R;
import com.project.appstreetassignment.ui.detailNews.DetailedNews;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


//    public void sharedElementTransition(View view)
//    {
//        Pair[] pair=new Pair[3];
//        pair[1]=new Pair<View,String>("","");
//        pair[1]=new Pair<View,String>("","");
//
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(this,pair);
//        }
// Intent i =new Intent(MainActivity.this,DetailedNews.class);
//    startActivity(i,options.toBundle());

// }
}
