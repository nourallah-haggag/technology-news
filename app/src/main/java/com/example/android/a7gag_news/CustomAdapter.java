package com.example.android.a7gag_news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

class CustomAdapter extends ArrayAdapter {
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater =  LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.row_layout , parent , false);
        ListItem titleItem = (ListItem)getItem(position);
     //   String titleItem2 = (String)getItem(position);
     //   String titleItem3= (String)getItem(position);

        TextView titleText = (TextView)customView.findViewById(R.id.title_text);
        TextView byText = (TextView)customView.findViewById(R.id.by_text);
        TextView dateText = (TextView)customView.findViewById(R.id.date_text);

        titleText.setText("Title: "+titleItem.title);
        byText.setText("By: "+titleItem.by);
        dateText.setText("date: "+titleItem.date);
        return customView;

    }

    public CustomAdapter(@NonNull Context context, ArrayList<ListItem> storiesList) {
        super(context, R.layout.row_layout , storiesList);





    }
}
