package com.xcmgxs.xsmixfeedback.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xcmgxs.xsmixfeedback.AppConfig;
import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.api.XsFeedbackApi;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.bean.ProjectIssue;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.ui.baseactivity.BaseActionBarActivity;
import com.xcmgxs.xsmixfeedback.ui.dialog.LightProgressDialog;
import com.xcmgxs.xsmixfeedback.util.FileUtils;
import com.xcmgxs.xsmixfeedback.util.ImageUtils;
import com.xcmgxs.xsmixfeedback.util.JsonUtils;
import com.xcmgxs.xsmixfeedback.util.StringUtils;
import com.xcmgxs.xsmixfeedback.util.ViewUtils;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class IssueEditActivity extends BaseActionBarActivity  implements View.OnClickListener {

    @InjectView(R.id.issue_edit_loading)
    ProgressBar mIssueEditLoading;
    @InjectView(R.id.issue_edit_type)
    Spinner mIssueEditType;
    @InjectView(R.id.issue_edit_state)
    Spinner mIssueEditState;
    @InjectView(R.id.issue_edit_desc)
    EditText mIssueEditDesc;
    @InjectView(R.id.issue_edit_prereason)
    EditText mIssueEditPreReason;
    @InjectView(R.id.issue_edit_advice)
    EditText mIssueEditAdvice;
    @InjectView(R.id.issue_pub_image1)
    ImageView mImage1;
    @InjectView(R.id.issue_pub_image2)
    ImageView mImage2;
    @InjectView(R.id.issue_pub_image3)
    ImageView mImage3;
    @InjectView(R.id.issue_pub_image4)
    ImageView mImage4;
    @InjectView(R.id.issue_pub_image5)
    ImageView mImage5;
    @InjectView(R.id.issue_pub_image_upload)
    ImageView mImageUpload;

    Project mProject;

    //private File imgFile;
    private File[] imgFiles = new File[5];
    private String theLarge;
    private String theThumbnail;

    private InputMethodManager imm;

    private Context mContext;

    private String tempIssueKey = AppConfig.TEMP_ISSUE;
    private String tempIssueImageKey = AppConfig.TEMP_ISSUE_IMAGE;

    private static final String[] TYPES = {"斜皮带输送系统", "搅拌主机", "电路", "平皮带输送系统", "气路系统", "控制系统", "控制室"
            , "骨料仓总成", "机制砂", "主机楼", "提斗机", "供水系统", "螺旋输送系统", "砂浆站", "外加剂供给系统","其他"};

    private static final String[] STATES = {"正在处理", "已处理"};

    private ArrayAdapter<String> adapter;

    private ArrayAdapter<String> stateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_edit);
        ButterKnife.inject(this);
        mContext = this;

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        initView();
    }

    private void initView() {
        mProject = (Project) getIntent().getSerializableExtra(Contanst.PROJECT);

        mActionBar.setTitle("新建问题反馈");
        mActionBar.setSubtitle(mProject.getName() + "/" + mProject.getCustomer());

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TYPES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mIssueEditType.setAdapter(adapter);

        stateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, STATES);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mIssueEditState.setAdapter(stateAdapter);

        mImageUpload.setOnClickListener(this);

        mImage1.setOnClickListener(this);
        mImage2.setOnClickListener(this);
        mImage3.setOnClickListener(this);
        mImage4.setOnClickListener(this);
        mImage5.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_issue_edit, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.issue_actionbar_menu_save) {
            pubIssue();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.issue_pub_image_upload:
                imm.hideSoftInputFromInputMethod(v.getWindowToken(),0);

                CharSequence[] items = {
                        IssueEditActivity.this.getString(R.string.img_from_album),
                        IssueEditActivity.this.getString(R.string.img_from_camera)
                };
                imgChooseItem(items);
                break;
            case R.id.issue_pub_image1:
                mImage1.setImageBitmap(null);
                mImage1.setVisibility(View.GONE);
                imgFiles[0] = null;
                break;
            case R.id.issue_pub_image2:
                mImage2.setImageBitmap(null);
                mImage2.setVisibility(View.GONE);
                imgFiles[1] = null;
                break;
            case R.id.issue_pub_image3:
                mImage3.setImageBitmap(null);
                mImage3.setVisibility(View.GONE);
                imgFiles[2] = null;
                break;
            case R.id.issue_pub_image4:
                mImage4.setImageBitmap(null);
                mImage4.setVisibility(View.GONE);
                imgFiles[3] = null;
                break;
            case R.id.issue_pub_image5:
                mImage5.setImageBitmap(null);
                mImage5.setVisibility(View.GONE);
                imgFiles[4] = null;
                break;
            default:break;
        }
    }

    private void imgChooseItem(CharSequence[] items){
        AlertDialog imageDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.ui_insert_image)
                .setIcon(android.R.drawable.btn_star)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent;
                            if (Build.VERSION.SDK_INT > 19) {
                                intent = new Intent();
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "选择照片"), ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
                            } else {
                                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "选择照片"), ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
                            }
                        } else if (which == 1) {
                            String savePath = "";
                            String storageState = Environment.getExternalStorageState();
                            if (storageState.equals(Environment.MEDIA_MOUNTED)) {
                                savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/XSFeedback/Camera/";
                                File saveDir = new File(savePath);
                                if (!saveDir.exists()) {
                                    saveDir.mkdirs();
                                }
                            }

                            if (StringUtils.isEmpty(savePath)) {
                                UIHelper.ToastMessage(IssueEditActivity.this,"无法保存照片，请检查SD卡是否挂载");
                                return;
                            }

                            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                            String fileName = "xs_"+timestamp+".jpg";
                            File out = new File(savePath,fileName);
                            Uri uri = Uri.fromFile(out);

                            theLarge = savePath + fileName;
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                            startActivityForResult(intent,ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);

                        }
                    }
                }).create();
        imageDialog.show();

    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if(resultCode != RESULT_OK){
            return;
        }

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.obj != null){
                    switch (msg.what){
                        case -1:
                            ViewUtils.showToast("最多只能上传5张照片!");
                            break;
                        case 1:
                            mImage1.setImageBitmap((Bitmap)msg.obj);
                            mImage1.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            mImage2.setImageBitmap((Bitmap) msg.obj);
                            mImage2.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            mImage3.setImageBitmap((Bitmap)msg.obj);
                            mImage3.setVisibility(View.VISIBLE);
                            break;
                        case 4:
                            mImage4.setImageBitmap((Bitmap)msg.obj);
                            mImage4.setVisibility(View.VISIBLE);
                            break;
                        case 5:
                            mImage5.setImageBitmap((Bitmap)msg.obj);
                            mImage5.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }
        };

        new Thread(){
            private String selectedImagePath;

            public void run(){
                Bitmap bitmap = null;
                if(requestCode == ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD){
                    if(data == null){
                        return;
                    }

                    Uri selectedImageUri = data.getData();
                    if(selectedImageUri != null){
                        selectedImagePath = ImageUtils.getImagePath(selectedImageUri,IssueEditActivity.this);

                    }

                    if(selectedImagePath != null){
                        theLarge = selectedImagePath;
                    }
                    else {
                        bitmap = ImageUtils.loadPicasaImageFromGalley(selectedImageUri, IssueEditActivity.this);
                    }

                    if(AppContext.isMethodsCompat(Build.VERSION_CODES.ECLAIR_MR1)){
                        String imaName = FileUtils.getFileName(theLarge);
                        if(imaName != null){
                            bitmap = ImageUtils.loadImgThumbnail(IssueEditActivity.this,imaName,MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
                            if(bitmap == null && !StringUtils.isEmpty(theLarge)){
                                bitmap = ImageUtils.loadImgThumbnail(theLarge,1000,1000);
                            }
                        }
                    }
                }
                else if(requestCode == ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA){
                    if(bitmap == null && !StringUtils.isEmpty(theLarge)){
                        bitmap = ImageUtils.loadImgThumbnail(theLarge,1000,1000);
                    }
                }
                int index = -1;
                if(bitmap != null){
                    String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() +"/XSFeedback/Camera/";
                    File saveDir = new File(savePath);
                    if(!saveDir.exists()){
                        saveDir.mkdirs();
                    }

                    String largeFileName = FileUtils.getFileName(theLarge);
                    String largeFilePath = savePath + largeFileName;

                    if(largeFileName.startsWith("thumb_") && new File(largeFilePath).exists()){
                        theThumbnail = largeFilePath;
                        index = setImageFile(new File(theThumbnail));
                    }
                    else {
                        String thumbFileName = "thumb_" + largeFileName;
                        theThumbnail = savePath + thumbFileName;
                        if(new File(theThumbnail).exists()){
                            index = setImageFile(new File(theThumbnail));
                        }
                        else {
                            try {
                                ImageUtils.createImageThumbnail(IssueEditActivity.this,theLarge,theThumbnail,800,100);
                                index = setImageFile(new File(theThumbnail));
                            }catch (IOException ex){
                                ex.printStackTrace();
                            }
                        }
                    }
                }

                ((AppContext)getApplication()).setProperty(tempIssueKey,theThumbnail);

                Message msg = new Message();
                msg.what = index;
                msg.obj = bitmap;
                handler.sendMessage(msg);

            }

        }.start();
    }


    private int setImageFile(File file) {
        for (int i = 0; i < imgFiles.length; i++) {
            if (imgFiles[i] == null) {
                imgFiles[i] = file;
                return i + 1;
            }
        }
        return -1;
    }

    private void pubIssue() {
        ProjectIssue issue = new ProjectIssue();
        issue.setTitle("");
        issue.setContent(mIssueEditDesc.getText().toString());
        issue.setPreReason(mIssueEditPreReason.getText().toString());
        issue.setAdvice(mIssueEditAdvice.getText().toString());
        issue.setType( mIssueEditType.getSelectedItem().toString());
        issue.setState( mIssueEditState.getSelectedItem().toString());
        final AlertDialog pubing = LightProgressDialog.create(this, "提交中...");
        XsFeedbackApi.pubCreateIssue(mProject.getId(),issue,imgFiles, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                ProjectIssue issue = JsonUtils.toBean(ProjectIssue.class,responseBody);
                if(issue != null){
                    UIHelper.ToastMessage(AppContext.getInstance(),"创建成功");
                    IssueEditActivity.this.finish();
                } else {
                    UIHelper.ToastMessage(AppContext.getInstance(),"创建失败");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                UIHelper.ToastMessage(AppContext.getInstance(),"创建失败" + statusCode);
            }

            @Override
            public void onStart() {
                super.onStart();
                pubing.show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                pubing.dismiss();
            }
        });
    }
}
