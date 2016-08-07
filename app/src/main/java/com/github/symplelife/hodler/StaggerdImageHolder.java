package com.github.symplelife.hodler;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.symplelife.MainActivity;
import com.github.symplelife.R;
import com.github.symplelife.bean.ImagesListEntity;
import com.github.symplelife.activity.ImagesDetailActivity;
import com.github.symplelife.view.PLAImageView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * 版权归本人所有
 * <p/>
 * 版本：1.0
 * <p/>
 * 描述：
 * <p/>
 * Created by AUTO-ROBOT on 2016/2/17.
 */
public class StaggerdImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private PLAImageView icon;
    private ImageOptions options;
    private ImagesListEntity entry;
    private Context mContext;

    public StaggerdImageHolder(Context context,View itemView) {
        super(itemView);
        mContext = context;
        icon = (PLAImageView) itemView.findViewById(R.id.pic);
        icon.setOnClickListener(this);
        options = new ImageOptions.Builder()
                // 是否忽略GIF格式的图片
                .setIgnoreGif(false)
//                   下载失败显示的图片
                .setFailureDrawableId(R.mipmap.ic_error)
//                   得到ImageOptions对象
                .setFadeIn(true)
                .build();
    }


    public void setData(ImagesListEntity entry){

        this.entry = entry;
        icon.setImageWidth(entry.thumbnailWidth);
        icon.setImageHeight(entry.thumbnailHeight);

           /* options = new ImageOptions.Builder()
                    // 是否忽略GIF格式的图片
                    .setIgnoreGif(false)
//                   图片缩放模式
//                  .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
//                   设置size属性, 不设置crop, 图片最大为size指定的宽高.避免发生异常导致图片重新载入
                    .setSize(imgList.get(position).thumbnailWidth, imgList.get(position).thumbnailHeight)
//                   下载中显示的图片
//                  .setLoadingDrawableId(R.mipmap.ic_error)
//                   下载失败显示的图片
                    .setFailureDrawableId(R.mipmap.ic_error)
//                   得到ImageOptions对象
                    .setFadeIn(true)
                    .build();*/
        x.image().bind(icon, entry.thumbnailUrl, options);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, ImagesDetailActivity.class);
        intent.putExtra("images", entry.thumbnailUrl);
        intent.putExtra("orignalImage", entry.imageUrl);
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        intent.putExtra("locationX", location[0]);
        intent.putExtra("locationY", location[1]);

        intent.putExtra("width", v.getWidth());
        intent.putExtra("height", v.getHeight());
        intent.putExtra("orignalWidth", entry.thumbnailWidth);
        intent.putExtra("orignalHeight", entry.thumbnailHeight);
        mContext.startActivity(intent);
        ((MainActivity) mContext).overridePendingTransition(0, 0);
    }
}
