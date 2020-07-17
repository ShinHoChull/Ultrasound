package com.m2comm.ultrasound.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.m2comm.ultrasound.DTO.CalendarListDTO;
import com.m2comm.ultrasound.DTO.CalendarListRealDTO;
import com.m2comm.ultrasound.R;

import java.util.ArrayList;

public class CalendarListViewAdapter extends BaseAdapter {

    private Context c;
    private Activity a;
    private LayoutInflater inflater;
    private ArrayList<CalendarListRealDTO> arrayList;

    public CalendarListViewAdapter(Context c, Activity a, LayoutInflater inflater, ArrayList<CalendarListRealDTO> arrayList) {
        this.c = c;
        this.a = a;
        this.inflater = inflater;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CalendarListRealDTO row = this.arrayList.get(position);

        if ( row.isHeader() ) {
            convertView = this.inflater.inflate(R.layout.calendar_list_header, parent, false);
            convertView.getLayoutParams().height = 150;
            TextView text = convertView.findViewById(R.id.listText);
            text.setText(row.getPeriod());
        } else {
            convertView = this.inflater.inflate(R.layout.calendar_list_item, parent, false);
            TextView text = convertView.findViewById(R.id.listText);
            text.setText(row.getSubject());
        }

        return convertView;
    }
}
