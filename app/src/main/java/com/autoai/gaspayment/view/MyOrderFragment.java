package com.autoai.gaspayment.view;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.autoai.gaspayment.R;
import com.autoai.gaspayment.adapter.MyOrderListAdapter;
import com.autoai.gaspayment.base.BaseActivity;
import com.autoai.gaspayment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的订单列表页
 */
public class MyOrderFragment extends BaseFragment {

    @BindView(R.id.rv_my_order)
    RecyclerView rvMyOrder;
    @BindView(R.id.tv_my_order_no_order)
    TextView tvMyOrderNoOrder;
    private MyOrderListAdapter.ItemCLickListener mItemCLickListener;

    public static MyOrderFragment newInstance(String s) {
        MyOrderFragment fragment = new MyOrderFragment();
        Bundle args = new Bundle();
        args.putString("testS", s);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            String s = args.getString("testS");
            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_order;
    }

    @Override
    protected void initData() {
        rvMyOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        mItemCLickListener = new MyOrderListAdapter.ItemCLickListener() {
            @Override
            public void onItemClick(int position) {
                showOrderDetailPop();
            }
        };

        MyOrderListAdapter adpter = new MyOrderListAdapter(getActivity());
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add("" + i);
        }
        adpter.setItemCLickListener(mItemCLickListener);
        adpter.setDatas(datas);
        rvMyOrder.setAdapter(adpter);
        //无订单
        isShowNoOrderPage(true);
    }

    @Override
    protected void lazyLoadData() {

    }

    private void showOrderDetailPop(){
        View v = getLayoutInflater().inflate(R.layout.dialog_custom_alert_order_detail,null);
        ImageView ivDetailDialogClose = v.findViewById(R.id.iv_detail_dialog_close);
        TextView tvDetailDialogRealPrice = v.findViewById(R.id.tv_detail_dialog_real_price);
        TextView tvDetailDialogAddGasPrice = v.findViewById(R.id.tv_detail_dialog_add_gas_price);
        TextView tvDetailDialogCheaperPrice = v.findViewById(R.id.tv_detail_dialog_cheaper_price);
        TextView tvDetailDialogGasNum = v.findViewById(R.id.tv_detail_dialog_gas_num);
        TextView detailDialogOrderNum = v.findViewById(R.id.detail_dialog_order_num);
        TextView detailDialogOrderState = v.findViewById(R.id.detail_dialog_order_state);
        TextView detailDialogOrderName = v.findViewById(R.id.detail_dialog_order_name);
        TextView detailDialogOrderLocation = v.findViewById(R.id.detail_dialog_order_location);
        TextView detailDialogGunNum = v.findViewById(R.id.detail_dialog_gun_num);
        TextView detailDialogGasNum = v.findViewById(R.id.detail_dialog_gas_num);
        TextView detailDialogPayStyle = v.findViewById(R.id.detail_dialog_pay_style);
        View parent = ((BaseActivity)getActivity()).getParentView();
        PopupWindow orderDetailPop = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        orderDetailPop.setOutsideTouchable(true);
        orderDetailPop.showAtLocation(parent, Gravity.CENTER, 0, 0);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha=0.3f;
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);
        orderDetailPop.setOnDismissListener(new PopupWindow.OnDismissListener(){
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha=1.0f;
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getActivity().getWindow().setAttributes(lp);
            }
        });
        ivDetailDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderDetailPop.dismiss();
            }
        });
    }

    private void isShowNoOrderPage(boolean isShowNoOrder){
        if (isShowNoOrder){
            rvMyOrder.setVisibility(View.GONE);
            tvMyOrderNoOrder.setVisibility(View.VISIBLE);
        } else {
            rvMyOrder.setVisibility(View.VISIBLE);
            tvMyOrderNoOrder.setVisibility(View.GONE);
        }
    }
}
