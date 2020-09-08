package com.example.farmersapp.model;

public class NewsItem {

    String newsTitle;
    String newsArticleFull;
    String newsArticle;
    String newsId;
    String timeStamp;

    public NewsItem(String newsTitle, String newArticleFull, String newsArticle, String newsId, String timeStamp) {
        this.newsTitle = newsTitle;
        this.newsArticleFull = newArticleFull;
        this.newsArticle = newsArticle;
        this.newsId = newsId;
        this.timeStamp = timeStamp;
    }

    public NewsItem() {
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsArticleFull() {
        return newsArticleFull;
    }

    public void setNewArticleFull(String newArticleFull) {
        this.newsArticleFull = newArticleFull;
    }

    public String getNewsArticle() {
        return newsArticle;
    }

    public void setNewsArticle(String newsArticle) {
        this.newsArticle = newsArticle;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
