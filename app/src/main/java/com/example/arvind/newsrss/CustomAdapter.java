package com.example.arvind.newsrss;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<News> news;
    LayoutInflater layoutInflator;

    CustomAdapter(Context context , ArrayList<News> news){
        this.context=context;
        this.news=news;
        this.layoutInflator=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View vi, ViewGroup viewGroup) {
        View view =layoutInflator.inflate(R.layout.row_layout,null);
        TextView titleTextView=view.findViewById(R.id.titleTextView);
        TextView discriptionTextView=view.findViewById(R.id.discriptionTextView);
        TextView pubDateTextView=view.findViewById(R.id.pubDateTextView);
        ImageView imageView=view.findViewById(R.id.imageView);

        titleTextView.setText(news.get(i).getTitle());
        discriptionTextView.setText(news.get(i).getDiscription());
        pubDateTextView.setText(news.get(i).getPubDates());
        Log.i("Image",news.get(i).getImage());
        Picasso.with(context).load(news.get(i).getImage()).into(imageView);
        return view;


    }
}
