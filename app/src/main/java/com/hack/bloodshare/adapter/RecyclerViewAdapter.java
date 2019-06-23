package com.hack.bloodshare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hack.bloodshare.model.Receivers;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hack.bloodshare.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Receivers> mData;
    RecyclerView mRecyclerView;
    public static ViewHolder.ClickListener clickListener;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public RecyclerViewAdapter(Context mContext, List<Receivers> mData) {
        this.mContext = mContext;
        this.mData = mData;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.receiver_row_item, parent, false);
        view.setOnClickListener(mOnClickListener);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.receiverName.setText(mData.get(position).getName());
        holder.receiverGroup.setText(mData.get(position).getGroup());
        holder.location.setText(mData.get(position).getLocation());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView receiverName;
        TextView receiverGroup;
        TextView location;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverName = itemView.findViewById(R.id.receiverName);
            receiverGroup = itemView.findViewById(R.id.receiverGroup);
            location = itemView.findViewById(R.id.location);

        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
        public void setOnItemClickListener(View.OnClickListener clickListener) {
            //RecyclerViewAdapter.clickListener = clickListener;
        }
        public interface ClickListener {
            void onItemClick(int position, View v);
            //void onItemLongClick(int position, View v);
        }
    }
}
