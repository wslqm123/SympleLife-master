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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.symplelife.R;
import com.github.symplelife.common.Constants;
import com.github.symplelife.view.SmoothImageView;
import com.github.symplelife.tools.FileUtil;
import com.github.symplelife.tools.PromptManager;

import org.xutils.common.Callback;
import org.xutils.common.util.MD5;
import org.xutils.image.GifDrawable;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import fr.castorflex.android.circularprogressbar.CircularProgressDrawable;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImagesDetailActivity extends Activity implements View.OnClickListener {

//    private ArrayList<String> mDatas;
    private String imgUrl;
    private String orignalImgUrl;
    private int mPosition;
    private int mLocationX;
    private int mLocationY;
    private int mWidth;
    private int mHeight;
    private int oWidth;
    private int oHeight;
    private SmoothImageView imageView;
    private TextView tv_download;
    private ImageOptions options;
    private boolean isBig =false;
    private Drawable drawable;
    private CircularProgressBar circularProgressBar;
    private CircularProgressDrawable circularProgressDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(this,R.layout.activity_images_detail,null);
        imageView = (SmoothImageView) view.findViewById(R.id.images_detail_smooth_image);
        tv_download = (TextView) view.findViewById(R.id.tv_download);
        circularProgressBar = (CircularProgressBar) view.findViewById(R.id.circularProgressBar);
        circularProgressDrawable =  (CircularProgressDrawable) circularProgressBar
                .getIndeterminateDrawable();
        tv_download.setOnClickListener(this);

//        mDatas = (ArrayList<String>) getIntent().getSerializableExtra("images");
        imgUrl = getIntent().getStringExtra("images");
        orignalImgUrl = getIntent().getStringExtra("orignalImage");
        mPosition = getIntent().getIntExtra("position", 0);
        mLocationX = getIntent().getIntExtra("locationX", 0);
        mLocationY = getIntent().getIntExtra("locationY", 0);
        mWidth = getIntent().getIntExtra("width", 0);
        mHeight = getIntent().getIntExtra("height", 0);
        oWidth = getIntent().getIntExtra("orignalWidth", 0);
        oHeight = getIntent().getIntExtra("orignalHeight", 0);

//        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        imageView.setScaleType(ImageView.Scal.
// eType.FIT_CENTER);
        setContentView(view);

        loadImage();
        initEvent();

        imageView.setDrawingCacheEnabled(true);
        imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY, oWidth, oHeight);
        imageView.transformIn();
    }

    private void loadImage() {

        //xutils3加载图片配置
        options = new ImageOptions.Builder()
                // 是否忽略GIF格式的图片
                .setIgnoreGif(false)
                        // 图片缩放模式
                .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
                        // 下载中显示的图片
//                    .setLoadingDrawableId(R.mipmap.ic_error)
                        // 下载失败显示的图片
                .setFailureDrawableId(R.mipmap.ic_error)
                        // 得到ImageOptions对象
                .build();

        // 加载图片
        x.image().bind(imageView, imgUrl, options);


        //显示图片的配置
       /* DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_stub)
                .showImageOnFail(R.mipmap.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(imgUrl, imageView,options);
        LogUtil.e("文件名"+imgUrl.hashCode());*/

    }

    private void initEvent() {
        imageView.setOnTransformListener(new SmoothImageView.TransformListener() {
            @Override
            public void onTransformComplete(int mode) {
                if (mode == 2) {
                    finish();
                }
            }
        });

        imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v2) {
                if (isBig){
                    x.image().bind(imageView,imgUrl,options);
                    isBig = false;
                    tv_download.setText("查看原图");
                }else {
                    imageView.transformOut();
                }

            }
        });
    }


    @Override
    public void onBackPressed() {
        if (isBig){
            x.image().bind(imageView,imgUrl,options);
            isBig = false;
            tv_download.setText("查看原图");
        }else {
            imageView.transformOut();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_download){
//            PromptManager.showToast(this,"保存成功");
            if (!isBig){
                circularProgressBar.setVisibility(View.VISIBLE);
                tv_download.setText("保存图片");
                isBig = true;
                x.image().bind(imageView, orignalImgUrl, options, new Callback.CommonCallback<Drawable>() {
                    @Override
                    public void onSuccess(Drawable result) {
                        drawable = result;
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {
//                        PromptManager.closeProgressDialog();
//                        circularProgressDrawable.stop();
                        circularProgressBar.setVisibility(View.GONE);

                    }
                });
            }else {
//                保存图片的方法
               saveImage();
            }

        }
    }


    /** 保存方法 */
    public void saveImage() {
        if (FileUtil.isSdcardExisting()) {
            // 如果路径不存在则创建路径
            FileUtil.createPath(Constants.PROJECTIMAGEPATH);
        }else {
            PromptManager.showToast(this,"未检测到SD卡，不能保存图片！");
            return;
        }

        String fromPath = FileUtil.getXutilImg(orignalImgUrl);
        String toPath = null;

        if (drawable instanceof GifDrawable){
            toPath = Constants.PROJECTIMAGEPATH + MD5.md5(orignalImgUrl) + ".gif";
        }else {
            toPath = Constants.PROJECTIMAGEPATH + MD5.md5(orignalImgUrl) + ".jpg";
        }

        if (org.xutils.common.util.FileUtil.copy(fromPath,toPath)){
            PromptManager.showToast(this,"保存成功！");
        }else {
            PromptManager.showToast(this,"保存失败！");
        }

    }
}
