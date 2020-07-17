package com.m2comm.ultrasound.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.m2comm.ultrasound.Adapter.SubMenuGridViewAdapter;
import com.m2comm.ultrasound.DTO.MenuDTO;
import com.m2comm.ultrasound.R;
import com.m2comm.ultrasound.module.Global;

import java.util.ArrayList;

public class SubMenuActivity extends AppCompatActivity implements View.OnClickListener , AdapterView.OnItemClickListener {

    private GridView gridView;
    private LinearLayout parent;
    private SubMenuGridViewAdapter subMenuGridViewAdapter;
    private ArrayList<MenuDTO> menuDTOArrayList;
    private Global g;

    private String subCode;
    private int subChildCode;

    private void regObj () {
        this.parent.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_menu);
        this.init();
        this.regObj();
    }

    private void init () {

        this.g = new Global();
        this.gridView = findViewById(R.id.gridview);
        this.parent = findViewById(R.id.parent);

        Intent intent = getIntent();
        this.subCode = intent.getStringExtra("subCode");
        this.subChildCode = intent.getIntExtra("subChildCode",-1);

        this.menuDTOArrayList = new ArrayList<>();
        this.menuDTOArrayList = this.g.getMenu.get(this.subCode);

        if ( this.menuDTOArrayList.size() % 2 != 0 ) {
            this.menuDTOArrayList.add(new MenuDTO("",""));
        }

        this.subMenuGridViewAdapter = new SubMenuGridViewAdapter(this.menuDTOArrayList , this , getLayoutInflater());
        this.gridView.setAdapter(this.subMenuGridViewAdapter);
        this.gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("subChildCode",position);
        setResult(RESULT_OK , intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.parent:
                finish();
                break;

        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }
}