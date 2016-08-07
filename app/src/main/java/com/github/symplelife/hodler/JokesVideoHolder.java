package com.github.symplelife.hodler;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.symplelife.R;
import com.github.symplelife.bean.funnyBean.GroupBean;
import com.github.symplelife.tools.UriHelper;
import com.github.symplelife.view.CustomVideoView;
import com.github.symplelife.view.PLAImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

/**
 * 版权归本人所有
 * <p>
 * 版本：1.0
 * <p>
 * 描述：
 * <p>
 * Created by AUTO-ROBOT on 2016/2/1.
 */
public class JokesVideoHolder extends RecyclerView.ViewHolder{
    private ImageView icon;
    private ImageView play;
    private PLAImageView mVideoView;
    private TextView name;
    private TextView content;
    private TextView digg;
    private TextView bury;
    private TextView comment;
    private TextView tag;
    private DisplayImageOptions options1;
    private DisplayImageOptions options2;

    private CustomVideoView customVideo;
    private ProgressBar pb;
//    private SeekBar seekBar;

    private Context mContext;

    public JokesVideoHolder(Context context ,View itemView) {
        super(itemView);
        mContext = context ;
        icon = (ImageView) itemView.findViewById(R.id.icon);
        play = (ImageView) itemView.findViewById(R.id.iv_play);
        mVideoView = (PLAImageView) itemView.findViewById(R.id.vv_video);
        name = (TextView) itemView.findViewById(R.id.tv_name);
        content = (TextView) itemView.findViewById(R.id.tv_context);
        digg = (TextView) itemView.findViewById(R.id.tv_digg_count);
        bury = (TextView) itemView.findViewById(R.id.tv_bury_count);
        comment = (TextView) itemView.findViewById(R.id.tv_comment_count);
        tag = (TextView) itemView.findViewById(R.id.tv_tag);

        customVideo = (CustomVideoView) itemView.findViewById(R.id.vv_videoview);
        pb = (ProgressBar) itemView.findViewById(R.id.pb_loading);
//        seekBar = (SeekBar) itemView.findViewById(R.id.seekBar);

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

        ImageLoader.getInstance().displayImage(bean.user.avatar_url, icon , options1);

        mVideoView.setImageWidth(bean.video_width);
        mVideoView.setImageHeight(bean.video_height);

        customVideo.setVideoWidth(bean.video_width);
        customVideo.setVideoHeight(bean.video_height);

        play.setVisibility(View.VISIBLE);
        pb.setVisibility(View.GONE);
        customVideo.setVisibility(View.INVISIBLE);

//        RelativeLayout.LayoutParams lp=new  RelativeLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        float scale = 1020/bean.video_width;
//        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
//        customVideo.setLayoutParams(lp);

        ImageLoader.getInstance().displayImage("http://p6.pstatp.com/" + bean.large_cover.uri, mVideoView, options2);

        Uri uri = Uri.parse(UriHelper.getInstance().getFunnyVideo(bean.uri));
        customVideo.setVideoURI(uri);
        customVideo.setMediaController(new MediaController(mContext , false));

        mVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, VideoViewActivity.class);
//                intent.putExtra("uri",bean.uri);
//                intent.putExtra("title",bean.content);
//                mContext.startActivity(intent);

//                if (customVideo.isPlaying()){
//                    customVideo.pause();
//                }else {
//                    customVideo.start();
//                }

                if (customVideo.getVisibility() == View.INVISIBLE){
                    play.setVisibility(View.INVISIBLE);
                    customVideo.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.VISIBLE);

//                seekBar.setVisibility(View.VISIBLE);
                    customVideo.start();

                    // 当视频准备完成时设置进度条最大值
                    customVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {

                            pb.setVisibility(View.GONE);
                            // 按照初始位置播放
                            customVideo.seekTo(0);
                            // 设置进度条的最大进度为视频流的最大播放时长
//                        seekBar.setMax(customVideo.getDuration());

                        }
                    });
                }

            }
        });

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



}
