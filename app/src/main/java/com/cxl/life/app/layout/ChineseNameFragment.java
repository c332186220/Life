package com.cxl.life.app.layout;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.cxl.life.R;
import com.cxl.life.util.AUtil;
import com.cxl.life.util.Constants;
import com.cxl.life.util.L;
import com.cxl.life.widget.FlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxl on 2017/8/30.
 * 中文名单个不跨行问题，以及点击事件
 */

public class ChineseNameFragment extends Fragment {
    private TextView name1, name2;
    private FlowLayout name3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chinese_name, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name1 = (TextView) view.findViewById(R.id.chinese_name1);
        name2 = (TextView) view.findViewById(R.id.chinese_name2);
        name3 = (FlowLayout) getView().findViewById(R.id.chinese_name3);

        initChineseName();
//        addChineseName();
    }

    /**
     * 汉字自动换行
     */
    private void initChineseName() {
        String name = "王俊凯、易烊千玺、王源、鹿晗、潘玮柏、汪苏泷、王一博、霍尊、古巨基、杨宗纬、林俊杰、周杰伦、邓紫棋、陈奕迅、张靓颖、李荣浩、凤凰传奇、梁静茹、蔡依林、萧亚轩、刘德华、陈瑞";
        name1.setText(name);
        name2.setText(name1.getText().toString());
        //获取高宽的监听，但这个会一直重复调用，故用完之后去除监听
        name2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                name2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                autoFormatText(name2);
            }
        });

        name1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                name1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                autoFillText();
            }
        });

    }


    //填充屏幕
    private void autoFillText() {
        String text = name1.getText().toString();//获取文本
        Paint tvPaint = name1.getPaint();//获取画笔信息,包括字体大小等
        float tvWidth = name1.getWidth() - name1.getPaddingLeft() - name1.getPaddingRight(); //控件可用宽度
        //将原始文本按行拆分
        String[] rawTextLines = text.replaceAll("\r", "").split("\n");
        StringBuilder sbNewText = new StringBuilder();
        for (String rawTextLine : rawTextLines) {
            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                //如果整行宽度在控件可用宽度之内，就不处理了
                sbNewText.append(rawTextLine);
            } else {
                //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
                float lineWidth = 0;
                for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                    char ch = rawTextLine.charAt(cnt);
                    lineWidth += tvPaint.measureText(String.valueOf(ch));
                    if (lineWidth <= tvWidth) {
                        sbNewText.append(ch);
                    } else {
                        sbNewText.append("\n");
                        lineWidth = 0;
                        --cnt;
                    }
                }
            }
            sbNewText.append("\n");
        }
        //把结尾多余的\n去掉
        if (!text.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length() - 1);
        }
        name1.setText(sbNewText.toString());
    }

    /**
     * 根据汉字名称自动换行
     */
    private void autoFormatText(TextView textView) {
        String text = textView.getText().toString();//获取文本(联想展示会获取到自动换行里面的回车)
        Paint tvPaint = textView.getPaint();//获取画笔信息,包括字体大小等
        float tvWidth = textView.getWidth() - textView.getPaddingLeft() - textView.getPaddingRight(); //控件可用宽度
        //将原始文本按行拆分
        String[] rawTextNames = text.replaceAll("\r", "").split("、");
        StringBuilder sbNewText = new StringBuilder();
        float lineWidth = 0;//一条长度
        for (int i = 0; i < rawTextNames.length; i++) {
            String rawTextName;
            if (i == rawTextNames.length - 1) {
                rawTextName = rawTextNames[i];
            } else {
                rawTextName = rawTextNames[i] + "、";
            }
            //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
            lineWidth += tvPaint.measureText(rawTextName);
            if (lineWidth <= tvWidth) {
                sbNewText.append(rawTextName);
            } else {
                sbNewText.append("\n");
                lineWidth = 0;
                --i;
            }
        }
        //高亮化某个人
//        String s1 = sbNewText.toString();
//        String s2 = "周杰伦";
//        if (s1.contains(s2)) {
//            int index = s1.indexOf(s2);
//            SpannableString spannableString = new SpannableString(s1);
//            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#303F9F")), index, index + s2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            textView.setText(spannableString);
//        } else {
//            textView.setText(s1);
//        }

        if(!AUtil.isSupportCall(getActivity())){
            L.e("该设备不支持打电话");
            return;
        }
        if(AUtil.isPad(getActivity())){
            L.e("该设备是平板");
            return;
        }

        String s1 = sbNewText.toString();
        String[] s2 = s1.split("、");
        int start = 0,end = 0;
        SpannableString spannableString = new SpannableString(s1);
        for(int i=0;i<s2.length;i++){
            end+=s2[i].length();
            if(s2[i].startsWith("\n")){
                start++;
            }
//          spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#303F9F")), start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //高亮个别人员
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    TextView tv = (TextView) widget;
                    String s = tv
                            .getText()
                            .subSequence(tv.getSelectionStart(),
                                    tv.getSelectionEnd()).toString();
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    AUtil.call(getActivity(),"10010");
                }

                //控制文字变色
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    // 文字不变色 可以去掉类似超链接的下划线
                    ds.setUnderlineText(false);
                }
            }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            end++;
            start=end;
        }
        // 设置可以点击
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        //设置可点击的文字颜色
        textView.setLinkTextColor(getResources().getColor(R.color.colorPrimary));
        // 设置文字点击后的背景色
        textView.setHighlightColor(getResources().getColor(R.color.colorAccent));
        textView.setText(spannableString);
    }

    //流布局添加的形式
    private void addChineseName(){
        final List<String> nameList = new ArrayList<>();
        nameList.add("郭德纲");
        nameList.add("岳云鹏");
        nameList.add("于谦");
        nameList.add("孙悦");
//        报错java.lang.ClassCastException: android.view.ViewGroup$LayoutParams cannot be cast to android.view.ViewGroup$MarginLayoutParams
//        at com.cxl.life.widget.FlowLayout.onMeasure(FlowLayout.java:48)

//        for(int i=0;i<nameList.size();i++) {
//            TextView tv = new TextView(getActivity());
//            tv.setText(nameList.get(i)+"、");
//            tv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getActivity(),((TextView)v).getText().toString(),Toast.LENGTH_SHORT).show();
//                }
//            });
//            name3.addView(tv);
//        }
    }
}
