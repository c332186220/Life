package com.cxl.life.app.voice;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cxl.life.R;
import com.cxl.life.util.Constants;
import com.cxl.life.util.FileUtil;
import com.cxl.life.util.TimeUtil;
import com.cxl.life.adapter.TextRecyclerAdapter;
import com.cxl.life.app.BaseActivity;

import java.io.File;
import java.util.List;

public class VoiceRecordActivity extends BaseActivity {
    static final int VOICE_REQUEST_CODE = 11;

    private Button record;
    private RelativeLayout rl;
    private RecyclerView recyclerView;
    private TextRecyclerAdapter adapter;
    private List<String> list;

    private AudioRecordUtils mAudioRecordUtils;
    private float downY;//记录按下时的y
    private boolean isCanceled = false; // 是否取消录音
    private PopupWindowFactory mPop;
    private ImageView mImageView;//图片
    private TextView mTextView;//文字描述
    private long recordTime = 0;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_record);

        initView();
        initData();
    }

    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.voice_record_toolbar);
        toolbar.setTitle(R.string.activity_voice_record);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.voice_record_rlv);
        record = (Button) findViewById(R.id.voice_record_button);
        rl = (RelativeLayout) findViewById(R.id.activity_voice_record);
        mAudioRecordUtils = new AudioRecordUtils();
        //录音回调
        mAudioRecordUtils.setOnAudioStatusUpdateListener(new AudioRecordUtils.OnAudioStatusUpdateListener() {

            //录音中....db为声音分贝，time为录音时长
            @Override
            public void onUpdate(double db, long time) {
                mImageView.getDrawable().setLevel((int) (3000 + 6000 * db / 100));
                mTextView.setText(TimeUtil.longToString(time));
                recordTime = time;
            }

            //录音结束，filePath为保存路径
            @Override
            public void onStop(String filePath) {
                Toast.makeText(VoiceRecordActivity.this, "录音保存在：" + filePath, Toast.LENGTH_SHORT).show();
                mTextView.setText(TimeUtil.longToString(0));
                list.add(filePath.substring(filePath.lastIndexOf("/") + 1));
                adapter.notifyItemInserted(list.size() - 1);
            }
        });

        //PopupWindow的布局文件
        View view = View.inflate(this, R.layout.voice_microphone, null);
        mPop = new PopupWindowFactory(this, view);
        mImageView = (ImageView) view.findViewById(R.id.iv_recording_icon);
        mTextView = (TextView) view.findViewById(R.id.tv_recording_time);

        requestPermissions();
    }

    private void initData() {
        list = FileUtil.getFileInPath(Constants.voice_sd, ".amr");
        adapter = new TextRecyclerAdapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        adapter.setOnItemClickListener(new TextRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(VoiceRecordActivity.this, list.get(position), Toast.LENGTH_SHORT).show();
                player(Constants.voice_sd + "/" + list.get(position));
            }
        });
    }

    /**
     * 开启录制之前判断权限是否打开
     */
    private void requestPermissions() {
        //判断是否开启摄像头权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                ) {
            //判断是否开启语音权限
            readyRecordVoice();
        } else {
            //请求获取摄像头权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, VOICE_REQUEST_CODE);
        }
    }

    /**
     * 请求权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == VOICE_REQUEST_CODE) {
            if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) && (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                readyRecordVoice();
            } else {
                Toast.makeText(this, "已拒绝权限！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 准备录制
     */
    private void readyRecordVoice() {
        record.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPop.showAtLocation(rl, Gravity.CENTER, 0, 0);
                        downY = event.getY();
                        record.setText("松开  结束");
                        mAudioRecordUtils.startRecord();
                        break;
                    case MotionEvent.ACTION_MOVE: // 滑动手指
                        float moveY = event.getY();
                        if (downY - moveY > 150) {
                            isCanceled = true;
                            record.setText("松开  取消");
                        } else {
                            isCanceled = false;
                            record.setText("松开  结束");
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (isCanceled) {
                            mAudioRecordUtils.cancelRecord();//取消录音（不保存录音文件）
                        } else if (recordTime < 1000) {
                            mAudioRecordUtils.cancelRecord();//取消录音（不保存录音文件）
                            Toast.makeText(VoiceRecordActivity.this, "录音时间太短", Toast.LENGTH_SHORT).show();
                        } else {
                            mAudioRecordUtils.stopRecord();  //结束录音（保存录音文件）
                        }
                        mPop.dismiss();
                        record.setText("按住  说话");
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 播放语音
     */
    protected void player(String path) {
        File file = new File(path);
        if (file.exists() && file.length() > 0) {
            try {
                if (mediaPlayer == null)
                    mediaPlayer = new MediaPlayer();
                mediaPlayer.reset();
                // 设置指定的流媒体地址
                mediaPlayer.setDataSource(path);
                // 设置音频流的类型
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                // 通过异步的方式装载媒体资源
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        // 装载完毕 开始播放流媒体
                        mediaPlayer.start();
                        Log.e("chengxuanle", "开始播放");
                    }
                });
                // 设置循环播放
                // mediaPlayer.setLooping(true);

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Toast.makeText(VoiceRecordActivity.this, "播放完成", Toast.LENGTH_SHORT).show();
                    }
                });
                mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        Toast.makeText(VoiceRecordActivity.this, "播放错误", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "播放失败", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
        }
    }

    //暂停播放，继续播放
    protected void playerPause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }

    //重新播放
    protected void playerReplay() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(0);
        }
    }

    //停止播放并回收资源
    protected void playerStop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        playerStop();
        super.onDestroy();
    }
}
