package com.project.appstreetassignment.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.project.appstreetassignment.R;

import java.util.ArrayList;

public class StatusAdapter extends ArrayAdapter<ItemData> {
    int groupid;
    ArrayList<ItemData> list;
    LayoutInflater inflater;
    final Typeface font;

    public StatusAdapter(Activity context, int groupid, int id, ArrayList<ItemData> list) {
        super(context, id, list);
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid = groupid;
        font = Typeface.createFromAsset(context.getAssets(), "fonts/UniversLTStd-LightCn.otf");

    }


    @Override
    public boolean isEnabled(int position) {
        if (position == 0) {
            return false;
        } else {
            return true;
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = inflater.inflate(groupid, parent, false);
        TextView textView = (TextView) itemView.findViewById(R.id.txt);
        textView.setText(list.get(position).getText());
        textView.setTypeface(font);
        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup
            parent) {

        View view = getView(position, convertView, parent);
        TextView tv = (TextView) view.findViewById(R.id.txt);
        if (position == 0) {
            // Set the hint text color gray
            tv.setTextColor(Color.GRAY);
        } else {
            tv.setTextColor(Color.BLACK);
        }
        return view;
//        return getView(position,convertView,parent);

    }
}