package com.cxl.life.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cxl.life.R;
import com.cxl.life.bean.KingGlory;

import java.util.List;

/**
 * Created by cxl on 2017/6/22.
 * 王者荣耀显示适配
 */

public class KingRecyclerAdapter extends RecyclerView.Adapter {
    private List<KingGlory> list;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public KingRecyclerAdapter(Context context, List<KingGlory> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.king_recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myHolder = (MyViewHolder) holder;
        String name = list.get(position).getName();
        myHolder.title.setText(TextUtils.isEmpty(name) ? "" : name);
        Glide.with(context).load(list.get(position).getImage()).into(myHolder.image);
        if (onItemClickListener != null) {
            myHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;

        private MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.king_recycler_item_image);
            title = (TextView) itemView.findViewById(R.id.king_recycler_item_title);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
