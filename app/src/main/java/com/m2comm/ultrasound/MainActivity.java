package com.m2comm.ultrasound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.m2comm.ultrasound.Adapter.MainListViewAdapter;
import com.m2comm.ultrasound.Adapter.MainPagerAdapter;
import com.m2comm.ultrasound.DTO.MainBannerDTO;
import com.m2comm.ultrasound.DTO.NoticeDTO;
import com.m2comm.ultrasound.databinding.ActivityMainBinding;
import com.m2comm.ultrasound.module.Global;
import com.m2comm.ultrasound.views.Bottom;
import com.m2comm.ultrasound.views.CalendarActivity;
import com.m2comm.ultrasound.views.ContentActivity;
import com.m2comm.ultrasound.views.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int BT1 = 0;
    int BT2 = 1;
    int BT3 = 2;

    Bottom bottomActivity;
    private ActivityMainBinding binding;
    private MainPagerAdapter mainPagerAdapter;
    private MainListViewAdapter mainListViewAdapter;
    private ArrayList<String> datas;

    ArrayList<MainBannerDTO> bt1Array;
    ArrayList<MainBannerDTO> bt2Array;
    ArrayList<MainBannerDTO> bt3Array;
    ArrayList<MainBannerDTO> realBannerArray;

    ArrayList<NoticeDTO> noticeDTOArrayList;


    private void regObj() {
        this.binding.loginBt.setOnClickListener(this);
        this.binding.bt1.setOnClickListener(this);
        this.binding.calendarBt.setOnClickListener(this);
        this.binding.bt3.setOnClickListener(this);
        this.binding.bt4.setOnClickListener(this);
        this.binding.bt5.setOnClickListener(this);
        this.binding.bt6.setOnClickListener(this);
        this.binding.bt7.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#2883AA"));

        setContentView(R.layout.activity_main);
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        this.bottomActivity = new Bottom(getLayoutInflater(), R.id.bottom, this, this);

        this.binding.mainPager.setClipToPadding(false);
        this.binding.mainPager.setPadding((int) (this.getMargin() / 1.2), 0, (int) (this.getMargin() / 1.2), 0);
        this.binding.mainPager.setPageMargin((int) (this.getMargin() / 3.5));

        this.realBannerArray = new ArrayList<>();
        this.bt1Array = new ArrayList<>();
        this.bt2Array = new ArrayList<>();
        this.bt3Array = new ArrayList<>();

        this.noticeDTOArrayList = new ArrayList<>();


        this.binding.mainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                binding.tabs.setScrollPosition(i, 0, true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        this.binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.mainPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        this.regObj();
        this.getBannerAndNotice();

    }

    private void getBannerAndNotice() {

        AndroidNetworking.post(Global.main_url + Global.getUrls.get("getBannerAndNotice"))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("getMain=", response.toString());

                        try {
                            if (!response.isNull("banner")) {

                                JSONArray bannerJsonArray = response.getJSONArray("banner");
                                for ( int i = 0 , j = bannerJsonArray.length(); i < j ; i++ ) {
                                    JSONArray parentObjArray = bannerJsonArray.getJSONArray(i);
                                    for ( int k = 0 , l = parentObjArray.length(); k < l; k++ ) {

                                        if ( i == 0 ) {

                                            JSONObject obj1 = parentObjArray.getJSONObject(k);
                                            bt1Array.add(new MainBannerDTO(
                                                    obj1.isNull("sid")?"":obj1.getString("sid"),
                                                    obj1.isNull("title")?"":obj1.getString("title"),
                                                    obj1.isNull("img")?"":obj1.getString("img"),
                                                    obj1.isNull("link")?"":obj1.getString("link")));

                                        } else if ( i == 1 ) {

                                            JSONObject obj2 = parentObjArray.getJSONObject(k);
                                            bt2Array.add(new MainBannerDTO(
                                                    obj2.isNull("sid")?"":obj2.getString("sid"),
                                                    obj2.isNull("title")?"":obj2.getString("title"),
                                                    obj2.isNull("img")?"":obj2.getString("img"),
                                                    obj2.isNull("link")?"":obj2.getString("link")));

                                        } else if ( i == 2 ) {

                                            JSONObject obj3 = parentObjArray.getJSONObject(k);
                                            bt3Array.add(new MainBannerDTO(
                                                    obj3.isNull("sid")?"":obj3.getString("sid"),
                                                    obj3.isNull("title")?"":obj3.getString("title"),
                                                    obj3.isNull("img")?"":obj3.getString("img"),
                                                    obj3.isNull("link")?"":obj3.getString("link")));
                                        }
                                    }
                                }//for End
                            }

                            if (!response.isNull("notice")) {
                                JSONArray noticeJsonArray = response.getJSONArray("notice");
                                for ( int i = 0 , j = noticeJsonArray.length(); i < j ; i++ ) {
                                    JSONObject obj = noticeJsonArray.getJSONObject(i);
                                    noticeDTOArrayList.add(new NoticeDTO(
                                            obj.isNull("sid")?"":obj.getString("sid"),
                                            obj.isNull("title")?"":obj.getString("title"),
                                            obj.isNull("img")?"":obj.getString("img")
                                            ));
                                }
                            }

                            if ( bt1Array.size() > 0 ) changeAdapter(BT1);
                            else if ( bt2Array.size() > 0 ) changeAdapter(BT2);
                            else if ( bt3Array.size() > 0 ) changeAdapter(BT3);
                            notilistChangeAdapter();

                        } catch (JSONException e) {
                            Log.d("getMainError=", e.toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("getMainanError=", anError.toString());
                    }
                });
    }

    private int getMargin() {
        int dpvalue = 24;
        float d = getResources().getDisplayMetrics().density;
        return (int) (dpvalue * d);
    }

    private void notilistChangeAdapter() {
        this.mainListViewAdapter = new MainListViewAdapter(this , this, getLayoutInflater() , this.noticeDTOArrayList);
        this.binding.listview.setAdapter(this.mainListViewAdapter);
    }

    private void resetBt() {
        this.binding.bt5.setBackgroundResource(R.drawable.main_middle_top_round);
        this.binding.bt6.setBackgroundColor(Color.parseColor("#0b546d"));
        this.binding.bt7.setBackgroundResource(R.drawable.main_middle_bottom_round);
    }

    private void changeAdapter(int num) {

        if (this.realBannerArray != null)this.realBannerArray.clear();
        this.resetBt();
        if ( num == BT1 ) {
            this.realBannerArray = (ArrayList<MainBannerDTO>) this.bt1Array.clone();
            this.binding.bt5.setBackgroundResource(R.drawable.main_middle_top_click_round);
        } else if ( num == BT2 ) {
            this.realBannerArray = (ArrayList<MainBannerDTO>)this.bt2Array.clone();
            this.binding.bt6.setBackgroundColor(Color.parseColor("#033d51"));
        } else if ( num == BT3 ) {
            this.realBannerArray = (ArrayList<MainBannerDTO>)this.bt3Array.clone();
            this.binding.bt7.setBackgroundResource(R.drawable.main_middle_bottom_click_round);
        }

        this.binding.tabs.removeAllTabs();
        for (int i = 0, j = this.realBannerArray.size(); i < j; i++) {
            this.binding.tabs.addTab(this.binding.tabs.newTab().setIcon(R.drawable.tab_selector));
        }


        this.mainPagerAdapter = new MainPagerAdapter(this, this.realBannerArray, this, getLayoutInflater());
        this.binding.mainPager.setAdapter(this.mainPagerAdapter);
        this.mainPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.bt1:
                intent = new Intent(this, ContentActivity.class);
                intent.putExtra("subCode", Global.MENU1);
                intent.putExtra("subChildCode", 0);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                break;

            case R.id.calendarBt:
                intent = new Intent(this, ContentActivity.class);
                intent.putExtra("subCode", Global.MENU3);
                intent.putExtra("subChildCode", 0);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                break;

            case R.id.bt3:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.e-ultrasonography.org/"));
                startActivity(intent);
                break;

            case R.id.bt4:
                intent = new Intent(this, ContentActivity.class);
                intent.putExtra("subCode", Global.MENU4);
                intent.putExtra("subChildCode", 0);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);

                break;

            case R.id.bt5:
                this.changeAdapter(BT1);
                break;

            case R.id.bt6:
                this.changeAdapter(BT2);
                break;

            case R.id.bt7:
                this.changeAdapter(BT3);
                break;

            case R.id.loginBt:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_bottom_login, 0);
                break;

        }
    }


}
