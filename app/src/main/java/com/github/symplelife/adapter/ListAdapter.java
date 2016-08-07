package com.github.symplelife.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.github.symplelife.R;
import com.github.symplelife.bean.NewsEntity;
import com.github.symplelife.activity.NewsDetailActivity;
import com.github.symplelife.common.Constants;
import com.github.symplelife.hodler.HeadNewsHolder;
import com.github.symplelife.hodler.CarouselPictureHolder;
import com.github.symplelife.hodler.MultiNewsHolder;
import com.github.symplelife.hodler.NormalNewsHolder;
import com.github.symplelife.hodler.SpecialNewsHolder;
import com.github.symplelife.tools.UriHelper;

import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEAD_VIEW = 0;
    private static final int NORMAL_VIEW = 1;
    private static final int MULTI_PICTURE = 2;
    private static final int SPECIAL_VIEW = 3;
    private static final int HEAD_VIEW_ADS = 4;

    Context context;
    List<NewsEntity> newsList;
    String keyWord;

    public ListAdapter(Context context, String keyWord,List<NewsEntity> newsList) {
        this.context = context;
        this.keyWord = keyWord;
        this.newsList = newsList;
    }

    /**
     * 决定元素的布局使用哪种类型
     * @param position 数据源List的下标
     * @return 一个int型标志，传递给onCreateViewHolder的第二个参数
     */
    @Override
    public int getItemViewType(int position) {

        if (position == 0){
            if (keyWord.equals("头条")){
//            if (newsList.get(0).ads!=null && newsList.get(0).ads.size()>1 ){
                return HEAD_VIEW_ADS;
            }else {
                return HEAD_VIEW;
            }
//            return HEAD_VIEW;
        }else if (newsList.get(position).specialextra!= null ){
            return SPECIAL_VIEW;
        }else if (newsList.get(position).imgextra!=null){
            return MULTI_PICTURE;
        }else {
            return NORMAL_VIEW;
        }
    }

    /**
     * 渲染具体的ViewHolder
     * @param parent ViewHolder的容器
     * @param viewType 一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == HEAD_VIEW){
//          只有parent不为空，attchToRoot为false时，布局参数才生效，否则都是包裹内容
            View view = LayoutInflater.from(context).inflate(R.layout.list_item_head, parent, false);
            return new HeadNewsHolder(view);
//        }else if(viewType == HEAD_VIEW_ADS){
//            View view = LayoutInflater.from(context).inflate(R.layout.list_item_flipper, parent, false);
//            return new FlipperImageHolder(context,view);
        }else if(viewType == HEAD_VIEW_ADS){
            View view = LayoutInflater.from(context).inflate(R.layout.list_item_ads, parent, false);
            return new CarouselPictureHolder(context,view);
        }else if (viewType == MULTI_PICTURE){
            View view = LayoutInflater.from(context).inflate(R.layout.list_item_multi_news, parent, false);
            return new MultiNewsHolder(view);
        }else if (viewType == SPECIAL_VIEW){
            View view = LayoutInflater.from(context).inflate(R.layout.list_item_special, parent, false);
            return new SpecialNewsHolder(context,view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.list_item_normal, parent, false);
            return new NormalNewsHolder(view);
        }

    }

    /**
     * 绑定ViewHolder的数据。
     * @param holder
     * @param position 数据源list的下标
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == MULTI_PICTURE ){
            ((MultiNewsHolder)holder).setData(newsList.get(position).imgsrc,newsList.get(position).imgextra.get(0).imgsrc,
                    newsList.get(position).imgextra.get(1).imgsrc,newsList.get(position).title);

        }else if (getItemViewType(position) == HEAD_VIEW){
            ((HeadNewsHolder)holder).setData(newsList.get(position).imgsrc,newsList.get(position).title);

//        }else if(getItemViewType(position) == HEAD_VIEW_ADS){
//            ((FlipperImageHolder)holder).setData(newsList.get(position).ads);

        }else if(getItemViewType(position) == HEAD_VIEW_ADS){

            ((CarouselPictureHolder)holder).setData(newsList.get(position).ads);
        }else if (getItemViewType(position) ==SPECIAL_VIEW){
            ((SpecialNewsHolder)holder).setData(newsList.get(position).imgsrc,newsList.get(position).specialextra);

        }else {
            ((NormalNewsHolder)holder).setData(newsList.get(position).imgsrc,newsList.get(position).title,
                    newsList.get(position).digest,newsList.get(position).TAG);
        }

        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(newsList.get(position).live_info!=null){
                    intent.putExtra("网址", UriHelper.getInstance().getLiveNewsUrl(newsList.get(position).live_info.roomId));
                }else if (TextUtils.isEmpty(newsList.get(position).photosetID)){
                    String docID;
                    if (newsList.get(position).specialextra !=null){
                        docID = newsList.get(position).specialextra.get(0).docid;
                    }else {
                        docID = newsList.get(position).docid;
                    }
                    intent.putExtra("docid",docID);
                    intent.putExtra("网址", UriHelper.getInstance().getCommonNewsUrl(docID));
                }else {
                    String setid = (newsList.get(position).photosetID).split("[|]")[1];
                    String channelid = (newsList.get(position).photosetID).substring(4, 8);
                    intent.putExtra("docid",newsList.get(position).postid);
                    intent.putExtra("网址", UriHelper.getInstance().getPhotoNewsUrl(setid,channelid));
                }
                intent.putExtra("title",newsList.get(position).title);
                intent.setClass(context, NewsDetailActivity.class);
                context.startActivity(intent);
            }
        });

//        排除轻松时刻
        if (getItemCount()>=20 && position == getItemCount()-2){
            Intent intent = new Intent();
            intent.putExtra("keyWord",keyWord);
            intent.setAction(Constants.LOAD_MORE_NEWS);
            context.sendBroadcast(intent);
        }

    }

//    @Override
//    public void onBindViewHolder(ListHolder holder, int position) {
//        holder.position = position;
//        holder.setData(position);
//    }

    @Override
    public int getItemCount() {
        return newsList.size();
   }

}
