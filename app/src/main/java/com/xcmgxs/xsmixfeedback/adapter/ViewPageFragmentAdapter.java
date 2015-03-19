package com.xcmgxs.xsmixfeedback.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.widget.PagerSlidingTabStrip;

import java.util.ArrayList;

/**
 * @author zhangyi
 * Created by zhangyi on 2015-3-19.
 */
public class ViewPageFragmentAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

    private final Context mContext;
    protected PagerSlidingTabStrip mTabStrip;
    private final ViewPager mViewPager;
    private final ArrayList<ViewPageInfo> mTabs = new ArrayList<ViewPageInfo>();

    static class DummyTabFactory implements TabHost.TabContentFactory {

        private final Context mContext;

        DummyTabFactory(Context context) {
            this.mContext = context;
        }

        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }

    public ViewPageFragmentAdapter(FragmentManager fm, PagerSlidingTabStrip tabStrip, ViewPager viewPager) {
        super(fm);
        this.mContext = viewPager.getContext();
        this.mTabStrip = tabStrip;
        this.mViewPager = viewPager;
        this.mViewPager.setAdapter(this);
        this.mViewPager.setOnPageChangeListener(this);
        this.mTabStrip.setViewPager(mViewPager);
    }

    private void addTab(String title,String tag,Class<?> clss,Bundle args){
        ViewPageInfo info = new ViewPageInfo(title,tag,clss,args);
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        for(ViewPageInfo info : mTabs){
            TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.sliding_tab_item,null);
            textView.setText(info.title);
            mTabStrip.addTab(textView);
        }
    }

    @Override
    public Fragment getItem(int position) {
        ViewPageInfo info = mTabs.get(position);
        return Fragment.instantiate(mContext,info.clss.getName(),info.args);
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).title;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
