package com.autoai.gaspayment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autoai.gaspayment.R;
import com.autoai.gaspayment.bean.SmartSelectBean;

import java.util.ArrayList;
import java.util.List;

public class OrderOtptionAdater extends RecyclerView.Adapter {

    //布局类型
    private static final int ITEM_TYPE_TITLE = 1;
    private static final int ITEM_TYPE_VALUE = 2;
    private Context mContext;
    private List<SmartSelectBean> mDatas;
    private List<SmartSelectBean> mGasNumDatas;
    private List<SmartSelectBean> mGunNumDatas;

    public OrderOtptionAdater(Context context) {
        mContext = context;
    }

    public void setDatas(List<SmartSelectBean> gasNumDatas, List<SmartSelectBean> gunNumDatas) {
        mGasNumDatas = gasNumDatas;
        mGunNumDatas = gunNumDatas;
        mDatas = new ArrayList<>();
        mDatas.addAll(mGasNumDatas);
        mDatas.addAll(mGunNumDatas);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == mGasNumDatas.size()){
            return ITEM_TYPE_TITLE;
        } else {
            return ITEM_TYPE_VALUE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case ITEM_TYPE_TITLE:
                View titleView = LayoutInflater.from(mContext).inflate(R.layout.item_order_select_title, parent, false);
                viewHolder = new TitleViewHold(titleView);
                break;
            case ITEM_TYPE_VALUE:
                View valueView = LayoutInflater.from(mContext).inflate(R.layout.item_order_select_value, parent, false);
                viewHolder = new ValueViewHolder(valueView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case ITEM_TYPE_TITLE:
                ((TitleViewHold)holder).tvItemOrderSelectTitle.setText(mDatas.get(position).getValue());
                break;
            case ITEM_TYPE_VALUE:
                ((ValueViewHolder)holder).tvItemOrderSelectValue.setText(mDatas.get(position).getValue());
                ((ValueViewHolder)holder).tvItemOrderSelectValue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //油号数据
                        if (position <= mGasNumDatas.size()){
                            for (int i = 0; i < mGasNumDatas.size(); i++) {
                                if (i == position){
                                    mGasNumDatas.get(i).setCurrentSelect(true);
                                } else {
                                    mGasNumDatas.get(i).setCurrentSelect(false);
                                }
                            }

                        } else {
                            //枪号数据
                            for (int i = 0; i < mGunNumDatas.size(); i++) {
                                if (i + mGasNumDatas.size() == position){
                                    mGunNumDatas.get(i).setCurrentSelect(true);
                                } else {
                                    mGunNumDatas.get(i).setCurrentSelect(false);
                                }
                            }
                        }
                        notifyDataSetChanged();
                        Toast.makeText(mContext, mDatas.get(position).getValue(), Toast.LENGTH_SHORT).show();
                    }
                });
                if (mDatas.get(position).isCurrentSelect()){
                    ((ValueViewHolder)holder).tvItemOrderSelectValue.setBackgroundResource(R.drawable.background_refresh_button);
                } else {
                    ((ValueViewHolder)holder).tvItemOrderSelectValue.setBackgroundResource(R.drawable.background_style_smart_list);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class TitleViewHold extends RecyclerView.ViewHolder{

        private TextView tvItemOrderSelectTitle;

        public TitleViewHold(@NonNull View itemView) {
            super(itemView);
            tvItemOrderSelectTitle = itemView.findViewById(R.id.tv_item_order_select_title);
        }
    }

    class ValueViewHolder extends RecyclerView.ViewHolder{

        private TextView tvItemOrderSelectValue;

        public ValueViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemOrderSelectValue = itemView.findViewById(R.id.tv_item_order_select_value);
        }
    }
}
