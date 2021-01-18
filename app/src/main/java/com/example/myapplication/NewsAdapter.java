package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<Event> {

    public NewsAdapter(@NonNull Context context, @NonNull List<Event> Events) {
        super(context, 0, Events);
    }
    @Override
    public View getView(int position,View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.main_activity_singleitem, parent, false);
        }
        Event currentEvent=getItem(position);
        String headline=currentEvent.getHeadline();
        String summary=currentEvent.getSummary();
        String author=currentEvent.getAuthor();
        String url=currentEvent.getUrl();
        TextView text1=(TextView) listItemView.findViewById(R.id.Headline);
        text1.setText(headline);
        TextView text2=(TextView) listItemView.findViewById(R.id.Summary);

        text2.setText(summary);
        TextView text3=(TextView) listItemView.findViewById(R.id.NewsProvider);
       
        text3.setText(author);
        TextView text4=(TextView) listItemView.findViewById(R.id.click_here);
        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url)));
            }
        });
       return listItemView;
}}
