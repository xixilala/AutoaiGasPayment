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

public class SmartAddGasStationListAdapter extends RecyclerView.Adapter<SmartAddGasStationListAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mDates;
    private ItemClickListener mItemClickListener;

    public SmartAddGasStationListAdapter(Context context, ItemClickListener itemClickListener) {
        mContext = context;
        mItemClickListener = itemClickListener;
    }

    public void setDates(List<String> dates) {
        mDates = dates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_smart_add_gas_station, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rlSmartListParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDates == null ? 0 : mDates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout rlSmartListParent;
        TextView tvSmartListName;
        TextView tvSmartListLocation;
        TextView tvSmartListPrice;
        TextView tvSmartListBasePrice;
        TextView tvSmartListBranch;
        TextView tvSmartListTicketStyle;
        TextView tvSmartListDistance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlSmartListParent = itemView.findViewById(R.id.rl_smart_list_parent);
            tvSmartListName = itemView.findViewById(R.id.tv_smart_list_name);
            tvSmartListLocation = itemView.findViewById(R.id.tv_smart_list_location);
            tvSmartListPrice = itemView.findViewById(R.id.tv_smart_list_price);
            tvSmartListBasePrice = itemView.findViewById(R.id.tv_smart_list_base_price);
            tvSmartListBranch = itemView.findViewById(R.id.tv_smart_list_branch);
            tvSmartListTicketStyle = itemView.findViewById(R.id.tv_smart_list_ticket_style);
            tvSmartListDistance = itemView.findViewById(R.id.tv_smart_list_distance);
        }
    }
    public interface ItemClickListener{
        void onItemClick(View view, int Position);
    }
}
