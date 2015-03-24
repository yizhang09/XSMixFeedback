package com.xcmgxs.xsmixfeedback.adapter;

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
public class MyBaseAdapter<T> extends BaseAdapter {

    protected boolean isLinkViewClick = false;
    protected Context context;
    protected List<T> listData;
    protected LayoutInflater listContainer;
    protected int itemViewResource;

    public MyBaseAdapter(Context context, List<T> listData, int itemViewResource) {
        this.context = context;
        this.listData = listData;
        this.itemViewResource = itemViewResource;
        this.listContainer = LayoutInflater.from(context);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
