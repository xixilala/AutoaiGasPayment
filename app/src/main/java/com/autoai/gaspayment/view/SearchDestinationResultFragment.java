package com.autoai.gaspayment.view;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.autoai.gaspayment.R;
import com.autoai.gaspayment.adapter.SearchDestinationResultAdapter;
import com.autoai.gaspayment.base.BaseNavigationFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * User: nxp
 * Date: 2020/6/11
 * Time: 16:17
 * Describe:搜索目的地结果页
 */
public class SearchDestinationResultFragment extends BaseNavigationFragment {


    @BindView(R.id.rv_search_destination_result)
    RecyclerView rvSearchDestinationResult;

    public static SearchDestinationResultFragment newInstance(String s){
        SearchDestinationResultFragment fragment = new SearchDestinationResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString("SearchDestinationResultFragment",s);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_destination_result;
    }

    @Override
    protected void initData() {
        SearchDestinationResultAdapter adapter = new SearchDestinationResultAdapter(getActivity());
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            datas.add("" + i);
        }
        adapter.setDatas(datas);
        SearchDestinationResultAdapter.ItemClickListener itemClickListener = (position) ->{
            ((SearchFragment) getParentFragment()).toStationResult();
        };
        adapter.setItemClickListener(itemClickListener);
        rvSearchDestinationResult.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSearchDestinationResult.setAdapter(adapter);
    }

    @Override
    protected void lazyLoadData() {

    }

}
