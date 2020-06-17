package com.autoai.gaspayment.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import com.autoai.gaspayment.R;
import com.autoai.gaspayment.base.BaseFragment;
import com.autoai.gaspayment.base.BaseNavigationFragment;
import com.autoai.gaspayment.test.MainViewModel;
import com.autoai.gaspayment.test.Person;
import com.autoai.gaspayment.utils.AlerDialogUtil;
import com.autoai.gaspayment.utils.DecimalDigitsInputFilter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 智慧加油下单第二步选择金额
 */
public class OrderSelectSecondStepFragment extends BaseNavigationFragment {
    @BindView(R.id.title_back_click)
    View titleBackClick;
    @BindView(R.id.tv_order_second_select_price)
    TextView tvOrderSecondSelectPrice;
    @BindView(R.id.tv_order_second_select_recommend_price)
    TextView tvOrderSecondSelectRecommendPrice;
    @BindView(R.id.tv_order_second_select_200_price)
    TextView tvOrderSecondSelect200Price;
    @BindView(R.id.tv_order_second_select_100_price)
    TextView tvOrderSecondSelect100Price;
    @BindView(R.id.ed_order_second_select_other_price)
    EditText edOrderSecondSelectOtherPrice;
    @BindView(R.id.tv_order_second_select_discont_price_num)
    TextView tvOrderSecondSelectDiscontPriceNum;
    @BindView(R.id.tv_order_second_select_real_price_num)
    TextView tvOrderSecondSelectRealPriceNum;
    @BindView(R.id.btn_orderselect_second_order)
    Button btnOrderselectSecondOrder;
    @BindView(R.id.btn_orderselect_second_reselection)
    Button btnOrderselectSecondReselection;
    @BindView(R.id.rbtn_wechat)
    RadioButton rbtnWechat;
    @BindView(R.id.rbtn_ali)
    RadioButton rbtnAli;
    @BindView(R.id.ll_order_second_select_reconmmand_price_parent)
    LinearLayout llOrderSecondSelectReconmmandPriceParent;
    @BindView(R.id.tv_hint_out_of_rang)
    TextView tvHintOutOfRang;
    private InputMethodManager mInputMethodManager;

    public static OrderSelectSecondStepFragment newInstance(String s) {
        OrderSelectSecondStepFragment fragment = new OrderSelectSecondStepFragment();
        Bundle args = new Bundle();
        args.putString("testS", s);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_select_second_step;
    }

    @Override
    protected void initData() {
        mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        Drawable weChatPayDrawable = getResources().getDrawable(R.mipmap.wechat_pay);
        Drawable aliPayDrawable = getResources().getDrawable(R.mipmap.ali_pay);
        weChatPayDrawable.setBounds(0, 0, 151, 54);
        aliPayDrawable.setBounds(0, 0, 151, 54);
        rbtnWechat.setCompoundDrawables(null, null, weChatPayDrawable, null);
        rbtnAli.setCompoundDrawables(null, null, aliPayDrawable, null);
        rbtnWechat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity(), "微信支付", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rbtnAli.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity(), "支付宝支付", Toast.LENGTH_SHORT).show();
                }
            }
        });
        edOrderSecondSelectOtherPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditTextIsFocus(true, edOrderSecondSelectOtherPrice);
            }
        });
        edOrderSecondSelectOtherPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    setSelectPriceState(v.getId());
                } else {
                    if (TextUtils.isEmpty(edOrderSecondSelectOtherPrice.getText())){
                        edOrderSecondSelectOtherPrice.setText(getString(R.string.other_price));
                    }
                }
            }
        });
        edOrderSecondSelectOtherPrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                //监听确认键
                if (!TextUtils.isEmpty(edOrderSecondSelectOtherPrice.getText().toString()) && Double.parseDouble(edOrderSecondSelectOtherPrice.getText().toString()) > 160){
                    tvHintOutOfRang.setVisibility(View.VISIBLE);
                } else {
                    tvHintOutOfRang.setVisibility(View.GONE);
                }
                return false;
            }
        });
        edOrderSecondSelectOtherPrice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
    }

    @Override
    protected void lazyLoadData() {

    }


    @OnClick({R.id.iv_title_back, R.id.title_back_click, R.id.btn_orderselect_second_order, R.id.btn_orderselect_second_reselection,
            R.id.ll_order_second_select_reconmmand_price_parent, R.id.tv_order_second_select_200_price, R.id.tv_order_second_select_100_price})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back_click:
//                Navigation.findNavController(view).navigate(R.id.action_orderSecondSelect_to_firstSelect);
                ((OrderPaymentFragment)getParentFragment()).preStep();
                break;
            case R.id.btn_orderselect_second_order:
                if (true){
                    //距离远提示
                    AlertDialog dialog = AlerDialogUtil.getCustomDialog(getActivity(), new AlerDialogUtil.DialogButtonClickListener() {
                        @Override
                        public void onPositiveButtonClick() {
//                            getViewModel(MainViewModel.class).toOrderCodePayFragment(R.id.order_payment_fragment_main_to_order_code_pay_fragment);
                            ((OrderPaymentFragment)getParentFragment()).toOrderCodePayFragment();
                        }

                        @Override
                        public void onNegativeButtonClick() {

                        }
                    }, getString(R.string.continue_to_order), getString(R.string.cancel), getString(R.string.warm_hint));
                    dialog.show();
                    dialog.getWindow().setBackgroundDrawable(null);
                }
                break;
            case R.id.btn_orderselect_second_reselection:
                //TODO getActivity().finish();
                break;
            case R.id.ll_order_second_select_reconmmand_price_parent:
                setSelectPriceState(R.id.ll_order_second_select_reconmmand_price_parent);
                break;
            case R.id.tv_order_second_select_200_price:
                setSelectPriceState(R.id.tv_order_second_select_200_price);
                break;
            case R.id.tv_order_second_select_100_price:
                setSelectPriceState(R.id.tv_order_second_select_100_price);
                break;
        }
    }

    private void setEditTextIsFocus(boolean isFocus, EditText editTextNotFocus){
        if (isFocus){
            edOrderSecondSelectOtherPrice.setFocusable(true);//设置输入框可聚集
            edOrderSecondSelectOtherPrice.setFocusableInTouchMode(true);//设置触摸聚焦
            edOrderSecondSelectOtherPrice.requestFocus();//请求焦点
            edOrderSecondSelectOtherPrice.findFocus();
            mInputMethodManager.showSoftInput(edOrderSecondSelectOtherPrice, InputMethodManager.SHOW_FORCED);// 显示输入法
        } else {
            editTextNotFocus.setFocusable(false);//设置输入框不可聚焦，即失去焦点和光标
            if (mInputMethodManager.isActive()) {
                mInputMethodManager.hideSoftInputFromWindow(editTextNotFocus.getWindowToken(), 0);// 隐藏输入法
            }
        }
    }

    private void setSelectPriceState(int clickViewId){
        switch (clickViewId){
            case R.id.ll_order_second_select_reconmmand_price_parent:
                llOrderSecondSelectReconmmandPriceParent.setBackgroundResource(R.drawable.background_refresh_button);
                tvOrderSecondSelect200Price.setBackgroundResource(R.drawable.background_style_smart_list);
                tvOrderSecondSelect100Price.setBackgroundResource(R.drawable.background_style_smart_list);
                edOrderSecondSelectOtherPrice.setBackgroundResource(R.drawable.background_style_smart_list);
                setEditTextIsFocus(false, edOrderSecondSelectOtherPrice);
                break;
            case R.id.tv_order_second_select_200_price:
                llOrderSecondSelectReconmmandPriceParent.setBackgroundResource(R.drawable.background_style_smart_list);
                tvOrderSecondSelect200Price.setBackgroundResource(R.drawable.background_refresh_button);
                tvOrderSecondSelect100Price.setBackgroundResource(R.drawable.background_style_smart_list);
                edOrderSecondSelectOtherPrice.setBackgroundResource(R.drawable.background_style_smart_list);
                setEditTextIsFocus(false, edOrderSecondSelectOtherPrice);
                break;
            case R.id.tv_order_second_select_100_price:
                llOrderSecondSelectReconmmandPriceParent.setBackgroundResource(R.drawable.background_style_smart_list);
                tvOrderSecondSelect200Price.setBackgroundResource(R.drawable.background_style_smart_list);
                tvOrderSecondSelect100Price.setBackgroundResource(R.drawable.background_refresh_button);
                edOrderSecondSelectOtherPrice.setBackgroundResource(R.drawable.background_style_smart_list);
                setEditTextIsFocus(false, edOrderSecondSelectOtherPrice);
                break;
            case R.id.ed_order_second_select_other_price:
                llOrderSecondSelectReconmmandPriceParent.setBackgroundResource(R.drawable.background_style_smart_list);
                tvOrderSecondSelect200Price.setBackgroundResource(R.drawable.background_style_smart_list);
                tvOrderSecondSelect100Price.setBackgroundResource(R.drawable.background_style_smart_list);
//                    edOrderSecondSelectOtherPrice.setBackgroundResource(R.drawable.background_refresh_button);
                if (edOrderSecondSelectOtherPrice.getText().toString().equals(getString(R.string.other_price))){
                    edOrderSecondSelectOtherPrice.setText("");
                }
                break;
        }
    }
}
