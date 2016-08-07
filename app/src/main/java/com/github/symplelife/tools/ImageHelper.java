package com.github.symplelife.tools;

import android.widget.ImageView;

import com.github.symplelife.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * 版权归本人所有
 * <p>
 * 版本：1.0
 * <p>
 * 描述：
 * <p>
 * Created by AUTO-ROBOT on 2016/1/23.
 */
public class ImageHelper {

    private static ImageHelper instance = null;

    private ImageHelper() {
    }

    public static synchronized ImageHelper getInstance() {
        if (null == instance) {
            instance = new ImageHelper();
        }
        return instance;
    }
    public void dispaly(ImageView image,String uri,int width,int height){
        ImageOptions options = new ImageOptions.Builder()
                // 是否忽略GIF格式的图片
                .setIgnoreGif(false)
//                   图片缩放模式
//                  .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
//                   设置size属性, 不设置crop, 图片最大为size指定的宽高.避免发生异常导致图片重新载入
                .setSize(width, height)
//                   下载中显示的图片
//                  .setLoadingDrawableId(R.mipmap.ic_error)
//                   下载失败显示的图片
                .setFailureDrawableId(R.mipmap.ic_error)
//                   得到ImageOptions对象
                .setFadeIn(true)
                .build();
        x.image().bind(image, uri, options);
    }

    public void dispaly(ImageView image,String uri){
        ImageOptions options = new ImageOptions.Builder()
                // 是否忽略GIF格式的图片
                .setIgnoreGif(false)
//                   图片缩放模式
                .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
//                   下载中显示的图片
//                  .setLoadingDrawableId(R.mipmap.ic_error)
//                   下载失败显示的图片
                .setFailureDrawableId(R.mipmap.ic_error)
//                   得到ImageOptions对象
                .setFadeIn(true)
                .build();
        x.image().bind(image, uri, options);
    }

}
