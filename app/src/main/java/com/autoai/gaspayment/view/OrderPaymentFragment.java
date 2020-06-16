package com.autoai.gaspayment.view;

import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import com.autoai.gaspayment.R;
import com.autoai.gaspayment.base.BaseFragment;
import com.autoai.gaspayment.test.MainViewModel;
import com.autoai.gaspayment.test.Person;

/**
 * 智慧加油下单页面
 */
public class OrderPaymentFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_payment;
    }

    @Override
    protected void initData() {
        getViewModel(MainViewModel.class).getIdMutableLeveData().observe(this, new Observer<ToOrderPaymentFragmentBean>() {
            @Override
            public void onChanged(ToOrderPaymentFragmentBean bean) {
                Navigation.findNavController(getView()).navigate(bean.getId());
            }
        });
    }

    @Override
    protected void lazyLoadData() {

    }
    public static class ToOrderPaymentFragmentBean{
        private int id;

        public ToOrderPaymentFragmentBean(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
