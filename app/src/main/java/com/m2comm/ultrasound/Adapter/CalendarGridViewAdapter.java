package com.m2comm.ultrasound.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m2comm.ultrasound.DTO.CalendarListRealDTO;
import com.m2comm.ultrasound.R;
import com.m2comm.ultrasound.module.CalendarModule;

import java.util.ArrayList;

public class CalendarGridViewAdapter extends BaseAdapter {

    private ArrayList<String> dayList;
    private ArrayList<CalendarListRealDTO> calendarListRealDTOArrayList;

    private Context c;
    private LayoutInflater inflater;
    private int width;
    private String dateStr;

    public CalendarGridViewAdapter(ArrayList<String> dayList, ArrayList<CalendarListRealDTO> calendarListRealDTOArrayList,
                                   Context c, LayoutInflater inflater, int width, String dateStr) {
        this.dayList = dayList;
        this.calendarListRealDTOArrayList = calendarListRealDTOArrayList;
        this.c = c;
        this.inflater = inflater;
        this.width = width;
        this.dateStr = dateStr;
    }

    @Override
    public int getCount() {
        return this.dayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.dayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = this.inflater.inflate(R.layout.calendar_item, parent, false);
        convertView.getLayoutParams().height = this.width + 10;

        TextView day = convertView.findViewById(R.id.day);
        LinearLayout dayParent = convertView.findViewById(R.id.day_bottom);

        if ( this.dayList != null ) {
            if ( this.dayList.get(position).equals("") ) {
                dayParent.setVisibility(View.INVISIBLE);
            } else {
                String pushDate = dateStr.replace(".","-")+"-"+zeroPoint(this.dayList.get(position));
                int count = 0;
                for ( int i = 0 , j = this.calendarListRealDTOArrayList.size(); i < j; i++ ) {
                    CalendarListRealDTO row = this.calendarListRealDTOArrayList.get(i);
                    if ( row.getPeriod() != null && row.getPeriod().equals(pushDate)) {
                        count += 1;
                    }
                }

                Log.d("dateStr=",count+"_");



            }

            day.setText(this.dayList.get(position));



        }


        return convertView;

    }


    public String zeroPoint(String data) {
        data = data.trim();
        if (data.length() == 1) {
            data = "0" + data;
        }
        return data;
    }

}
