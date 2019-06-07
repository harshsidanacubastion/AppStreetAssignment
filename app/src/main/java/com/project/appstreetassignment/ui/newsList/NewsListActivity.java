package com.project.appstreetassignment.ui.newsList;

import android.app.ActivityOptions;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.project.appstreetassignment.MyApplication;
import com.project.appstreetassignment.R;
import com.project.appstreetassignment.data.model.Article;
import com.project.appstreetassignment.ui.detailNews.DetailedNews;
import com.project.appstreetassignment.utils.ApiResponse;
import com.project.appstreetassignment.utils.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NewsListActivity extends AppCompatActivity implements NewsListAdapter.onClick {


    @Inject
    ViewModelFactory viewModelFactory;

    NewsListViewModel viewModel;
    public static List<Article> articleList = new ArrayList<>();

    String country = "";
    String category = "";
    boolean isOnline = false;
    NewsListAdapter listAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (getIntent().getStringExtra("OnlineStatus").equalsIgnoreCase("Online")) {
            isOnline = true;
        }
        listAdapter = new NewsListAdapter(articleList, NewsListActivity.this, isOnline, NewsListActivity.this);
        recyclerView = (RecyclerView) findViewById(R.id.newsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listAdapter);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
        recyclerView.setHasFixedSize(true);

        ((MyApplication) getApplicationContext()).getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsListViewModel.class);
        viewModel.fetchApiResponse().observe((LifecycleOwner) this, this::consumeApiResponse);
        viewModel.fetchDbResponse().observe((LifecycleOwner) this, this::consumeDbResponse);


        country = getIntent().getStringExtra("Country");
        category = getIntent().getStringExtra("Category");


        if (isOnline)
            viewModel.fetchDataFromApi(country, category, 1);
        else
            viewModel.fetchDataFromDb(country, category);

    }

    private void consumeDbResponse(Article article) {
        if (article == null) {
            Toast.makeText(this, "Error in database update", Toast.LENGTH_SHORT).show();
        } else {

        }
    }

    private void consumeApiResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
//                progressDialog.show();
                break;

            case SUCCESS:


                articleList = apiResponse.data.getArticleList();
                listAdapter.setArticleList(articleList);
                listAdapter.notifyDataSetChanged();

                if (isOnline)
                    for (Article article : apiResponse.data.getArticleList()) {
                        viewModel.updateInDb(article, country, category);
                    }

                break;

            case ERROR:
//                progressDialog.dismiss();
                Log.i("Harsh Sidana", apiResponse.error.toString());
                Toast.makeText(NewsListActivity.this, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    @Override
    public void onItemClick(int position,Bundle bundle) {

        Intent i = new Intent(getApplicationContext(), DetailedNews.class);
        i.putExtra("position", position);
        startActivity(i,bundle);


    }


//    public void sharedElementTransition(View view)
//    {
//        Pair[] pair=new Pair[1];
//        pair[1]=new Pair<View,String>(,"Image_shared");
//
//
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(this,pair);
//        }
// Intent i =new Intent(NewsListActivity.this,DetailedNews.class);
//    startActivity(i,options.toBundle());
//
// }
}
