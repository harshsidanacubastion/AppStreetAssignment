package com.project.appstreetassignment.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "article_table")
public class ArticleDbModel {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "article_id")
    private int articleId;

    @ColumnInfo(name = "author")
    private String author;
    @ColumnInfo(name = "source_name")
    private String sourceName;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "imageBase64")
    private String imageBase64;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "published_at")
    private String publishedAt;

    @ColumnInfo(name = "content")
    private String content;


    @ColumnInfo(name = "country")
    private String country;


    @ColumnInfo(name = "category")
    private String category;


    public ArticleDbModel(@NonNull String author, @NonNull String sourceName, @NonNull String title, @NonNull String description, @NonNull String imageBase64, @NonNull String url, @NonNull String publishedAt, @NonNull String content, @NonNull String country, @NonNull String category) {
        this.author = author;
        this.sourceName = sourceName;
        this.title = title;
        this.description = description;
        this.imageBase64 = imageBase64;
        this.url = url;
        this.publishedAt = publishedAt;
        this.content = content;
        this.country = country;
        this.category = category;
    }

    @NonNull
    public String getCountry() {
        return country;
    }

    public void setCountry(@NonNull String country) {
        this.country = country;
    }

    @NonNull
    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    @NonNull
    public String getAuthor() {
        return author;
    }

    public void setAuthor(@NonNull String author) {
        this.author = author;
    }

    @NonNull
    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(@NonNull String sourceName) {
        this.sourceName = sourceName;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(@NonNull String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    @NonNull
    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(@NonNull String publishedAt) {
        this.publishedAt = publishedAt;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }
}
