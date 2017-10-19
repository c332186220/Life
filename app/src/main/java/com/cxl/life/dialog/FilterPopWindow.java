package com.cxl.life.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cxl.life.R;

import java.util.List;

/**
 * Created by cxl on 2017/9/30.
 * 显示文本信息选择的窗口
 */

public class FilterPopWindow extends PopupWindow {
    private View contentView;
    private ListView lv;
    private Context context;
    private PopupWindowOnItemClickListener listener;
    private List<String> list;
    private MyAdapter adapter;
    private int width;

    public FilterPopWindow(Context context, List<String> list, int width) {
        this.context = context;
        this.list = list;
        this.width = width;
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popup_form, null);
        this.setContentView(contentView);
        this.setWidth(width);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        ColorDrawable dw = new ColorDrawable(00000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(android.R.style.Animation_Dialog);

        lv = (ListView) contentView.findViewById(R.id.popup_form_list);
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(listener != null)
                    listener.getMessage(position);
            }
        });
    }

    public void showPopupWindow(View view) {
        if (!this.isShowing()) {
            this.showAsDropDown(view, 0, 0);
        } else {
            listener.getMessage(-1);
            this.dismiss();
        }
    }

    // 接口回调
    public interface PopupWindowOnItemClickListener {
        void getMessage(int position);
    }

    public void setOnItemPopupWindowListener(PopupWindowOnItemClickListener l) {
        this.listener = l;
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
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
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.item_select_view, null);
                viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView
                        .findViewById(R.id.item_select_text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.title.setText(list.get(position));
            return convertView;
        }
    }

    class ViewHolder {
        TextView title;
    }
}
