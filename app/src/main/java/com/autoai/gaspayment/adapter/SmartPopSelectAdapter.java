package com.autoai.gaspayment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autoai.gaspayment.R;
import com.autoai.gaspayment.bean.SmartSelectBean;

import java.util.List;

public class SmartPopSelectAdapter extends RecyclerView.Adapter<SmartPopSelectAdapter.ViewHolder> {

    private Context mContext;
    private List<SmartSelectBean> mDates;
    private ItemClickListener mItemClickListener;

    public SmartPopSelectAdapter(Context context) {
        mContext = context;
    }

    public void setDates(List<SmartSelectBean> dates) {
        mDates = dates;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_smart_pop_select, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvSmartPopSelectValue.setText(mDates.get(position).getValue());
        if (mDates.get(position).isCurrentSelect()){
            holder.rlSmartPopSelectParent.setBackgroundColor(mContext.getColor(R.color._242B47));
        } else {
            holder.rlSmartPopSelectParent.setBackground(null);
        }

        holder.rlSmartPopSelectParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null){
                    mItemClickListener.onItemClick(position);
                    for (int i = 0; i < mDates.size(); i++) {
                        mDates.get(i).setCurrentSelect(false);
                    }
                    mDates.get(position).setCurrentSelect(true);
                    notifyDataSetChanged();
                }
            }
        });
        //最后一条数据不加分割线
        if (mDates.size() == position + 1){
            holder.viewSmartPopSelectLine.setVisibility(View.GONE);
        } else {
            holder.viewSmartPopSelectLine.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mDates == null ? 0 : mDates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvSmartPopSelectValue;
        View viewSmartPopSelectLine;
        RelativeLayout rlSmartPopSelectParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSmartPopSelectValue = itemView.findViewById(R.id.tv_smart_pop_select_value);
            viewSmartPopSelectLine = itemView.findViewById(R.id.view_smart_pop_select_line);
            rlSmartPopSelectParent = itemView.findViewById(R.id.rl_smart_pop_select_parent);
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
