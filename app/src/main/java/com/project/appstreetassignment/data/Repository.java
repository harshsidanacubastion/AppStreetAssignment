package com.project.appstreetassignment.data;

import android.os.Build;
import android.support.annotation.RequiresApi;


import com.project.appstreetassignment.data.db.SampleRoomDatabase;
import com.project.appstreetassignment.data.model.Article;
import com.project.appstreetassignment.data.model.ArticleData;
import com.project.appstreetassignment.data.model.db.ArticleDbModel;
import com.project.appstreetassignment.data.model.network.ArticleNw;
import com.project.appstreetassignment.data.model.network.NewsResponse;
import com.project.appstreetassignment.data.restApi.ApiInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.functions.Function;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Repository {


    private SampleRoomDatabase db;
    private ApiInterface apiInterface;

    public Repository(SampleRoomDatabase db, ApiInterface apiInterface) {
        this.db = db;
        this.apiInterface = apiInterface;
    }

    public Single<ArticleData> fetchData(String country, String category, long pageNo) {
        return apiInterface.fetchNews(country, category, "d521212fa7b547c58bea0ad45070de80", pageNo)
                .map(new Function<NewsResponse, ArticleData>() {
                    @Override
                    public ArticleData apply(NewsResponse newsResponse) throws Exception {

                        List<Article> articleList = new ArrayList<>();
                        for (ArticleNw articleNw : newsResponse.getArticleNws()) {
                            Article article = new Article(
                                    articleNw.getAuthor(),
                                    articleNw.getSource().getName(),
                                    articleNw.getTitle(),
                                    articleNw.getDescription(),
                                    articleNw.getUrlToImage(),
                                    articleNw.getUrl(),
                                    articleNw.getPublishedAt(),
                                    articleNw.getContent()
                            );

                            articleList.add(article);
                        }

                        return new ArticleData(newsResponse.getTotalResults(), articleList);
                    }
                });


    }

    public Single<Article> fetchPhoto(final Article article, final String country, final String category) {
        return Single.create(new SingleOnSubscribe<Article>() {
            @Override
            public void subscribe(final SingleEmitter<Article> emitter) throws Exception {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(article.getUrl())
                        .build();


                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println("request failed: " + e.getMessage());
                        emitter.tryOnError(e);
                    }

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        InputStream finput = response.body().byteStream();

                        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                        int nRead;
                        byte[] data = new byte[16384];
                        while ((nRead = finput.read(data, 0, data.length)) != -1) {
                            buffer.write(data, 0, nRead);
                        }
                        byte[] bytes = buffer.toByteArray();
                        String encoded = Base64.getEncoder().encodeToString(bytes);


                        ArticleDbModel articleDbModel = new ArticleDbModel(
                                article.getAuthor(),
                                article.getSourceName(),
                                article.getTitle(),
                                article.getDescription(),
                                encoded,
                                article.getUrl(),
                                article.getPublishedAt(),
                                article.getContent(),
                                country,
                                category
                        );


                        db.sampleDao().insertAtricle(articleDbModel);

                        emitter.onSuccess(article);
                    }

                });
            }
        });
    }


    public Single<ArticleData> getFromDb(String country, String category) {
        return db.sampleDao().getAllArticles(country, category).map(new Function<List<ArticleDbModel>, ArticleData>() {
            @Override
            public ArticleData apply(List<ArticleDbModel> articleDbModels) throws Exception {

                List<Article> articleList = new ArrayList<>();
                for (ArticleDbModel articleDbModel : articleDbModels) {
                    Article article = new Article(
                            articleDbModel.getAuthor(),
                            articleDbModel.getSourceName(),
                            articleDbModel.getTitle(),
                            articleDbModel.getDescription(),
                            articleDbModel.getImageBase64(),
                            articleDbModel.getUrl(),
                            articleDbModel.getPublishedAt(),
                            articleDbModel.getContent()
                    );
                    articleList.add(article);
                }
                return new ArticleData(0, articleList);
            }
        });
    }


}
