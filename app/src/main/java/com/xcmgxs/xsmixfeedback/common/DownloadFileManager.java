package com.xcmgxs.xsmixfeedback.common;

import android.annotation.SuppressLint;
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
 * Created by zhangyi on 2015-06-19.
 */

@SuppressLint("HandlerLeak")
public class DownloadFileManager {

    private static final int DOWN_NOSDCARD = 0;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;

    private static DownloadFileManager downloadFileManager;

    private Context mContext;

    //通知对话框
    private Dialog noticeDialog;

    //下载对话框
    private Dialog downloadDialog;

    //进度条
    private ProgressBar mProgress;

    //进度条显示下载数值
    private TextView mProgressText;

    //进度值
    private int progress;

    //下载线程
    private Thread downloadThread;

    //终止标记
    private boolean interceptFlag;

    //filename
    private String fileName = "";

    //file地址
    private String fileUrl = "";


    //保存路径
    private String savePath = "";

    //apk完整路径
    private String filePath = "";

    //临时下载文件路径
    private String tempFilePath = "";

    //文件大小
    private String fileSize;

    //临时文件大小
    private String tempFileSize;


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DOWN_UPDATE:
                    mProgress.setProgress(progress);
                    mProgressText.setText(tempFileSize + "/" + fileSize);
                    break;
                case DOWN_OVER:
                    downloadDialog.dismiss();
                    openFile();
                    break;
                case DOWN_NOSDCARD:
                    downloadDialog.dismiss();
                    Toast.makeText(mContext, "无法下载文件，请检查SD卡是否挂载。", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    public static DownloadFileManager getDownloadFileManager(Context context,String url,String name){
        if(downloadFileManager == null){
            downloadFileManager = new DownloadFileManager();
        }
        downloadFileManager.mContext = context;
        downloadFileManager.fileUrl = url;
        downloadFileManager.fileName = name;
        downloadFileManager.interceptFlag = false;
        return downloadFileManager;
    }

    public void showOpenFileDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("下载文件");
        builder.setMessage("确定下载？");
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
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
        builder.setTitle("正在下载");

        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.update_progress,null);
        mProgress = (ProgressBar)v.findViewById(R.id.update_progress);
        mProgressText = (TextView)v.findViewById(R.id.update_progress_text);

        builder.setView(v);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
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

        downloadFile();
    }

    private Runnable mDownloadApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                String tempName = fileName + ".tmp";

                String storageState = Environment.getExternalStorageState();
                if(storageState.equals(Environment.MEDIA_MOUNTED)){
                    savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/XSFeedback/File/";
                    File file = new File(savePath);
                    if(!file.exists()){
                        file.mkdirs();
                    }
                    filePath = savePath + fileName;
                    tempFilePath = savePath + tempName;
                }

                if(filePath == null || filePath == ""){
                    mHandler.sendEmptyMessage(DOWN_NOSDCARD);
                    return;
                }

                File file = new File(filePath);

                if(file.exists()){
                    downloadDialog.dismiss();
                    openFile();
                    return;
                }

                File tempfile = new File(tempFilePath);
                FileOutputStream fos = new FileOutputStream(tempfile);

                URL url = new URL(fileUrl);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                DecimalFormat df = new DecimalFormat("0.00");
                fileSize = df.format((float)length / 1024 / 1024) + "MB";

                int count = 0;
                byte buff[] = new byte[1024];

                do{
                    int numread = is.read(buff);
                    count += numread;

                    tempFileSize = df.format((float)count / 1024 / 1024) + "MB";

                    progress = (int)(((float)count / length) * 100);
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if(numread <= 0){
                        if(tempfile.renameTo(file)){
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

    private void downloadFile() {
        downloadThread = new Thread(mDownloadApkRunnable);
        downloadThread.start();
    }

    private void openFile() {
        File file = new File(filePath);
        if(!file.exists()){
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addCategory("android.intent.category.DEFAULT");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String fileType = file.getPath().split("\\.")[1];
        switch (fileType){
            case "pdf":
                i.setDataAndType(Uri.parse("file://" + file.toString()),"application/pdf");
                break;
            case "doc":
                i.setDataAndType(Uri.parse("file://" + file.toString()),"application/msword");
                break;
            case "docx":
                i.setDataAndType(Uri.parse("file://" + file.toString()),"application/msword");
                break;
            case "xls":
                i.setDataAndType(Uri.parse("file://" + file.toString()),"application/vnd.ms-excel");
                break;
            case "xlsx":
                i.setDataAndType(Uri.parse("file://" + file.toString()),"application/vnd.ms-excel");
                break;
            case "ppt":
                i.setDataAndType(Uri.parse("file://" + file.toString()),"application/vnd.ms-powerpoint");
                break;
            case "pptx":
                i.setDataAndType(Uri.parse("file://" + file.toString()),"application/vnd.ms-powerpoint");
                break;
            case "jpg":
                i.setDataAndType(Uri.parse("file://" + file.toString()),"image/*");
                break;
            case ".zip":
                i.setDataAndType(Uri.parse("file://" + file.toString()),"application/x-zip-compressed");
                break;
            default:
                i.setDataAndType(Uri.parse("file://" + file.toString()),"text/plain");
                break;

        }
        mContext.startActivity(i);
    }


    public static Intent getPdfFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    public static Intent getImageFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    //android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }



    //android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    //android获取一个用于打开PPT文件的intent
    public static Intent getPptFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

}
