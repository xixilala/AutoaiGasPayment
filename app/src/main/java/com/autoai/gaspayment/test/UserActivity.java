package com.autoai.gaspayment.test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.autoai.gaspayment.R;

/**
 * Created by Android Studio.
 * User: autoai
 * Date: 2020/6/12
 * Time: 11:37
 * Describe:
 */
public class UserActivity extends FragmentActivity {

    private final String TAG = "UserActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLifecycle().addObserver(new UserController(getLifecycle()));
        getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
            public void a(){
                Log.d(TAG, "a: ssssss");
            }
        });
        log("onCreate");
        findViewById(R.id.btn_signin_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().mMyLiveData.setValue("1111111");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        log("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log("onResume");
    }

    @Override
    protected void onPause() {
        log("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        log("onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        log("onDestroy");
        super.onDestroy();
    }

    private void log(String msg) {
        Log.i("BBB", msg);
    }
}
