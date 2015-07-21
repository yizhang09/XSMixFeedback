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

        Bundle buildingBundle = new Bundle();
        buildingBundle.putByte(EXPLORE_TYPE,TYPE_BUILDING);
        adapter.addTab(title[0],"building",ExploreListProjectFragment.class,buildingBundle);
        Bundle allBundle = new Bundle();
        allBundle.putByte(EXPLORE_TYPE,TYPE_ALL);
        adapter.addTab(title[1],"all",ExploreListProjectFragment.class,allBundle);
        Bundle stopBundle = new Bundle();
        stopBundle.putByte(EXPLORE_TYPE,TYPE_STOP);
        adapter.addTab(title[2],"stop",ExploreListProjectFragment.class,stopBundle);



    }
}
