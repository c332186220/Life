package com.cxl.life.app.layout;

import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cxl.life.R;
import com.cxl.life.widget.fillblank.ReplaceSpan;
import com.cxl.life.widget.fillblank.SpansManager;

/**
 * Created by cxl on 2017/11/14.
 * 填空题的实现方式
 * TODO 代码放在activity里面可正常运行，fragment有点错。
 */
public class FillBlankFragment extends Fragment implements ReplaceSpan.OnClickListener {
    private String mTestStr = "我是个____学生,我有一个梦想，我要成为像____一样的人.";
    private TextView mTvContent;
    private EditText mEtInput;
    private SpansManager mSpansManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fill_blank, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTvContent = (TextView) view.findViewById(R.id.tv_content);
        mEtInput = (EditText) view.findViewById(R.id.et_input);
        mSpansManager = new SpansManager(getActivity(),mTvContent,mEtInput);
        mSpansManager.doFillBlank(mTestStr);
    }

    @Override
    public void OnClick(TextView v, int id, ReplaceSpan span) {
        mSpansManager.setData(mEtInput.getText().toString(),null, mSpansManager.mOldSpan);
        mSpansManager.mOldSpan = id;
        //如果当前span身上有值，先赋值给et身上
        mEtInput.setText(TextUtils.isEmpty(span.mText)?"":span.mText);
        mEtInput.setSelection(span.mText.length());
        span.mText = "";
        //通过rf计算出et当前应该显示的位置
        RectF rf = mSpansManager.drawSpanRect(span);
        //设置EditText填空题中的相对位置
        mSpansManager.setEtXY(rf);
        mSpansManager.setSpanChecked(id);
    }
}
