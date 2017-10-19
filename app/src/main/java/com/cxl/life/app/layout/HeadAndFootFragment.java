package com.cxl.life.app.layout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cxl.life.R;
import com.cxl.life.adapter.HeadFootAdapter;
import com.cxl.life.bean.FormData;
import com.cxl.life.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxl on 2017/9/30.
 * 给
 */

public class HeadAndFootFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private HeadFootAdapter adapter;
    private List<FormData> data;
    private TextView name, time;//名字，时间
    private TextView remark, cancel, save, submit;//备注

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_head_foot, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.head_foot_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initData();
        adapter = new HeadFootAdapter(data, getActivity());
        recyclerView.setAdapter(adapter);

        View viewHeader = LayoutInflater.from(getActivity()).inflate(R.layout.item_header_layout, null);
        name = (TextView) viewHeader.findViewById(R.id.form_name);
        time = (TextView) viewHeader.findViewById(R.id.form_time);
        adapter.addHeaderView(viewHeader);
        View viewFooter = LayoutInflater.from(getActivity()).inflate(R.layout.item_footer_layout, null);
        remark = (TextView) viewFooter.findViewById(R.id.form_remark);
        cancel = (TextView) viewFooter.findViewById(R.id.form_cancel);
        save = (TextView) viewFooter.findViewById(R.id.form_save);
        submit = (TextView) viewFooter.findViewById(R.id.form_submit);
        cancel.setOnClickListener(this);
        save.setOnClickListener(this);
        submit.setOnClickListener(this);
        adapter.addFooterView(viewFooter);
        setData();

    }

    private void setData() {
        time.setText(TimeUtil.getCurrentTime(4));
        name.setText("系统管理员");
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 1; i < 51; i++) {
            FormData fd = new FormData();
            fd.setLOOP_NAME("数据" + i);
            fd.setLIGHT_GRADE("请选择");
            fd.setPART("3");
            fd.setSORT(i - 1);
            data.add(fd);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.form_cancel:
                getActivity().finish();
                break;
            case R.id.form_save:
                save();
                break;
            case R.id.form_submit:
                save();
                break;
        }
    }

    private void save() {
        StringBuilder sData = new StringBuilder();
        sData.append("值班人：" + name.getText().toString());
        sData.append("\n巡检时间：" + time.getText().toString());
        for (int i = 0; i < data.size(); i++) {
            FormData fd = data.get(i);
            sData.append("\n" + fd.getLOOP_NAME() + " 电压：" + fd.getFIRST_VALUE() + " 电流：" + fd.getSECOND_VALUE() + " 光级：" + fd.getLIGHT_GRADE());
        }
        sData.append("\n备注：" + remark.getText().toString());
        showDialog("数据展示", sData.toString());
    }

    //弹出框展示信息
    protected void showDialog(String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(content)
                .setPositiveButton("确定", null)
                .show();
    }
}
