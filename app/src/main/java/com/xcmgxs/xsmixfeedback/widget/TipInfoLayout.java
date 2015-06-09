package com.xcmgxs.xsmixfeedback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.util.StringUtils;
import com.xcmgxs.xsmixfeedback.util.TypefaceUtils;
import com.xcmgxs.xsmixfeedback.util.ViewUtils;

/**
 * 一些提示信息显示，包含有加载过程的显示
 * Created by zhangyi on 2015-06-05.
 */
public class TipInfoLayout extends FrameLayout {

    private String netWorkError = "轻触重新加载";
    private String empty = "暂无数据";

    private ProgressBar mPbProgressBar;

    private View mTipContainer;

    private TextView mTvTipState;

    private TextView mTvTipMsg;

    public TipInfoLayout(Context context) {
        super(context);
        initView(context);
    }

    public TipInfoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TipInfoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.tip_info_layout, null, false);
        mPbProgressBar = ViewUtils.findViewById(view, R.id.pb_loading);
        mTvTipState = ViewUtils.findViewById(view, R.id.tv_tip_state);
        mTvTipMsg = ViewUtils.findViewById(view, R.id.tv_tip_msg);
        mTipContainer = ViewUtils.findViewById(view, R.id.ll_tip);
        setLoading();
        addView(view);
    }

    public void setOnClick(OnClickListener onClik) {
        this.setOnClickListener(onClik);
    }

    public void setHiden() {
        this.setVisibility(View.GONE);
    }

    public void setLoading() {
        this.setVisibility(VISIBLE);
        this.mPbProgressBar.setVisibility(View.VISIBLE);
        this.mTipContainer.setVisibility(View.GONE);
    }

    public void setLoadError() {
        setLoadError("");
    }

    public void setLoadError(String errorTip) {
        String tip = netWorkError;
        if (errorTip != null && !StringUtils.isEmpty(errorTip))
            tip = errorTip;
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTipContainer.setVisibility(View.VISIBLE);
        this.mTvTipState.setText(R.string.fa_wifi);
        TypefaceUtils.setFontAwsome(this.mTvTipState);
        this.mTvTipMsg.setText(tip);
    }

    public void setEmptyData(String emptyTip) {
        this.setVisibility(VISIBLE);
        String tip = empty;
        if (emptyTip != null && !StringUtils.isEmpty(emptyTip))
            tip = emptyTip;
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTipContainer.setVisibility(View.VISIBLE);
        this.mTvTipState.setText(R.string.fa_refresh);
        //TypefaceUtils.setFontAwsome(this.mTvTipState);
        this.mTvTipMsg.setText(tip);
    }
}
