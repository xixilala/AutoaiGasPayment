package com.autoai.gaspayment.test;

import androidx.lifecycle.MutableLiveData;

import com.autoai.gaspayment.base.BaseViewModel;
import com.autoai.gaspayment.view.OrderPaymentFragment;

/**
 * User: nxp
 * Date: 2020/6/15
 * Time: 14:25
 * Describe:
 */
public class MainViewModel extends BaseViewModel {

    //监听子fragment，跳转OrderPaymentFragment页面
    public MutableLiveData<OrderPaymentFragment.ToOrderPaymentFragmentBean> getIdMutableLeveData(){
        return getMutableLiveData(OrderPaymentFragment.ToOrderPaymentFragmentBean.class);
    }
    public void toOrderCodePayFragment(int id){
        getMutableLiveData(OrderPaymentFragment.ToOrderPaymentFragmentBean.class).postValue(new OrderPaymentFragment.ToOrderPaymentFragmentBean(id));
    }

    //测试方法
    public MutableLiveData<User> getUserMutableLiveData(){
        return getMutableLiveData(User.class);
    }

    public MutableLiveData<Person> getPersonMutableLiveData(){
        return getMutableLiveData(Person.class);
    }

    public void changeUser(){
        getMutableLiveData(User.class).postValue(new User("xixi","27"));
    }
}
