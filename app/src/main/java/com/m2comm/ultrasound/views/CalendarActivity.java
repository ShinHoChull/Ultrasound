package com.m2comm.ultrasound.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.m2comm.ultrasound.Adapter.CalendarGridViewAdapter;
import com.m2comm.ultrasound.Adapter.CalendarListViewAdapter;
import com.m2comm.ultrasound.DTO.CalendarListContentDTO;
import com.m2comm.ultrasound.DTO.CalendarListDTO;
import com.m2comm.ultrasound.DTO.CalendarListRealDTO;
import com.m2comm.ultrasound.R;
import com.m2comm.ultrasound.databinding.ActivityCalendarBinding;
import com.m2comm.ultrasound.module.CalendarModule;
import com.m2comm.ultrasound.module.Global;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity implements View.OnClickListener {


    public static int GRIDVIEW_CODE = 1;

    ActivityCalendarBinding binding;
    private CalendarModule cm;
    private ArrayList<String> dayList;
    private CalendarGridViewAdapter calendarGridViewAdapter;
    private CalendarListViewAdapter calendarListViewAdapter;

    private Bottom bottomActivity;
    private Top topActivity;
    private SubMenuTop subMenuTop;

    private ArrayList<CalendarListRealDTO> calendarDTOArrayList;
    private ArrayList<CalendarListRealDTO> calendarListRealDTOArrayList;
    private ArrayList<CalendarListDTO> calendarListDTOArrayList;
    private ArrayList<CalendarListContentDTO> calendarListContentDTOArrayList;

    String subCode;
    int subChildCode;

    private void regObj() {
        this.binding.nextBt.setOnClickListener(this);
        this.binding.backBt.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_calendar);
        this.binding.setCalendar(this);

        this.init();
        this.regObj();
    }

    private void init() {
        this.cm = new CalendarModule(this, this);
        this.dayList = new ArrayList<>();

        Intent intent = new Intent(this.getIntent());
        this.subCode = intent.getStringExtra("subCode");
        this.subChildCode = intent.getIntExtra("subChildCode",-1);

        this.topActivity = new Top(getLayoutInflater(), R.id.top, this, this , this.subCode);
        this.subMenuTop = new SubMenuTop(getLayoutInflater(), R.id.subTop, this, this , this.subCode , this.subChildCode , null);
        this.subMenuTop.setScrollPosition(this.subChildCode);

        this.bottomActivity = new Bottom(getLayoutInflater(), R.id.bottom, this, this);


        this.calendarDTOArrayList = new ArrayList<>();
        this.calendarListRealDTOArrayList = new ArrayList<>();
        this.calendarListDTOArrayList = new ArrayList<>();
        this.calendarListContentDTOArrayList = new ArrayList<>();

        //시작날짜 가져오기.
        this.binding.thisMonth.setText(this.cm.getStrRealDate());
        this.setDayList(0);

        this.getData();
    }

    private void setDayList(int count) {

        if (this.dayList != null) this.dayList.clear();
        this.dayList = this.cm.getCalendar(this.binding.thisMonth.getText().toString(), count);
        this.binding.thisMonth.setText(this.cm.nowDateStr);

    }


    private void getData() {
        String date = this.binding.thisMonth.getText().toString().replace(".", "-");

        AndroidNetworking.post(Global.main_url + Global.getUrls.get("getSchedule"))
                .addBodyParameter("date", date)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response=", response.toString());

                        if (!response.isNull("resultList")) {

                            try {
                                JSONArray jsonArray = response.getJSONArray("resultList");
                                for (int i = 0, j = jsonArray.length(); i < j; i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    calendarListRealDTOArrayList.add(new CalendarListRealDTO(true, 0, "", obj.getString("date"), ""));
                                    JSONArray date_vals = obj.getJSONArray("date_val");

                                    for (int k = 0, l = date_vals.length(); k < l; k++) {
                                        JSONObject child_obj = date_vals.getJSONObject(k);
                                        calendarListRealDTOArrayList.add(new CalendarListRealDTO(false, child_obj.getInt("sid"), child_obj.getString("subject"),
                                                child_obj.getString("period"), child_obj.getString("place")));
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Success
                            gridViewAdapterReload();
                        }

                        //listViewAdapterReload();


                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void listViewAdapterReload() {

//        this.calendarListContentDTOArrayList.add(new CalendarListContentDTO(0, "제 5차 상임이사회"));
//        this.calendarListContentDTOArrayList.add(new CalendarListContentDTO(0, "제 5차 상임이사회제 5차 상임이사회제 5차 상\n" +
//                "제 5차 상임이사회제 5차 상임이사회제 5차 상"));
//
//        this.calendarListDTOArrayList.add(new CalendarListDTO("07-07(화)", this.calendarListContentDTOArrayList));
//
//        this.calendarListContentDTOArrayList.clear();
//        this.calendarListContentDTOArrayList.add(new CalendarListContentDTO(0, "제 6차 상임이사회"));
//        this.calendarListContentDTOArrayList.add(new CalendarListContentDTO(0, "제 6차 상임이사회제 5차 상임이사회제 6차 상\n" +
//                "제 6차 상임이사회제 6차 상임이사회제 6차 상"));
//
//        this.calendarListDTOArrayList.add(new CalendarListDTO("07-09(화)", this.calendarListContentDTOArrayList));
//
//
//        for (int i = 0, j = this.calendarListDTOArrayList.size(); i < j; i++) {
//            CalendarListDTO row = this.calendarListDTOArrayList.get(i);
//            this.calendarListRealDTOArrayList.add(new CalendarListRealDTO(true, 0, row.getDate_txt()));
//
//            for (int k = 0, l = row.getDate_val().size(); k < l; k++) {
//                CalendarListContentDTO r = row.getDate_val().get(k);
//                this.calendarListRealDTOArrayList.add(new CalendarListRealDTO(false, r.getSid(), r.getTitle()));
//            }
//        }
//
//        this.calendarListViewAdapter = new CalendarListViewAdapter(this, this,
//                getLayoutInflater(), this.calendarListRealDTOArrayList);
//        this.binding.calendarListView.setAdapter(this.calendarListViewAdapter);

    }

    private void gridViewAdapterReload() {
        this.binding.gridview.post(new Runnable() {
            @Override
            public void run() {

                calendarGridViewAdapter = new CalendarGridViewAdapter(dayList, calendarListRealDTOArrayList, getApplicationContext(), getLayoutInflater(),
                        binding.gridview.getHeight() / 7, binding.thisMonth.getText().toString());
                binding.gridview.setAdapter(calendarGridViewAdapter);

            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.nextBt:
                //this.dayList = this.cm.changeCalendar(this.binding.thisMonth.getText().toString(),1);
                this.setDayList(1);
                break;

            case R.id.backBt:
                //this.dayList = this.cm.changeCalendar(this.binding.thisMonth.getText().toString(),-1);
                this.setDayList(-1);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            assert data != null;

            if (requestCode == CalendarActivity.GRIDVIEW_CODE) {

                this.subChildCode = data.getIntExtra("subChildCode",-1);

                if (this.subChildCode == 4)return;

                Intent intent = new Intent(this , CalendarActivity.class);
                intent.putExtra("subCode", Global.MENU3);
                intent.putExtra("subChildCode",this.subChildCode);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        }
    }

}