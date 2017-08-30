package com.cxl.life.app.about;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cxl.life.R;
import com.cxl.life.util.AUtil;

public class AboutUsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView vName;//版本名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        initView();
    }

    private void initView() {
        //各个历史版本功能介绍
        findViewById(R.id.about_version_function).setOnClickListener(this);
        //检查版本更新
        findViewById(R.id.about_version_update).setOnClickListener(this);

        vName = (TextView) findViewById(R.id.about_version);
        vName.setText(getResources().getString(R.string.app_name)+"  "+AUtil.getVersionName(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_version_function:

                break;
            case R.id.about_version_update:

                break;
        }
    }
}
