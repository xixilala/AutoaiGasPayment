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

import static com.scwang.smartrefresh.layout.constant.RefreshState.PullDownToRefresh;

/**
 * User: nxp
 * Date: 2020/6/19
 * Time: 15:24
 * Describe: 刷新头布局
 */
public class RefreshHeader extends InternalAbstract {
    public static String REFRESH_HEADER_PULLING = "下拉可以刷新";//"下拉可以刷新";
    public static String REFRESH_HEADER_LOADING = "正在加载...";//"正在加载...";
    public static String REFRESH_HEADER_RELEASE = "释放立即刷新";
    public static String REFRESH_HEADER_FINISH = "刷新成功";//"刷新完成";
    public static String REFRESH_HEADER_FAILED = "刷新失败";//"刷新失败";

//    private TextView mTitleText;
    private ProgressBar mProgressBar;

    public RefreshHeader(Context context) {
        this(context, null);
    }

    public RefreshHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.refresh_refresh_head, this);
//        mTitleText = view.findViewById(R.id.txt);
        mProgressBar = view.findViewById(R.id.refresh_header);

    }

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        if (success) {
//            mTitleText.setText(REFRESH_HEADER_FINISH);
        } else {
//            mTitleText.setText(REFRESH_HEADER_FAILED);
        }
        super.onFinish(layout, success);
        return 500; //延迟500毫秒之后再弹回
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState) {
            case PullDownToRefresh: //下拉过程
//                mTitleText.setText(REFRESH_HEADER_PULLING);
                mProgressBar.setVisibility(GONE);
                break;
            case ReleaseToRefresh: //松开刷新
//                mTitleText.setText(REFRESH_HEADER_RELEASE);
                mProgressBar.setVisibility(VISIBLE);
                break;
            case Refreshing: //loading中
//                mTitleText.setText(REFRESH_HEADER_LOADING);
                break;
        }
    }
}
