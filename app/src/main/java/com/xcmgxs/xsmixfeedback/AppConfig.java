package com.xcmgxs.xsmixfeedback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import com.xcmgxs.xsmixfeedback.common.Contanst;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

/**
 * 应用程序配置类：用于保存用户相关信息及设置
 *
 * @author zhangyi
 * @version 1.0
 * @created 2014-04-21
 */
public class AppConfig {

    private final static String APP_CONFIG = "config";

    public final static String CONF_APP_UNIQUEID = "APP_UNIQUEID";

    public final static String CONF_LOAD_IMAGE = "perf_loadimage";

    public final static String CONF_HTTPS_LOGIN = "perf_httpslogin";

    public final static String CONF_RECEIVENOTICE = "perf_receivenotice";

    public final static String CONF_VOICE = "perf_voice";

    public final static String CONF_CHECKUP = "perf_checkup";

    public final static String CONF_FRIST_START = "isFristStart";

    public static final String KEY_NOTIFICATION_ACCEPT = "KEY_NOTIFICATION_ACCEPT";
    public static final String KEY_NOTIFICATION_SOUND = "KEY_NOTIFICATION_SOUND";
    public static final String KEY_NOTIFICATION_VIBRATION = "KEY_NOTIFICATION_VIBRATION";
    public final static String KEY_NOTIFICATION_DISABLE_WHEN_EXIT = "isFristStart";




    //默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "XCMG_XS"
            + File.separator
            + "Feedback"
            + File.separator;

    //默认存放图片的路径
    public final static String DEFAULT_SAVE_FILE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "XCMG_XS"
            + File.separator
            + "Feedback"
            + File.separator
            + "download"
            + File.separator;

    public static final String TEMP_LOG = "temp_log";
    public static final String TEMP_LOG_IMAGE = "temp_log_image";

    public static final String TEMP_ISSUE = "temp_issue";
    public static final String TEMP_ISSUE_IMAGE = "temp_issue_image";

    public static final String TEMP_FILE = "temp_issue";
    public static final String TEMP_FILE_IMAGE = "temp_issue_image";

    private Context mContext;
    private static AppConfig appConfig;

    public static AppConfig getAppConfig(Context context) {
        if (appConfig == null) {
            appConfig = new AppConfig();
            appConfig.mContext = context;
        }
        return appConfig;
    }

    /**
     * 获取Preference设置
     */
    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 是否加载显示文章图片
     */
    public static boolean isLoadImage(Context context) {
        return getSharedPreferences(context).getBoolean(CONF_LOAD_IMAGE, true);
    }

    /**
     * 获得用户的token
     *
     * @return
     */
    public String getPrivateToken() {
        return get(Contanst.PROP_KEY_PRIVATE_TOKEN);
    }

    public String get(String key) {
        Properties props = get();
        return (props != null) ? props.getProperty(key) : null;
    }

    public Properties get() {
        FileInputStream fis = null;
        Properties props = new Properties();
        try {
            // 读取files目录下的config
            // fis = activity.openFileInput(APP_CONFIG);

            // 读取app_config目录下的config
            File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
            fis = new FileInputStream(dirConf.getPath() + File.separator
                    + APP_CONFIG);

            props.load(fis);
        } catch (Exception e) {
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return props;
    }

    private void setProps(Properties p) {
        FileOutputStream fos = null;
        try {
            // 把config建在files目录下
            // fos = activity.openFileOutput(APP_CONFIG, Context.MODE_PRIVATE);

            // 把config建在(自定义)app_config的目录下
            File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
            File conf = new File(dirConf, APP_CONFIG);
            fos = new FileOutputStream(conf);

            p.store(fos, null);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    public void set(Properties ps) {
        Properties props = get();
        props.putAll(ps);
        setProps(props);
    }

    public void set(String key, String value) {
        Properties props = get();
        props.setProperty(key, value);
        setProps(props);
    }

    public void remove(String... key) {
        Properties props = get();
        for (String k : key)
            props.remove(k);
        setProps(props);
    }
}