package com.xcmgxs.xsmixfeedback.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.adapter.NotificationAdapter;
import com.xcmgxs.xsmixfeedback.adapter.ProjectAdapter;
import com.xcmgxs.xsmixfeedback.api.XsFeedbackApi;
import com.xcmgxs.xsmixfeedback.bean.Notification;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseFragment;
import com.xcmgxs.xsmixfeedback.util.JsonUtils;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知列表页面
 * 1.加载未读的通知
 * 2.加载已读的通知
 * （加载什么类型的通知通过mAction来区分）
 * Created by zhangyi on 2015-06-05.
 */
public class NotificationFragment extends BaseFragment implements View.OnClickListener,AdapterView.OnItemClickListener {


    private final int MENU_REFRESH_ID = 1;

    public static final String NOTIFICATION_ACTION_KEY = "notification_action";

    public static final int ACTION_UNREAD = 0;//未读

    public static final int ACTION_READED = 1;//已读

    private int mAction = ACTION_UNREAD;

    private ProgressBar mProgressBar;

    private View mEmpty;

    private ListView mListView;

    private List<List<Notification>> mData;

    //private List<ProjectNotification> mGroups;

    private NotificationAdapter adapter;

    private ImageView mEmptyImage;

    private TextView mEmptyMsg;

    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setupList();
        initData();
    }



    private void initView(View view) {
        mProgressBar = (ProgressBar) view.findViewById(R.id.notification_fragment_loading);
        mEmpty = view.findViewById(R.id.notification_fragment_empty);
        mListView = (ListView) view.findViewById(R.id.notification_fragment_list);

        mEmptyImage = (ImageView) view.findViewById(R.id.notification_empty_img);
        mEmptyMsg = (TextView) view.findViewById(R.id.notification_empty_msg);
        mListView.setOnItemClickListener(this);
    }

    private void initData() {
        Bundle args = getArguments();
        if (args != null) {
            mAction = args.getInt(NOTIFICATION_ACTION_KEY, 0);
        }
        if (mAction == ACTION_UNREAD) {
            mEmptyMsg.setText("没有未读的通知");
            loadData(false);
        } else {
            mEmptyMsg.setText("没有已读的通知");
            loadData(true);
        }
    }


    private void setupList() {
        mData = new ArrayList<List<Notification>>();
        adapter = new NotificationAdapter(getActivity(),R.layout.notification_listitem);
        mListView.setAdapter(adapter);
        TextView v = new TextView(getActivity());
        v.setText("空的数据");
        mListView.setEmptyView(v);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            default:
                break;
        }
    }


    private void beforeLoading() {
        mEmpty.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
    }

    private void afterLoading(boolean isEmpty) {
        mProgressBar.setVisibility(View.GONE);
        if (isEmpty) {
            mEmpty.setVisibility(View.VISIBLE);
        } else {
            mListView.setVisibility(View.VISIBLE);
        }
    }

    private void loadData(final boolean isRead) {
        XsFeedbackApi.getNotification(isRead, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                List<Notification> notificationArrays = JsonUtils.getList(Notification[].class, responseBody);
                boolean isEmpty = true;
                if (notificationArrays.size() != 0) {
                    isEmpty = false;
                    adapter.addItem(notificationArrays);
                    mListView.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
                afterLoading(isEmpty);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }

            @Override
            public void onStart() {
                super.onStart();
                beforeLoading();
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Notification notification = adapter.getItem(position);
        UIHelper.showNotificationDetail(getActivity(),notification);

    }
}
