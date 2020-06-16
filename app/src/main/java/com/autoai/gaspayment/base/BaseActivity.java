package com.autoai.gaspayment.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * User: nxp
 * Date: 2020/6/15
 * Time: 11:16
 * Describe:基础类，处理ViewModelProvider的初始化
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnbinder;
    private ViewModelProvider mViewModelProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.getLayoutId());
        setStatusBarFullTransparent();
        setFitSystemWindow(false);
        mUnbinder = ButterKnife.bind(this);
        mViewModelProvider = getViewModelProvider();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mViewModelProvider = null;
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
        return ViewModelProviders.of(this);
    }

    /**
     * 设置布局
     * @return
     */
    protected abstract int getLayoutId();

    public View getParentView(){
        View parentView = LayoutInflater.from(this).inflate(this.getLayoutId(), null);
        return parentView;
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 全透状态栏
     */
    protected void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 如果需要内容紧贴着StatusBar
     * 应该在对应的xml布局文件中，设置根布局fitsSystemWindows=true。
     */
    private View contentViewGroup;

    protected void setFitSystemWindow(boolean fitSystemWindow) {
        if (contentViewGroup == null) {
            contentViewGroup = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        }
        contentViewGroup.setFitsSystemWindows(fitSystemWindow);
    }
}
