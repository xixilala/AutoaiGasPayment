package com.autoai.gaspayment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autoai.gaspayment.R;

import java.util.List;

/**
 * Created by Android Studio.
 * User: autoai
 * Date: 2020/6/9
 * Time: 19:26
 * Describe:
 */
public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mDatas;
    private ItemclickListener mItemclickListener;

    public SearchHistoryAdapter(Context context) {
        mContext = context;
    }

    public void setItemclickListener(ItemclickListener itemclickListener) {
        mItemclickListener = itemclickListener;
    }

    public void setDatas(List<String> datas) {
        mDatas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvSearchHistoryValue.setText(mDatas.get(position));
        holder.tvSearchHistoryValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemclickListener != null){
                    mItemclickListener.onItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvSearchHistoryValue;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSearchHistoryValue = itemView.findViewById(R.id.tv_search_history_value);
        }
    }

    public interface ItemclickListener{

        void onItemClick(View view, int position);
    }
}
