package com.m2comm.ultrasound.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m2comm.ultrasound.DTO.MenuDTO;
import com.m2comm.ultrasound.R;

import java.util.ArrayList;

public class SubMenuGridViewAdapter extends BaseAdapter {

    private ArrayList<MenuDTO> menuDTOArrayList;
    private Context c;
    private LayoutInflater inflater;

    public SubMenuGridViewAdapter(ArrayList<MenuDTO> menuDTOArrayList, Context c, LayoutInflater inflater) {
        this.menuDTOArrayList = menuDTOArrayList;
        this.c = c;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return this.menuDTOArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.menuDTOArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MenuDTO row = this.menuDTOArrayList.get(position);
        convertView = this.inflater.inflate(R.layout.sub_menu_item, parent, false);
        TextView titleTV = convertView.findViewById(R.id.title);
        titleTV.setText(row.getTitle());

        return convertView;

    }
}
