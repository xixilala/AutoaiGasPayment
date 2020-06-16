package com.autoai.gaspayment.view;

import android.view.View;
import android.widget.Button;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.autoai.gaspayment.R;
import com.autoai.gaspayment.adapter.OrderOtptionAdater;
import com.autoai.gaspayment.base.BaseFragment;
import com.autoai.gaspayment.bean.SmartSelectBean;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 下单第一步选择油号枪号
 */
public class OrderSelectFirstStepFragment extends BaseFragment {
    @BindView(R.id.title_back_click)
    View titleBackClick;
    @BindView(R.id.btn_orderselect_next_step)
    Button btnOrderselectNextStep;
    @BindView(R.id.rv_order_first_select)
    RecyclerView rvOrderFirstSelect;
    @BindView(R.id.btn_orderselect_reselection)
    Button btnOrderselectReselection;
    private List<SmartSelectBean> mGasNumSelectLists;
    private List<SmartSelectBean> mGunNumSelectLists;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_select_first_step;
    }

    @Override
    protected void initData() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == mGasNumSelectLists.size()){
                    return 4;
                } else {
                    return 1;
                }
            }
        });
        rvOrderFirstSelect.setLayoutManager(gridLayoutManager);
        mGasNumSelectLists = new ArrayList<>();
        mGunNumSelectLists = new ArrayList<>();
    }

    @Override
    protected void lazyLoadData() {
        //初始化可选择油号数据
        mGasNumSelectLists.clear();
        mGasNumSelectLists.add(new SmartSelectBean("油号", false));
        mGasNumSelectLists.add(new SmartSelectBean("90#", false));
        mGasNumSelectLists.add(new SmartSelectBean("92#", false));
        mGasNumSelectLists.add(new SmartSelectBean("95#", false));
        mGasNumSelectLists.add(new SmartSelectBean("98#", false));
        mGasNumSelectLists.add(new SmartSelectBean("101#", false));
        //初始化可选择抢号数据
        mGunNumSelectLists.clear();
        mGunNumSelectLists.add(new SmartSelectBean("枪号", false));
        for (int i = 1; i < 19; i++) {
            mGunNumSelectLists.add(new SmartSelectBean(i + "号", false));
        }
        OrderOtptionAdater adater = new OrderOtptionAdater(getActivity());
        adater.setDatas(mGasNumSelectLists, mGunNumSelectLists);
        rvOrderFirstSelect.setAdapter(adater);
    }

    @OnClick({R.id.title_back_click, R.id.btn_orderselect_next_step, R.id.btn_orderselect_reselection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back_click:
            case R.id.btn_orderselect_reselection:
                getActivity().finish();
                break;
            case R.id.btn_orderselect_next_step:
                Navigation.findNavController(view).navigate(R.id.action_orderFirstSelect_to_secondSelect);
                break;
        }
    }

}
