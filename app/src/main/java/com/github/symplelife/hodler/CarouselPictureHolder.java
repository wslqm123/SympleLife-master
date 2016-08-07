package com.github.symplelife.hodler;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.symplelife.R;
import com.github.symplelife.bean.PhotoSet;
import com.github.symplelife.activity.NewsDetailActivity;
import com.github.symplelife.tools.UiUtils;
import com.github.symplelife.tools.UriHelper;

import org.xutils.x;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 版权归本人所有
 * <p/>
 * 版本：1.0
 * <p/>
 * 描述：
 * <p/>
 * Created by AUTO-ROBOT on 2015/10/28.
 */
public class CarouselPictureHolder extends RecyclerView.ViewHolder implements ViewPager.OnPageChangeListener,OnTouchListener{

    private ViewPager viewPager;
    private LinearLayout dotLayout;
    //放圆点的View的list
    private List<ImageView> dotViewsList;
    private List<PhotoSet> datas;
    private Context context;
    private boolean hasSet;

    public CarouselPictureHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        viewPager = (ViewPager) itemView.findViewById(R.id.viewPager);
        dotLayout = (LinearLayout)itemView.findViewById(R.id.dotLayout);
        dotViewsList = new ArrayList<ImageView>();
        viewPager.setAdapter(new HomeAdapter());
        viewPager.addOnPageChangeListener(this);
        viewPager.setOnTouchListener(this);
        runTask = new AuToRunTask();
    }

    public void setData(final List<PhotoSet> datas) {
        this.datas = datas;

        if (!hasSet){
            for (int i = 0; i<datas.size() ; i++){
                ImageView dotView =  new ImageView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 4;
                params.rightMargin = 4;
                dotLayout.addView(dotView, params);
                dotViewsList.add(dotView);
            }


            viewPager.setCurrentItem(1000 * datas.size());

            if (datas.size()>1){
                runTask.start();
            }else {
                runTask.stop();
            }
        }

        hasSet = true;

    }

    boolean flag;
    private AuToRunTask runTask;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotViewsList.size(); i++) {
            if (i == position % datas.size()) {
                dotViewsList.get(position % datas.size()).setBackgroundResource(R.mipmap.dot_2);
            } else {
                dotViewsList.get(i).setBackgroundResource(R.mipmap.dot_1);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                runTask.stop();
                break;
            case MotionEvent.ACTION_CANCEL:  // 事件的取消
            case MotionEvent.ACTION_UP:
                runTask.start();
                break;
        }

        return false; // viewPager 触摸事件 返回值要是false
    }

    public class AuToRunTask implements Runnable {

        @Override
        public void run() {
            if(flag){
                UiUtils.cancel(this);  // 取消之前
                int currentItem = viewPager.getCurrentItem();
                currentItem++;
                viewPager.setCurrentItem(currentItem);
                //  延迟执行当前的任务
                UiUtils.postDelayed(this, 5000);// 递归调用
            }
        }
        public void start(){
            if(!flag){
                UiUtils.cancel(this);  // 取消之前
                flag=true;
                UiUtils.postDelayed(this, 5000);// 递归调用
            }
        }
        public  void stop(){
            if(flag){
                flag=false;
                UiUtils.cancel(this);
            }
        }

    }

    class HomeAdapter extends PagerAdapter{
        LinkedList<View> convertView = new LinkedList<View>();
        ImageView image;
        TextView title;


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            convertView.add(view);
            container.removeView(view);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final int index = position%datas.size();
            View view;
            if (convertView.size()>0){
                view = convertView.remove(0);
            }else {
//                view = new ImageView(context);
                view = View.inflate(context,R.layout.carousel_picture_view,null);
            }
            image = (ImageView) view.findViewById(R.id.icon);
            title = (TextView) view.findViewById(R.id.name);

            x.image().bind(image,datas.get(index).imgsrc);
            title.setText(datas.get(index).title);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    if (datas.get(index).url.contains("|")){
                        String setid = (datas.get(index).url).split("[|]")[1];
                        String channelid = (datas.get(index).url).substring(4, 8);
                        intent.putExtra("网址", UriHelper.getInstance().getPhotoNewsUrl(setid,channelid));
                    }else {

                        intent.putExtra("docid",datas.get(index).url);
                        intent.putExtra("网址", UriHelper.getInstance().getCommonNewsUrl(datas.get(index).url));
                    }

                    intent.putExtra("title",datas.get(index).title);
                    intent.setClass(context, NewsDetailActivity.class);
                    context.startActivity(intent);
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

}
