package com.cxl.life.app.layout;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cxl.life.R;
import com.cxl.life.util.KeyboardUtil;
import com.cxl.life.util.L;
import com.cxl.life.util.ScreenUtil;
import com.cxl.life.widget.MyKeyboard;

/**
 * Created by cxl on 2017/8/29.
 * 自定义键盘
 * 两种方式，分别为edit2跟edit4，edit4方便，简单，但未找到切换到系统键盘的方式
 */

public class KeyboardFragment extends Fragment {
    private EditText edit1, edit2;
    private int inputBack2;
    private KeyboardUtil keyboardUtil;
    private LinearLayout whole;//最外层布局
    private View mContentView;//窗口布局
    private MyKeyboard edit4;

    public KeyboardFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_keyboard, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edit1 = (EditText) view.findViewById(R.id.edit_one);
        edit2 = (EditText) view.findViewById(R.id.edit_two);
        inputBack2 = edit2.getInputType();
        edit2.setInputType(InputType.TYPE_NULL);
        edit2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    edit2.setInputType(InputType.TYPE_NULL);
                    if (keyboardUtil == null) {
                        keyboardUtil = new KeyboardUtil(getActivity(), getActivity(), edit2);
                    }
                    keyboardUtil.showKeyboard();   //显示键盘
                    edit2.setInputType(inputBack2);//设置显示光标.
                    edit2.setSelection(edit2.getText().length());    //设置光标定位到内容末尾.
                    edit2.requestFocus();
                }
                return true;
            }
        });
        edit2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
//                    keyboardUtil.hideKeyboard();//失去焦点时隐藏键盘
                }
            }
        });

        whole = (LinearLayout) view.findViewById(R.id.keyboard_ll);
        whole.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (keyboardUtil != null) {
                    keyboardUtil.hideKeyboard();//全局点击任意地方隐藏键盘
//                    whole.requestFocus();
                }
                return false;
            }
        });
        edit4 = (MyKeyboard) view.findViewById(R.id.edit_four);
        Window mWindow = ((Activity) getContext()).getWindow();
        this.mContentView = mWindow.findViewById(Window.ID_ANDROID_CONTENT);
    }

    //计算移动距离，存在问题：键盘跟布局整体移动
    private void calculateHeight() {
        int[] location = new int[2];
        edit2.getLocationInWindow(location);
        int h1 = location[1];
        int h = ScreenUtil.getScreenHeight(getActivity());
        int h2 = ScreenUtil.dpTopx(getActivity(), 215);
        L.e("编辑框高度 " + h1 + "  屏幕高 " + h + "  键盘高 " + h2);
        if (h - h1 < h2) {
            L.e("需要移动");
            mContentView.scrollBy(0, h2 - h + h1);
        }
    }

}
