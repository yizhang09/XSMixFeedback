package com.xcmgxs.xsmixfeedback.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.xcmgxs.xsmixfeedback.AppConfig;
import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.api.ApiClient;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.bean.ProjectLog;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.ui.baseactivity.BaseActionBarActivity;
import com.xcmgxs.xsmixfeedback.util.FileUtils;
import com.xcmgxs.xsmixfeedback.util.ImageUtils;
import com.xcmgxs.xsmixfeedback.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogEditActivity extends BaseActionBarActivity implements View.OnClickListener {

    private final static String TAG = "PROJECT_LOG";

    // 录制
    private final static byte RECORDER_STATE_RECARDING = 0x0;
    // 录制时间太短
    private final static byte RECORDER_STATE_SHORT = 0x01;
    // 发布中
    private final static byte LOG_PUBING = 0X02;
    // 取消发布
    private final static byte RECORDER_STATE_CANALE = 0x03;
    // 普通日志
    private final static byte LOG_TYPE_CONTENT = 0X04;
    // 语音日志
    private final static byte LOG_TYPE_VOICE = 0X05;

    // 语音最短时间(秒)
    private final static int RECORDER_TIME_MINTIME = 1;
    // 语音最长时间(秒)
    private final static int RECORDER_TIME_MAXTIME = 60;


    private AppContext appContext;
    private DisplayMetrics dm;
    private FrameLayout mform;
    private EditText mContent;
    private ImageView mFace;
    private ImageView mAudio;
    private ImageView mPick;
    private Spinner mSpinerType;
    private Spinner mSpinerStep;
    private Spinner mSpinerProgress;
    private Handler mHandler;

    private ImageView mImage;

    private GridView mGridView;

    private ProjectLog log;
    private Project mProject;
    private File imgFile;
    private File amrFile;
    private String theLarge;
    private String theThumbnail;
    private InputMethodManager imm;
    private LinearLayout mMessage;


    private Context mContext;

    private String tempLogKey = AppConfig.TEMP_LOG;
    private String tempLogImageKey = AppConfig.TEMP_LOG_IMAGE;

    private static final String[] TYPES = {"普通日志","基础", "筒仓", "发车接车", "安装调试", "封装"};

    private ArrayAdapter<String> adaptertype;

    private static final String[] STEPS = {"无","筒仓进场", "发车","安装", "左站安装完毕","右站安装完毕", "左站调试","右站调试", "左站出料","右站出料", "左站验收", "右站验收"};

    private ArrayAdapter<String> adapterstep;

    private static final String[] PROGRESS = {"基础未做","基础制作", "筒仓施工", "正在发车", "正在安装", "安装完毕","签字验收"};

    private ArrayAdapter<String> adapterprogress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_edit);

        mContext = this;

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }



    private void initView(){
        appContext = getXSApplication();
        dm = new DisplayMetrics();
        Intent intent = getIntent();
        mProject = (Project) intent.getSerializableExtra(Contanst.PROJECT);
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mform = (FrameLayout)findViewById(R.id.log_pub_form);
        mContent = (EditText)findViewById(R.id.log_pub_content);
        mFace = (ImageView)findViewById(R.id.log_pub_footbar_face);
        mPick = (ImageView)findViewById(R.id.log_pub_footbar_photo);
        mAudio = (ImageView)findViewById(R.id.log_pub_footbar_audio);
        mGridView = (GridView)findViewById(R.id.log_pub_faces);
        mImage = (ImageView)findViewById(R.id.log_pub_image);
        mMessage = (LinearLayout)findViewById(R.id.log_pub_message1);
        mSpinerType = (Spinner)findViewById(R.id.log_pub_type);
        mSpinerStep = (Spinner)findViewById(R.id.log_pub_step);
        mSpinerProgress = (Spinner)findViewById(R.id.log_pub_progress);

        mFace.setOnClickListener(this);
        mPick.setOnClickListener(this);
        mAudio.setOnClickListener(this);

        adaptertype = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TYPES);
        adaptertype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinerType.setAdapter(adaptertype);

        adapterstep = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, STEPS);
        adapterstep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinerStep.setAdapter(adapterstep);

        adapterprogress = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PROGRESS);
        adapterprogress.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinerProgress.setAdapter(adapterprogress);

        // 编辑器点击事件
        mContent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 显示软键盘
                showIMM();
            }
        });

    }

    private void showIMM() {
        mFace.setTag(1);
        showOrHideIMM();
    }

    private void showOrHideIMM() {
        if (mFace.getTag() == null) {
            // 隐藏软键盘
            imm.hideSoftInputFromWindow(mContent.getWindowToken(), 0);
            // 显示表情
            //showFace();
        } else {
            // 显示软键盘
            imm.showSoftInput(mContent, 0);
            // 隐藏表情
            //hideFace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.log_actionbar_menu_save) {
            String content = mContent.getText().toString();
            pubLog(LOG_TYPE_CONTENT,content);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.log_pub_footbar_face:
                break;
            case R.id.log_pub_footbar_photo:
                imm.hideSoftInputFromInputMethod(v.getWindowToken(),0);

                CharSequence[] items = {
                        LogEditActivity.this.getString(R.string.img_from_album),
                        LogEditActivity.this.getString(R.string.img_from_camera)
                };

                imgChooseItem(items);
                break;
            case R.id.log_pub_footbar_audio:
                break;
            case R.id.log_actionbar_menu_save:
                String content = mContent.getText().toString();
                pubLog(LOG_TYPE_CONTENT,content);
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
                                UIHelper.ToastMessage(LogEditActivity.this,"无法保存照片，请检查SD卡是否挂载");
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
                if(msg.what == 1 && msg.obj != null){
                    mImage.setImageBitmap((Bitmap)msg.obj);
                    mImage.setVisibility(View.VISIBLE);
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
                        selectedImagePath = ImageUtils.getImagePath(selectedImageUri,LogEditActivity.this);

                    }

                    if(selectedImagePath != null){
                        theLarge = selectedImagePath;
                    }
                    else {
                        bitmap = ImageUtils.loadPicasaImageFromGalley(selectedImageUri, LogEditActivity.this);
                    }

                    if(AppContext.isMethodsCompat(Build.VERSION_CODES.ECLAIR_MR1)){
                        String imaName = FileUtils.getFileName(theLarge);
                        if(imaName != null){
                            bitmap = ImageUtils.loadImgThumbnail(LogEditActivity.this,imaName,MediaStore.Images.Thumbnails.MICRO_KIND);
                            if(bitmap == null && !StringUtils.isEmpty(theLarge)){
                                bitmap = ImageUtils.loadImgThumbnail(theLarge,100,100);
                            }
                        }
                    }
                }
                else if(requestCode == ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA){
                    if(bitmap == null && !StringUtils.isEmpty(theLarge)){
                        bitmap = ImageUtils.loadImgThumbnail(theLarge,100,100);
                    }
                }

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
                        imgFile = new File(theThumbnail);
                    }
                    else {
                        String thumbFileName = "thumb_" + largeFileName;
                        theThumbnail = savePath + thumbFileName;
                        if(new File(theThumbnail).exists()){
                            imgFile = new File(theThumbnail);
                        }
                        else {
                            try {
                                ImageUtils.createImageThumbnail(LogEditActivity.this,theLarge,theThumbnail,800,80);
                                imgFile = new File(theThumbnail);
                            }catch (IOException ex){
                                ex.printStackTrace();
                            }
                        }
                    }
                }

                ((AppContext)getApplication()).setProperty(tempLogImageKey,theThumbnail);

                Message msg = new Message();
                msg.what = 1;
                msg.obj = bitmap;
                handler.sendMessage(msg);

            }

        }.start();
    }

    // 录音的路径
    private String savePath;
    // 语音动弹文件名
    private String fileName;
    // 是否正在录音中
    private boolean isRecording = false;
    // 是否超时
    private boolean IS_OVERTIME = false;


    //发布日志
    private void pubLog(final byte logType,String content){
        IS_OVERTIME = false;
//        if(!appContext.isLogin()){
//            UIHelper.showLoginActivity(LogEditActivity.this);
//            return;
//        }

        log = new ProjectLog();

        mMessage.setVisibility(View.VISIBLE);
        mform.setVisibility(View.GONE);

        log.setProjectid(mProject.getId());
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        log.setCreatedate(date);
        log.setAuthorid(appContext.getLoginUid());
        log.setContent(content);

        if(logType == LOG_TYPE_CONTENT){
            log.setImagefile(imgFile);
        }

        if(logType == LOG_TYPE_VOICE){
            log.setAmrfile(amrFile);
        }

        new AsyncTask<Void,Void,Message>(){

            @Override
            protected Message doInBackground(Void... params) {
                Message msg = new Message();
                try {
                    msg.what = 1;
                    msg.obj = ApiClient.pubProjectLog(appContext,log);
                    //这里添加发送日志接口调用代码

                }
                catch (Exception e){
                    msg.what = -1;
                    msg.obj = e;
                    e.printStackTrace();
                }

                return msg;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Message message) {
                getActivity().finish();
                super.onPostExecute(message);
            }


        }.execute();





    }
}
