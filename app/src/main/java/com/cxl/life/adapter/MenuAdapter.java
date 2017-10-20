package com.cxl.life.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxl.life.R;
import com.cxl.life.bean.function.MenuData;
import com.cxl.life.util.ScreenUtil;

import java.util.List;

/**
 * Created by chengxl on 2016/12/22.
 * 模块布局的适配器
 */

public class MenuAdapter extends BaseAdapter {
    private List<MenuData> list;
    private Context context;

    public MenuAdapter(Context context, List<MenuData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_function_menu, null);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView
                    .findViewById(R.id.item_menu_iv);
            viewHolder.title = (TextView) convertView
                    .findViewById(R.id.item_menu_title);
            viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.item_menu_ll);
            viewHolder.layout.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, (ScreenUtil.getScreenWidth(context) - ScreenUtil.dpTopx(context, 4)) / 3));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.image.setImageResource(list.get(position).getIcon());
        return convertView;
    }

    class ViewHolder {
        TextView title;
        ImageView image;
        LinearLayout layout;
    }
}
