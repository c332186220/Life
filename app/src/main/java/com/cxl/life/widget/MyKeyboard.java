package com.cxl.life.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.cxl.life.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by cxl on 2017/8/29.
 * 自定义键盘输入框
 */

public class MyKeyboard extends EditText implements KeyboardView.OnKeyboardActionListener {
    public static int screenw = -1;//屏宽
    public static int screenh = -1;//屏高
    public static float density = 1.0f;//屏幕密度
    public static int densityDpi = 160;
    public static int screenh_nonavbar = -1;    //不包含导航栏的高度
    public static int real_scontenth = -1;    //实际内容高度，  计算公式:屏幕高度-导航栏高度-电量栏高度
    private int scrolldis = 0;    //输入框在键盘被弹出时，要被推上去的距离

    private PopupWindow mKeyboardWindow;
    private boolean needcustomkeyboard = true;    //是否启用自定义键盘

    private Keyboard mKeyboard;
    private KeyboardView mKeyboardView;

    private View mContentView;
    private View mDecorView;
    private Window mWindow;

    public MyKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initScreenParams(context);
        initAttributes();
        initKeyboard(context, attrs);
    }

    private void initAttributes() {
        this.setLongClickable(false);
        this.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);

        if (this.getText() != null) {
            this.setSelection(this.getText().length());//切换光标位置
        }
        this.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // 失去焦点时隐藏键盘
                if (!hasFocus) {
                    hideKeyboard();
                }
            }
        });
    }

    private void initKeyboard(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.keyboard);

        if (a.hasValue(R.styleable.keyboard_xml)) {
            needcustomkeyboard = true;
            int xmlid = a.getResourceId(R.styleable.keyboard_xml, 0);

            mKeyboard = new Keyboard(context, xmlid);
            mKeyboardView = (KeyboardView) LayoutInflater.from(context).inflate(R.layout.my_keyboard_view, null);
            //布局里以下两个属性可以获取焦点   android:focusable="true"
//            android:focusableInTouchMode="true"

            mKeyboardView.setKeyboard(mKeyboard);
            mKeyboardView.setEnabled(true);
            mKeyboardView.setPreviewEnabled(false);
            mKeyboardView.setOnKeyboardActionListener(this);

            mKeyboardWindow = new PopupWindow(mKeyboardView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mKeyboardWindow.setAnimationStyle(R.style.AnimationFade);
//			mKeyboardWindow.setBackgroundDrawable(new BitmapDrawable());
            mKeyboardWindow.setOutsideTouchable(true);//点击外部取消
            mKeyboardWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

                @Override
                public void onDismiss() {
                    // 键盘隐藏时收回上移距离
                    if (scrolldis > 0) {
                        if (null != mContentView) {
                            mContentView.scrollBy(0, -scrolldis);
                        }
                        scrolldis = 0;
                    }
                }
            });
        } else {
            needcustomkeyboard = false;
        }
        a.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        requestFocus();
        requestFocusFromTouch();
        if (needcustomkeyboard) {
            hideSysInput();
            showKeyboard();
        }
        return true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mWindow = ((Activity) getContext()).getWindow();
        this.mDecorView = this.mWindow.getDecorView();
        this.mContentView = this.mWindow.findViewById(Window.ID_ANDROID_CONTENT);
        hideSysInput();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        hideKeyboard();

        mKeyboardWindow = null;
        mKeyboardView = null;
        mKeyboard = null;

        mDecorView = null;
        mContentView = null;
        mWindow = null;
    }

    private void hideSysInput() {
        if (this.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void showKeyboard() {
        if (null != mKeyboardWindow) {
            if (!mKeyboardWindow.isShowing()) {
                mKeyboardView.setKeyboard(mKeyboard);

                mKeyboardWindow.showAtLocation(this.mDecorView, Gravity.BOTTOM, 0, 0);
                mKeyboardWindow.update();

                if (null != mDecorView && null != mContentView) {
                    int[] pos = new int[2];
                    getLocationOnScreen(pos);
                    float height = dpToPx(getContext(), 221);

                    Rect outRect = new Rect();
                    mDecorView.getWindowVisibleDisplayFrame(outRect);

                    int screen = real_scontenth;
                    scrolldis = (int) ((pos[1] + getMeasuredHeight() - outRect.top) - (screen - height));

                    if (scrolldis > 0) {
                        mContentView.scrollBy(0, scrolldis);
                    }
                }
            }
        }
    }

    public void hideKeyboard() {
        if (null != mKeyboardWindow) {
            if (mKeyboardWindow.isShowing()) {
                mKeyboardWindow.dismiss();
            }
        }
    }

    /**
     * 密度转换为像素值
     *
     * @param dp
     * @return
     */
    public static int dpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private void initScreenParams(Context context) {
        DisplayMetrics dMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dMetrics);

        screenw = dMetrics.widthPixels;
        screenh = dMetrics.heightPixels;
        density = dMetrics.density;
        densityDpi = dMetrics.densityDpi;

        screenh_nonavbar = screenh;

        int ver = Build.VERSION.SDK_INT;

        // 新版本的android 系统有导航栏，造成无法正确获取高度
        if (ver == 13) {
            try {
                Method mt = display.getClass().getMethod("getRealHeight");
                screenh_nonavbar = (Integer) mt.invoke(display);
            } catch (Exception e) {

            }
        } else if (ver > 13) {
            try {
                Method mt = display.getClass().getMethod("getRawHeight");
                screenh_nonavbar = (Integer) mt.invoke(display);
            } catch (Exception e) {

            }
        }
        real_scontenth = screenh_nonavbar - getStatusBarHeight(context);
    }

    /**
     * 电量栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        int x, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        Editable editable = this.getText();
        int start = this.getSelectionStart();
        if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
            hideKeyboard();
        } else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
            if (editable != null && editable.length() > 0) {
                if (start > 0) {
                    editable.delete(start - 1, start);
                }
            }
        } else if (primaryCode == 57419) { // 光标向左
            if (start > 0) {
                this.setSelection(start - 1);
            }
        } else if (primaryCode == 57421) { // 光标向右
            if (start < this.length()) {
                this.setSelection(start + 1);
            }
        } else {
            editable.insert(start, Character.toString((char) primaryCode));
        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
