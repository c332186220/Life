package com.cxl.life.app.layout;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cxl.life.R;

/**
 * Created by cxl on 2017/8/31.
 * 富文本实现
 */

public class RichTextFragment extends Fragment {
    private TextView textView;
    private String text = "前景色背景色相对大小删除线下划线上标小上标下标粗体斜体显示图片点击超链接";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rich_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = (TextView) view.findViewById(R.id.rich_text);
        initRichText();
    }

    private void initRichText() {
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.parseColor("#AC00FF30"));
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(2f);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        UnderlineSpan underlineSpan = new UnderlineSpan();
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        RelativeSizeSpan relativeSizeSpan2 = new RelativeSizeSpan(0.5f);
        SubscriptSpan subscriptSpan = new SubscriptSpan();
        StyleSpan styleSpan_B = new StyleSpan(Typeface.BOLD);
        StyleSpan styleSpan_I = new StyleSpan(Typeface.ITALIC);

        ImageSpan imageSpan = new ImageSpan(getActivity(), R.drawable.disperse_link);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //获取点击文本的内容，即整个文本中被选中的部分
                TextView tv = (TextView) widget;
                String s = tv
                        .getText()
                        .subSequence(tv.getSelectionStart(),
                                tv.getSelectionEnd()).toString();
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                // 文字不变色
                ds.setUnderlineText(false);
//                ds.setColor(getResources().getColor(R.color.colorAccent));// 设置字体颜色
            }
        };
        URLSpan urlSpan = new URLSpan("http://www.baidu.com");

        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(foregroundColorSpan, 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(backgroundColorSpan, 3, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(relativeSizeSpan, 6, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(strikethroughSpan, 10, 13, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(underlineSpan, 13, 16, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(superscriptSpan, 16, 21, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(relativeSizeSpan2, 18, 21, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(subscriptSpan, 21, 23, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan_B, 23, 25, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan_I, 25, 27, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(imageSpan, 29, 31, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan, 31, 33, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(urlSpan, 33, 36, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        // 可以点击
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        //设置可点击的文字颜色
//        textView.setLinkTextColor(getResources().getColor(R.color.colorPrimary));
        // 点击背景色
        // spanString.setHighlightColor(Color.parseColor("#FF4081"));
        textView.setText(spannableString);
    }
}
