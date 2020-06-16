package com.autoai.gaspayment.base;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: nxp
 * Date: 2020/6/15
 * Time: 13:48
 * Describe: 基础ViewModel类，管理LiveData
 */
public class BaseViewModel extends ViewModel {

    private Map<String, MutableLiveData> mMaps;

    /**
     * 在viewModelProvider里通过class.newInstance创建实例
     */
    public BaseViewModel(){
        //初始化集合（线程安全）
        mMaps = new ConcurrentHashMap<>();
    }

    /**
     * 通过指定的数据实体类获取对应的MutableLiveData类
     */
    protected <T> MutableLiveData<T> getMutableLiveData(Class<T> clazz){
        return getMutableLiveData(null,clazz);
    }

    /**
     * 通过指定的key或者数据实体类获取对应的MutableLiveData类
     */
    protected <T> MutableLiveData<T> getMutableLiveData(String key,Class<T> clazz){
        String keyName = "";
        if (TextUtils.isEmpty(key)){
            keyName = clazz.getCanonicalName();
        } else {
            keyName = key;
        }
        MutableLiveData<T> mutableLiveData = mMaps.get(keyName);
        //1.判断集合是否已经存在livedata
        if (mutableLiveData != null){
            return mutableLiveData;
        }
        //2.如果集合中没有对应实体类的livedata对象，就创建并添加到集合中
        mutableLiveData = new MutableLiveData<>();
        mMaps.put(keyName, mutableLiveData);
        return mutableLiveData;
    }

    /**
     * 在对应的FragmentActivity销毁之后调用
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        if (mMaps != null){
            mMaps.clear();
        }
    }
}
