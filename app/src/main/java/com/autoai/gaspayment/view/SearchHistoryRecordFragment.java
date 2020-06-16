package com.autoai.gaspayment.view;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.autoai.gaspayment.R;
import com.autoai.gaspayment.adapter.SearchHistoryAdapter;
import com.autoai.gaspayment.base.BaseFragment;
import com.autoai.gaspayment.utils.AlerDialogUtil;
import com.autoai.gaspayment.utils.SearchHistoryUtil;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Android Studio.
 * User: autoai
 * Date: 2020/6/11
 * Time: 15:51
 * Describe:搜索历史记录页面
 */
public class SearchHistoryRecordFragment extends BaseFragment {

    @BindView(R.id.tv_search_clear_history)
    TextView tvSearchClearHistory;
    @BindView(R.id.rv_search_history)
    RecyclerView rvSearchHistory;
    @BindView(R.id.rl_search_bottom_parent)
    RelativeLayout rlSearchBottomParent;
    @BindView(R.id.tv_search_no_history)
    TextView tvSearchNoHistory;
    private SearchHistoryAdapter mHistoryAdapter;
    private List<String> mHistoryDatas;
    private boolean mIsSearchDestination;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_history_record;
    }

    @Override
    protected void initData() {
        mHistoryAdapter = new SearchHistoryAdapter(getActivity());
        mHistoryDatas = new ArrayList<>();
        rvSearchHistory.setLayoutManager(new FlexboxLayoutManager(getActivity()));
        mHistoryAdapter.setItemclickListener(new SearchHistoryAdapter.ItemclickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (mIsSearchDestination){
                    Navigation.findNavController(v).navigate(R.id.history_record_to_destination_result);
                } else {
                    Navigation.findNavController(v).navigate(R.id.history_recoed_to_gas_station_result);
                }
            }
        });
    }

    @Override
    protected void lazyLoadData() {
        searchHistoryRecord();
    }

    @OnClick({R.id.tv_search_clear_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search_clear_history:
                AlertDialog dialog = AlerDialogUtil.getCustomDialog(getActivity(), new AlerDialogUtil.DialogButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick() {
                        SearchHistoryUtil.clearHistory(getActivity());
                        mHistoryDatas.clear();
                        mHistoryAdapter.notifyDataSetChanged();
                        tvSearchNoHistory.setVisibility(View.VISIBLE);
                        rvSearchHistory.setVisibility(View.GONE);
                        tvSearchClearHistory.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNegativeButtonClick() {

                    }
                }, getString(R.string.sure), getString(R.string.cancel), null);
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(null);
                break;
        }
    }

    void refreshHistoryRecord(String searchValue){
        SearchHistoryUtil.saveSearchHistory(getActivity(), searchValue);
        mHistoryDatas.clear();
        mHistoryDatas.addAll(SearchHistoryUtil.getSearchHistory(getActivity()));
        if (mHistoryDatas.size() == 0){
            tvSearchNoHistory.setVisibility(View.VISIBLE);
            tvSearchClearHistory.setVisibility(View.GONE);
            rvSearchHistory.setVisibility(View.GONE);
        } else {
            tvSearchNoHistory.setVisibility(View.GONE);
            rvSearchHistory.setVisibility(View.VISIBLE);
            tvSearchClearHistory.setVisibility(View.VISIBLE);
            mHistoryAdapter.notifyDataSetChanged();
        }
    }

    void setSearchStyle(boolean isSearchDestination){
        mIsSearchDestination = isSearchDestination;
    }

    private void searchHistoryRecord(){
        //搜索历史记录
        mHistoryDatas.clear();
        mHistoryDatas.addAll(SearchHistoryUtil.getSearchHistory(getActivity()));
        if (mHistoryDatas.size() == 0){
            tvSearchNoHistory.setVisibility(View.VISIBLE);
            rvSearchHistory.setVisibility(View.GONE);
            tvSearchClearHistory.setVisibility(View.GONE);
        } else {
            tvSearchNoHistory.setVisibility(View.GONE);
            rvSearchHistory.setVisibility(View.VISIBLE);
            tvSearchClearHistory.setVisibility(View.VISIBLE);
            mHistoryAdapter.setDatas(mHistoryDatas);
            rvSearchHistory.setAdapter(mHistoryAdapter);
        }
    }
}
