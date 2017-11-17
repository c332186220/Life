package com.cxl.life.app.function;

import android.graphics.RectF;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cxl.life.R;
import com.cxl.life.app.BaseTitleActivity;
import com.cxl.life.util.ToastUtil;
import com.cxl.life.widget.fillblank.ReplaceSpan;
import com.cxl.life.widget.fillblank.SpansManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 填空题，用于实现textview夹杂edittext的功能。
 */
public class FillBlankActivity extends BaseTitleActivity implements ReplaceSpan.OnClickListener, View.OnClickListener {
    private String mTestStr = "我是个____学生,我有一个梦想，我要成为像____一样的人.";
    private TextView mTvContent;
    private EditText mEtInput;
    private SpansManager mSpansManager;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_fill_blank);
    }

    @Override
    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, getResources().getString(R.string.activity_fill_blank), true);

        mTvContent = (TextView) findViewById(R.id.tv_content);
        mEtInput = (EditText) findViewById(R.id.et_input);
        mSpansManager = new SpansManager(this, mTvContent, mEtInput);
        mSpansManager.doFillBlank(mTestStr);

        findViewById(R.id.btn_et1_get).setOnClickListener(this);
        findViewById(R.id.btn_et2_get).setOnClickListener(this);
        setInitialAnswer();
    }

    @Override
    public void OnClick(TextView v, int id, ReplaceSpan span) {
        mSpansManager.setData(mEtInput.getText().toString(), null, mSpansManager.mOldSpan);
        mSpansManager.mOldSpan = id;
        //如果当前span身上有值，先赋值给et身上
        mEtInput.setText(TextUtils.isEmpty(span.mText) ? "" : span.mText);
        mEtInput.setSelection(span.mText.length());
        span.mText = "";
        //通过rf计算出et当前应该显示的位置
        RectF rf = mSpansManager.drawSpanRect(span);
        //设置EditText填空题中的相对位置
        mSpansManager.setEtXY(rf);
        mSpansManager.setSpanChecked(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_et1_get:
                String s1 = mSpansManager.getItemAnswer(0);
                ToastUtil.showToast(s1);
                break;
            case R.id.btn_et2_get:
                String s2 = mSpansManager.getItemAnswer(1);
                ToastUtil.showToast(s2);
                break;
        }
    }

    //获取所有答案
    private String getAll() {
        mSpansManager.setLastCheckedSpanText(mEtInput.getText().toString());
        return mSpansManager.getAllAnswer().toString();
    }

    private void setInitialAnswer() {
        List<String> list = new ArrayList<>();
        list.add("聪明");
        list.add("");
        list.add("");
        list.add("马云");
        mSpansManager.setInitial(list);
    }
}
