package com.xcmgxs.xsmixfeedback.ui.fragments;

import static com.xcmgxs.xsmixfeedback.ui.fragments.ExploreListProjectFragment.*;
import android.os.Bundle;

import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.adapter.ViewPageFragmentAdapter;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseViewPagerFragment;

/**
 * 发现界面
 * @author zhangyi
 * Created by zhangyi on 2015-3-19.
 */
public class ExploreViewPagerFragment extends BaseViewPagerFragment {

    public static ExploreViewPagerFragment newInstance(){
        return new ExploreViewPagerFragment();
    }

    @Override
    protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {
        String[] title = getResources().getStringArray(R.array.explore_title_array);

        Bundle myBundle = new Bundle();
        myBundle.putByte(EXPLORE_TYPE,TYPE_MY);
        adapter.addTab(title[0],"my",ExploreListProjectFragment.class,myBundle);
        Bundle allBundle = new Bundle();
        allBundle.putByte(EXPLORE_TYPE,TYPE_ALL);
        adapter.addTab(title[1],"all",ExploreListProjectFragment.class,allBundle);
        Bundle latestBundle = new Bundle();
        latestBundle.putByte(EXPLORE_TYPE,TYPE_LATEST);
        adapter.addTab(title[2],"latest",ExploreListProjectFragment.class,latestBundle);


    }
}
