package com.xcmgxs.xsmixfeedback.ui.dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xcmgxs.xsmixfeedback.R;

/**
 * Created by zhangyi on 2015-06-03.
 */
public class LightProgressDialog extends ProgressDialog {

    /*
    * Create progress dialog
    */
    public LightProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    private LightProgressDialog(Context context, CharSequence message) {
        super(context, THEME_HOLO_LIGHT);
    }

    public static AlertDialog create(Context context,CharSequence message){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO){
            ProgressDialog dialog;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
                dialog = new LightProgressDialog(context,message);
            }
            else {
                dialog = new ProgressDialog(context);
                dialog.setInverseBackgroundForced(true);
            }
            dialog.setMessage(message);
            dialog.setIndeterminate(true);
            dialog.setProgressStyle(STYLE_SPINNER);
            return dialog;
        }
        else {
            AlertDialog dialog = LightAlertDialog.create(context);
            dialog.setInverseBackgroundForced(true);
            View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog,null);
            ((TextView)view.findViewById(R.id.tv_loading)).setText(message);
            dialog.setView(view);
            return dialog;
        }
    }



}
