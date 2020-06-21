package com.autoai.gaspayment.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.autoai.gaspayment.R;
import com.autoai.gaspayment.adapter.SmartAddGasStationListAdapter;
import com.autoai.gaspayment.adapter.SmartPopSelectAdapter;
import com.autoai.gaspayment.base.BaseFragment;
import com.autoai.gaspayment.base.BaseNavigationFragment;
import com.autoai.gaspayment.bean.SmartSelectBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * User: nxp
 * Date: 2020/6/11
 * Time: 16:07
 * Describe:搜索加油站结果页
 */
public class SearchGasStationResultFragment extends BaseNavigationFragment {


    @BindView(R.id.tv_gas_num)
    TextView tvGasNum;
    @BindView(R.id.tv_gas_branch)
    TextView tvGasBranch;
    @BindView(R.id.tv_gas_nearest)
    TextView tvGasNearest;
    @BindView(R.id.tv_gas_cheapest)
    TextView tvGasCheapest;
    @BindView(R.id.rv_gasStation_list)
    RecyclerView rvGasStationList;
    @BindView(R.id.view_smart_select_line)
    View viewSmartSelectLine;
    @BindView(R.id.ll_no_datas_parent)
    LinearLayout llNoDatasParent;
    @BindView(R.id.ll_loading_parent)
    LinearLayout llLoadingParent;
    @BindView(R.id.ll_no_net_parent)
    LinearLayout llNoNetParent;
    @BindView(R.id.btn_refresh)
    Button btnRefresh;
    PopupWindow mSmartSelectPop;
    SmartPopSelectAdapter mSmartPopSelectAdapter;
    RecyclerView mSmartPopSelectRv;
    //标记弹窗是选择加油还是选择品牌
    private boolean mIsGasNum;
    //选择油号，品牌点击事件监听
    private SmartPopSelectAdapter.ItemClickListener mSelectGasNumItemClickListener;
    private SmartPopSelectAdapter.ItemClickListener mSelectBranchItemClickListener;
    //加油站列表行布局点击事件
    private SmartAddGasStationListAdapter.ItemClickListener mGasStationListItemClickListener;
    private List<SmartSelectBean> mGasoNums;
    private List<SmartSelectBean> mGasoBranchs;
    //请求加油站列表状态，1.代表请求中 2.展示加油站列表 3.无符合要求的加油站 4.无网络
    private int mGasStationState;

    public static SearchGasStationResultFragment newInstance(String s) {
        SearchGasStationResultFragment fragment = new SearchGasStationResultFragment();
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
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_station_result;
    }

    @Override
    protected void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvGasStationList.setLayoutManager(linearLayoutManager);
        mSelectGasNumItemClickListener = new SmartPopSelectAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                tvGasNum.setText(mGasoNums.get(position).getValue());
                mSmartSelectPop.dismiss();
            }
        };
        mSelectBranchItemClickListener = new SmartPopSelectAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                tvGasBranch.setText(mGasoBranchs.get(position).getValue());
                mSmartSelectPop.dismiss();
            }
        };
        mGasStationListItemClickListener = new SmartAddGasStationListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int Position) {
                Navigation.findNavController(view).navigate(R.id.payment_main_fragment_to_order_payment_fragment_main);
            }
        };

        //初始化加油站列表
        SmartAddGasStationListAdapter adapter = new SmartAddGasStationListAdapter(getActivity(), mGasStationListItemClickListener);
        List<String> dates = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            dates.add("" + i);
        }
        adapter.setDates(dates);
        rvGasStationList.setAdapter(adapter);
        //初始化可选择油号列表数据
        mGasoNums = new ArrayList<>();
        mGasoNums.clear();
        mGasoNums.add(new SmartSelectBean("92#", false));
        mGasoNums.add(new SmartSelectBean("95#", false));
        mGasoNums.add(new SmartSelectBean("95#", false));
        mGasoNums.add(new SmartSelectBean("90#", false));
        mGasoNums.add(new SmartSelectBean("101#", false));
        //初始化可选择油品牌列表数据
        mGasoBranchs = new ArrayList<>();
        mGasoBranchs.clear();
        mGasoBranchs.add(new SmartSelectBean("全部", false));
        mGasoBranchs.add(new SmartSelectBean("中石化", false));
        mGasoBranchs.add(new SmartSelectBean("中石油", false));
        mGasoBranchs.add(new SmartSelectBean("壳牌", false));
        mGasoBranchs.add(new SmartSelectBean("其他", false));
    }

    @Override
    protected void lazyLoadData() {

    }

    @OnClick({R.id.tv_gas_num, R.id.tv_gas_branch, R.id.tv_gas_nearest, R.id.tv_gas_cheapest})
    public void onViewClicked(View view) {
        setSelectedTextColor(view.getId());
        switch (view.getId()) {
            case R.id.tv_gas_num:
                mIsGasNum = true;
                showPopupWindow(mGasoNums, mSelectGasNumItemClickListener);
                break;
            case R.id.tv_gas_branch:
                mIsGasNum = false;
                showPopupWindow(mGasoBranchs, mSelectBranchItemClickListener);
                break;
            case R.id.tv_gas_nearest:
                mGasStationState = 1;
                setNoGasoStationList();
                break;
            case R.id.tv_gas_cheapest:
                mGasStationState = 2;
                setNoGasoStationList();
                break;
        }
    }

    private void setSelectedTextColor(int id){
        switch (id){
            case R.id.tv_gas_num:
                tvGasNum.setTextColor(getActivity().getColor(R.color.FF4281FF));
                tvGasBranch.setTextColor(getActivity().getColor(R.color.ffc8c8c8));
                tvGasBranch.setCompoundDrawables(null,null,getTvRightDrawble(2),null);
                tvGasNearest.setTextColor(getActivity().getColor(R.color.ffc8c8c8));
                tvGasCheapest.setTextColor(getActivity().getColor(R.color.ffc8c8c8));
                break;
            case R.id.tv_gas_branch:
                tvGasNum.setTextColor(getActivity().getColor(R.color.ffc8c8c8));
                tvGasNum.setCompoundDrawables(null,null,getTvRightDrawble(2),null);
                tvGasBranch.setTextColor(getActivity().getColor(R.color.FF4281FF));
                tvGasNearest.setTextColor(getActivity().getColor(R.color.ffc8c8c8));
                tvGasCheapest.setTextColor(getActivity().getColor(R.color.ffc8c8c8));
                break;
            case R.id.tv_gas_nearest:
                tvGasNum.setTextColor(getActivity().getColor(R.color.ffc8c8c8));
                tvGasNum.setCompoundDrawables(null,null,getTvRightDrawble(2),null);
                tvGasBranch.setTextColor(getActivity().getColor(R.color.ffc8c8c8));
                tvGasBranch.setCompoundDrawables(null,null,getTvRightDrawble(2),null);
                tvGasNearest.setTextColor(getActivity().getColor(R.color.FF4281FF));
                tvGasCheapest.setTextColor(getActivity().getColor(R.color.ffc8c8c8));
                break;
            case R.id.tv_gas_cheapest:
                tvGasNum.setTextColor(getActivity().getColor(R.color.ffc8c8c8));
                tvGasNum.setCompoundDrawables(null,null,getTvRightDrawble(2),null);
                tvGasBranch.setTextColor(getActivity().getColor(R.color.ffc8c8c8));
                tvGasBranch.setCompoundDrawables(null,null,getTvRightDrawble(2),null);
                tvGasNearest.setTextColor(getActivity().getColor(R.color.ffc8c8c8));
                tvGasCheapest.setTextColor(getActivity().getColor(R.color.FF4281FF));
                break;
        }
    }

    private void showPopupWindow(List<SmartSelectBean> dates, SmartPopSelectAdapter.ItemClickListener itemClickListener){
        if (mSmartSelectPop == null){
            View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_select, null);
            mSmartPopSelectRv = contentView.findViewById(R.id.rv_smart_pop_select);
            mSmartSelectPop = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            mSmartSelectPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (mIsGasNum){
                        tvGasNum.setCompoundDrawables(null,null,getTvRightDrawble(4),null);
                    } else {
                        tvGasBranch.setCompoundDrawables(null,null,getTvRightDrawble(4),null);
                    }
                }
            });
            mSmartPopSelectAdapter = new SmartPopSelectAdapter(getActivity());
            mSmartPopSelectRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        if (mIsGasNum){
            tvGasNum.setCompoundDrawables(null,null,getTvRightDrawble(3),null);
        } else {
            tvGasBranch.setCompoundDrawables(null,null,getTvRightDrawble(3),null);
        }
        mSmartPopSelectAdapter.setItemClickListener(itemClickListener);
        mSmartPopSelectAdapter.setDates(dates);
        mSmartPopSelectRv.setAdapter(mSmartPopSelectAdapter);
        mSmartSelectPop.showAsDropDown(viewSmartSelectLine,20,0);
    }

    /**
     * 获取删选文字右侧图标
     * @param tag 获取图标样式
     *            1.代表白色向上
     *            2.代表白色乡下
     *            3.代表蓝色向上
     *            4.代表蓝色向下
     * @return
     */
    private Drawable getTvRightDrawble(int tag){
        Drawable drawable = null;
        switch (tag){
            case 1:
                drawable = getResources().getDrawable(R.mipmap.pull_up);
                break;
            case 2:
                drawable = getResources().getDrawable(R.mipmap.pull_down);
                break;
            case 3:
                drawable = getResources().getDrawable(R.mipmap.pull_up_blue);
                break;
            case 4:
                drawable = getResources().getDrawable(R.mipmap.pull_down_blue);
                break;
        }
        if (drawable != null){
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        }
        return drawable;
    }

    /**
     * 根据加油站列表状态不同，展示不同布局
     * 请求加油站列表状态，1.代表请求中 2.展示加油站列表 3.无符合要求的加油站 4.无网络
     */
    private void setNoGasoStationList(){
        switch (mGasStationState){
            case 1:
                llNoDatasParent.setVisibility(View.GONE);
                rvGasStationList.setVisibility(View.GONE);
                llLoadingParent.setVisibility(View.VISIBLE);
                llNoNetParent.setVisibility(View.GONE);
                break;
            case 2:
                llNoDatasParent.setVisibility(View.GONE);
                rvGasStationList.setVisibility(View.VISIBLE);
                llLoadingParent.setVisibility(View.GONE);
                llNoNetParent.setVisibility(View.GONE);
                break;
            case 3:
                llNoDatasParent.setVisibility(View.VISIBLE);
                rvGasStationList.setVisibility(View.GONE);
                llLoadingParent.setVisibility(View.GONE);
                llNoNetParent.setVisibility(View.GONE);
                break;
            case 4:
                llNoDatasParent.setVisibility(View.GONE);
                rvGasStationList.setVisibility(View.GONE);
                llLoadingParent.setVisibility(View.GONE);
                llNoNetParent.setVisibility(View.VISIBLE);
                break;

        }

    }

}
