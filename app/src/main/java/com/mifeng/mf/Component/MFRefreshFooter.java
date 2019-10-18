package com.mifeng.mf.Component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cunoraz.gifview.library.GifView;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

import com.mifeng.mf.R;

/**
 * Created by Administrator on 2019/9/18.
 */

public class MFRefreshFooter extends LinearLayout implements RefreshFooter{



    GifView gifView;
    TextView textView;

    public MFRefreshFooter(Context context) {
        super(context);
        initView(context);
    }


    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View refreshView = inflater.inflate(R.layout.common_refresh_footer_layout, this, false);
        gifView = refreshView.findViewById(R.id.footer_gif);
        textView = refreshView.findViewById(R.id.footer_text);
        addView(refreshView);
    }

    public void resetView() {
        gifView.setVisibility(VISIBLE);
        textView.setText("正在努力加载更多数据~~");
    }
    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        return true;
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        if (!success) {
            textView.setText("暂无更多数据");
            gifView.setVisibility(GONE);
        }
        return 0;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

    }
}
