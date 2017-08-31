package com.cxl.life.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cxl.life.R;
import com.cxl.life.app.face.MyItemClickListener;
import com.cxl.life.bean.KingGlory;

import java.util.List;
import java.util.Random;

/**
 * Created by cxl on 2017/6/22.
 * 王者荣耀显示适配
 */

public class LayoutItemAdapter extends RecyclerView.Adapter {
    private List<String> list;
    private Context context;
    private Random random = new Random(255);
    private int height = 50;

    private MyItemClickListener onItemClickListener;

    public void setMyItemClickListener(MyItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public LayoutItemAdapter(Context context, List<String> list, int height) {
        this.context = context;
        this.list = list;
        this.height = height;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grid_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myHolder = (MyViewHolder) holder;
        String name = list.get(position);
        myHolder.title.setText(TextUtils.isEmpty(name) ? "匿名" : name);
        myHolder.title.setBackgroundColor(Color.rgb(random.nextInt() + 1,
                random.nextInt(), random.nextInt()));
        myHolder.title.setLayoutParams(new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, height));
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

        TextView title;

        private MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.layout_item_title);
        }
    }
}
