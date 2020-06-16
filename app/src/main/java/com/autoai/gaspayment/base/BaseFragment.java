package com.autoai.gaspayment.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * User: nxp
 * Date: 2020/6/15
 * Time: 11:34
 * Describe: 基类，处理viewModelProvider初始化
 */
public abstract class BaseFragment extends Fragment {

    private static String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    protected Context mActivity;
    private Unbinder mUnbinder;
    private ViewModelProvider mViewModelProvider;
    private boolean isVisible;
    private boolean isPrepared;
    private boolean isFirstLoad = true;

    protected abstract int getLayoutId();
    protected abstract void initData();
    protected abstract void lazyLoadData();

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = onCreateView(inflater,container,savedInstanceState,0);
        mUnbinder = ButterKnife.bind(this, view);
        isFirstLoad = true;
        isPrepared = true;
        initData();
        lazyLoadData();
        return view;
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState, int FOR_OVERRIDE){
        View view = inflater.inflate(getLayoutId(), container,false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModelProvider = getViewModelProvider();
    }

    protected void readyGo(Class<?> clazz){
        Intent intent = new Intent(mActivity, clazz);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.mActivity = context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModelProvider = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    /**
     * 创建viewModel对象
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T getViewModel(Class<T> clazz){
        return mViewModelProvider.get(clazz);
    }

    /**
     * 初始化ViewModelProvider对象
     * @return
     */
    private ViewModelProvider getViewModelProvider(){
        return ViewModelProviders.of(getActivity());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible(){
        lazyLoad();
    }

    protected void onInvisible(){

    }

    protected void lazyLoad(){
        if (!isPrepared || !isVisible || !isFirstLoad){
            return;
        }
        isFirstLoad = false;
        lazyLoadData();
    }
}
