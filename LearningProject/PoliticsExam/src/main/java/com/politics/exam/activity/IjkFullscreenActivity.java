package com.politics.exam.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dl7.player.media.IjkPlayerView;
import com.politics.exam.R;
import com.politics.exam.entity.VideoInfo;

public class IjkFullscreenActivity extends AppCompatActivity {

    private static final String IMAGE_URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540927613660&di=8537e6a8b088fe1389d781fcb1dd06b3&imgtype=0&src=http%3A%2F%2Fp.ssl.qhimg.com%2Ft018aab68a8489b3308.jpg";
    IjkPlayerView mPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayerView = new IjkPlayerView(this);
        setContentView(mPlayerView);

        VideoInfo videoInfo = (VideoInfo) getIntent().getSerializableExtra("videoInfo");

        Glide.with(this).load(IMAGE_URL).fitCenter().into(mPlayerView.mPlayerThumb);

        mPlayerView.init()
                .alwaysFullScreen()
                .enableOrientation()
                .setVideoPath(videoInfo.getUrl())
                .enableDanmaku(false)
                .setTitle(videoInfo.getName())
                .start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayerView.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayerView.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mPlayerView.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (mPlayerView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
