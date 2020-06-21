package com.autoai.gaspayment.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.autoai.gaspayment.R;

/**
 * Created by Android Studio.
 * User: autoai
 * Date: 2020/6/9
 * Time: 15:52
 * Describe:
 */
public class ToastUtil {

    public static void showToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        View view = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
        TextView tvToastMsg = view.findViewById(R.id.tv_toast_msg);
        toast.setView(tvToastMsg);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @SuppressLint("WrongConstant")
    public static void showTextToas(Context context, String message){
        View toastview= LayoutInflater.from(context).inflate(R.layout.toast_custom,null);
        TextView text = (TextView) toastview.findViewById(R.id.tv_toast_msg);
        text.setText(message);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.setDuration(3000);
        toast.setView(toastview);
        toast.show();
    }
}
