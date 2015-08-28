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
import com.xcmgxs.xsmixfeedback.bean.ProjectDoc;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.ui.baseactivity.BaseActionBarActivity;
import com.xcmgxs.xsmixfeedback.ui.dialog.LightProgressDialog;
import com.xcmgxs.xsmixfeedback.util.FileUtils;
import com.xcmgxs.xsmixfeedback.util.ImageUtils;
import com.xcmgxs.xsmixfeedback.util.JsonUtils;
import com.xcmgxs.xsmixfeedback.util.StringUtils;

import org.apache.http.Header;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DocEditActivity extends BaseActionBarActivity implements View.OnClickListener {

    @InjectView(R.id.doc_edit_loading)
    ProgressBar mDocEditLoading;
    @InjectView(R.id.doc_edit_type)
    Spinner mDocEditType;
    @InjectView(R.id.doc_pub_image_upload)
    ImageView mDocPubImageUpload;
    @InjectView(R.id.doc_edit_desc)
    EditText mDocEditDesc;

    Project mProject;
    private File imgFile;
    private String theLarge;
    private String theThumbnail;

    private InputMethodManager imm;

    private Context mContext;

    private String tempDocKey = AppConfig.TEMP_DOC;
    private String tempDocImageKey = AppConfig.TEMP_DOC_IMAGE;

    private static final String[] TYPES = {
            "搅拌站安装告知函",
            "筒仓进场验收通知单",
            "筒仓涂装方案确认单",
            "筒仓、外加剂罐制作验收报告",
            "设备进场安装条件验收报告",
            "混凝土搅拌站综合验收通知单",
            "混凝土搅拌设备用户培训确认单",
            "客户满意度调查表",
            "混凝土搅拌设备验收报告",
            "混凝土搅拌设备安装服务卡"
    };

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_edit);
        ButterKnife.inject(this);
        mContext = this;

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        initView();
    }

    private void initView() {
        mProject = (Project) getIntent().getSerializableExtra(Contanst.PROJECT);

        mActionBar.setTitle("上传单据");
        mActionBar.setSubtitle(mProject.getName() + "/" + mProject.getCustomer());

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TYPES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDocEditType.setAdapter(adapter);
        mDocPubImageUpload.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doc_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.doc_actionbar_menu_save) {
            pubDoc();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.doc_pub_image_upload:
                imm.hideSoftInputFromInputMethod(v.getWindowToken(), 0);

                CharSequence[] items = {
                        DocEditActivity.this.getString(R.string.img_from_album),
                        DocEditActivity.this.getString(R.string.img_from_camera)
                };
                imgChooseItem(items);
                break;
            default:
                break;
        }
    }


    private void imgChooseItem(CharSequence[] items) {
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
                                UIHelper.ToastMessage(DocEditActivity.this, "无法保存照片，请检查SD卡是否挂载");
                                return;
                            }

                            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                            String fileName = "xs_" + timestamp + ".jpg";
                            File out = new File(savePath, fileName);
                            Uri uri = Uri.fromFile(out);

                            theLarge = savePath + fileName;
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);

                        }
                    }
                }).create();
        imageDialog.show();

    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1 && msg.obj != null) {
                    mDocPubImageUpload.setImageBitmap((Bitmap) msg.obj);
                    mDocPubImageUpload.setVisibility(View.VISIBLE);
                }
            }
        };

        new Thread() {
            private String selectedImagePath;

            public void run() {
                Bitmap bitmap = null;
                if (requestCode == ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD) {
                    if (data == null) {
                        return;
                    }

                    Uri selectedImageUri = data.getData();
                    if (selectedImageUri != null) {
                        selectedImagePath = ImageUtils.getImagePath(selectedImageUri, DocEditActivity.this);

                    }

                    if (selectedImagePath != null) {
                        theLarge = selectedImagePath;
                    } else {
                        bitmap = ImageUtils.loadPicasaImageFromGalley(selectedImageUri, DocEditActivity.this);
                    }

                    if (AppContext.isMethodsCompat(Build.VERSION_CODES.ECLAIR_MR1)) {
                        String imaName = FileUtils.getFileName(theLarge);
                        if (imaName != null) {
                            bitmap = ImageUtils.loadImgThumbnail(DocEditActivity.this, imaName, MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
                            if (bitmap == null && !StringUtils.isEmpty(theLarge)) {
                                bitmap = ImageUtils.loadImgThumbnail(theLarge, 1000, 1000);
                            }
                        }
                    }
                } else if (requestCode == ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA) {
                    if (bitmap == null && !StringUtils.isEmpty(theLarge)) {
                        bitmap = ImageUtils.loadImgThumbnail(theLarge, 1000, 1000);
                    }
                }

                if (bitmap != null) {
                    String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/XSFeedback/Camera/";
                    File saveDir = new File(savePath);
                    if (!saveDir.exists()) {
                        saveDir.mkdirs();
                    }

                    String largeFileName = FileUtils.getFileName(theLarge);
                    String largeFilePath = savePath + largeFileName;

                    if (largeFileName.startsWith("thumb_") && new File(largeFilePath).exists()) {
                        theThumbnail = largeFilePath;
                        imgFile = new File(theThumbnail);
                    } else {
                        String thumbFileName = "thumb_" + largeFileName;
                        theThumbnail = savePath + thumbFileName;
                        if (new File(theThumbnail).exists()) {
                            imgFile = new File(theThumbnail);
                        } else {
                            try {
                                ImageUtils.createImageThumbnail(DocEditActivity.this, theLarge, theThumbnail, 800, 80);
                                imgFile = new File(theThumbnail);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }

                ((AppContext) getApplication()).setProperty(tempDocKey, theThumbnail);

                Message msg = new Message();
                msg.what = 1;
                msg.obj = bitmap;
                handler.sendMessage(msg);

            }

        }.start();
    }


    private void pubDoc() {
        if(imgFile == null){
            UIHelper.ToastMessage(AppContext.getInstance(), "请上传一张照片！");
            return;
        }
        String type = mDocEditType.getSelectedItem().toString();
        String desc = mDocEditDesc.getText().toString();
        final AlertDialog pubing = LightProgressDialog.create(this, "提交中...");
        XsFeedbackApi.pubCreateDoc(mProject.getId(), null, desc, type, imgFile, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                ProjectDoc issue = JsonUtils.toBean(ProjectDoc.class, responseBody);
                if (issue != null) {
                    UIHelper.ToastMessage(AppContext.getInstance(), "创建成功");
                    DocEditActivity.this.finish();
                } else {
                    UIHelper.ToastMessage(AppContext.getInstance(), "创建失败");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                UIHelper.ToastMessage(AppContext.getInstance(), "创建失败" + statusCode);
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
