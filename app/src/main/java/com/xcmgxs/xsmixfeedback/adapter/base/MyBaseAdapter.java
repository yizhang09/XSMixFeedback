package com.xcmgxs.xsmixfeedback.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author zhangyi
 * Created by zhangyi on 2015-3-23.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

    protected boolean isLinkViewClick = false;
    protected Context context;
    protected List<T> listData;
    protected LayoutInflater listContainer;
    protected int itemViewResource;

    public MyBaseAdapter(Context context, List<T> listData, int itemViewResource) {
        this.context = context;
        this.listContainer = LayoutInflater.from(context);	//创建视图容器并设置上下文
        this.itemViewResource = itemViewResource;
        this.listData = listData;
    }

    public boolean isLinkViewClick(){
        return isLinkViewClick;
    }

    public void setLinkViewClick(boolean isLinkViewClick){
        this.isLinkViewClick = isLinkViewClick;
    }

    @Override
    public int getCount() {
        return this.listData.size();
    }

    @Override
    public T getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
