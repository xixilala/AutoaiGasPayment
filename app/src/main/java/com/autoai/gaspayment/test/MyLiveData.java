package com.autoai.gaspayment.test;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by Android Studio.
 * User: autoai
 * Date: 2020/6/12
 * Time: 14:00
 * Describe:
 */
public class MyLiveData extends LiveData<String> {

    private final String TAG = "BBB";
    @Override
    protected void setValue(String value) {
        super.setValue(value);
        Log.d(TAG, "setValue: " + value);
    }

    @Override
    protected void onActive() {
        super.onActive();
        Log.d(TAG, "onActive: ");
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        Log.d(TAG, "onInactive: ");
    }
}
