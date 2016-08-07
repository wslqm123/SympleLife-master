package com.github.symplelife.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.symplelife.MainActivity;
import com.github.symplelife.R;
import com.github.symplelife.bean.ImagesListEntity;
import com.github.symplelife.common.Constants;
import com.github.symplelife.activity.ImagesDetailActivity;
import com.github.symplelife.view.PLAImageView;

import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;


public class StaggeredGridAdapter1 extends RecyclerView.Adapter<StaggeredGridAdapter1.ListHolder> {

    private Context context;
    private List<ImagesListEntity> imgList;
    private String keyWord;

    public StaggeredGridAdapter1(Context context, String keyWord, List<ImagesListEntity> imgList){
        this.context = context;
        this.imgList = imgList;
        this.keyWord = keyWord;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.staggered_grid_item, parent, false);
        return new ListHolder(view);
    }


    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        holder.setData(position);
        holder.pos = position;
        if (position == getItemCount()-2){
            LogUtil.e("加载更多数据");
//            HomeFragment.instantiate(context,).loadData(2);
            Intent intent = new Intent();
            intent.putExtra("keyWord",keyWord);
            intent.setAction(Constants.LOAD_MORE_IMAGE);
            context.sendBroadcast(intent);
        }
    }

    @Override
    public int getItemCount() {
//      百度图片API多返回了一个空参数
        return imgList.size()-1;
    }

    class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private PLAImageView icon;
        private ImageOptions options;
        private int pos;

        public ListHolder(View itemView) {
            super(itemView);
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


        public void setData(final int position){

            icon.setImageWidth(imgList.get(position).thumbnailWidth);
            icon.setImageHeight(imgList.get(position).thumbnailHeight);

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
            x.image().bind(icon, imgList.get(position).thumbnailUrl, options);


            //ImageLoader加载图片配置
            /*DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_stub)
                    .showImageOnFail(R.mipmap.ic_error)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .delayBeforeLoading(200)//载入图片前稍做延时可以提高整体滑动的流畅度
                    .build();
            ImageLoader.getInstance().displayImage(imageUrl, icon, options);*/

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ImagesDetailActivity.class);
            intent.putExtra("images", imgList.get(pos).thumbnailUrl);
            intent.putExtra("orignalImage", imgList.get(pos).imageUrl);
            int[] location = new int[2];
            v.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);

            intent.putExtra("width", v.getWidth());
            intent.putExtra("height", v.getHeight());
            intent.putExtra("orignalWidth", imgList.get(pos).thumbnailWidth);
            intent.putExtra("orignalHeight", imgList.get(pos).thumbnailHeight);
            context.startActivity(intent);
            ((MainActivity) context).overridePendingTransition(0, 0);
        }
    }
}
