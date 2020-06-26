package com.autoai.gaspayment.view;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.autoai.gaspayment.R;
import com.autoai.gaspayment.base.BaseFragment;
import com.autoai.gaspayment.base.BaseNavigationFragment;
import com.autoai.gaspayment.utils.AlerDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by nxp
 * Date: 2020/6/7
 * Time: 17:18
 * Describe:扫码支付页面
 */
public class OrderCodePayFragment extends BaseNavigationFragment {
    @BindView(R.id.title_back_click)
    View titleBackClick;
    @BindView(R.id.iv_pay_code)
    ImageView ivPayCode;
    @BindView(R.id.tv_order_payfragment_code_hint)
    TextView tvOrderPayfragmentCodeHint;
    @BindView(R.id.tv_order_payfragment_name)
    TextView tvOrderPayfragmentName;
    @BindView(R.id.tv_order_payfragment_price_num)
    TextView tvOrderPayfragmentPriceNum;
    @BindView(R.id.tv_order_payfragment_time)
    TextView tvOrderPayfragmentTime;
    @BindView(R.id.view_pay_code_mask)
    View viewPayCodeMask;
    @BindView(R.id.iv_pay_code_refresh)
    ImageView ivPayCodeRefresh;
    @BindView(R.id.tv_order_pay_reorder)
    Button tvOrderPayReorder;
    @BindView(R.id.ll_order_payfragment_left_reorder_parent)
    LinearLayout llOrderPayfragmentLeftReorderParent;
    @BindView(R.id.ll_order_payfragment_left_code_parent)
    LinearLayout llOrderPayfragmentLeftCodeParent;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_pay;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void lazyLoadData() {

    }


    @OnClick({R.id.title_back_click, R.id.iv_pay_code_refresh, R.id.tv_order_payfragment_code_hint, R.id.tv_order_payfragment_name,R.id.tv_order_pay_reorder})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.title_back_click:
                Navigation.findNavController(view).popBackStack();
                break;
            case R.id.iv_pay_code_refresh:
                //刷新二维码
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_refresh_code);
                LinearInterpolator lin = new LinearInterpolator();
                animation.setInterpolator(lin);
                ivPayCodeRefresh.startAnimation(animation);
                break;
            case R.id.tv_order_payfragment_code_hint:
                //二维码刷新成功
                ivPayCodeRefresh.clearAnimation();
                setIsShowRefresh(false);
                break;
            case R.id.tv_order_payfragment_name:
                //支付成功弹窗
                AlertDialog dialog = AlerDialogUtil.getPaySuccDialog(getActivity(), new AlerDialogUtil.DialogButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick() {

                    }

                    @Override
                    public void onNegativeButtonClick() {

                    }
                });
                dialog.show();
                Window window = dialog.getWindow();
                if (window != null){
                    window.setBackgroundDrawable(null);
                    WindowManager.LayoutParams lp = window.getAttributes();
                    window.setBackgroundDrawable(null);
                    lp.width = 790;
                    lp.height = 512;
                    dialog.getWindow().setAttributes(lp);
                }
                //订单过期
                showReOrderPage();
                //二维码过期
                setIsShowRefresh(true);
                break;
            case R.id.tv_order_pay_reorder:
                break;
        }

    }

    /**
     * 是否展示二维码过期页面
     * @param isShowRefresh
     */
    private void setIsShowRefresh(boolean isShowRefresh){
        if (isShowRefresh){
            viewPayCodeMask.setVisibility(View.VISIBLE);
            ivPayCodeRefresh.setVisibility(View.VISIBLE);
        } else {
            viewPayCodeMask.setVisibility(View.GONE);
            ivPayCodeRefresh.setVisibility(View.GONE);
        }
    }

    /**
     * 展示重新下单页面
     */
    private void showReOrderPage(){
        llOrderPayfragmentLeftReorderParent.setVisibility(View.VISIBLE);
        llOrderPayfragmentLeftCodeParent.setVisibility(View.GONE);
    }
}
