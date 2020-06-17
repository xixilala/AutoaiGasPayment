package com.autoai.gaspayment.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.autoai.gaspayment.R;
import com.autoai.gaspayment.base.BaseFragment;
import com.autoai.gaspayment.base.BaseNavigationFragment;

import butterknife.BindView;
import butterknife.OnClick;

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
    private SearchHistoryRecordFragment mSearchHistoryRecordFragment;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initData() {
        Drawable drawable=getResources().getDrawable(R.mipmap.search);
        drawable.setBounds(0,0,28,28);
        etSearch.setHint(getString(R.string.search_gas_station));
        etSearch.setCompoundDrawables(drawable,null,null,null);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                //监听确认键
                if (!TextUtils.isEmpty(etSearch.getText().toString())){
                    //TODO mSearchHistoryRecordFragment.refreshHistoryRecord(etSearch.getText().toString());
                    etSearch.setText("");
                }
                return false;
            }
        });
        //点击软键盘外部，收起软键盘
        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    InputMethodManager manager = ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
                    if (manager != null)
                        manager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }

    @OnClick({R.id.title_back_click, R.id.tv_search_select_style})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back_click:
                //TODO finish();
                break;
            case R.id.tv_search_select_style:
                InputMethodManager manager = ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
                if (manager != null)
                    manager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                showSelectPopupWindow();
                break;
        }
    }

    private void showSelectPopupWindow(){
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_select_search_style, null);
        PopupWindow searchTypePop = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        Button btnSearchStylegas = contentView.findViewById(R.id.btn_search_style_gas);
        Button btnSearchStyleDestination = contentView.findViewById(R.id.btn_search_style_destination);
        if (getString(R.string.search_gas_station).equals(tvSearchSelectStyle.getText())){
            btnSearchStylegas.setSelected(true);
            btnSearchStyleDestination.setSelected(false);
        } else if (getString(R.string.search_destination).equals(tvSearchSelectStyle.getText())){
            btnSearchStyleDestination.setSelected(true);
            btnSearchStylegas.setSelected(false);
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_search_style_gas){
                    mSearchHistoryRecordFragment.setSearchStyle(false);
                    btnSearchStylegas.setSelected(true);
                    btnSearchStyleDestination.setSelected(false);
                    etSearch.setHint(getString(R.string.search_gas_station));
                    tvSearchSelectStyle.setText(getString(R.string.search_gas_station));
                    searchTypePop.dismiss();
                } else if (v.getId() == R.id.btn_search_style_destination){
                    mSearchHistoryRecordFragment.setSearchStyle(true);
                    btnSearchStyleDestination.setSelected(true);
                    btnSearchStylegas.setSelected(false);
                    etSearch.setHint(getString(R.string.search_destination));
                    tvSearchSelectStyle.setText(getString(R.string.search_destination));
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
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                tvSearchSelectStyle.setCompoundDrawables(null,null,drawable,null);
            }
        });
        Drawable drawableUp = getResources().getDrawable(R.mipmap.pull_up);
        drawableUp.setBounds(0,0,drawableUp.getMinimumWidth(),drawableUp.getMinimumHeight());
        tvSearchSelectStyle.setCompoundDrawables(null,null,drawableUp,null);
        searchTypePop.showAsDropDown(tvSearchSelectStyle,0,10);
    }

    @Override
    protected void lazyLoadData() {

    }
}
