package com.m2comm.ultrasound.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.m2comm.ultrasound.DTO.CalendarListRealDTO;
import com.m2comm.ultrasound.DTO.NoticeDTO;
import com.m2comm.ultrasound.R;

import java.util.ArrayList;

public class MainListViewAdapter extends BaseAdapter {

    private Context c;
    private Activity a;
    private LayoutInflater inflater;
    private ArrayList<NoticeDTO> arrayList;

    public MainListViewAdapter(Context c, Activity a, LayoutInflater inflater, ArrayList<NoticeDTO> arrayList) {
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

        NoticeDTO row = this.arrayList.get(position);
        convertView = this.inflater.inflate(R.layout.main_list_item, parent, false);
        convertView.getLayoutParams().height = 70;
        TextView mainTv = convertView.findViewById(R.id.mainTv);
        mainTv.setText("･"+row.getTitle());

        convertView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return convertView;
    }
}
