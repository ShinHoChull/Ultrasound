package com.m2comm.ultrasound.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.m2comm.ultrasound.MainActivity;
import com.m2comm.ultrasound.R;
import com.m2comm.ultrasound.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.binding = DataBindingUtil.setContentView(this , R.layout.activity_login);
        this.binding.setLogin(this);
        this.init();
        this.regObj();
    }

    private void regObj () {
        this.binding.loginBt.setOnClickListener(this);
    }

    private void init () {

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.loginBt:
                intent = new Intent(this , MainActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }
}