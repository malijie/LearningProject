package com.politics.exam.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.politics.exam.R;
import com.politics.exam.util.IntentManager;


/**
 * Created by Administrator on 2017/3/21.
 */

public class WelcomeActivity extends FragmentActivity {
    private ImageView mImageWelcome = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.setContentView(R.layout.welcome_layout);

        initViews();
    }

    public void initViews(){
        mImageWelcome = (ImageView) findViewById(R.id.id_welcome_image);
        AlphaAnimation alpha = new AlphaAnimation(1.1f, 0.1f);
        alpha.setDuration(3000);
        mImageWelcome.setAnimation(alpha);

        alpha.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                IntentManager.startActivity(HomePageActivity.class);
                mImageWelcome.setVisibility(View.GONE);
                WelcomeActivity.this.finish();
            }
        });
        mImageWelcome.startAnimation(alpha);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
