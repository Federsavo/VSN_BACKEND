package com.generation.vsnbackend.model.dtoSteam;

import java.time.LocalDate;

public class NewsDTO {
//    [
//    {
//      "gid": "1789039014379251",
//              "title": "Left 4 Dead 2 - Update",
//              "url": "https://steamstore-a.akamaihd.net/news/externalpost/steam_community_announcements/1789039014379251",
//              "is_external_url": true,
//              "author": "Kerry",
//              "contents": "An update has been released for Left 4 Dead 2. - Fixed exploits used to crash remote servers.",
//              "feedlabel": "Community Announcements",
//              "date": 1737148598,
//              "feedname": "steam_community_announcements",
//              "feed_type": 1,
//              "appid": 550,
//              "tags": [
//              "patchnotes"
//              ]

    private String url;
    private String title;
    private String author;
    private String contents;
    private String feedLabel;
    private LocalDate date;
    private String feedName;
    private String tags;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getFeedLabel() {
        return feedLabel;
    }

    public void setFeedLabel(String feedLabel) {
        this.feedLabel = feedLabel;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getFeedName() {
        return feedName;
    }

    public void setFeedName(String feedName) {
        this.feedName = feedName;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
