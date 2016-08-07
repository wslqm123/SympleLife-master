package com.github.symplelife.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.github.symplelife.R;
import com.github.symplelife.base.BaseApplication;

public class NewsCommentActivity extends Activity{

	private WebView mWebView;
	private ProgressBar mprogressBar;
	private String address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if ( BaseApplication.mTheme != 0) {
			setTheme(BaseApplication.mTheme);
		}
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.activity_news_detail_2);
		address = getIntent().getStringExtra("网址");
		initView();
		initActionbar();
	}

	private void initActionbar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);//设置显示左侧按钮
		actionBar.setHomeButtonEnabled(true);//设置左侧按钮可点
		actionBar.setDisplayShowTitleEnabled(true);//设置显示标题
		actionBar.setTitle("评论");
	}

	private void initView() {

		mprogressBar = (ProgressBar) this.findViewById(R.id.mProgress);
		mWebView = (WebView) this.findViewById(R.id.web);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setUseWideViewPort(true);
		mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
		// mWebView.getSettings().setBlockNetworkImage(true);
//		mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
		mWebView.loadData("", "text/html", null);
		mWebView.loadUrl("javascript:alert(injectedObject.toString())");
		// mWebView.setInitialScale(39);
		mWebView.setWebViewClient(new MyWebViewClient(mprogressBar));
		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				// MainActivity.this.setProgress(newProgress * 100);
				mprogressBar.setProgress(newProgress);
			}
		});
		mWebView.loadUrl(address);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		finish();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mWebView.clearCache(true);
				mWebView.clearFormData();
				((ViewGroup) mWebView.getParent()).removeView(mWebView);
				mWebView.destroy();
			}
		}, 300);
		return super.onOptionsItemSelected(item);
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK ){
			if (mWebView.canGoBack()) {
				mWebView.goBack();
//			return true;
			}else {
				finish();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mWebView.clearCache(true);
						mWebView.clearFormData();
						((ViewGroup) mWebView.getParent()).removeView(mWebView);
						mWebView.destroy();
					}
				},300);

			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


}
