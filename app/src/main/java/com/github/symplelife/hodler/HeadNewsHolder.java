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
public class HeadNewsHolder extends RecyclerView.ViewHolder{
    private ImageView icon;
    private TextView name;

    public HeadNewsHolder(View itemView) {
        super(itemView);
        icon = (ImageView) itemView.findViewById(R.id.icon);
        name = (TextView) itemView.findViewById(R.id.name);

    }

    public void setData(String imgsrc, String title) {

        x.image().bind(icon, imgsrc);
        name.setText(title);


    }
}
