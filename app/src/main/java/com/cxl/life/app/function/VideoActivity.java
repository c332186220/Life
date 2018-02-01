package com.cxl.life.app.function;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.cxl.life.R;
import com.cxl.life.util.Constants;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView videoPlay1, videoPlay2;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.activity_video_play);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        videoPlay1 = (TextView) findViewById(R.id.video_system_play);
        videoPlay1.setOnClickListener(this);
        videoPlay2 = (TextView) findViewById(R.id.video_view_play);
        videoPlay2.setOnClickListener(this);
        videoView = (VideoView) findViewById(R.id.video_view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_system_play:
                play1();
                break;
            case R.id.video_view_play:
                play2();
                break;
        }
    }

    private void play1() {
        Uri uri = Uri.parse(Constants.video_store_sd);
        //调用系统自带的播放器
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "video/mp4");
        startActivity(intent);
    }

    private void play2() {
        Uri uri = Uri.parse(Constants.video_store_sd);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.requestFocus();
    }
}
