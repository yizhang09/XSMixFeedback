package com.xcmgxs.xsmixfeedback.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.bean.Result;
import com.xcmgxs.xsmixfeedback.bean.URLs;
import com.xcmgxs.xsmixfeedback.bean.UpLoadFile;
import com.xcmgxs.xsmixfeedback.bean.User;
import com.xcmgxs.xsmixfeedback.common.BroadcastController;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.ui.baseactivity.BaseActionBarActivity;
import com.xcmgxs.xsmixfeedback.util.FileUtils;
import com.xcmgxs.xsmixfeedback.util.ImageUtils;
import com.xcmgxs.xsmixfeedback.util.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MySelfInfoActivity extends BaseActionBarActivity implements View.OnClickListener {

    private static final String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/XSFeedback/User/Portrait/";
    private static final int CROP = 200;
    private Uri origUri;
    private Uri cropUri;
    private File portraitFile;
    private Bitmap portraitBitmap;
    private String portraitPath;

    private User mUser;

    private ImageView mUserFace;

    private TextView mUserName;

    private Button mEditName;

    private TextView mJoinTime;

    private TextView mPhoneNumber;

    private TextView mDepartment;

    private Button mLogout;

    private AppContext mAppContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myself_info);
        mAppContext = getXSApplication();
        initView();
        initData();
    }

    private void initView(){
        mUserFace = (ImageView)findViewById(R.id.myself_info_userface);
        mUserName = (TextView)findViewById(R.id.myself_info_username);
        mJoinTime = (TextView)findViewById(R.id.myself_info_detail_jointime);
        mPhoneNumber = (TextView)findViewById(R.id.myself_info_detail_phonenumber);
        mEditName = (Button)findViewById(R.id.myself_info_editor);
        mLogout = (Button)findViewById(R.id.myself_info_logout_btn);

        mEditName.setOnClickListener(this);
        mLogout.setOnClickListener(this);
        mUserFace.setOnClickListener(this);


    }

    private void initData(){
        mUser = getXSApplication().getLoginInfo();
        if(mUser != null){
            mUserName.setText(mUser.getName());
            mJoinTime.setText(mUser.getAddTime() == null ? "" : mUser.getAddTime().toString());
            mPhoneNumber.setText(mUser.getPhoneNumber());
        }

        String portrait = mUser.getPortrait() == null || mUser.getPortrait().equals("null") ? "" : mUser.getPortrait();
        if(portrait.endsWith("portrait.gif") || StringUtils.isEmpty(portrait)) {
            mUserFace.setImageResource(R.drawable.widget_dface);
        }
        else {
            String faceUrl = URLs.URL_PORTRAIT + portrait;
            UIHelper.showUserFace(mUserFace,faceUrl);
        }


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.myself_info_logout_btn:
                logOut();
                break;
            case R.id.myself_info_userface:
                CharSequence[] items = { getString(R.string.img_from_album), getString(R.string.img_from_camera) };
                imageChooseItem(items);
                break;
            default:
                break;
        }

    }

    private void logOut(){
        getXSApplication().loginout();
        BroadcastController.sendUserChangeBroadcast(getXSApplication());
        this.finish();
    }

    private void imageChooseItem(CharSequence[] items){
        AlertDialog imageDialog = new AlertDialog.Builder(this).setTitle("上传头像").setIcon(android.R.drawable.btn_star)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            startImagePick();
                        }
                        if(which == 1){
                            startActionCamera();
                        }
                    }
                }).create();
        imageDialog.show();
    }



    private void startImagePick() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "选择图片"), ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);

    }

    private void startActionCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, this.getCameraTempFile());
        startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
    }

    private Uri getCameraTempFile(){
        String storageState = Environment.getExternalStorageState();
        if(storageState.equals(Environment.MEDIA_MOUNTED)){
            File saveDir = new File(FILE_PATH);
            if(!saveDir.exists()){
                saveDir.mkdirs();
            }
        }
        else {
            UIHelper.ToastMessage(getXSApplication(),"无法上传头像，请检查SD卡是否安装");
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String cropFileName = "xsfeedback_camera_" + timeStamp + ".jpg";
        portraitPath = FILE_PATH + cropFileName;
        portraitFile = new File(portraitPath);
        cropUri = Uri.fromFile(portraitFile);
        this.origUri = cropUri;
        return cropUri;
    }

    private Uri getUploadTempFile(Uri uri){
        String storageState = Environment.getExternalStorageState();
        if(storageState.equals(Environment.MEDIA_MOUNTED)){
            File saveDir = new File(FILE_PATH);
            if(!saveDir.exists()){
                saveDir.mkdirs();
            }
        }
        else {
            UIHelper.ToastMessage(getXSApplication(),"无法上传头像，请检查SD卡是否安装");
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);

        if(StringUtils.isEmpty(thePath)){
            thePath = ImageUtils.getAbsoluteImagePath(MySelfInfoActivity.this,uri);
        }
        String ext = FileUtils.getFileFormat(thePath);
        ext = StringUtils.isEmpty(ext)?"jpg":ext;
        String cropFileName = "xsfeedback_crop_" + timeStamp + "." + ext;
        portraitPath = FILE_PATH + cropFileName;
        portraitFile = new File(portraitPath);
        cropUri = Uri.fromFile(portraitFile);
        return cropUri;
    }

    private void startActionCrop(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("output", this.getUploadTempFile(uri));
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", CROP);// 输出图片大小
        intent.putExtra("outputY", CROP);
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
    }

    private void uploadNewPhoto(){
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("正在上传头像...");

        new AsyncTask<Void,Void,Message>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.show();
            }

            @Override
            protected void onPostExecute(Message message) {
                super.onPostExecute(message);
                dialog.dismiss();
                if(message.what == 1){
                    Result file = (Result)message.obj;
                    if(file == null){
                        Log.i("Test","返回结果为空");
                    }
                    mUserFace.setImageBitmap(portraitBitmap);
                    UIHelper.ToastMessage(getXSApplication(), "上传头像成功");
                }
                else {
                    UIHelper.ToastMessage(getXSApplication(),"上传头像失败");
                }
            }

            @Override
            protected Message doInBackground(Void... params) {

                Message message = new Message();
                if(!StringUtils.isEmpty(portraitPath) || portraitFile.exists()){
                    portraitBitmap = ImageUtils.loadImgThumbnail(portraitPath,200,200);
                }
                else {
                    UIHelper.ToastMessage(getActivity(),"图像不存在");
                }
                if(portraitBitmap != null && portraitFile != null){
                    try {
                        Result file =  mAppContext.upLoad(portraitFile);
                        message.what = 1;
                        message.obj = file;
                    }
                    catch (Exception ex){
                        dialog.dismiss();
                        message.what = -1;
                        message.obj = ex;
                    }
                }

                return message;
            }
        }.execute();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
                startActionCrop(origUri);// 拍照后裁剪
                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
                startActionCrop(data.getData());// 选图后裁剪
                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD:
                uploadNewPhoto();// 上传新照片
                break;
        }
    }


}
