package com.m2comm.ultrasound.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.m2comm.ultrasound.DTO.MenuDTO;
import com.m2comm.ultrasound.R;
import com.m2comm.ultrasound.module.Global;

import java.util.ArrayList;


public class SubMenuTop implements View.OnClickListener {

    private LayoutInflater inflater;
    private int parentID;
    private FrameLayout parent;
    private Context context;
    private Activity activity;
    private ArrayList<MenuDTO> menuDTOArrayList;
    private Global g;

    private String subCode;
    private int subChildCode;
    private ContentActivity contentActivity;
    private TabLayout tabLayout;


    public SubMenuTop(LayoutInflater inflater, int parentID, Context context, Activity activity ,
                       String subCode , int subChildCode , ContentActivity contentActivity) {
        this.inflater = inflater;
        this.parentID = parentID;
        this.context = context;
        this.activity = activity;
        this.subCode = subCode;
        this.subChildCode = subChildCode;
        this.contentActivity = contentActivity;

        this.init();
    }

    private void init() {
        this.parent = activity.findViewById(this.parentID);
        this.g = new Global();

        View view = inflater.inflate(R.layout.sub_top_activity,this.parent,true);
        ImageView down_arrow = view.findViewById(R.id.down_arrow);
        down_arrow.setColorFilter(Color.parseColor("#FFFFFF"));
        down_arrow.setOnClickListener(this);
        this.tabLayout = view.findViewById(R.id.content_tab);
        this.menuDTOArrayList = new ArrayList<>();
        this.menuDTOArrayList = this.g.getMenu.get(this.subCode);

        if (this.menuDTOArrayList.size() > 0) {
            for ( int i = 0, j = this.menuDTOArrayList.size(); i < j; i++ ) {
                MenuDTO row = this.menuDTOArrayList.get(i);
                this.tabLayout.addTab(this.tabLayout.newTab().setText(row.getTitle()));
            }
        } else {
            this.tabLayout.setVisibility(View.GONE);
        }

        this.tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#f3f5f7"));
        this.tabLayout.setTabTextColors(Color.parseColor("#cbe9eb"), Color.parseColor("#f3f5f7"));


        this.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                subChildCode = tab.getPosition();
                setUrl();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void setScrollPosition (int subChildCode) {
        this.subChildCode = subChildCode;
        this.tabLayout.setScrollPosition(this.subChildCode, 0, true);
    }

    private void setUrl () {

        if ( this.subCode.equals(Global.MENU3) && this.subChildCode == 4 ) {
            Intent intent = new Intent(this.activity , CalendarActivity.class);
            intent.putExtra("subCode", Global.MENU3);
            intent.putExtra("subChildCode",this.subChildCode);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            this.activity.startActivity(intent);
            return;
        }

        if ( this.contentActivity != null ) {
            this.contentActivity.setUrl(this.subCode , this.subChildCode);
        } else {
            Intent intent = new Intent(this.activity , ContentActivity.class);
            intent.putExtra("subCode", Global.MENU3);
            intent.putExtra("subChildCode",this.subChildCode);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            this.activity.startActivity(intent);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.down_arrow:
                Intent intent = new Intent(this.activity , SubMenuActivity.class);
                intent.putExtra("subCode", this.subCode);
                intent.putExtra("subChildCode",this.subChildCode);
                if ( this.contentActivity == null )this.activity.startActivityForResult(intent , CalendarActivity.GRIDVIEW_CODE);
                else this.activity.startActivityForResult(intent , ContentActivity.GRIDVIEW_CODE);

                break;
        }
    }
}
