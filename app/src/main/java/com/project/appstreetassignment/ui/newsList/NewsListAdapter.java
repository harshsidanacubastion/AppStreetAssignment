package com.project.appstreetassignment.ui.newsList;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.appstreetassignment.R;
import com.project.appstreetassignment.data.model.Article;


import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> {
    List<Article> articleList;
    Context context;
    boolean isOnline;

    public NewsListAdapter(List<Article> articleList, Context context, boolean isOnline) {
        this.articleList = articleList;
        this.context = context;
        this.isOnline = isOnline;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleText;
        ImageView imageIcon;


        public MyViewHolder(final View view) {
            super(view);

            titleText = (TextView) view.findViewById(R.id.newsTitle);
            imageIcon = (ImageView) view.findViewById(R.id.newsImage);


        }

        @Override
        public void onClick(View v) {

        }
    }

    @NonNull
    @Override
    public NewsListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_element, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.titleText.setText(articleList.get(i).getTitle());
        Glide.with(context)
                .load(Uri.parse(articleList.get(i).getImageBase64()))
                .into(myViewHolder.imageIcon);
    }


    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public interface onClick
    {
        public void onItemClick(int position);
    }
}
