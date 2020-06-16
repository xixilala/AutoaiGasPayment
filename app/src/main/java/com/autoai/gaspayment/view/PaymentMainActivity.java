package com.autoai.gaspayment.view;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.autoai.gaspayment.R;
import com.autoai.gaspayment.base.BaseActivity;

/**
 * User: nxp111
 * Date: 2020/6/15
 * Time: 15:34
 * Describe:主Activity
 */
public class PaymentMainActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_payment_main;
    }

    @Override
    protected void initData() {

    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_payment_main);
//        if (fragment == null){
//            return false;
//        }
//        return NavHostFragment.findNavController(fragment).navigateUp();
//    }
}
