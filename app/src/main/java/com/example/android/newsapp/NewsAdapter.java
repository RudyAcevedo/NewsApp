package com.example.android.newsapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Rudster on 10/31/2016.
 */
public class NewsAdapter extends ArrayAdapter {
    public NewsAdapter(Activity context, ArrayList<News> news) {
        super(context, 0, news);
    }

    /**
     * Provides a view for an AdapterView
     *
     * @param position    position in the list of data that shoudl be displayed in the list view item
     * @param convertView recycled view to populate
     * @param parent      parent ViewGroup that is used for inflation
     * @return the view for the position
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        //Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        }
        //Get the News object located at this position in the list
        News currentNews = (News) getItem(position);

        // Find the ImageView in the list_item.xml layout with the ID news_title
        TextView titleView = (TextView) listItemView.findViewById(R.id.news_title);

        titleView.setText(currentNews.getNewsTitle());

        //Find the ImageView in the list_item.xml layout with the ID section
        TextView sectionView = (TextView) listItemView.findViewById(R.id.section);

        sectionView.setText(currentNews.getNewsSection());

        //Find the ImageView int he list_item.xml layout witht he ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);

        dateView.setText(currentNews.getTimeInMilliseconds());

        return listItemView;

    }

    private String formateDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");

        return dateFormat.format(dateObject);
    }

    private String formateTime(Date dateobject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");

        return dateFormat.format(dateobject);
    }
}
