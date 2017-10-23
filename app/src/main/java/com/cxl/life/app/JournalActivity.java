package com.cxl.life.app;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cxl.life.R;
import com.cxl.life.util.AUtil;
import com.cxl.life.util.Constants;
import com.cxl.life.adapter.ImageViewPagerAdapter;
import com.cxl.life.app.pager.DepthPageTransformer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JournalActivity extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {
    private EditText txt;
    private TextView result;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        initView();
    }

    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.journal_toolbar);
        toolbar.setTitle("博能日志统计");
        setSupportActionBar(toolbar);

        FloatingActionButton analysis = (FloatingActionButton) findViewById(R.id.journal_fab_analysis);
        analysis.setOnClickListener(this);
        result = (TextView) findViewById(R.id.journal_analysis_result);
        txt = (EditText) findViewById(R.id.journal_txt);
        result.setOnLongClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.journal_viewpager);
        setViewPager();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.journal_fab_analysis:
                hideSoftKeyboard();
                getAnalysisResult();
                break;
            default:
                break;
        }
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

    private void getAnalysisResult() {
        String t = txt.getText().toString().trim();
        if (TextUtils.isEmpty(t)) {
            Toast.makeText(this, "文件名不能为空", Toast.LENGTH_SHORT).show();
        } else {
            String tt = getFileContent(Constants.journal_sd + t + ".txt");
            if (tt.startsWith("error")) {
                result.setText(tt.substring(5));
            } else {
                result.setText(analysisContent(tt));
            }
        }
    }

    /**
     * 解析内容
     */
    public String analysisContent(String s) {
        Map<String, Double> map = new HashMap<>();// 分析后的集合

        s = s.replaceAll("h", "小时").replaceAll("H", "小时");// 格式化
        while (s.indexOf("小时") != -1) {
            int index = s.indexOf("小时");// 索引
            String news = s.substring(0, index + 2);// 截取一小点分析
            String key = "其它";
            if (news.indexOf("ST1") != -1) {
                int indexST = news.indexOf("ST1");
                key = s.substring(indexST, indexST + 8);
            } else if (news.indexOf("S1") != -1) {
                int indexS = news.indexOf("S1");
                key = s.substring(indexS, indexS + 7);
            }
            String v = s.substring(0, index);
            double value = getLastNum(v);
            if (map.containsKey(key)) {
                map.put(key, map.get(key) + value);
            } else {
                map.put(key, value);
            }
            s = s.substring(index + 2);
        }
        StringBuilder result = new StringBuilder();
        for (String key : map.keySet()) {
//            result.append("项目：" + key + "  " + map.get(key) + "小时" + "\n");
            result.append(key + "  " + map.get(key) + "小时" + "\n");
        }
        return result.toString();
    }

    /**
     * 获取字符串最后面的数字内容
     */
    public double getLastNum(String a) {
        a = a.trim();
        double hour = 0;
        // 方法一
        // if (a.matches(".*\\d$")) {
        // a = a.replaceAll(".*[^\\d|.](?=(\\d+|.))", "");
        // }
        // hour = Double.valueOf(a);
        // 方法二
        Pattern p = Pattern.compile("\\d{0,}(\\d\\.)?\\d{1,}$");
        Matcher m = p.matcher(a);
        if (m.find()) {
            hour = Double.valueOf(m.group());
        }
        return hour;
    }

    /**
     * 读取指定路径TXT文件的文件内容
     */
    protected String getFileContent(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return "error 没找到文件";
        }
        StringBuilder content = new StringBuilder();
        //文件格式为txt文件
        try {
            FileInputStream instream = new FileInputStream(file);
            InputStreamReader inputreader
                    = new InputStreamReader(instream, "GBK");
            BufferedReader buffreader = new BufferedReader(inputreader);
            String line;
            //分行读取
            while ((line = buffreader.readLine()) != null) {
                content.append(line);
            }
            instream.close();//关闭输入流
        } catch (java.io.FileNotFoundException e) {
            content.append("error文件不存在");
        } catch (IOException e) {
            content.append("error解析异常");
        }
        return content.toString();
    }

    //ViewPager设置切换动画
    private void setViewPager() {
        int[] mImgIds = new int[]{R.mipmap.xixi1,
                R.mipmap.xixi2, R.mipmap.xixi3, R.mipmap.xixi4};
        List<ImageView> imageViews = new ArrayList<>();
        ImageView imageView1 = new ImageView(getApplicationContext());
        imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView1.setBackgroundResource(R.color.body_background);
        imageViews.add(imageView1);

        for (int imgId : mImgIds) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(imgId);
            imageViews.add(imageView);
        }
        ImageViewPagerAdapter adapter = new ImageViewPagerAdapter(imageViews);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.journal_analysis_result:
                AUtil.copy(this, result.getText().toString());
                Toast.makeText(this,"复制成功",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
