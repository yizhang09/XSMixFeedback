package com.xcmgxs.xsmixfeedback.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.api.XsFeedbackApi;
import com.xcmgxs.xsmixfeedback.bean.Update;
import com.xcmgxs.xsmixfeedback.ui.dialog.LightProgressDialog;
import com.xcmgxs.xsmixfeedback.util.JsonUtils;
import com.xcmgxs.xsmixfeedback.util.ViewUtils;

import org.apache.http.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * Created by zhangyi on 2015-06-15.
 */
public class UpdateManager {

    private static final int DOWN_NOSDCARD = 0;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;

    private static final int DIALOG_TYPE_LATEST = 0;
    private static final int DIALOG_TYPE_FAIL = 1;

    private static UpdateManager updateManager;

    private Context mContext;

    //֪ͨ�Ի���
    private Dialog noticeDialog;

    //���ضԻ���
    private Dialog downloadDialog;

    //�Ѿ����� �� �޷���ȡ�汾 �Ի���
    private Dialog latestOrFailDialog;

    //������
    private ProgressBar mProgress;

    //��������ʾ������ֵ
    private TextView mProgressText;

    //��ѯ����
    private ProgressDialog mProgressDialog;

    //����ֵ
    private int progress;

    //�����߳�
    private Thread downloadThread;

    //��ֹ���
    private boolean interceptFlag;

    //��ʾ��
    private String updateMsg = "";

    //apk��ַ
    private String apkUrl = "";

    //����·��
    private String savePath = "";

    //apk����·��
    private String apkFilePath = "";

    //��ʱ�����ļ�·��
    private String tempFilePath = "";

    //apk�ļ���С
    private String apkFileSize;

    //��ʱ�ļ���С
    private String tempFileSize;

    //��ǰ�汾��
    private int curVersionCode;

    private Update mUpdate;


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DOWN_UPDATE:
                    mProgress.setProgress(progress);
                    mProgressText.setText(tempFileSize + "/" + apkFileSize);
                    break;
                case DOWN_OVER:
                    downloadDialog.dismiss();
                    installApk();
                    break;
                case DOWN_NOSDCARD:
                    downloadDialog.dismiss();
                    Toast.makeText(mContext,"�޷������ļ�������SD���Ƿ���ء�",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    public static UpdateManager getUpdateManager(){
        if(updateManager == null){
            updateManager = new UpdateManager();
        }
        updateManager.interceptFlag = false;
        return updateManager;
    }

    //��ʾ�Ѿ����»��޷���ȡ���°汾��Ϣ
    private void showLatestOrFailDialog(int dialogtype){
        if(latestOrFailDialog != null){
            latestOrFailDialog.dismiss();
            latestOrFailDialog = null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("ϵͳ��ʾ");
        if(dialogtype == DIALOG_TYPE_FAIL){
            builder.setMessage("�޷���ȡ���°汾��Ϣ");
        }
        if(dialogtype == DIALOG_TYPE_LATEST){
            builder.setMessage("����ǰ�Ѿ������°汾");
        }
        builder.setPositiveButton("ȷ��",null);
        latestOrFailDialog = builder.create();
        latestOrFailDialog.show();
    }

    private void getCurrentVersion(){
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(),0);
            curVersionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
    }

    public void checkAppUpdate(Context context,final boolean isShowMsg){
        this.mContext = context;
        getCurrentVersion();
        final AlertDialog check = LightProgressDialog.create(context, "���ڼ�� ���Ժ򡭡�");
        check.setCanceledOnTouchOutside(false);
        XsFeedbackApi.getUpdateInfo(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Update update = JsonUtils.toBean(Update.class, responseBody);
                if (update != null) {
                    mUpdate = update;
                    if (curVersionCode < mUpdate.getNum_version()) {
                        apkUrl = mUpdate.getDownload_url();
                        updateMsg = mUpdate.getDescription();
                        showNoticeDialog();
                    } else {
                        if (isShowMsg) {
                            showLatestOrFailDialog(DIALOG_TYPE_LATEST);
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                ViewUtils.showToast("�����쳣");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                check.dismiss();
            }
        });
    }

    private void showNoticeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("����汾����");
        builder.setMessage(updateMsg);
        builder.setPositiveButton("��������", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();
            }
        });

        builder.setPositiveButton("�Ժ���˵", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        noticeDialog = builder.create();
        noticeDialog.show();
    }

    private void showDownloadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("�������ذ汾");

        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.update_progress,null);
        mProgress = (ProgressBar)v.findViewById(R.id.update_progress);
        mProgressText = (TextView)v.findViewById(R.id.update_progress_text);

        builder.setView(v);
        builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                interceptFlag = true;
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                interceptFlag = true;
            }
        });

        downloadDialog = builder.create();
        downloadDialog.setCanceledOnTouchOutside(false);
        downloadDialog.show();

        downloadApk();
    }

    private Runnable mDownloadApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                String apkName = "XSFeedBackApp_" + mUpdate.getVersion() + ".apk";
                String tempName = "XSFeedBackApp_" + mUpdate.getVersion() + ".tmp";

                String storageState = Environment.getExternalStorageState();
                if(storageState == Environment.MEDIA_MOUNTED){
                    savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/XSFeedback/Update/";
                    File file = new File(savePath);
                    if(!file.exists()){
                        file.mkdirs();
                    }
                    apkFilePath = savePath + apkName;
                    tempFilePath = savePath + tempName;
                }

                if(apkFilePath == null || apkFilePath == ""){
                    mHandler.sendEmptyMessage(DOWN_NOSDCARD);
                    return;
                }

                File apkFile = new File(apkFilePath);

                if(apkFile.exists()){
                    downloadDialog.dismiss();
                    installApk();
                    return;
                }

                File tempfile = new File(tempFilePath);
                FileOutputStream fos = new FileOutputStream(tempfile);

                URL url = new URL(apkUrl);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                DecimalFormat df = new DecimalFormat("0.00");
                apkFileSize = df.format((float)length / 1024 / 1024) + "MB";

                int count = 0;
                byte buff[] = new byte[1024];

                do{
                    int numread = is.read(buff);
                    count += numread;

                    tempFileSize = df.format((float)count / 1024 / 1024) + "MB";

                    progress = (int)(((float)count / length) * 100);
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if(numread <= 0){
                        if(tempfile.renameTo(apkFile)){
                            mHandler.sendEmptyMessage(DOWN_OVER);
                        }
                        break;
                    }
                    fos.write(buff,0,numread);
                }
                while (!interceptFlag);

                fos.close();
                is.close();

            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private void downloadApk() {
        downloadThread = new Thread(mDownloadApkRunnable);
        downloadThread.start();
    }

    private void installApk() {
        File apkfile = new File(apkFilePath);
        if(!apkfile.exists()){
            return;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),"application/vnd.android.package-archive");
        mContext.startActivity(i);
    }

}
