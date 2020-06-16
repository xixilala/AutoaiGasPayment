package com.autoai.gaspayment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autoai.gaspayment.R;
import com.autoai.gaspayment.utils.ToastUtil;

import java.util.List;

/**
 * Created by Android Studio.
 * User: autoai
 * Date: 2020/6/8
 * Time: 16:38
 * Describe:
 */
public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.ViewHold> {

    private Context mContext;
    private List<String> mDatas;
    private ItemCLickListener mItemCLickListener;

    public void setItemCLickListener(ItemCLickListener itemCLickListener) {
        mItemCLickListener = itemCLickListener;
    }

    public MyOrderListAdapter(Context context) {
        mContext = context;
    }

    public void setDatas(List<String> datas) {
        mDatas = datas;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_my_order, parent, false);
        return new ViewHold(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.rl_my_order_item_parent:
                        if (mItemCLickListener != null){
                            mItemCLickListener.onItemClick(position);
                        }
                        break;
                    case R.id.btn_my_order_again:
                        ToastUtil.showTextToas(mContext, mContext.getText(R.string._gas_station_offline) + "\n" + mContext.getText(R.string._select_other_station));
                }
            }
        };
        holder.rlMyOrderItemParent.setOnClickListener(clickListener);
        holder.btnMyOrderAgain.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHold extends RecyclerView.ViewHolder{

        RelativeLayout rlMyOrderItemParent;
        TextView tvMyOrderName;
        TextView tvMyOrderTime;
        TextView tvMyOrderTimeRemaining;
        TextView tvMyOrderCancelOverTime;
        Button btnMyOrderAgain;
        TextView tvMyOrderPrice;
        TextView tvMyOrderWaitPay;
        TextView tvMyOrderApplingDrawback;
        TextView tvMyOrderHasDrawback;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            rlMyOrderItemParent = itemView.findViewById(R.id.rl_my_order_item_parent);
            tvMyOrderName = itemView.findViewById(R.id.tv_my_order_name);
            tvMyOrderTime = itemView.findViewById(R.id.tv_my_order_time);
            tvMyOrderTimeRemaining = itemView.findViewById(R.id.tv_my_order_time_remaining);
            tvMyOrderCancelOverTime = itemView.findViewById(R.id.tv_my_order_cancel_over_time);
            btnMyOrderAgain = itemView.findViewById(R.id.btn_my_order_again);
            tvMyOrderPrice = itemView.findViewById(R.id.tv_my_order_price);
            tvMyOrderWaitPay = itemView.findViewById(R.id.tv_my_order_wait_pay);
            tvMyOrderApplingDrawback = itemView.findViewById(R.id.tv_my_order_appling_drawback);
            tvMyOrderHasDrawback = itemView.findViewById(R.id.tv_my_order_has_drawback);
        }
    }

    public interface ItemCLickListener{
        void onItemClick(int position);
    }
}
