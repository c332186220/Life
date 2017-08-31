package com.cxl.life.login;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cxl.life.R;
import com.cxl.life.app.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private AutoCompleteTextView mAccount;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }

        mAccount = (AutoCompleteTextView) findViewById(R.id.login_account);
        addAccountToAutoComplete();
        mPassword = (EditText) findViewById(R.id.login_password);
        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button signIn = (Button) findViewById(R.id.action_sign_in_button);
        signIn.setOnClickListener(this);
        RelativeLayout outLayout = (RelativeLayout) findViewById(R.id.activity_login);
        autoScrollView(outLayout, signIn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_sign_in_button:
                attemptLogin();
                break;
        }
    }

    //自动完成设置
    private void addAccountToAutoComplete() {
        //优化  放置本地数据库，并加入王者荣耀数据
        List<String> accountList = new ArrayList<>();
        accountList.add("luban7hao");
        accountList.add("zhugeliang");
        accountList.add("ake");
        accountList.add("direnjie");
        accountList.add("zhaoyun");
        //simple_list_item_1很合规  simple_spinner_item间距小
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_list_item_1, accountList);
        mAccount.setAdapter(adapter);
    }

    //尝试登录
    private void attemptLogin() {
        hideSoftKeyboard();
        String account = mAccount.getText().toString();
        String password = mPassword.getText().toString();
        //判断后执行登录
        if (TextUtils.isEmpty(account)) {
            showDialog("用户名不能为空，请输入用户名", 1);
        } else if (TextUtils.isEmpty(password)) {
            showDialog("密码不能为空，请输入密码", 2);
        } else {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            isLogin = true;
            finish();
        }
    }

    //提示框
    private void showDialog(String content, final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录失败")
                .setMessage(content)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        if (type == 1) {
//                            mAccount.requestFocus();
                            showInput(LoginActivity.this, mAccount);
                        } else if (type == 2) {
//                            mPassword.requestFocus();
                            showInput(LoginActivity.this, mPassword);
                        }
                    }
                })
                .show();
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            View view = getWindow().peekDecorView();
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    /**
     * 打开键盘
     **/
    protected void showInput(final Context context, final View view) {
        //延迟，为了能弹出键盘
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    view.requestFocus();
                    imm.showSoftInput(view, 0);
                }
            }
        }, 20);
    }

    /**
     * 软键盘遮挡点击按钮处理
     *
     * @param root         最外层的View
     * @param scrollToView 不想被遮挡的View,会移动到这个Veiw的可见位置
     */
    private void autoScrollView(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                //获取root在窗体的不可视区域高度(被遮挡的高度)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                //若不可视区域高度大于150，则键盘显示
                if (rootInvisibleHeight > 150) {
                    int[] location = new int[2];
                    //获取scrollToView在窗体的坐标
                    scrollToView.getLocationInWindow(location);
                    //计算root滚动高度，使scrollToView在可见区域的底部
                    int srollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
                    root.scrollTo(0, srollHeight);
                } else {
                    //键盘隐藏
                    root.scrollTo(0, 0);
                }
            }
        });
    }
}
