package com.project.appstreetassignment.data.model;

public class Article {


    private String author;

    private String sourceName;

    private String title;

    private String description;

    private String imageBase64;

    private String url;

    private String publishedAt;

    private String content;

    public Article(String author, String sourceName, String title, String description, String imageBase64, String url, String publishedAt, String content) {
        this.author = author;
        this.sourceName = sourceName;
        this.title = title;
        this.description = description;
        this.imageBase64 = imageBase64;
        this.url = url;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
