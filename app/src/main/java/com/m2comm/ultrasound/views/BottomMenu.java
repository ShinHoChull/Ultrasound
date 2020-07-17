package com.m2comm.ultrasound.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.m2comm.ultrasound.MainActivity;
import com.m2comm.ultrasound.R;
import com.m2comm.ultrasound.databinding.ActivityBottomMenuBinding;
import com.m2comm.ultrasound.module.Global;
import com.m2comm.ultrasound.module.menu.SideMenuAdapterN;
import com.m2comm.ultrasound.module.menu.SideMenuClass;
import com.m2comm.ultrasound.module.menu.SubSideMenuClass;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BottomMenu extends AppCompatActivity implements View.OnClickListener {

    ActivityBottomMenuBinding binding;
    Bottom bottomActivity;
    SideMenuAdapterN sidemenuadapter;
    int lastClickedPosition;
    private Timer timer = new Timer();
    private float alphaCount = 0;
    SharedPreferences prefs;

    private void regObj() {
        this.binding.close.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.setContentView(this , R.layout.activity_bottom_menu);
        this.binding.setBottomMenu(this);

        this.bottomActivity = new Bottom(getLayoutInflater(), R.id.bottom, this, this);
        this.sidemenuadapter = new SideMenuAdapterN(this,R.layout.nbottom_menu_item);
        this.binding.menuList.setAdapter(this.sidemenuadapter);
        prefs = getSharedPreferences("kairb", MODE_PRIVATE);
        this.regObj();
        this.menuSetting();
        this.bottomActivity.changeX();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                binding.bottomMenuParent.setAlpha(alphaCount);
                if ( alphaCount >= 1 ) timer.cancel();
                alphaCount += 0.08;
            }
        };
        timer.schedule(tt, 0, 30);

        this.binding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void menuSetting() {
        ArrayList<SubSideMenuClass> subSideMenuClass = new ArrayList<SubSideMenuClass>();
        subSideMenuClass = new ArrayList<SubSideMenuClass>();
        subSideMenuClass.add(new SubSideMenuClass("인사말",null, ContentActivity.class));
        subSideMenuClass.add(new SubSideMenuClass("연혁",null, ContentActivity.class));
        subSideMenuClass.add(new SubSideMenuClass("회칙",null, ContentActivity.class));
        subSideMenuClass.add(new SubSideMenuClass("임원진 및 위원회",null, ContentActivity.class));
        subSideMenuClass.add(new SubSideMenuClass("역대임원진",null, ContentActivity.class));
        subSideMenuClass.add(new SubSideMenuClass("명예회원",null, ContentActivity.class));
        subSideMenuClass.add(new SubSideMenuClass("사무국 안내",null, ContentActivity.class));
        this.sidemenuadapter.add(new SideMenuClass("학회소개", R.drawable.menu_ico1, null,null, subSideMenuClass));


        this.sidemenuadapter.add(new SideMenuClass("학회지", R.drawable.menu_ico2, null,"", null));

        subSideMenuClass = new ArrayList<SubSideMenuClass>();
        subSideMenuClass.add(new SubSideMenuClass("정기학술대회",null, ContentActivity.class));
        subSideMenuClass.add(new SubSideMenuClass("정기연수교육",null, ContentActivity.class));
        subSideMenuClass.add(new SubSideMenuClass("정기 Hands-on 교육",null, ContentActivity.class));
        subSideMenuClass.add(new SubSideMenuClass("이사회 일정",null, ContentActivity.class));
        subSideMenuClass.add(new SubSideMenuClass("국제학회 일정",null, ContentActivity.class));
        this.sidemenuadapter.add(new SideMenuClass("학술행사", R.drawable.menu_ico3, null,"", subSideMenuClass));

        subSideMenuClass = new ArrayList<SubSideMenuClass>();
        subSideMenuClass.add(new SubSideMenuClass("공지사항",null, ContentActivity.class));
        subSideMenuClass.add(new SubSideMenuClass("뉴스레터",null, ContentActivity.class));
        subSideMenuClass.add(new SubSideMenuClass("게시판",null, ContentActivity.class));
        subSideMenuClass.add(new SubSideMenuClass("자료실",null, ContentActivity.class));
        subSideMenuClass.add(new SubSideMenuClass("증례토론",null, ContentActivity.class));
        this.sidemenuadapter.add(new SideMenuClass("회원공간", R.drawable.menu_ico4, null,"", subSideMenuClass));

        subSideMenuClass = new ArrayList<SubSideMenuClass>();
        subSideMenuClass.add(new SubSideMenuClass("연수교육 자료",null, ContentActivity.class));
        subSideMenuClass.add(new SubSideMenuClass("VODs",null, ContentActivity.class));
        subSideMenuClass.add(new SubSideMenuClass("KAFE 자료",null, ContentActivity.class));
        subSideMenuClass.add(new SubSideMenuClass("일반인을 위한 초음파 진료 소개",null, ContentActivity.class));
        this.sidemenuadapter.add(new SideMenuClass("교육자료", R.drawable.menu_ico5, null,"https://kairb.org/mobile/bbs/list.php?code=notice&id="+prefs.getString("user_ID2",""), subSideMenuClass));


//        binding.menuList.setSelection(0);
//        binding.menuList.expandGroupWithAnimation(0);

        this.binding.menuList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long ids) {

                Boolean isExpand = (!binding.menuList.isGroupExpanded(groupPosition));

                //그룹 클릭시 닫히기를 원하면 주석을 풀면됨.
                if ( binding.menuList.isGroupExpanded(lastClickedPosition)) {
                    binding.menuList.collapseGroupWithAnimation(lastClickedPosition);
                }

                binding.menuList.setSelection(groupPosition);

                if (sidemenuadapter.SideMenuList.get(groupPosition).SubSideMenuList == null) {
                    if ( groupPosition == 1 ) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://www.e-ultrasonography.org/"));
                        startActivity(intent);
                        finish();
                    }
//                    if(sidemenuadapter.SideMenuList.get(groupPosition).activity != null) {
//                        //aniYN=false;
//                        Intent intent = new Intent(BottomMenu.this, sidemenuadapter.SideMenuList.get(groupPosition).activity);
//                        intent.putExtra("page", sidemenuadapter.SideMenuList.get(groupPosition).url);
//                        intent.putExtra("title", sidemenuadapter.SideMenuList.get(groupPosition).info);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                        finish();
//                    }
                    return true;
                }

                if (isExpand) {
                    binding.menuList.expandGroupWithAnimation(groupPosition);
                }
                lastClickedPosition = groupPosition;
                binding.menuList.setSelection(groupPosition);
                return true;
            }
        });

        this.binding.menuList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long ids) {
                Intent intent;
                //aniYN=false;
                intent = new Intent(BottomMenu.this, sidemenuadapter.getGroup(groupPosition).SubSideMenuList.get(childPosition).activity);
                intent.putExtra("subCode", String.valueOf(groupPosition));
                intent.putExtra("subChildCode",childPosition);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                finish();

                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                finish();
                break;
        }
    }
}
