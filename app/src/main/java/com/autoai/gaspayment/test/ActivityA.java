package com.autoai.gaspayment.test;

import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.autoai.gaspayment.R;
import com.autoai.gaspayment.base.BaseActivity;

/**
 * User: nxp
 * Date: 2020/6/15
 * Time: 14:34
 * Describe:
 */
public class ActivityA extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        findViewById(R.id.btn_signin_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getViewModel(MainViewModel.class).changeUser();
            }
        });
        //添加数据更改监听器
        getViewModel(MainViewModel.class).getUserMutableLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Toast.makeText(ActivityA.this, "user" + user.getUserName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
