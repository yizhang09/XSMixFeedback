package com.xcmgxs.xsmixfeedback.ui.fragments;

import android.os.Bundle;

import static com.xcmgxs.xsmixfeedback.ui.fragments.ProjectStateListFragment.*;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.adapter.ViewPageFragmentAdapter;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseViewPagerFragment;

/**
 * Created by zhangyi on 2015-07-03.
 */
public class ProjectStateViewPagerFragment extends BaseViewPagerFragment {

    public static ProjectStateViewPagerFragment newInstance(){
        return new ProjectStateViewPagerFragment();
    }

    @Override
    protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {

        int year = getActivity().getIntent().getIntExtra("year",-1);

        String[] title = getResources().getStringArray(R.array.state_title_array);

        Bundle bundle1 = new Bundle();
        bundle1.putInt(STATE_TYPE, STATE1);
        bundle1.putInt(YEAR_TAG, year);
        adapter.addTab(title[0],"1",ProjectStateListFragment.class,bundle1);

        Bundle bundle2 = new Bundle();
        bundle2.putInt(STATE_TYPE, STATE2);
        bundle2.putInt(YEAR_TAG, year);
        adapter.addTab(title[1],"2",ProjectStateListFragment.class,bundle2);

        Bundle bundle3 = new Bundle();
        bundle3.putInt(STATE_TYPE,STATE3);
        bundle3.putInt(YEAR_TAG, year);
        adapter.addTab(title[2],"3",ProjectStateListFragment.class,bundle3);

        Bundle bundle4 = new Bundle();
        bundle4.putInt(STATE_TYPE, STATE4);
        bundle4.putInt(YEAR_TAG, year);
        adapter.addTab(title[3],"4",ProjectStateListFragment.class,bundle4);

        Bundle bundle5 = new Bundle();
        bundle5.putInt(STATE_TYPE, STATE5);
        bundle5.putInt(YEAR_TAG, year);
        adapter.addTab(title[4],"5",ProjectStateListFragment.class,bundle5);

        Bundle bundle6 = new Bundle();
        bundle6.putInt(STATE_TYPE, STATE6);
        bundle6.putInt(YEAR_TAG, year);
        adapter.addTab(title[5],"6",ProjectStateListFragment.class,bundle6);

        Bundle bundle7 = new Bundle();
        bundle7.putInt(STATE_TYPE, STATE7);
        bundle7.putInt(YEAR_TAG, year);
        adapter.addTab(title[6],"7",ProjectStateListFragment.class,bundle7);


    }
}
