package com.cxl.life.app.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cxl.life.R;
import com.cxl.life.util.ScreenUtil;
import com.cxl.life.util.ToastUtil;
import com.cxl.life.widget.FlowLayout;

/**
 * Created by cxl on 2017/9/8.
 * 流布局（适合搜索的关键字显示）
 */

public class FlowLayoutFragment extends Fragment implements View.OnClickListener {

    private FlowLayout flowLayout;
    String[] texts = new String[]{
            "good", "bad", "understand", "it is a good day !",
            "how are you", "ok", "fine", "name", "momo",
            "lankton", "lan", "flowlayout demo", "soso"
    };
    int length;//texts的长度

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flow_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        length = texts.length;
        flowLayout = (FlowLayout) view.findViewById(R.id.flow_layout);
        view.findViewById(R.id.flow_layout_add).setOnClickListener(this);
        view.findViewById(R.id.flow_layout_remove).setOnClickListener(this);
        view.findViewById(R.id.flow_layout_compress).setOnClickListener(this);
        view.findViewById(R.id.flow_layout_align).setOnClickListener(this);
        view.findViewById(R.id.flow_layout_line).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flow_layout_add:
                addViewToFlow();
                break;
            case R.id.flow_layout_remove:
                flowLayout.removeAllViews();
                break;
            case R.id.flow_layout_compress:
                flowLayout.relayoutToCompress();
                break;
            case R.id.flow_layout_align:
                flowLayout.relayoutToAlign();
                break;
            case R.id.flow_layout_line:
                flowLayout.specifyLines(3);
                break;
        }
    }

    //添加布局到流里面
    private void addViewToFlow() {
        int ranHeight = ScreenUtil.dpTopx(getActivity(), 30);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight);
        lp.setMargins(ScreenUtil.dpTopx(getActivity(), 8), ScreenUtil.dpTopx(getActivity(), 5), ScreenUtil.dpTopx(getActivity(), 8), ScreenUtil.dpTopx(getActivity(), 5));
        TextView tv = new TextView(getActivity());
        tv.setPadding(ScreenUtil.dpTopx(getActivity(), 15), 0, ScreenUtil.dpTopx(getActivity(), 15), 0);
        tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.body_text));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        int index = (int) (Math.random() * length);
        tv.setText(texts[index]);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setLines(1);
        tv.setBackgroundResource(R.drawable.bg_flow_tag);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(((TextView) v).getText().toString());
            }
        });
        flowLayout.addView(tv, lp);
    }
}
