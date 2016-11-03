package com.example.android.newsapp;

/**
 * Created by Rudster on 10/31/2016.
 */
public class News {

    private String mNewsTitle;

    private String mNewsSection;

    String mTimeInMilliseconds;

    private String mUrl;

    public News(String NewsTitle, String Location, String TimeInMilliseconds, String Url){
        mNewsTitle = NewsTitle;
        mNewsSection = Location;
        mTimeInMilliseconds = TimeInMilliseconds;
        mUrl = Url;
    }

    public String getNewsTitle(){
        return mNewsTitle;
    }

    public String getNewsSection(){
        return mNewsSection;
    }
    public String getTimeInMilliseconds(){
        return mTimeInMilliseconds;
    }
    public String getURL(){
        return mUrl;
    }
}
