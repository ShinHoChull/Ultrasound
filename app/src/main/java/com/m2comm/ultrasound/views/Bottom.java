package com.m2comm.ultrasound.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m2comm.ultrasound.R;
import com.m2comm.ultrasound.module.Global;
import com.m2comm.ultrasound.views.BottomMenu;
import com.m2comm.ultrasound.views.LoginActivity;


public class Bottom implements View.OnClickListener {

    private LayoutInflater inflater;
    private int ParentID;
    private LinearLayout parent;
    private Context context;
    private Activity activity;
    private ImageView bt3Img;
    private TextView bt3Txt;
    private boolean isClose = false;
    SharedPreferences prefs;


    public Bottom(LayoutInflater inflater, int parentID, Context context, Activity activity) {
        this.inflater = inflater;
        ParentID = parentID;
        this.context = context;
        this.activity = activity;
        this.init();
    }

    private void init () {
        this.parent = this.activity.findViewById(this.ParentID);
        View view = inflater.inflate(R.layout.activity_new_bottom,this.parent,true);
        view.findViewById(R.id.bottomBt1).setOnClickListener(this);
        view.findViewById(R.id.bottomBt2).setOnClickListener(this);
        view.findViewById(R.id.bottomBt3).setOnClickListener(this);
        view.findViewById(R.id.bottomBt4).setOnClickListener(this);
        view.findViewById(R.id.bottomBt5).setOnClickListener(this);

        this.bt3Img = view.findViewById(R.id.MenuImg);
        this.bt3Txt = view.findViewById(R.id.MenuTxt);

        prefs = this.activity.getSharedPreferences("kairb", this.activity.MODE_PRIVATE);
    }

    public void changeX () {
        this.bt3Img.setImageResource(R.drawable.menu_close);
        this.isClose = true;
        //this.bt3Txt.setText("CLOSE");
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch ( v.getId() ) {


            case R.id.bottomBt1:
                intent = new Intent(this.activity , ContentActivity.class);
                intent.putExtra("subCode", Global.MENU4);
                intent.putExtra("subChildCode",0);
                this.activity.startActivity(intent);
                this.activity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                break;

            case R.id.bottomBt2:
                intent = new Intent(this.activity , ContentActivity.class);
                intent.putExtra("subCode", Global.MENU4);
                intent.putExtra("subChildCode",1);
                this.activity.startActivity(intent);
                this.activity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                break;

            case R.id.bottomBt3:
                if ( !isClose ) {
                    intent = new Intent(this.activity , BottomMenu.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    this.activity.startActivity(intent);
                    this.activity.overridePendingTransition(R.anim.anim_slide_in_bottom_login,0);
                } else {
                    this.activity.finish();
                }

                break;

            case R.id.bottomBt4:
                intent = new Intent(this.activity , ContentActivity.class);
                intent.putExtra("subCode", Global.MENU4);
                intent.putExtra("subChildCode",4);
                this.activity.startActivity(intent);
                this.activity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                break;

            case R.id.bottomBt5:
                intent = new Intent(this.activity , ContentActivity.class);
                intent.putExtra("subCode", Global.MENU5);
                intent.putExtra("subChildCode",1);
                this.activity.startActivity(intent);
                this.activity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                break;
        }

    }
}
