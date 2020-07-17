package com.m2comm.ultrasound.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.m2comm.ultrasound.MainActivity;
import com.m2comm.ultrasound.R;
import com.m2comm.ultrasound.module.Global;


public class Top implements View.OnClickListener {

    private LayoutInflater inflater;
    private int parentID;
    private FrameLayout parent;
    private Context context;
    private Activity activity;
    private String subCode;


    public Top(LayoutInflater inflater, int parentID, Context context, Activity activity , String subCode) {
        this.inflater = inflater;
        this.parentID = parentID;
        this.context = context;
        this.activity = activity;
        this.subCode = subCode;

        this.init();
    }

    private void init() {
        this.parent = activity.findViewById(this.parentID);

        View view = inflater.inflate(R.layout.top_activity,this.parent,true);

        this.parent.setBackgroundColor(Color.parseColor("#FFFFFF"));

        TextView tv = view.findViewById(R.id.topText);
        ImageView backBt = view.findViewById(R.id.backBt);
        backBt.setColorFilter(Color.parseColor("#000000"));
        backBt.setOnClickListener(this);

        tv.setText(Global.top_title[Integer.parseInt(this.subCode)]);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.backBt:
                this.activity.finish();
                this.activity.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                break;
        }


    }
}
