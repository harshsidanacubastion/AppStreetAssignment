package com.project.appstreetassignment.ui.newsList;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.project.appstreetassignment.R;
import com.project.appstreetassignment.data.model.Article;


import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> {
    List<Article> articleList;
    Context context;
    boolean isOnline;
    onClick onClick;

    private final RequestManager requestManager;


    public NewsListAdapter(List<Article> articleList, Context context, boolean isOnline, NewsListAdapter.onClick onClick) {
        this.articleList = articleList;
        this.context = context;
        this.isOnline = isOnline;
        this.onClick = onClick;
        this.requestManager = Glide.with(context);

    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        ImageView imageIcon;
        CardView newsLayout;
        private final Typeface newsTextFont;


        public MyViewHolder(final View view) {
            super(view);
            newsTextFont = Typeface.createFromAsset(context.getAssets(), "fonts/UniversLTStd-LightCn.otf");

            titleText = (TextView) view.findViewById(R.id.newsTitle);
            imageIcon = (ImageView) view.findViewById(R.id.newsImage);
            newsLayout = (CardView) view.findViewById(R.id.newsLayout);
            titleText.setTypeface(newsTextFont);
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
        if (articleList.get(i).getTitle() != null)
            myViewHolder.titleText.setText(articleList.get(i).getTitle());
        if (articleList.get(i).getImageBase64() != null) {
//            Glide.with(context)
//                    .load(Uri.parse(articleList.get(i).getImageBase64()))
//                    .into(myViewHolder.imageIcon);

            if (isOnline) {
                Log.e("harsh"," online");
                requestManager.load(Uri.parse(articleList.get(i).getImageBase64()))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                        Target<Drawable> target, boolean isFirstResource) {
                                Toast.makeText(context, "image load failed for index " + i, Toast.LENGTH_SHORT).show();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable>
                                    target, DataSource dataSource, boolean isFirstResource) {

                                return false;
                            }
                        })
                        .into(myViewHolder.imageIcon);
            } else {
                Log.e("harsh"," offline");
                Log.e("harsh","  "+articleList.get(i).getImageBase64());
                byte[] decodedString = Base64.decode(articleList.get(i).getImageBase64(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                myViewHolder.imageIcon.setImageBitmap(decodedByte);
//                requestManager.load(decodedByte)
//                        .listener(new RequestListener<Drawable>() {
//                            @Override
//                            public boolean onLoadFailed(@Nullable GlideException e, Object model,
//                                                        Target<Drawable> target, boolean isFirstResource) {
//                                Toast.makeText(context, "image load failed for index " + i, Toast.LENGTH_SHORT).show();
//                                return false;
//                            }
//
//                            @Override
//                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable>
//                                    target, DataSource dataSource, boolean isFirstResource) {
//
//                                return false;
//                            }
//                        })
//                        .into(myViewHolder.imageIcon);
            }
        }

        myViewHolder.newsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = null;
                Pair[] pair = new Pair[1];
                pair[0] = new Pair<View, String>(myViewHolder.imageIcon, "Image_shared");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pair);
                }

                onClick.onItemClick(i, options.toBundle());

            }
        });

    }


    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public interface onClick {
        void onItemClick(int position, Bundle bundle);
    }
}
