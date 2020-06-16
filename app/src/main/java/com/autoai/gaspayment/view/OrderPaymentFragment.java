package com.autoai.gaspayment.view;

import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.autoai.gaspayment.R;
import com.autoai.gaspayment.adapter.PaymentFragmentsAdapter;
import com.autoai.gaspayment.base.BaseFragment;
import com.autoai.gaspayment.test.MainViewModel;
import com.autoai.gaspayment.widget.NoPreloadViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 智慧加油下单页面
 */
public class OrderPaymentFragment extends BaseFragment {

    @BindView(R.id.vp_fragment_order_select)
    NoPreloadViewPager vpFragmentOrderSelect;
    @BindView(R.id.tv_gas_detail_name)
    TextView tvGasDetailName;
    @BindView(R.id.tv_gas_detail_location)
    TextView tvGasDetailLocation;
    @BindView(R.id.tv_gas_detail_price)
    TextView tvGasDetailPrice;
    @BindView(R.id.tv_gas_detail_station_price)
    TextView tvGasDetailStationPrice;
    @BindView(R.id.tv_gas_detail_international_price)
    TextView tvGasDetailInternationalPrice;
    @BindView(R.id.tv_gas_detail_branch)
    TextView tvGasDetailBranch;
    @BindView(R.id.tv_gas_detail_ticket_mode)
    TextView tvGasDetailTicketMode;
    @BindView(R.id.tv_gas_detail_ticket_explain)
    TextView tvGasDetailTicketExplain;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_payment;
    }

    @Override
    protected void initData() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(OrderSelectFirstStepFragment.newInstance("加载智能加油"));
        fragments.add(OrderSelectSecondStepFragment.newInstance("我的订单"));
        vpFragmentOrderSelect.setOffscreenPageLimit(0);
        PaymentFragmentsAdapter adapter = new PaymentFragmentsAdapter(getChildFragmentManager(), fragments);
        vpFragmentOrderSelect.setAdapter(adapter);

    }

    @Override
    protected void lazyLoadData() {

    }

    public void nextStep(){
        vpFragmentOrderSelect.setCurrentItem(1);
    }

    public void preStep(){
        vpFragmentOrderSelect.setCurrentItem(0);
    }

    public void toOrderCodePayFragment(){
        Navigation.findNavController(getView()).navigate(R.id.order_payment_fragment_main_to_order_code_pay_fragment);
    }

    public static class ToOrderPaymentFragmentBean {
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
