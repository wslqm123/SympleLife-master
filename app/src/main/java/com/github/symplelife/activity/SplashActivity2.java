/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.symplelife.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.symplelife.MainActivity;
import com.github.symplelife.R;
import com.github.symplelife.interactor.SplashInteractor;
import com.github.symplelife.interactor.impl.SplashInteractorImpl;
import com.github.symplelife.view.KenBurnsView;
import com.github.symplelife.view.SplashView;


/**
 * Author:  Tau.Chen
 * Email:   1076559197@qq.com | tauchen1990@gmail.com
 * Date:    2015/3/9.
 * Description:
 */
public class SplashActivity2 extends Activity implements SplashView {

    private KenBurnsView mSplashImage;
    private ImageView splash_app_icon;
    private TextView mVersionName;
    private TextView mCopyright;

    private SplashInteractor mSplashInteractor = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splash_app_icon = (ImageView) findViewById(R.id.splash_app_icon);
        mSplashImage = (KenBurnsView) findViewById(R.id.splash_image);
        mVersionName = (TextView) findViewById(R.id.splash_version_name);
        mCopyright = (TextView) findViewById(R.id.splash_copyright);

        mSplashInteractor = new SplashInteractorImpl();
        initialized();
    }

    public void initialized() {
        initializeViews(mSplashInteractor.getVersionName(this),
                mSplashInteractor.getCopyright(this),
                mSplashInteractor.getBackgroundImageResID());

        Animation animation = mSplashInteractor.getBackgroundImageAnimation(this);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                navigateToHomePage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animateBackgroundImage(animation);
    }


    @Override
    public void initializeViews(String versionName, String copyright, int backgroundResId) {
        mCopyright.setText(copyright);
        mVersionName.setText(versionName);
//        mSplashImage.setImageResource(backgroundResId);
        mSplashImage.setResourceIds(backgroundResId, backgroundResId);
    }

    @Override
    public void animateBackgroundImage(Animation animation) {
//        mSplashImage.startAnimation(animation);
        splash_app_icon.startAnimation(animation);
    }

    @Override
    public void navigateToHomePage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
