package com.github.symplelife.hodler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.symplelife.R;

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
public class MultiNewsHolder extends RecyclerView.ViewHolder{
    private ImageView image01;
    private ImageView image02;
    private ImageView image03;
    private TextView name;

    public MultiNewsHolder(View itemView) {
        super(itemView);
        image01 = (ImageView) itemView.findViewById(R.id.image_01);
        image02 = (ImageView) itemView.findViewById(R.id.image_02);
        image03 = (ImageView) itemView.findViewById(R.id.image_03);
        name = (TextView) itemView.findViewById(R.id.name);

    }

    public void setData(String imgsrc,String imgsrc_1,String imgsrc_2,String title) {
        x.image().bind(image01, imgsrc);
        x.image().bind(image02, imgsrc_1);
        x.image().bind(image03, imgsrc_2);
        name.setText(title);
    }
}
