package com.autoai.gaspayment.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Android Studio.
 * User: nxp
 * Date: 2020/6/10
 * Time: 11:13
 * Describe: 搜索历史记录工具
 */
public class SearchHistoryUtil {

    private final static String PREFERENCE_NAME = "gasPayment";
    private final static String GAS_PAYMENT_SEARCH_HISTORY = "gasPaymentSearchHitory";
    //搜加油站
    public final static String TYPEY_GAS_STATION = "typeGasStation";
    //搜目的地
    public final static String TYPE_DESTINATION = "typeDestination";
    //历史记录保存30条
    private final static int MAX_SIZE = 30;

    /**
     * 保存搜索记录
     * @param inputText
     */
    public static void saveSearchHistory(Context context, String inputText, String searchType){
        inputText.replaceAll(";" ,"");
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (TextUtils.isEmpty(inputText)){
            return;
        }
        //获取之前保存的历史记录
        String longHistory = sp.getString(searchType + GAS_PAYMENT_SEARCH_HISTORY, "");
        //分号截取，保存在数组中
        String[] tmpHistory = longHistory.split(";");
        //将数组转换成ArrayList
        List<String> historyList = new ArrayList<>(Arrays.asList(tmpHistory));
        SharedPreferences.Editor editor = sp.edit();
        if (historyList.size() > 0){
            //移除之前重复添加的元素
            for (int i = 0; i < historyList.size(); i++) {
                if (inputText.equals(historyList.get(i))){
                    historyList.remove(i);
                    break;
                }
            }
            //将新输入的文字添加集合的最前面
            historyList.add(0, inputText);
            //超过最多保存记录，删除最早那一条
            if (historyList.size() > MAX_SIZE){
                historyList.remove(historyList.size() - 1);
            }
            //;号拼接
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < historyList.size(); i++) {
                sb.append(historyList.get(i) + ";");
            }
            //保存sp
            editor.putString(searchType + GAS_PAYMENT_SEARCH_HISTORY, sb.toString());
            editor.commit();
        } else {
            //之前未提交过
            editor.putString(searchType + GAS_PAYMENT_SEARCH_HISTORY, inputText + ";");
            editor.commit();
        }
    }

    public static List<String> getSearchHistory(Context context, String searchType){
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        String longHistory = sp.getString(searchType + GAS_PAYMENT_SEARCH_HISTORY, "");
        String[] tmpHistory = longHistory.split(";");
        List<String> historyList = new ArrayList<>(Arrays.asList(tmpHistory));
        if (historyList.size() == 1 && historyList.get(0).equals("")){
            historyList.clear();
        }
        return historyList;
    }

    public static void clearHistory(Context context, String searchType){
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(searchType + GAS_PAYMENT_SEARCH_HISTORY, "");
        editor.commit();
    }
}
