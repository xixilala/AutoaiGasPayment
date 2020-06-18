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
    private ItemClickListener mItemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

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
        holder.rlSearchDestinationResultParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null){
                    mItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlSearchDestinationResultParent;
        TextView tvItemSearchDestinationName;
        TextView tvItemSearchDestinationLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemSearchDestinationName = itemView.findViewById(R.id.tv_item_search_destination_name);
            tvItemSearchDestinationLocation = itemView.findViewById(R.id.tv_item_search_destination_location);
            rlSearchDestinationResultParent = itemView.findViewById(R.id.rl_search_destination_result_parent);
        }
    }

    public interface ItemClickListener{

        void onItemClick(int position);
    }
}
