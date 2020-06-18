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

import butterknife.BindView;

/**
 * User: nxp
 * Date: 2020/6/18
 * Time: 15:05
 * Describe:
 */
public class SearchDestinationResultAdapter extends RecyclerView.Adapter<SearchDestinationResultAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mDatas;

    public void setDatas(List<String> datas) {
        mDatas = datas;
    }

    public SearchDestinationResultAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_destination_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItemSearchDestinationName;
        TextView tvItemSearchDestinationLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemSearchDestinationName = itemView.findViewById(R.id.tv_item_search_destination_name);
            tvItemSearchDestinationLocation = itemView.findViewById(R.id.tv_item_search_destination_location);
        }
    }
}
