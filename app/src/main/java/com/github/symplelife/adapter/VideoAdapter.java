package com.github.symplelife.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.symplelife.R;
import com.github.symplelife.bean.VideoListEntity;
import com.github.symplelife.common.Constants;
import com.github.symplelife.hodler.EyesVideoHolder;
import com.github.symplelife.panorama.MD360PlayerActivity;

import java.util.List;


public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int PANORAMA_VIEW = 0;


    Context context;
    List<VideoListEntity.VideoList> videoList;
    String keyWord;

    public VideoAdapter(Context context, String keyWord, List<VideoListEntity.VideoList> videoList) {
        this.context = context;
        this.keyWord = keyWord;
        this.videoList = videoList;
    }

    /**
     * 决定元素的布局使用哪种类型
     * @param position 数据源List的下标
     * @return 一个int型标志，传递给onCreateViewHolder的第二个参数
     */
    @Override
    public int getItemViewType(int position) {

        return PANORAMA_VIEW;

    }

    /**
     * 渲染具体的ViewHolder
     * @param parent ViewHolder的容器
     * @param viewType 一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_eyes_video, parent, false);
        return new EyesVideoHolder(view);

    }

    /**
     * 绑定ViewHolder的数据。
     * @param holder
     * @param position 数据源list的下标
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ((EyesVideoHolder)holder).setData(videoList.get(position).data.cover.detail,
                videoList.get(position).data.title ,videoList.get(position).data.category ,
                videoList.get(position).data.duration );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (keyWord.equals("全景视频")){
                    MD360PlayerActivity.startVideo(context, Uri.parse(videoList.get(position).data.playUrl),false);
                }else {
                    MD360PlayerActivity.startVideo(context, Uri.parse(videoList.get(position).data.playUrl),true);
                }

            }
        });

        if (position == videoList.size()-2){
            Intent intent = new Intent();
            intent.putExtra("keyWord",keyWord);
            intent.setAction(Constants.LOAD_MORE_NEWS);
            context.sendBroadcast(intent);
        }
    }


    @Override
    public int getItemCount() {
        return videoList.size();
   }

}
