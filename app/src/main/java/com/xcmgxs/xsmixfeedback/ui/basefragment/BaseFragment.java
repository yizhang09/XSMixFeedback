package com.xcmgxs.xsmixfeedback.ui.basefragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.xcmgxs.xsmixfeedback.AppContext;

/**
 * Created by zhangyi on 2015-3-19.
 */
public class BaseFragment extends Fragment {

    public AppContext getXsApplication(){
        return (AppContext)getActivity().getApplication();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
