package com.autoai.gaspayment.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;
import com.autoai.gaspayment.R;
import com.autoai.gaspayment.adapter.PaymentFragmentsAdapter;
import com.autoai.gaspayment.base.BaseFragment;
import com.autoai.gaspayment.utils.AlerDialogUtil;
import com.autoai.gaspayment.widget.NoPreloadViewPager;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主fragment页面111
 */
public class PaymentMainFragment extends BaseFragment {

    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.ll_smart_add_sagoline)
    LinearLayout llSmartAddSagoline;
    @BindView(R.id.ll_my_order)
    LinearLayout llMyOrder;
    @BindView(R.id.viewpager)
    NoPreloadViewPager viewpager;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.ll_left_parent)
    LinearLayout llLeftParent;
    @BindView(R.id.tv_my_order)
    TextView tvMyOrder;
    @BindView(R.id.tv_smart_add_gas)
    TextView tvSmartAddGas;
    @BindView(R.id.iv_my_order)
    ImageView ivMyOrder;
    @BindView(R.id.iv_smart_add_sag)
    ImageView ivSmartAddSag;
    //是否登录
    private boolean isLogin = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_payment_main;
    }

    @Override
    protected void initData() {
        //未登录，提示登录
        if (!isLogin) {
            AlertDialog dialog = AlerDialogUtil.getCustomDialog(getActivity(), new AlerDialogUtil.DialogButtonClickListener() {
                @Override
                public void onPositiveButtonClick() {
                    Toast.makeText(getActivity(), "登录逻辑", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNegativeButtonClick() {
                    llTitle.setVisibility(View.VISIBLE);
                    llLeftParent.setVisibility(View.VISIBLE);
                    showPage();
                }
            }, getString(R.string.login), getString(R.string.cancel), null);
            dialog.show();
            dialog.getWindow().setBackgroundDrawable(null);
            llTitle.setVisibility(View.GONE);
            llLeftParent.setVisibility(View.GONE);
        } else {
            llTitle.setVisibility(View.VISIBLE);
            llLeftParent.setVisibility(View.VISIBLE);
            showPage();
        }
    }

    @Override
    protected void lazyLoadData() {

    }


    @OnClick({R.id.tv_search, R.id.ll_smart_add_sagoline, R.id.ll_my_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                Navigation.findNavController(view).navigate(R.id.payment_main_fragment_to_search_fragment);
                break;
            case R.id.ll_smart_add_sagoline:
                setNavigationBarState(false);
                break;
            case R.id.ll_my_order:
                setNavigationBarState(true);
                break;
        }
    }

    private void showPage(){
        setNavigationBarState(false);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(SmartAddGasolineFragment.newInstance("加载智能加油"));
        fragments.add(MyOrderFragment.newInstance("我的订单"));
        viewpager.setOffscreenPageLimit(0);
        PaymentFragmentsAdapter adapter = new PaymentFragmentsAdapter(getChildFragmentManager(), fragments);
        viewpager.setAdapter(adapter);
    }

    /***
     * 导航栏状态
     * @param isSelectOrderList 是否选中我的订单
     */
    private void setNavigationBarState(boolean isSelectOrderList){
        if (isSelectOrderList){
            //背景，文字，图标
            llMyOrder.setBackgroundColor(getActivity().getColor(R.color._242B47));
            tvMyOrder.setTextColor(getActivity().getColor(R.color.color_text_white));
            ivMyOrder.setImageResource(R.mipmap.left_order_selected);

            llSmartAddSagoline.setBackground(null);
            tvSmartAddGas.setTextColor(getActivity().getColor(R.color.ffc8c8c8));
            ivSmartAddSag.setImageResource(R.mipmap.left_smart_add_gas);
            viewpager.setCurrentItem(1, true);
        } else {
            llSmartAddSagoline.setBackgroundColor(getActivity().getColor(R.color._242B47));
            tvSmartAddGas.setTextColor(getActivity().getColor(R.color.color_text_white));
            ivSmartAddSag.setImageResource(R.mipmap.left_smart_add_gas_selected);

            llMyOrder.setBackground(null);
            tvMyOrder.setTextColor(getActivity().getColor(R.color.ffc8c8c8));
            ivMyOrder.setImageResource(R.mipmap.left_order);
            viewpager.setCurrentItem(0,true);
        }
    }
}
