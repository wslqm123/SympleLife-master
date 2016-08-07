/*
 * Copyright (C) 2013 yixia.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.symplelife.activity;


public class VideoViewActivity {
//public class VideoViewActivity extends Activity implements View.OnClickListener{

/*	private VideoView mVideoView;
	private MediaController mediaController;
	private Button changeLayout;
	private Button back;
	private int mVideoLayout = 1;

	@Override
	public void onCreate(Bundle icicle) {
		overridePendingTransition(R.anim.zoom_in, R.anim.fade_out);
		super.onCreate(icicle);

//		Vitamio.isInitialized(this);
		// 强制开启屏幕旋转
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
		setContentView(R.layout.activity_videoview);

		mVideoView = (VideoView) findViewById(R.id.surface_view);
		changeLayout = (Button) findViewById(R.id.btn_changeLayout);
		back = (Button) findViewById(R.id.btn_back);
		changeLayout.setOnClickListener(this);
		back.setOnClickListener(this);

		mediaController = new MediaController(this);

		String url = getIntent().getStringExtra("uri");

		playfunction(UriHelper.getInstance().getFunnyVideo(url));

	}


	void playfunction(String path ){

		*//*
		 * Alternatively,for streaming media you can use
		 * mVideoView.setVideoURI(Uri.parse(URLstring));
		 *//*


		mVideoView.setVideoPath(path);
		mVideoView.setMediaController(mediaController);
		mVideoView.requestFocus();

		mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mediaPlayer) {
				// optional need Vitamio 4.0
				mediaPlayer.setPlaybackSpeed(1.0f);
				// 刚开始播放 seekbar 不会自动隐藏，需要设置默认显示时间
				mediaController.show();
			}
		});

		mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				finish();
			}
		});

		mediaController.setOnShownListener(new MediaController.OnShownListener() {
			@Override
			public void onShown() {
				changeLayout.setVisibility(View.VISIBLE);
				back.setVisibility(View.VISIBLE);
			}
		});

		mediaController.setOnHiddenListener(new MediaController.OnHiddenListener() {
			@Override
			public void onHidden() {
				changeLayout.setVisibility(View.INVISIBLE);
				back.setVisibility(View.INVISIBLE);
			}
		});
	}


	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		mVideoView.setVideoLayout(mVideoLayout, 0);

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()){
			case R.id.btn_back:
				// 横屏时
//				if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
//					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//				}else {
//					finish();
//				}

				finish();
				overridePendingTransition(R.anim.fade_in, R.anim.zoom_out);
				break;

			case R.id.btn_changeLayout:

//				if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
//					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//				}else {
//					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//				}

				mVideoLayout++;
				if (mVideoLayout == 3) {
					mVideoLayout = 0;
				}
				switch (mVideoLayout) {
					case 0:
						mVideoLayout = VideoView.VIDEO_LAYOUT_ORIGIN;
						view.setBackgroundResource(R.mipmap.mediacontroller_sreen_size_100);
						break;
					case 1:
						mVideoLayout = VideoView.VIDEO_LAYOUT_SCALE;
						view.setBackgroundResource(R.mipmap.mediacontroller_screen_fit);

						break;
					case 2:
						mVideoLayout = VideoView.VIDEO_LAYOUT_STRETCH;
						view.setBackgroundResource(R.mipmap.mediacontroller_screen_size);
						break;

				}
				mVideoView.setVideoLayout(mVideoLayout, 0);
				break;
		}

	}

//	@Override
//	public void onBackPressed() {
//		if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//			return;
//		}
//		super.onBackPressed();
//	}

	@Override
	public void onBackPressed() {
		this.finish();
		overridePendingTransition(R.anim.fade_in, R.anim.zoom_out);
	}*/
}
