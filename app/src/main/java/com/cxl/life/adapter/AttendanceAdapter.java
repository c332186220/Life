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
import com.cxl.life.bean.Attendance;

import java.util.List;

/**
 * Created by cxl on 2017/09/12.
 * 考勤适配器
 */

public class AttendanceAdapter extends BaseAdapter {
    private List<Attendance> list;
    private Context context;

    public AttendanceAdapter(Context context, List<Attendance> list) {
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
                    R.layout.item_attendance, null);
            viewHolder = new ViewHolder();
            viewHolder.number = (TextView) convertView
                    .findViewById(R.id.item_attendance_number);
            viewHolder.name = (TextView) convertView
                    .findViewById(R.id.item_attendance_name);
            viewHolder.dateTime = (TextView) convertView
                    .findViewById(R.id.item_attendance_date_time);
            viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.item_attendance);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Attendance info = list.get(position);
        viewHolder.number.setText(info.getNumber());
        viewHolder.name.setText(info.getName());
        viewHolder.dateTime.setText(info.getDateTime());
        if (info.getState() == 1) {
            viewHolder.layout.setBackgroundResource(R.color.item_level1);
        } else if (info.getState() == 2) {
            viewHolder.layout.setBackgroundResource(R.color.item_level2);
        } else {
            viewHolder.layout.setBackgroundResource(R.color.transparent);
        }
        return convertView;
    }

    class ViewHolder {
        TextView number, name, dateTime;
        LinearLayout layout;
    }
}
