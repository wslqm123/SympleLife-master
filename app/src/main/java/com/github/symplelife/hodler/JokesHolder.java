package com.github.symplelife.hodler;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.symplelife.R;
import com.github.symplelife.activity.ViewPagerActivity;
import com.github.symplelife.bean.funnyBean.GroupBean;
import com.github.symplelife.tools.UiUtils;
import com.github.symplelife.view.FitGifDrawable;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifDrawableBuilder;

/**
 * 版权归本人所有
 * <p>
 * 版本：1.0
 * <p>
 * 描述：
 * <p>
 * Created by AUTO-ROBOT on 2016/2/1.
 */
public class JokesHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ImageView icon;
    private FitGifDrawable pic;
    private TextView name;
    private TextView content;
    private TextView digg;
    private TextView bury;
    private TextView comment;
    private TextView tag;
    private TextView progress;
    private TextView detail;
//    private ImageOptions xOptions;
    private DisplayImageOptions options1;
    private DisplayImageOptions options2;

    private String imageUrl;
    private boolean isgif;

    public JokesHolder(View itemView) {
        super(itemView);
        icon = (ImageView) itemView.findViewById(R.id.icon);
        pic = (FitGifDrawable) itemView.findViewById(R.id.iv_pic);
        name = (TextView) itemView.findViewById(R.id.tv_name);
        content = (TextView) itemView.findViewById(R.id.tv_context);
        digg = (TextView) itemView.findViewById(R.id.tv_digg_count);
        bury = (TextView) itemView.findViewById(R.id.tv_bury_count);
        comment = (TextView) itemView.findViewById(R.id.tv_comment_count);
        tag = (TextView) itemView.findViewById(R.id.tv_tag);
        progress = (TextView) itemView.findViewById(R.id.tv_progress);
        detail = (TextView) itemView.findViewById(R.id.tv_detail);

        pic.setOnClickListener(this);

//        xOptions = new ImageOptions.Builder()
////                   下载失败显示的图片
//                .setFailureDrawableId(R.mipmap.ic_error)
////                   得到ImageOptions对象
//                .setFadeIn(true)
//                .setCircular(true)
//                .build();

        //头像图片的配置
        options1 = new DisplayImageOptions.Builder()
                //                .showImageOnLoading(R.drawable.ic_stub)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new CircleBitmapDisplayer())
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        //显示图片的配置
        options2 = new DisplayImageOptions.Builder()
                //                .showImageOnLoading(R.drawable.ic_stub)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(R.mipmap.ic_error)
                .showImageOnLoading(R.mipmap.ic_loading)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();


    }

    public void setData(final GroupBean bean) {

        if (bean.is_gif == 1){
            isgif = true;
        } else {
            isgif = false;
        }

        if (bean.large_image !=null){
            imageUrl = "http://p6.pstatp.com/" + bean.large_image.uri;
            pic.setImageHeight(bean.large_image.r_height);
            pic.setImageWidth(bean.large_image.r_width);
        }

//        x.image().bind(icon, bean.user.avatar_url, xOptions);
        ImageLoader.getInstance().displayImage(bean.user.avatar_url, icon , options1);

        detail.setVisibility(View.GONE);
        pic.setVisibility(View.VISIBLE);

        // 判断是否是GIF
        if (isgif){
            progress.setVisibility(View.VISIBLE);

            ImageLoader.getInstance().displayImage( imageUrl , pic, options2, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    loadGif(bean);
                    progress.setVisibility(View.INVISIBLE);
                }

            }, new ImageLoadingProgressListener() {
                @Override
                public void onProgressUpdate(String imageUri, View view, int current, int total) {
                    progress.setText((current*100/total) + "%");

                }
            }
            );

        }else {

            progress.setVisibility(View.GONE);

            // 判断是否包含图片
            if (bean.large_image!=null){

                if (bean.large_image.r_height > bean.large_image.r_width*3){
                    pic.setImageHeight(bean.large_image.r_width);
                    detail.setVisibility(View.VISIBLE);
                }
//                else {
//                    pic.setImageHeight(bean.large_image.r_height);
//                }

//                pic.setImageWidth(bean.large_image.r_width);
                ImageLoader.getInstance().displayImage( imageUrl , pic, options2);

            }else {
                pic.setVisibility(View.GONE);

            }

        }

        if(TextUtils.isEmpty(bean.content)){
            content.setVisibility(View.GONE);
        }else {
            content.setVisibility(View.VISIBLE);
            content.setText(bean.content);
        }

        name.setText(bean.user.name);
        digg.setText(bean.digg_count);
        bury.setText(bean.bury_count);
        comment.setText(bean.comment_count);
        tag.setText(bean.category_name);


    }

    private void loadGif(GroupBean bean) {
        pic.setImageDrawable(null);
//        pic.setImageHeight(bean.large_image.r_height);
//        pic.setImageWidth(bean.large_image.r_width);
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
    public void onClick(View v) {

        Intent intent=new Intent(UiUtils.getContext(), ViewPagerActivity.class);
        intent.putExtra("single", true);
        intent.putExtra("images", imageUrl);
        intent.putExtra("isgif", isgif);
        UiUtils.startActivity(intent);
    }
}
