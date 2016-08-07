package com.github.symplelife.hodler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.github.symplelife.R;
import com.github.symplelife.bean.PhotoSet;

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
public class FlipperImageHolder extends RecyclerView.ViewHolder implements View.OnTouchListener{
    private ViewFlipper flipper;
    private Context context;

    public FlipperImageHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        flipper = (ViewFlipper) itemView.findViewById(R.id.flipper);

    }

    public void setData(List<PhotoSet> ads) {

        for (int i = 0; i < ads.size(); i++) {
            flipper.addView(getContentView(ads.get(i)));
        }
        flipper.setInAnimation(context, R.anim.left_in);
        flipper.setOutAnimation(context, R.anim.left_out);
        flipper.setFlipInterval(5000);
        flipper.startFlipping();

    }

    private View view;
    private ImageView image;
    private TextView title;
    private View getContentView(PhotoSet sets) {
        view = View.inflate(context,R.layout.flipper_contain_view,null);
        image = (ImageView) view.findViewById(R.id.icon);
        title = (TextView) view.findViewById(R.id.name);
        x.image().bind(image, sets.imgsrc);
        title.setText(sets.title);
        return view;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
