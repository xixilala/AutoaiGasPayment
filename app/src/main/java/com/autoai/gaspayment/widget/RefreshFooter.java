package com.autoai.gaspayment.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.autoai.gaspayment.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.internal.InternalAbstract;

/**
 * User: nxp
 * Date: 2020/6/19
 * Time: 16:13
 * Describe:刷新脚布局
 */
public class RefreshFooter extends InternalAbstract {

    public static String REFRESH_FOOTER_PULLING = "上拉加载更多";//"上拉加载更多";
    public static String REFRESH_FOOTER_RELEASE = "释放立即加载";//"释放立即加载";
    public static String REFRESH_FOOTER_LOADING = "正在加载...";//"正在加载...";
    public static String REFRESH_FOOTER_REFRESHING = "正在刷新...";//"正在刷新...";
    public static String REFRESH_FOOTER_FINISH = "加载完成";//"加载完成";
    public static String REFRESH_FOOTER_FAILED = "加载失败";//"加载失败";
    public static String REFRESH_FOOTER_NOTHING = "没有更多数据了";//"没有更多数据了";

    private TextView mTitleText;
    private ProgressBar mProgressBar;

    public RefreshFooter(Context context) {
        this(context, null);
    }

    public RefreshFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.refresh_refresh_footer, this);
        mTitleText = view.findViewById(R.id.tv_refresh_foot_state);
        mProgressBar = view.findViewById(R.id.refresh_header);

    }

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        if (success) {
            mTitleText.setText(REFRESH_FOOTER_FINISH);
        } else {
            mTitleText.setText(REFRESH_FOOTER_FAILED);
        }
        super.onFinish(layout, success);
        return 500; //延迟500毫秒之后再弹回
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState) {
            case PullUpToLoad:
                mTitleText.setText(REFRESH_FOOTER_PULLING);
                break;
            case Loading:
            case LoadReleased:
                mTitleText.setText(REFRESH_FOOTER_LOADING);
                mProgressBar.setVisibility(VISIBLE);
                break;
            case ReleaseToLoad:
                mTitleText.setText(REFRESH_FOOTER_RELEASE);
                break;
            case Refreshing:
                mTitleText.setText(REFRESH_FOOTER_REFRESHING);
                break;

            case PullDownToRefresh: //下拉过程
                mTitleText.setText(REFRESH_FOOTER_PULLING);
                mProgressBar.setVisibility(GONE);
                break;
        }
    }
}
