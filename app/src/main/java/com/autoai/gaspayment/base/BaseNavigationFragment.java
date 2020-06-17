package com.autoai.gaspayment.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * User: nxp
 * Date: 2020/6/17
 * Time: 10:15
 * Describe:
 */
public abstract class BaseNavigationFragment extends BaseFragment{

    //记录是否已经初始化过一次视图
    protected boolean isNavigationViewInit = false;
    //记录上次创建的view
    private View lastView = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState, int FOR_OVERRIDE) {
        //如果fragment的view已经创建则不再重新创建
        if (lastView == null){
            lastView = super.onCreateView(inflater, container, saveInstanceState, 0);
        }
        return lastView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (!isNavigationViewInit){
            super.onViewCreated(view, savedInstanceState);
            isNavigationViewInit = true;
        }
    }
}
