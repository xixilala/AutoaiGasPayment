package com.autoai.gaspayment.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.autoai.gaspayment.R;
import com.autoai.gaspayment.adapter.PaymentFragmentsAdapter;
import com.autoai.gaspayment.adapter.SearchHistoryAdapter;
import com.autoai.gaspayment.base.BaseNavigationFragment;
import com.autoai.gaspayment.utils.AlerDialogUtil;
import com.autoai.gaspayment.utils.SearchHistoryUtil;
import com.autoai.gaspayment.widget.NoPreloadViewPager;
import com.autoai.gaspayment.widget.NoScrollViewPager;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.autoai.gaspayment.utils.SearchHistoryUtil.TYPEY_GAS_STATION;
import static com.autoai.gaspayment.utils.SearchHistoryUtil.TYPE_DESTINATION;

/**
 * User: nxp
 * Date: 2020/6/15
 * Time: 15:47
 * Describe:搜索历史记录页面
 */
public class SearchFragment extends BaseNavigationFragment {
    @BindView(R.id.title_back_click)
    View titleBackClick;
    @BindView(R.id.tv_search_select_style)
    TextView tvSearchSelectStyle;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search_clear_history)
    TextView tvSearchClearHistory;
    @BindView(R.id.rv_search_history)
    RecyclerView rvSearchHistory;
    @BindView(R.id.rl_search_history_parent)
    RelativeLayout rlSearchBottomParent;
    @BindView(R.id.tv_search_no_history)
    TextView tvSearchNoHistory;
    @BindView(R.id.vp_search_result)
    NoScrollViewPager vpSearchResult;
    //搜索框父容器
    @BindView(R.id.ll_search_title_parent)
    LinearLayout llSearchTitleParent;
    @BindView(R.id.ll_search_destination_result_parent)
    LinearLayout llSearchDestinationResultParent;
    @BindView(R.id.tv_search_destination_result_location)
    TextView tvSearchDestinationResultLocation;


    //搜索历史记录
    private SearchHistoryAdapter mHistoryAdapter;
    private List<String> mHistoryDatas;
    //选择搜索方式
    private String mSelectType;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initData() {
        Drawable drawable = getResources().getDrawable(R.mipmap.search);
        drawable.setBounds(0, 0, 28, 28);
        etSearch.setHint(getString(R.string.search_gas_station));
        mSelectType = TYPEY_GAS_STATION;
        etSearch.setCompoundDrawables(drawable, null, null, null);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                //监听确认键
                if (!TextUtils.isEmpty(etSearch.getText().toString())) {
                    refreshHistoryRecord(etSearch.getText().toString(), mSelectType);
                    etSearch.setText("");
                    showSearchResultPage();
                }
                return false;
            }
        });
        //点击软键盘外部，收起软键盘
        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager manager = ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
                    if (manager != null)
                        manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

        //初始化历史记录
        mHistoryAdapter = new SearchHistoryAdapter(getActivity());
        mHistoryDatas = new ArrayList<>();
        rvSearchHistory.setLayoutManager(new FlexboxLayoutManager(getActivity()));
        mHistoryAdapter.setItemclickListener(new SearchHistoryAdapter.ItemclickListener() {
            @Override
            public void onItemClick(View v, int position) {
                showSearchResultPage();
            }
        });

        searchHistoryRecord();
        initResultPage();
        //显示历史记录
        vpSearchResult.setVisibility(View.GONE);
        rlSearchBottomParent.setVisibility(View.VISIBLE);
        llSearchTitleParent.setVisibility(View.VISIBLE);
        llSearchDestinationResultParent.setVisibility(View.GONE);
    }

    @OnClick({R.id.title_back_click, R.id.tv_search_select_style, R.id.tv_search_clear_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back_click:
                Navigation.findNavController(view).popBackStack();
                break;
            case R.id.tv_search_select_style:
                InputMethodManager manager = ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
                if (manager != null)
                    manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                showSelectPopupWindow();
                break;
            case R.id.tv_search_clear_history:
                AlertDialog dialog = AlerDialogUtil.getCustomDialog(getActivity(), new AlerDialogUtil.DialogButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick() {
                        SearchHistoryUtil.clearHistory(getActivity(), mSelectType);
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
                Window window = dialog.getWindow();
                if (window != null){
                    WindowManager.LayoutParams lp = window.getAttributes();
                    window.setBackgroundDrawable(null);
                    lp.width = 790;
                    lp.height = 340;
                    dialog.getWindow().setAttributes(lp);
                }
                break;
        }
    }

    private void showSelectPopupWindow() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_select_search_style, null);
        PopupWindow searchTypePop = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        Button btnSearchStylegas = contentView.findViewById(R.id.btn_search_style_gas);
        Button btnSearchStyleDestination = contentView.findViewById(R.id.btn_search_style_destination);
        if (getString(R.string.search_gas_station).equals(tvSearchSelectStyle.getText())) {
            btnSearchStylegas.setSelected(true);
            btnSearchStyleDestination.setSelected(false);
        } else if (getString(R.string.search_destination).equals(tvSearchSelectStyle.getText())) {
            btnSearchStyleDestination.setSelected(true);
            btnSearchStylegas.setSelected(false);
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_search_style_gas) {
                    btnSearchStylegas.setSelected(true);
                    btnSearchStyleDestination.setSelected(false);
                    etSearch.setHint(getString(R.string.search_gas_station));
                    tvSearchSelectStyle.setText(getString(R.string.search_gas_station));
                    mSelectType = TYPEY_GAS_STATION;
                    searchHistoryRecord();
                    searchTypePop.dismiss();
                } else if (v.getId() == R.id.btn_search_style_destination) {
                    btnSearchStyleDestination.setSelected(true);
                    btnSearchStylegas.setSelected(false);
                    etSearch.setHint(getString(R.string.search_destination));
                    tvSearchSelectStyle.setText(getString(R.string.search_destination));
                    mSelectType = TYPE_DESTINATION;
                    searchHistoryRecord();
                    searchTypePop.dismiss();
                }
            }
        };
        btnSearchStylegas.setOnClickListener(listener);
        btnSearchStyleDestination.setOnClickListener(listener);

        searchTypePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Drawable drawable = getResources().getDrawable(R.mipmap.pull_down);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvSearchSelectStyle.setCompoundDrawables(null, null, drawable, null);
            }
        });
        Drawable drawableUp = getResources().getDrawable(R.mipmap.pull_up);
        drawableUp.setBounds(0, 0, drawableUp.getMinimumWidth(), drawableUp.getMinimumHeight());
        tvSearchSelectStyle.setCompoundDrawables(null, null, drawableUp, null);
        searchTypePop.showAsDropDown(tvSearchSelectStyle, 0, 10);
    }

    @Override
    protected void lazyLoadData() {

    }

    /**
     * 获取历史记录
     */
    private void searchHistoryRecord() {
        //搜索历史记录
        mHistoryDatas.clear();
        mHistoryDatas.addAll(SearchHistoryUtil.getSearchHistory(getActivity(), mSelectType));
        if (mHistoryDatas.size() == 0) {
            tvSearchNoHistory.setVisibility(View.VISIBLE);
            rvSearchHistory.setVisibility(View.GONE);
            tvSearchClearHistory.setVisibility(View.GONE);
        } else {
            tvSearchNoHistory.setVisibility(View.GONE);
            rvSearchHistory.setVisibility(View.VISIBLE);
            tvSearchClearHistory.setVisibility(View.VISIBLE);
        }
        mHistoryAdapter.setDatas(mHistoryDatas);
        rvSearchHistory.setAdapter(mHistoryAdapter);
    }

    /**
     * 添加历史记录并更新列表
     *
     * @param searchValue
     * @param type
     */
    private void refreshHistoryRecord(String searchValue, String type) {
        SearchHistoryUtil.saveSearchHistory(getActivity(), searchValue, type);
        mHistoryDatas.clear();
        mHistoryDatas.addAll(SearchHistoryUtil.getSearchHistory(getActivity(), mSelectType));
        if (mHistoryDatas.size() == 0) {
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

    /**
     * 初始化搜索结果页，分为
     */
    private void initResultPage(){
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(SearchGasStationResultFragment.newInstance("SearchGasStationResultFragment"));
        fragments.add(SearchDestinationResultFragment.newInstance("SearchDestinationResultFragment"));
        PaymentFragmentsAdapter adapter = new PaymentFragmentsAdapter(getChildFragmentManager(), fragments);
        vpSearchResult.setAdapter(adapter);
    }

    /**
     * 切换搜索油站页
     */
    public void toStationResult(){
        llSearchTitleParent.setVisibility(View.GONE);
        llSearchDestinationResultParent.setVisibility(View.VISIBLE);
        vpSearchResult.setCurrentItem(0);
    }

    private void showSearchResultPage(){
        if (TYPEY_GAS_STATION.equals(mSelectType)){
            //切换搜加油站结果页
            rlSearchBottomParent.setVisibility(View.GONE);
            vpSearchResult.setVisibility(View.VISIBLE);
            vpSearchResult.setCurrentItem(0);
        } else {
            //切换搜目的地结果页
            rlSearchBottomParent.setVisibility(View.GONE);
            vpSearchResult.setVisibility(View.VISIBLE);
            vpSearchResult.setCurrentItem(1);
        }
    }

}
