package com.cxl.life.app.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cxl.life.R;
import com.cxl.life.bean.User;
import com.cxl.life.util.L;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cxl on 2017/9/11.
 * 编辑资料
 */

public class EditDataFragment extends Fragment implements View.OnClickListener {
    private TextView textView;
    private EditText company;
    private TextView toJson;
    private LinearLayout jsonTo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_data, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = (TextView) view.findViewById(R.id.edit_text);
        company = (EditText) view.findViewById(R.id.edit_data_company);
        toJson = (TextView) view.findViewById(R.id.edit_data_to_json);
        toJson.setOnClickListener(this);
        jsonTo = (LinearLayout) view.findViewById(R.id.edit_data_json_to);
        jsonTo.setOnClickListener(this);
        initData();
    }

    private void initData() {
        User user = new User();
        user.setAddress("北京市/");
        user.setBirthday("1990-05-06");
        user.setHeadPortrait("http://baidu.com");
        user.setName("[");
        user.setSchool("bb]");
        user.setCompany(company.getText().toString());
        String result = JSON.toJSONString(user);
        L.e(result);
        textView.setText(result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_data_to_json:
                initData();
                break;
            case R.id.edit_data_json_to:
                try {
                    JSONObject json = new JSONObject(textView.getText().toString());
                    textView.setText("company:"+json.getString("company")+"\nschool:"+json.getString("school")+"\nname:"+json.getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
