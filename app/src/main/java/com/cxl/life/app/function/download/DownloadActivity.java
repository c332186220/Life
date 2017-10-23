package com.cxl.life.app.function.download;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cxl.life.R;
import com.cxl.life.app.BaseTitleActivity;
import com.cxl.life.app.face.DownloadListener;
import com.cxl.life.util.ToastUtil;

public class DownloadActivity extends BaseTitleActivity implements View.OnClickListener {

    DownloadManager mDownloadManager;
    String wechatUrl = "http://dldir1.qq.com/weixin/android/weixin657android1040.apk";
    String qqUrl = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";

    private Button downloadAll, cancelAll;//全部下载  全部取消
    private TextView name1, name2, progressTv1, progressTv2;//下载的名字  下载的进度
    private Button download1, download2, cancel1, cancel2;//下载   取消
    private ProgressBar progress1, progress2;//下载的进度条

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_download);
    }

    @Override
    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "断点并多线程下载", true);

        downloadAll = (Button) findViewById(R.id.btn_download_all);
        downloadAll.setOnClickListener(this);
        cancelAll = (Button) findViewById(R.id.btn_cancel_all);
        cancelAll.setOnClickListener(this);

        name1 = (TextView) findViewById(R.id.download_file_name1);
        name2 = (TextView) findViewById(R.id.download_file_name2);
        name1.setText("微信");
        name2.setText("QQ");
        progressTv1 = (TextView) findViewById(R.id.download_tv_progress1);
        progressTv2 = (TextView) findViewById(R.id.download_tv_progress2);

        download1 = (Button) findViewById(R.id.btn_download1);
        download2 = (Button) findViewById(R.id.btn_download2);
        cancel1 = (Button) findViewById(R.id.btn_cancel1);
        cancel2 = (Button) findViewById(R.id.btn_cancel2);
        download1.setOnClickListener(this);
        download2.setOnClickListener(this);
        cancel1.setOnClickListener(this);
        cancel2.setOnClickListener(this);

        progress1 = (ProgressBar) findViewById(R.id.download_pb_progress1);
        progress2 = (ProgressBar) findViewById(R.id.download_pb_progress2);

        initDownloads();
    }

    private void initDownloads() {
        mDownloadManager = DownloadManager.getInstance();
        mDownloadManager.add(wechatUrl, new DownloadListener() {
            @Override
            public void onFinished() {
                ToastUtil.showToast("下载完成!");
                progressTv1.setText("100%");
                progress1.setProgress(100);
                download1.setVisibility(View.GONE);
                cancel1.setVisibility(View.GONE);
            }

            @Override
            public void onProgress(float progress) {
                progress1.setProgress((int) (progress * 100));
                progressTv1.setText(String.format("%.2f", progress * 100) + "%");
            }

            @Override
            public void onPause() {
                ToastUtil.showToast("暂停了!");
                changeButtonText();
            }

            @Override
            public void onCancel() {
                progressTv1.setText("0%");
                progress1.setProgress(0);
                download1.setText("下载");
                ToastUtil.showToast("下载已取消!");
                changeButtonText();
            }
        });

        mDownloadManager.add(qqUrl, new DownloadListener() {
            @Override
            public void onFinished() {
                ToastUtil.showToast("下载完成!");
                progressTv2.setText("100%");
                progress2.setProgress(100);
                download2.setVisibility(View.GONE);
                cancel2.setVisibility(View.GONE);
            }

            @Override
            public void onProgress(float progress) {
                progress2.setProgress((int) (progress * 100));
                progressTv2.setText(String.format("%.2f", progress * 100) + "%");
            }

            @Override
            public void onPause() {
                ToastUtil.showToast("暂停了!");
                changeButtonText();
            }

            @Override
            public void onCancel() {
                progressTv1.setText("0%");
                progress2.setProgress(0);
                download2.setText("下载");
                ToastUtil.showToast("下载已取消!");
                changeButtonText();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_download_all:
                if (!mDownloadManager.isDownloading(wechatUrl, qqUrl)) {
                    download1.setText("暂停");
                    download2.setText("暂停");
                    downloadAll.setText("全部暂停");
                    mDownloadManager.download(wechatUrl, qqUrl);//最好传入个String[]数组进去
                } else {
                    mDownloadManager.pause(wechatUrl, qqUrl);
                    download1.setText("下载");
                    download2.setText("下载");
                    downloadAll.setText("全部下载");
                }
                break;
            case R.id.btn_cancel_all:
                mDownloadManager.cancel(wechatUrl, qqUrl);
                download1.setText("下载");
                download2.setText("下载");
                downloadAll.setText("全部下载");
                break;
            case R.id.btn_download1:
                if (!mDownloadManager.isDownloading(wechatUrl)) {
                    mDownloadManager.download(wechatUrl);
                    download1.setText("暂停");
                } else {
                    download1.setText("下载");
                    mDownloadManager.pause(wechatUrl);
                }
                break;
            case R.id.btn_cancel1:
                mDownloadManager.cancel(wechatUrl);
                break;
            case R.id.btn_download2:
                if (!mDownloadManager.isDownloading(qqUrl)) {
                    mDownloadManager.download(qqUrl);
                    download2.setText("暂停");
                } else {
                    download2.setText("下载");
                    mDownloadManager.pause(qqUrl);
                }
                break;
            case R.id.btn_cancel2:
                mDownloadManager.cancel(qqUrl);
                break;
        }
    }

    private void changeButtonText(){
        String d1 = download1.getText().toString();
        String d2 = download2.getText().toString();
        if(d1.equals("下载")&&d2.equals("下载")){
            downloadAll.setText("全部下载");
        }else{
            downloadAll.setText("全部暂停");
        }
    }
}
