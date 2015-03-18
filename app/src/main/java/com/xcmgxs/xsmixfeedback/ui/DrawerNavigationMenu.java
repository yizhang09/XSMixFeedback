package com.xcmgxs.xsmixfeedback.ui;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.interfaces.DrawerMenuCallBack;
import com.xcmgxs.xsmixfeedback.widget.BadgeView;
import com.xcmgxs.xsmixfeedback.widget.CircleImageView;

/**
 * @author zhangyi 20150318
 * 左滑菜单Fragment
 */
public class DrawerNavigationMenu extends Fragment implements View.OnClickListener {

    public static DrawerNavigationMenu newInstance(){
        return new DrawerNavigationMenu();
    }

    private View mSavedView;
    private RelativeLayout mMenu_user_layout;
    private LinearLayout mMenu_user_info_layout;
    private LinearLayout mMenu_user_login_tips;
    private CircleImageView mUser_info_userface;
    private TextView mUser_info_username;

    private LinearLayout mMenu_item_explore;
    private LinearLayout mMenu_item_myself;
    private LinearLayout mMenu_item_language;
    private LinearLayout mMenu_item_shake;
    private LinearLayout mMenu_item_setting;
    private View mMenu_item_exit;

    private DrawerMenuCallBack mCallBack;
    private AppContext mApplication;

    public static BadgeView mNotification_bv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_drawer_menu, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }
}
