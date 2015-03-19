package com.xcmgxs.xsmixfeedback.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.bean.URLs;
import com.xcmgxs.xsmixfeedback.bean.User;
import com.xcmgxs.xsmixfeedback.common.BroadcastController;
import com.xcmgxs.xsmixfeedback.common.StringUtils;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
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
        mApplication = (AppContext)getActivity().getApplication();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof DrawerMenuCallBack){
            mCallBack = (DrawerMenuCallBack)activity;
        }
        // 注册一个用户发生变化的广播
        BroadcastController.registerUserChangeReceiver(activity, mUserChangeReceiver);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // 注销接收用户信息变更的广播
        BroadcastController.unregisterReceiver(getActivity(), mUserChangeReceiver);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setupUserView(mApplication.isLogin());
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
        int id = v.getId();
        switch (id) {
            case R.id.menu_user_layout:
                onClickLogin();
                break;
            case R.id.menu_item_explore:
                onClickExplore();
                highlightSelectedItem(v);
                break;
            case R.id.menu_item_myself:
                onClickMySelf();
                if (mApplication.isLogin()) {
                    highlightSelectedItem(v);
                }
                break;
            case R.id.menu_item_language:
                onClickLanguage();
                break;
            case R.id.menu_item_shake:
                onClickmShake();
                break;
            case R.id.menu_item_setting:
                onClickSetting();
                break;
            case R.id.menu_item_exit:
                onClickExit();
                break;
        }
    }

    private void initView(View view){
        mMenu_user_layout = (RelativeLayout)view.findViewById(R.id.menu_user_layout);
        mMenu_user_info_layout = (LinearLayout)view.findViewById(R.id.menu_user_info_layout);
        mUser_info_userface = (CircleImageView)view.findViewById(R.id.menu_user_info_userface);
        mUser_info_username = (TextView)view.findViewById(R.id.menu_user_info_username);
        mMenu_user_layout = (RelativeLayout)view.findViewById(R.id.menu_user_layout);
        mMenu_user_login_tips = (LinearLayout)view.findViewById(R.id.menu_user_info_login_tips_layout);

        mMenu_item_explore = (LinearLayout) view.findViewById(R.id.menu_item_explore);
        mMenu_item_myself = (LinearLayout) view.findViewById(R.id.menu_item_myself);
        mMenu_item_language = (LinearLayout) view.findViewById(R.id.menu_item_language);
        mMenu_item_shake = (LinearLayout) view.findViewById(R.id.menu_item_shake);
        mMenu_item_setting = (LinearLayout) view.findViewById(R.id.menu_item_setting);
        mMenu_item_exit = view.findViewById(R.id.menu_item_exit);

        mMenu_user_layout.setOnClickListener(this);
        mMenu_item_explore.setOnClickListener(this);
        mMenu_item_myself.setOnClickListener(this);
        mMenu_item_language.setOnClickListener(this);
        mMenu_item_shake.setOnClickListener(this);
        mMenu_item_setting.setOnClickListener(this);
        mMenu_item_exit.setOnClickListener(this);


    }

    private void setupUserView(final boolean reflash){
        //判断是否已经登录
        if(!mApplication.isLogin()){
            mUser_info_userface.setImageResource(R.drawable.mini_avatar);
            mUser_info_username.setText("");
            mMenu_user_info_layout.setVisibility(View.GONE);
            mMenu_user_login_tips.setVisibility(View.VISIBLE);
            return;
        }

        mMenu_user_info_layout.setVisibility(View.VISIBLE);
        mMenu_user_login_tips.setVisibility(View.GONE);
        mUser_info_username.setText("");

        new AsyncTask<Void, Void, User>() {
            @Override
            protected User doInBackground(Void... params) {
                User user = mApplication.getLoginInfo();
                return user;
            }

            @Override
            protected void onPostExecute(User user) {
                if(user == null||isDetached()){
                    return;
                }
                //加载用户头像
                String portrait = user.getPortrait() == null || user.getPortrait().equals("null")?"":user.getPortrait();
                if(portrait.endsWith("portrait.gif") || StringUtils.isEmpty(portrait)){
                    mUser_info_userface.setImageResource(R.drawable.widget_dface);
                }else {
                    String faceUrl = URLs.HTTPS + URLs.HOST + URLs.URL_SPLITTER + user.getPortrait();
                    UIHelper.showUserFace(mUser_info_userface, faceUrl);
                }
                mUser_info_username.setText(user.getUsername());

            }
        }.execute();

    }

    private BroadcastReceiver mUserChangeReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //接收到变化后，更新用户资料
            setupUserView(true);
        }
    };

    private void highlightSelectedItem(View v){
        setSelected(null,false);
        setSelected(v,true);
    }

    public void highlightExplore(){
        highlightSelectedItem(mMenu_item_explore);
    }

    private void setSelected(View v,boolean selected){
        View view;
        if(v == null&&mSavedView == null){
            return;
        }

        if(v != null){
            mSavedView = v;
            view = mSavedView;
        }
        else{
            view = mSavedView;
        }

        if(selected){
            ViewCompat.setHasTransientState(view,true);
            view.setBackgroundColor(getResources().getColor(R.color.menu_layout_item_pressed_color));
        }else{
            ViewCompat.setHasTransientState(view,false);
            view.setBackgroundResource(R.drawable.menu_layout_item_selector);
        }

    }

    private void onClickLogin() {
        if (mCallBack != null) {
            mCallBack.onClickLogin();
        }
    }

    private void onClickSetting() {
        if (mCallBack != null) {
            mCallBack.onClickSetting();
        }
    }

    private void onClickExplore() {
        if (mCallBack != null) {
            mCallBack.onClickExplore();
        }
    }

    private void onClickMySelf() {
        if (mCallBack != null) {
            mCallBack.onClickMySelf();
        }
    }

    private void onClickLanguage() {
        if (mCallBack != null) {
            mCallBack.onClickLanguage();
        }
    }

    private void onClickmShake() {
        if (mCallBack != null) {
            mCallBack.onClickShake();
        }
    }

    private void onClickExit() {
        if (mCallBack != null) {
            mCallBack.onClickExit();
        }
    }




}
