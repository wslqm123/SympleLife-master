package com.github.symplelife.hodler;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.symplelife.R;
import com.github.symplelife.activity.ViewPagerActivity;
import com.github.symplelife.bean.funnyBean.GroupBean;
import com.github.symplelife.tools.UiUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import java.util.ArrayList;

/**
 * 版权归本人所有
 * <p>
 * 版本：1.0
 * <p>
 * 描述：
 * <p>
 * Created by AUTO-ROBOT on 2016/2/1.
 */
public class JokesImagesHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ImageView icon;
    private GridLayout gridLayout;
    private TextView name;
    private TextView content;
    private TextView digg;
    private TextView bury;
    private TextView comment;
    private TextView tag;
    private DisplayImageOptions options1;
    private DisplayImageOptions options2;


    private ImageView[] list;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private ImageView image6;
    private ImageView image7;
    private ImageView image8;
    private ImageView image9;

    private ArrayList<String> images;
    private boolean isgif;

    public JokesImagesHolder(View itemView) {
        super(itemView);
        icon = (ImageView) itemView.findViewById(R.id.icon);
        gridLayout = (GridLayout) itemView.findViewById(R.id.gl_multi_image);
        name = (TextView) itemView.findViewById(R.id.tv_name);
        content = (TextView) itemView.findViewById(R.id.tv_context);
        digg = (TextView) itemView.findViewById(R.id.tv_digg_count);
        bury = (TextView) itemView.findViewById(R.id.tv_bury_count);
        comment = (TextView) itemView.findViewById(R.id.tv_comment_count);
        tag = (TextView) itemView.findViewById(R.id.tv_tag);
        image1 = (ImageView) itemView.findViewById(R.id.iv_pic_1);
        image2 = (ImageView) itemView.findViewById(R.id.iv_pic_2);
        image3 = (ImageView) itemView.findViewById(R.id.iv_pic_3);
        image4 = (ImageView) itemView.findViewById(R.id.iv_pic_4);
        image5 = (ImageView) itemView.findViewById(R.id.iv_pic_5);
        image6 = (ImageView) itemView.findViewById(R.id.iv_pic_6);
        image7 = (ImageView) itemView.findViewById(R.id.iv_pic_7);
        image8 = (ImageView) itemView.findViewById(R.id.iv_pic_8);
        image9 = (ImageView) itemView.findViewById(R.id.iv_pic_9);

        list = new ImageView[]{image1 ,image2 ,image3 ,image4 ,image5 ,image6 ,image7 ,image8 ,image9};

        images = new ArrayList<>();

        for (int i = 0 ; i < 9 ; i++){
            list[i].setOnClickListener(this);
        }



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
                .showImageOnLoading(R.mipmap.ic_launcher)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();


    }

    public void setData(final GroupBean bean) {

        images.clear();
        isgif = bean.thumb_image_list.get(0).is_gif;

        ImageLoader.getInstance().displayImage(bean.user.avatar_url, icon, options1);
//        "http://p6.pstatp.com/" + bean.large_image.uri

        for (int i = 0 ; i < 9 ; i++){
            list[i].setVisibility(View.GONE);
        }

        for (int i = 0 ; i<bean.thumb_image_list.size(); i++){
            list[i].setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(bean.thumb_image_list.get(i).url, list[i], options2);

            images.add(i, bean.large_image_list.get(i).url);
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

    @Override
    public void onClick(View v) {

        Intent intent=new Intent(UiUtils.getContext(), ViewPagerActivity.class);
        intent.putStringArrayListExtra("images", images);
        intent.putExtra("isgif",isgif);

        switch (v.getId()){
            case R.id.iv_pic_1:
                intent.putExtra("position",0);
                UiUtils.startActivity(intent);
                break;
            case R.id.iv_pic_2:
                intent.putExtra("position",1);
                UiUtils.startActivity(intent);
                break;
            case R.id.iv_pic_3:
                intent.putExtra("position",2);
                UiUtils.startActivity(intent);
                break;
            case R.id.iv_pic_4:
                intent.putExtra("position",3);
                UiUtils.startActivity(intent);
                break;
            case R.id.iv_pic_5:
                intent.putExtra("position",4);
                UiUtils.startActivity(intent);
                break;
            case R.id.iv_pic_6:
                intent.putExtra("position",5);
                UiUtils.startActivity(intent);
                break;
            case R.id.iv_pic_7:
                intent.putExtra("position",6);
                UiUtils.startActivity(intent);
                break;
            case R.id.iv_pic_8:
                intent.putExtra("position",7);
                UiUtils.startActivity(intent);
                break;
            case R.id.iv_pic_9:
                intent.putExtra("position",8);
                UiUtils.startActivity(intent);
                break;

        }
    }
}
