/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.github.symplelife.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.symplelife.R;
import com.github.symplelife.base.BaseActivity;
import com.github.symplelife.common.Constants;
import com.github.symplelife.tools.FileUtil;
import com.github.symplelife.tools.PromptManager;
import com.github.symplelife.view.HackyViewPager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.IOException;
import java.util.ArrayList;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifDrawableBuilder;
import pl.droidsonroids.gif.GifImageView;
import uk.co.senab.photoview.PhotoView;

/**
 * Lock/Unlock button is added to the ActionBar.
 * Use it to temporarily disable ViewPager navigation in order to correctly interact with ImageView by gestures.
 * Lock/Unlock state of ViewPager is saved and restored on configuration changes.
 * 
 * Julia Zudikova
 */

public class ViewPagerActivity extends BaseActivity implements ViewPager.OnPageChangeListener ,View.OnClickListener{

	private ViewPager mViewPager;
	private TextView pageCount;

	private ArrayList<String> imageList;
	private int cutrrentPos;
	private boolean isgif;
	private String currentUrl;
	private DisplayImageOptions options;
	private CircularProgressBar circularProgressBar;
	private Button saveImage;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.zoom_in, R.anim.fade_out);// 淡出淡入动画效果
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
		// 忽略 pointerIndex out of range 异常
        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
		pageCount = (TextView) findViewById(R.id.tv_page);
		circularProgressBar = (CircularProgressBar) findViewById(R.id.circularProgressBar);
		saveImage = (Button) findViewById(R.id.btn_save);
		saveImage.setOnClickListener(this);

		imageList = new ArrayList<>();

		if (getIntent().getBooleanExtra("single",false)){
			imageList.add(getIntent().getStringExtra("images"));
			pageCount.setVisibility(View.INVISIBLE);
		}else {
			imageList.addAll(getIntent().getStringArrayListExtra("images"));
			pageCount.setVisibility(View.VISIBLE);
		}

		cutrrentPos = getIntent().getIntExtra("position", 0);
		isgif = getIntent().getBooleanExtra("isgif",false);

		//设置缓存个数（不包括当前显示的view）
		mViewPager.setOffscreenPageLimit(imageList.size() - 1);
		mViewPager.setAdapter(new SamplePagerAdapter());
		mViewPager.addOnPageChangeListener(this);
		mViewPager.setCurrentItem(cutrrentPos);
		pageCount.setText(cutrrentPos + 1 + "/" + imageList.size());
		currentUrl = imageList.get(cutrrentPos);

		//显示图片的配置
		options = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.showImageOnFail(R.mipmap.ic_error)
//				.showImageOnLoading(R.mipmap.ic_loading)
				.imageScaleType(ImageScaleType.NONE_SAFE)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();


	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		pageCount.setText(position + 1 + "/" + imageList.size());
		currentUrl = imageList.get(position);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_save){
			saveImage();
		}
	}

	private boolean exit;
	private boolean tap ;
	private long durition;
	private long lastTime;
	private long currentTime;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:
				exit = true;
				lastTime = System.currentTimeMillis();
				break;
			case MotionEvent.ACTION_UP:
				currentTime = System.currentTimeMillis();
				if (exit && (currentTime - lastTime) < 200){

					finish();
				}
				break;
			case MotionEvent.ACTION_MOVE:
				exit = false ;
				break;
		}

//		durition = currentTime - lastTime ;


		return super.onTouchEvent(event);
	}

	class SamplePagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return imageList.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, final int position) {
			circularProgressBar.setVisibility(View.VISIBLE);
			final ImageView image;
			if (isgif){
				image = new GifImageView(container.getContext());
			}else {
				image = new PhotoView(container.getContext());
//				image.setScaleType(ImageView.ScaleType.CENTER);

			}
//			PhotoView photoView = new PhotoView(container.getContext());
//			ImageLoader.getInstance().displayImage(imageList.get(position), photoView);
//			final GifImageView gifImageView = new GifImageView(container.getContext());

			ImageLoader.getInstance().displayImage(imageList.get(position), image, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					super.onLoadingComplete(imageUri, view, loadedImage);
					if (isgif) {
						loadGif((GifImageView) image, imageList.get(position));
					}
					circularProgressBar.setVisibility(View.GONE);
				}

			});

					 // Now just add PhotoView to ViewPager and return it
					 container.addView(image, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			return image;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

	private void loadGif(GifImageView pic , String imageUrl) {
		pic.setImageDrawable(null);
		final GifDrawable existingOriginalDrawable = (GifDrawable) pic.getDrawable();
		final GifDrawableBuilder builder = new GifDrawableBuilder().with(existingOriginalDrawable);
		builder.from(ImageLoader.getInstance().getDiskCache().get(imageUrl).getAbsolutePath());
		try {
			pic.setImageDrawable(builder.build());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		this.finish();
		overridePendingTransition(R.anim.fade_in, R.anim.zoom_out);
	}

	/** 保存图片 */
	public void saveImage() {
		if (FileUtil.isSdcardExisting()) {
			// 如果路径不存在则创建路径
			FileUtil.createPath(Constants.PROJECTIMAGEPATH);
		}else {
			PromptManager.showToast(this, "未检测到SD卡，不能保存图片！");
			return;
		}

		String fromPath = this.getExternalCacheDir().getAbsolutePath() + java.io.File.separator + currentUrl.hashCode();
		String toPath = null;

		if (isgif){
			toPath = Constants.PROJECTIMAGEPATH + currentUrl.hashCode()+ ".gif";
		}else {
			toPath = Constants.PROJECTIMAGEPATH + currentUrl.hashCode()+ ".jpg";
		}

		if (org.xutils.common.util.FileUtil.copy(fromPath,toPath)){
			PromptManager.showToast(this,"保存成功！");
		}else {
			PromptManager.showToast(this,"保存失败！");
		}

	}

}
