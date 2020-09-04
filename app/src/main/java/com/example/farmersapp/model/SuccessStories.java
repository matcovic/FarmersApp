package com.example.farmersapp.model;

import com.google.firebase.Timestamp;

public class SuccessStories {
    private String storyTitle, storyArticle, storyImageUrl;
    private Timestamp storyTimeAdded;

    public SuccessStories() {
    }

    public SuccessStories(String storyTitle, String storyArticle, String storyImageUrl) {
        this.storyTitle = storyTitle;
        this.storyArticle = storyArticle;
        this.storyImageUrl = storyImageUrl;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public String getStoryArticle() {
        return storyArticle;
    }

    public void setStoryArticle(String storyArticle) {
        this.storyArticle = storyArticle;
    }

    public String getStoryImageUrl() {
        return storyImageUrl;
    }

    public void setStoryImageUrl(String storyImageUrl) {
        this.storyImageUrl = storyImageUrl;
    }

    public Timestamp getStoryTimeAdded() {
        return storyTimeAdded;
    }

    public void setStoryTimeAdded(Timestamp storyTimeAdded) {
        this.storyTimeAdded = storyTimeAdded;
    }
}
