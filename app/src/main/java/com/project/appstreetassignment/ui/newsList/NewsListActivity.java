package com.project.appstreetassignment.ui.newsList;
import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.project.appstreetassignment.MyApplication;
import com.project.appstreetassignment.R;
import com.project.appstreetassignment.data.model.Article;
import com.project.appstreetassignment.ui.detailNews.DetailedNews;
import com.project.appstreetassignment.utils.ApiResponse;
import com.project.appstreetassignment.utils.ViewModelFactory;
import java.util.ArrayList;
import java.util.List;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import javax.inject.Inject;

public class NewsListActivity extends AppCompatActivity implements NewsListAdapter.onClick {


    @Inject
    ViewModelFactory viewModelFactory;

    NewsListViewModel viewModel;
    public static List<Article> articleList = new ArrayList<>();

    String country = "";
    String category = "";
    boolean isOnline = false;
    boolean isLoading = false;
    NewsListAdapter listAdapter;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    TextView newsFeed;
    Typeface valueFonts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (getIntent().getStringExtra("OnlineStatus").equalsIgnoreCase("Online")) {
            isOnline = true;
        }
        newsFeed = (TextView) findViewById(R.id.newsFeed);
        newsFeed.setPaintFlags(newsFeed.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        valueFonts = Typeface.createFromAsset(getAssets(), "fonts/UniversLTStd-LightCn.otf");
        newsFeed.setTypeface(valueFonts);
        listAdapter = new NewsListAdapter(articleList, NewsListActivity.this, isOnline, NewsListActivity.this);
        recyclerView = (RecyclerView) findViewById(R.id.newsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listAdapter);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
        recyclerView.setHasFixedSize(true);
        initScrollListener();

        ((MyApplication) getApplicationContext()).getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsListViewModel.class);
        viewModel.fetchApiResponse().observe((LifecycleOwner) this, this::consumeApiResponse);
        viewModel.fetchDbResponse().observe((LifecycleOwner) this, this::consumeDbResponse);

        progressDialog = new ProgressDialog(this, R.style.ProgressTheme);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        country = getIntent().getStringExtra("Country");
        category = getIntent().getStringExtra("Category");


        if (isOnline)
            viewModel.fetchDataFromApi(country, category, 1);
        else
            viewModel.fetchDataFromDb(country, category);
        articleList.clear();

    }
    private void initScrollListener() {

        final int[] currentPage = {1};
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    final int visibleThreshold = 2;

                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    int currentTotalCount = linearLayoutManager.getItemCount();

                    if (currentTotalCount <= lastItem + visibleThreshold && isOnline == true && isLoading == false) {
                        isLoading = true;
                        currentPage[0]++;
                        progressDialog.show();
                        viewModel.fetchDataFromApi(country, category, currentPage[0]);
                    }
                }
                          }
        });


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
                progressDialog.show();
                break;

            case SUCCESS:

                progressDialog.dismiss();

                isLoading = false;
                articleList.addAll(apiResponse.data.getArticleList());
                listAdapter.setArticleList(articleList);
                listAdapter.notifyDataSetChanged();

                if (isOnline)
                    for (Article article : apiResponse.data.getArticleList()) {
                        viewModel.updateInDb(article, country, category);
                    }

                break;

            case ERROR:
                progressDialog.dismiss();
                Log.i("Harsh Sidana", apiResponse.error.toString());
                Toast.makeText(NewsListActivity.this, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    @Override
    public void onItemClick(int position, Bundle bundle) {

        Intent i = new Intent(getApplicationContext(), DetailedNews.class);
        i.putExtra("position", position);
        startActivity(i, bundle);


    }


}
