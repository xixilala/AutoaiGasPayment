package com.autoai.gaspayment.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.autoai.gaspayment.R;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "BBB";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_signin_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserActivity.class));
            }
        });

        MyApplication.getInstance().mMyLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "onChanged: 数据变化");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Main_onPause: ");
    }
}