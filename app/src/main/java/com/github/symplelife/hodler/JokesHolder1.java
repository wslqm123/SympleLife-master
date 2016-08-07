package com.github.symplelife.hodler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.symplelife.R;
import com.github.symplelife.bean.funnyBean.GroupBean;
import com.github.symplelife.view.PLAImageView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * 版权归本人所有
 * <p>
 * 版本：1.0
 * <p>
 * 描述：
 * <p>
 * Created by AUTO-ROBOT on 2016/2/1.
 */
public class JokesHolder1 extends RecyclerView.ViewHolder{
    private ImageView icon;
    private PLAImageView pic;
    private TextView name;
    private TextView content;
    private TextView digg;
    private TextView bury;
    private TextView comment;
    private TextView tag;
    private TextView detail;
    private ImageOptions options;
    private ImageOptions options1;

    public JokesHolder1(View itemView) {
        super(itemView);
        icon = (ImageView) itemView.findViewById(R.id.icon);
        pic = (PLAImageView) itemView.findViewById(R.id.iv_pic);
        name = (TextView) itemView.findViewById(R.id.tv_name);
        content = (TextView) itemView.findViewById(R.id.tv_context);
        digg = (TextView) itemView.findViewById(R.id.tv_digg_count);
        bury = (TextView) itemView.findViewById(R.id.tv_bury_count);
        comment = (TextView) itemView.findViewById(R.id.tv_comment_count);
        tag = (TextView) itemView.findViewById(R.id.tv_tag);
        detail = (TextView) itemView.findViewById(R.id.tv_detail);

        options = new ImageOptions.Builder()
//                   下载失败显示的图片
                .setFailureDrawableId(R.mipmap.ic_error)
//                   得到ImageOptions对象
                .setFadeIn(true)
                .setCircular(true)
                .build();

        options1 = new ImageOptions.Builder()
                // 是否忽略GIF格式的图片
                .setIgnoreGif(false)
//                   下载失败显示的图片
                .setFailureDrawableId(R.mipmap.ic_error)
//                   得到ImageOptions对象
                .setFadeIn(true)
                .build();

    }

    public void setData(GroupBean bean) {

        x.image().bind(icon, bean.user.avatar_url,options);
        if (bean.large_image!=null){
            pic.setVisibility(View.VISIBLE);
            if (bean.large_image.r_height>(bean.large_image.r_width*2)){
                detail.setVisibility(View.VISIBLE);
                pic.setImageWidth(bean.large_image.r_width);
                pic.setImageHeight(bean.large_image.r_width);
            }else {
                detail.setVisibility(View.INVISIBLE);
                pic.setImageWidth(bean.large_image.r_width);
                pic.setImageHeight(bean.large_image.r_height);
            }

            x.image().bind(pic, "http://p6.pstatp.com/"+bean.large_image.uri, options1);
        }else {
            pic.setVisibility(View.GONE);
        }
        name.setText(bean.user.name);
        content.setText(bean.content);
        digg.setText(bean.digg_count);
        bury.setText(bean.bury_count);
        comment.setText(bean.comment_count);
        tag.setText(bean.category_name);


    }
}
