package com.cxl.life.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cxl.life.R;
import com.cxl.life.bean.FormData;
import com.cxl.life.dialog.FilterPopWindow;
import com.cxl.life.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxl on 2017/9/30.
 * 用于处理表单填报
 */

public class HeadFootAdapter extends RecyclerView.Adapter<HeadFootAdapter.MyHolder> {

    private RecyclerView mRecyclerView;

    private List<FormData> data;
    private Context mContext;

    private View VIEW_FOOTER;
    private View VIEW_HEADER;

    //Type 区分不同布局
    private int TYPE_NORMAL = 1000;
    private int TYPE_HEADER = 1001;
    private int TYPE_FOOTER = 1002;

    private FilterPopWindow window;
    List<String> listLevel = new ArrayList<>();

    public HeadFootAdapter(List<FormData> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public HeadFootAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new MyHolder(VIEW_FOOTER);
        } else if (viewType == TYPE_HEADER) {
            return new MyHolder(VIEW_HEADER);
        } else {
//            return new MyHolder(getLayout(R.layout.item_head_foot));
            return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_head_foot, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        if (!isHeaderView(position) && !isFooterView(position)) {
            if (haveHeaderView()) position--;
            TextView content = (TextView) holder.itemView.findViewById(R.id.item_form_title);
            TextView item3 = (TextView) holder.itemView.findViewById(R.id.item_form_tv3);
            EditText item1 = (EditText) holder.itemView.findViewById(R.id.item_form_edit1);
            EditText item2 = (EditText) holder.itemView.findViewById(R.id.item_form_edit2);
            FormData fd = data.get(position);
            content.setText(fd.getLOOP_NAME());
            item3.setText(fd.getLIGHT_GRADE());
//            item1.setText(TextUtils.isEmpty(fd.getFIRST_VALUE()) ? "" : fd.getFIRST_VALUE());
//            item2.setText(TextUtils.isEmpty(fd.getSECOND_VALUE()) ? "" : fd.getSECOND_VALUE());
            item1.setText(fd.getFIRST_VALUE());
            item2.setText(fd.getSECOND_VALUE());
            final int finalPosition = position;
            item3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupWindow(v, finalPosition);
                }
            });

            L.e("bianlitie:   "+finalPosition);

//            item1.setTag(finalPosition);
//            item1.addTextChangedListener(new TextWatcher() {
//
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                }
//
//                public void afterTextChanged(Editable s) {
//                    int fd = (int) item1.getTag();
//                    data.get(fd).setFIRST_VALUE(s.toString());
//                    L.e("bianli:   "+fd+" value   "+s.toString());
//                }
//            });
            item1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        String value =((EditText) v).getText().toString();
                        data.get(finalPosition).setFIRST_VALUE(value);
                        L.e("bianli:   "+finalPosition+" value   "+value);
                        //失去焦点查库，判断是否超过上限或者低于下限
                    }
                }
            });
            item2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        String value =((EditText) v).getText().toString();
                        data.get(finalPosition).setSECOND_VALUE(value);
                        //失去焦点查库，判断是否超过上限或者低于下限
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int count = (data == null ? 0 : data.size());
        if (VIEW_FOOTER != null) {
            count++;
        }

        if (VIEW_HEADER != null) {
            count++;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return TYPE_HEADER;
        } else if (isFooterView(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        try {
            if(mRecyclerView==null){
                mRecyclerView = recyclerView;
            }
            if (mRecyclerView != null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView;
            }
//            ifGridLayoutManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View getLayout(int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }

    public void addHeaderView(View headerView) {
        if (haveHeaderView()) {
            throw new IllegalStateException("hearview has already exists!");
        } else {
            //避免出现宽度自适应
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(params);
            VIEW_HEADER = headerView;
//            ifGridLayoutManager();
            notifyItemInserted(0);
        }

    }

    public void addFooterView(View footerView) {
        if (haveFooterView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            VIEW_FOOTER = footerView;
//            ifGridLayoutManager();
            notifyItemInserted(getItemCount() - 1);
        }
    }

    private void ifGridLayoutManager() {
        if (mRecyclerView == null) return;
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager.SpanSizeLookup originalSpanSizeLookup =
                    ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderView(position) || isFooterView(position)) ?
                            ((GridLayoutManager) layoutManager).getSpanCount() :
                            1;
                }
            });
        }
    }

    private boolean haveHeaderView() {
        return VIEW_HEADER != null;
    }

    public boolean haveFooterView() {
        return VIEW_FOOTER != null;
    }

    private boolean isHeaderView(int position) {
        return haveHeaderView() && position == 0;
    }

    private boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }


    public static class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(View itemView) {
            super(itemView);
        }
    }

    void showPopupWindow(View view, final int location) {
        if (window == null) {
            listLevel.clear();
            listLevel.add("请选择");
            listLevel.add("1");
            listLevel.add("2");
            listLevel.add("3");
            listLevel.add("4");
            listLevel.add("5");
            window = new FilterPopWindow(mContext, listLevel, view.getWidth());
        }
        window.setOnItemPopupWindowListener(new FilterPopWindow.PopupWindowOnItemClickListener() {
            @Override
            public void getMessage(int position) {
                changeLightLevel(listLevel.get(position), location);
                window.dismiss();
            }
        });
        window.showPopupWindow(view);
    }

    /**
     * 改变光级的数据
     */
    void changeLightLevel(String level, int location) {
        data.get(location).setLIGHT_GRADE(level);
        data.get(location).setSECOND_VALUE(getAmmeterWithLevel(level));
        notifyItemChanged(location + 1);
    }

    /**
     * 根据光级获取默认电流
     */
    private String getAmmeterWithLevel(String level) {
        String ammeter;
        switch (level) {
            case "1":
                ammeter = "2.8";
                break;
            case "2":
                ammeter = "3.4";
                break;
            case "3":
                ammeter = "4.1";
                break;
            case "4":
                ammeter = "5.2";
                break;
            case "5":
                ammeter = "6.6";
                break;
            default:
                ammeter = "";
                break;
        }
        return ammeter;
    }
}
