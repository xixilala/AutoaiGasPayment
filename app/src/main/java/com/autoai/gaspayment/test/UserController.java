package com.autoai.gaspayment.test;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Created by Android Studio.
 * User: autoai
 * Date: 2020/6/12
 * Time: 11:39
 * Describe:
 */
public class UserController implements LifecycleObserver {

    private final String TAG = "UserController";
    private Lifecycle mLifecycle;

    public UserController(Lifecycle lifecycle) {
        mLifecycle = lifecycle;
    }

    private void log(String msg) {
        Log.d(TAG, msg);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate(){
        log("onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        log("onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        log("onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        log("onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        log("onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        log("onDestroy");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onAny() {
        log("onAny:" + mLifecycle.getCurrentState());
    }
}
