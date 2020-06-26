package com.autoai.gaspayment.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;

import com.autoai.gaspayment.R;

public class AlerDialogUtil {
    public static AlertDialog getCustomDialog(Context context, DialogButtonClickListener dialogClickListener, String posText, String negText, String title){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        View v = null;
        if (TextUtils.isEmpty(title)){
            v = LayoutInflater.from(context).inflate(R.layout.dialog_custom_alert_new, null);
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.dialog_custom_alert_with_title_new, null);
            TextView tvTitle = v.findViewById(R.id.tv_dialog_title);
            tvTitle.setText(title);
        }
        Button positive = v.findViewById(R.id.btn_logindialog_login);
        Button negative = v.findViewById(R.id.btn_logindialog_cancel);
        positive.setText(posText);
        negative.setText(negText);
        dialogBuilder.setView(v);
        dialogBuilder.setCancelable(false);
        AlertDialog dialog = dialogBuilder.create();
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClickListener.onPositiveButtonClick();
                dialog.dismiss();
            }
        });
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClickListener.onNegativeButtonClick();
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public static AlertDialog getPaySuccDialog(Context context, DialogButtonClickListener listener){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_custom_alert_one_button, null);
        Button positive = v.findViewById(R.id.btn_pay_succ_okay);
        TextView tvPaySuccTime = v.findViewById(R.id.tv_pay_succ_time);
        TextView tvPaySuccPrice = v.findViewById(R.id.tv_pay_succ_price);
        TextView tvPaySuccOrderNum = v.findViewById(R.id.tv_pay_succ_order_num);
        dialogBuilder.setView(v);
        dialogBuilder.setCancelable(false);
        AlertDialog dialog = dialogBuilder.create();
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onPositiveButtonClick();
            }
        });
        return dialog;
    }


    public interface DialogButtonClickListener {

        void onPositiveButtonClick();

        void onNegativeButtonClick();
    }
}
