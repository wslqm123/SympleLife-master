package com.github.symplelife.hodler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.symplelife.R;
import com.github.symplelife.tools.TimeUtil;

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
public class EyesVideoHolder extends RecyclerView.ViewHolder{
    private ImageView icon;
    private TextView videoTitle;
    private TextView videoInfo;

    public EyesVideoHolder(View itemView) {
        super(itemView);
        icon = (ImageView) itemView.findViewById(R.id.icon);
        videoTitle = (TextView) itemView.findViewById(R.id.tv_title);
        videoInfo = (TextView) itemView.findViewById(R.id.tv_video_info);

    }

    public void setData(String imgsrc, String title , String category , int duration) {

        x.image().bind(icon, imgsrc);
        videoTitle.setText(title);
        videoInfo.setText("# " + category + "  /  " + TimeUtil.second2Min(duration));
    }
}
