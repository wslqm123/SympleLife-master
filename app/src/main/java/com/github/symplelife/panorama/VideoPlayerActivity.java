package com.github.symplelife.panorama;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.asha.vrlib.MDVRLibrary;
import com.github.symplelife.R;
import com.github.symplelife.tools.TimeUtil;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by hzqiujiadi on 16/4/5.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class VideoPlayerActivity extends MD360PlayerActivity {

    private MediaPlayerWrapper mMediaPlayerWrapper = new MediaPlayerWrapper();

    private IMediaPlayer iPlayer ;
    private SeekBarHandler seekBarHandler;
    private int totalTime;
    private int currentTime;

    private static final int HANDLER_UPDATE_PROGRESS = 0;
    private static final int HANDLER_TRACKING_SEEKBAR = 1;
    private static final int HANDLER_SHOW_SEEKBAR = 2;
    private static final int HANDLER_HIDE_SEEKBAR = 3;
    private static final int HANDLER_VIDEO_COMPLETE = 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMediaPlayerWrapper.init();
        mMediaPlayerWrapper.setPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                cancelBusy();
                totalTime = (int)iPlayer.getDuration()/1000;
                panoramaSeekbar.setMax(totalTime);
//                panoramaDuration.setText(TimeUtil.second2Hour(totalTime));
                seekBarHandler.sendEmptyMessage(HANDLER_UPDATE_PROGRESS);

                seekBarHandler.removeMessages(HANDLER_HIDE_SEEKBAR);
                seekBarHandler.sendEmptyMessageDelayed(HANDLER_HIDE_SEEKBAR ,3000);
            }
        });

        iPlayer = mMediaPlayerWrapper.getPlayer();
        seekBarHandler = new SeekBarHandler();

        iPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {
                String error = String.format("Play Error what=%d extra=%d",what,extra);
                Toast.makeText(VideoPlayerActivity.this, error, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        iPlayer.setOnVideoSizeChangedListener(new IMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
                getVRLibrary().onTextureResize(width, height);
            }
        });

        iPlayer.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                seekBarHandler.sendEmptyMessage(HANDLER_VIDEO_COMPLETE);
            }
        });

        Uri uri = getUri();
        if (uri != null){
            mMediaPlayerWrapper.openRemoteFile(uri.toString());
            mMediaPlayerWrapper.prepare();
        }

        panoramaSeekbar.setOnSeekBarChangeListener(change);
        playerToogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iPlayer!=null){
                    if (iPlayer.isPlaying()){
                        iPlayer.pause();
                        playerToogle.setImageResource(android.R.drawable.ic_media_play);
                    }else {
                        iPlayer.start();
                        playerToogle.setImageResource(android.R.drawable.ic_media_pause);
                    }
                }

            }
        });

    }

    private OnSeekBarChangeListener change = new OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // 当进度条停止修改的时候触发
            // 取得当前进度条的刻度
            int progress = seekBar.getProgress();

            if (iPlayer != null && iPlayer.isPlaying()) {
                // 设置当前播放的位置
                iPlayer.seekTo(progress*1000); // 进度条单位为秒需要转换成毫秒传递给播放器
            }
            // 拖拽完成后更新进度条，3秒后自动隐藏
            seekBarHandler.sendEmptyMessageDelayed(HANDLER_UPDATE_PROGRESS , 1000);

            seekBarHandler.sendEmptyMessageDelayed(HANDLER_HIDE_SEEKBAR , 3000);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // 当开始拖拽进度条时不更新视频进度，进度条不隐藏
            seekBarHandler.removeMessages(HANDLER_UPDATE_PROGRESS);
            seekBarHandler.removeMessages(HANDLER_HIDE_SEEKBAR);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            String time = TimeUtil.second2Hour(progress) + "/" + TimeUtil.second2Hour(totalTime);
            panoramaDuration.setText(time);
        }
    };


    @Override
    protected MDVRLibrary createVRLibrary() {
        return MDVRLibrary.with(this)
                .displayMode(MDVRLibrary.DISPLAY_MODE_NORMAL)
                .interactiveMode(MDVRLibrary.INTERACTIVE_MODE_MOTION)
                .asVideo(new MDVRLibrary.IOnSurfaceReadyCallback() {
                    @Override
                    public void onSurfaceReady(Surface surface) {
                        mMediaPlayerWrapper.getPlayer().setSurface(surface);
                    }
                })
                .ifNotSupport(new MDVRLibrary.INotSupportCallback() {
                    @Override
                    public void onNotSupport(int mode) {
//                        String tip = mode == MDVRLibrary.INTERACTIVE_MODE_MOTION
//                                ? "onNotSupport:MOTION" : "onNotSupport:" + String.valueOf(mode);
//                        Toast.makeText(VideoPlayerActivity.this, tip, Toast.LENGTH_SHORT).show();
                    }
                })
                .pinchEnabled(true)
                .gesture(new MDVRLibrary.IGestureListener() {
                    @Override
                    public void onClick(MotionEvent e) {
//                        Toast.makeText(VideoPlayerActivity.this, "onClick!", Toast.LENGTH_SHORT).show();
                        // 点击屏幕控制隐藏和显示进度条
                        if (mediaController.getVisibility() == View.VISIBLE){
                            seekBarHandler.removeMessages(HANDLER_HIDE_SEEKBAR);
                            seekBarHandler.sendEmptyMessage(HANDLER_HIDE_SEEKBAR);
                        }else {
                            seekBarHandler.sendEmptyMessage(HANDLER_SHOW_SEEKBAR);
                        }

                    }
                })
                .build(R.id.gl_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayerWrapper.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayerWrapper.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayerWrapper.onResume();
    }


    private class SeekBarHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANDLER_UPDATE_PROGRESS:
                    if (iPlayer != null && iPlayer.isPlaying()){
                        currentTime = (int) (iPlayer.getCurrentPosition()/1000);
                        panoramaSeekbar.setProgress(currentTime);
                        String time = TimeUtil.second2Hour(currentTime) + "/" + TimeUtil.second2Hour(totalTime);
                        panoramaDuration.setText(time);
                    }
                    seekBarHandler.sendEmptyMessageDelayed(HANDLER_UPDATE_PROGRESS , 1000);
                    break;
                case HANDLER_TRACKING_SEEKBAR:
                    break;

                case HANDLER_SHOW_SEEKBAR:
                    mediaController.setVisibility(View.VISIBLE);
                    // 3秒后自动隐藏进度条
                    seekBarHandler.removeMessages(HANDLER_HIDE_SEEKBAR); //发生隐藏进度条的消息时应清除上一条
                    seekBarHandler.sendEmptyMessageDelayed(HANDLER_HIDE_SEEKBAR , 3000);
                    break;

                case HANDLER_HIDE_SEEKBAR:
                    mediaController.setVisibility(View.INVISIBLE);
                    break;

                case HANDLER_VIDEO_COMPLETE:
                    finish();
                    break;
            }
        }
    }
}
