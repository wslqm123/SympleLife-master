package com.github.symplelife.hodler;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.symplelife.R;
import com.github.symplelife.bean.Special;
import com.github.symplelife.activity.NewsDetailActivity;
import com.github.symplelife.tools.UiUtils;
import com.github.symplelife.tools.UriHelper;

import org.xutils.x;

import java.util.List;

/**
 * 版权归本人所有
 * <p>
 * 版本：1.0
 * <p>
 * 描述：
 * <p>
 * Created by AUTO-ROBOT on 2016/2/1.
 */
public class SpecialNewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Context context;
    private List<Special> specialextra;

    private ImageView special;
    private ImageView icon;
    private ImageView icon_1;
    private ImageView icon_2;
    private TextView name;
    private TextView name_1;
    private TextView name_2;
    private TextView subName;
    private TextView subName_1;
    private TextView subName_2;
    private TextView tag;
    private TextView tag_1;
    private TextView tag_2;

    private RelativeLayout content;
    private RelativeLayout content_1;
    private RelativeLayout content_2;

    public SpecialNewsHolder(Context context,View itemView) {
        super(itemView);
        this.context = context;
        special = (ImageView) itemView.findViewById(R.id.iv_special);
        content = (RelativeLayout) itemView.findViewById(R.id.rl_news_content);
        content_1 = (RelativeLayout) itemView.findViewById(R.id.rl_news_content_1);
        content_2 = (RelativeLayout) itemView.findViewById(R.id.rl_news_content_2);
        content.setOnClickListener(this);
        content_1.setOnClickListener(this);
        content_2.setOnClickListener(this);

        icon = (ImageView) itemView.findViewById(R.id.icon);
        icon_1 = (ImageView) itemView.findViewById(R.id.icon_1);
        icon_2 = (ImageView) itemView.findViewById(R.id.icon_2);
        name = (TextView) itemView.findViewById(R.id.name);
        name_1 = (TextView) itemView.findViewById(R.id.name_1);
        name_2 = (TextView) itemView.findViewById(R.id.name_2);
        subName = (TextView) itemView.findViewById(R.id.subname);
        subName_1 = (TextView) itemView.findViewById(R.id.subname_1);
        subName_2 = (TextView) itemView.findViewById(R.id.subname_2);
        tag = (TextView) itemView.findViewById(R.id.tv_tag);
        tag_1 = (TextView) itemView.findViewById(R.id.tv_tag_1);
        tag_2 = (TextView) itemView.findViewById(R.id.tv_tag_2);

    }

    public void setData(String imgsrc,List<Special> specialextra) {
        this.specialextra = specialextra;

        x.image().bind(special, imgsrc);
        x.image().bind(icon, specialextra.get(0).imgsrc);
        x.image().bind(icon_1, specialextra.get(1).imgsrc);
        x.image().bind(icon_2, specialextra.get(2).imgsrc);
        name.setText(specialextra.get(0).title);
        name_1.setText(specialextra.get(1).title);
        name_2.setText(specialextra.get(2).title);
        subName.setText(UiUtils.ToDBC(specialextra.get(0).digest));
        subName_1.setText(UiUtils.ToDBC(specialextra.get(1).digest));
        subName_2.setText(UiUtils.ToDBC(specialextra.get(2).digest));

        if (TextUtils.isEmpty(specialextra.get(0).TAG)){
            tag.setVisibility(View.INVISIBLE);
            tag_1.setVisibility(View.INVISIBLE);
            tag_2.setVisibility(View.INVISIBLE);
        }else {
            tag.setVisibility(View.VISIBLE);
            tag_1.setVisibility(View.VISIBLE);
            tag_2.setVisibility(View.VISIBLE);
            tag.setText(specialextra.get(0).TAG);
            tag_1.setText(specialextra.get(0).TAG);
            tag_2.setText(specialextra.get(0).TAG);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        String docID;
        switch (v.getId()){
            case R.id.rl_news_content:
                docID = specialextra.get(0).docid;
                intent.putExtra("title",specialextra.get(0).title);
                break;
            case R.id.rl_news_content_1:
                docID = specialextra.get(1).docid;
                intent.putExtra("title",specialextra.get(1).title);
                break;
            case R.id.rl_news_content_2:
                docID = specialextra.get(2).docid;
                intent.putExtra("title",specialextra.get(2).title);
                break;
            default:
                docID = specialextra.get(0).docid;
                intent.putExtra("title",specialextra.get(0).title);
                break;
        }
        intent.putExtra("docid",docID);
        intent.putExtra("网址", UriHelper.getInstance().getCommonNewsUrl(docID));
        intent.setClass(context, NewsDetailActivity.class);
        context.startActivity(intent);
    }
}

