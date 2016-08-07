package com.github.symplelife.hodler;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.symplelife.R;
import com.github.symplelife.tools.UiUtils;

import org.xutils.x;

/**
 * 版权归本人所有
 * <p/>
 * 版本：1.0
 * <p/>
 * 描述：
 * <p/>
 * Created by AUTO-ROBOT on 2016/2/1.
 */
public class NormalNewsHolder extends RecyclerView.ViewHolder {
    private ImageView icon;
    private TextView name;
    private TextView subName;
    private TextView tag;

    public NormalNewsHolder(View itemView) {
        super(itemView);
        icon = (ImageView) itemView.findViewById(R.id.icon);
        name = (TextView) itemView.findViewById(R.id.name);
        subName = (TextView) itemView.findViewById(R.id.subname);
        tag = (TextView) itemView.findViewById(R.id.tv_tag);

    }

    public void setData(String imgUrl,String title,String digest,String TAG) {

        x.image().bind(icon, imgUrl);
        name.setText(title);
        //          避免因末尾符号自动换行
        subName.setText(UiUtils.ToDBC(digest));

//            subName.setText(newsList.get(position).digest);

        if (TextUtils.isEmpty(TAG)){
            tag.setVisibility(View.INVISIBLE);
        }else {
            tag.setVisibility(View.VISIBLE);
            tag.setText(TAG);
        }
    }
}
