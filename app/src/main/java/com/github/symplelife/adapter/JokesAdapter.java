package com.github.symplelife.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.symplelife.R;
import com.github.symplelife.api.ApiConstants;
import com.github.symplelife.bean.funnyBean.JokesEntry;
import com.github.symplelife.activity.EssenceActivity;
import com.github.symplelife.common.Constants;
import com.github.symplelife.hodler.HeadNewsHolder;
import com.github.symplelife.hodler.JokesHolder;
import com.github.symplelife.hodler.JokesImagesHolder;
import com.github.symplelife.hodler.JokesVideoHolder;

import java.util.List;


public class JokesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int PICTURE_VIEW = 0;
    private static final int SUGGEST_VIEW = 1;
    private static final int VIDEO_VIEW = 2;
    private static final int MULTI_IMAGE = 3;


    Context context;
    List<JokesEntry.JokesContent> funnyList;
    String keyWord;

    public JokesAdapter(Context context, String keyWord, List<JokesEntry.JokesContent> funnyList) {
        this.context = context;
        this.keyWord = keyWord;
        this.funnyList = funnyList;
    }

    /**
     * 决定元素的布局使用哪种类型
     * @param position 数据源List的下标
     * @return 一个int型标志，传递给onCreateViewHolder的第二个参数
     */
    @Override
    public int getItemViewType(int position) {

        if (funnyList.get(position).group.type == 2){
            return VIDEO_VIEW;
        }else if (funnyList.get(position).group.type == 4){
            return SUGGEST_VIEW;
        }else if (funnyList.get(position).group.type == 5){
            return MULTI_IMAGE;
        }
        return PICTURE_VIEW;

    }

    /**
     * 渲染具体的ViewHolder
     * @param parent ViewHolder的容器
     * @param viewType 一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIDEO_VIEW){
            view = LayoutInflater.from(context).inflate(R.layout.list_funny_video, parent, false);
            return new JokesVideoHolder(context,view);
        }else if (viewType == SUGGEST_VIEW) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_head, parent, false);
            return new HeadNewsHolder(view);
        }else if (viewType == MULTI_IMAGE){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_multi_image, parent, false);
            return new JokesImagesHolder(view);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_funny, parent, false);
            return new JokesHolder(view);
        }


    }

    /**
     * 绑定ViewHolder的数据。
     * @param holder
     * @param position 数据源list的下标
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (getItemViewType(position) == VIDEO_VIEW){

            ((JokesVideoHolder)holder).setData(funnyList.get(position).group);
        }else if (getItemViewType(position) == SUGGEST_VIEW){

            String imageUrl = "http://p6.pstatp.com/"+funnyList.get(position).group.large_image.uri;
            ((HeadNewsHolder) holder).setData(imageUrl , funnyList.get(position).group.title);
        }else if (getItemViewType(position) == MULTI_IMAGE){

            ((JokesImagesHolder)holder).setData(funnyList.get(position).group);
        }else {

            ((JokesHolder) holder).setData(funnyList.get(position).group);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,EssenceActivity.class);
                if (getItemViewType(position) == SUGGEST_VIEW){
                    intent.putExtra("url",  funnyList.get(position).group.open_url);
                    intent.putExtra("title", funnyList.get(position).group.title);
                }else {
                    intent.putExtra("url",  ApiConstants.JOKES_DETAIL+funnyList.get(position).group.group_id);
                    intent.putExtra("title","详情");
                }
                context.startActivity(intent);
            }
        });

            if (position == funnyList.size()-2){
            Intent intent = new Intent();
            intent.putExtra("keyWord",keyWord);
            intent.setAction(Constants.LOAD_MORE_NEWS);
            context.sendBroadcast(intent);
        }


    }


    @Override
    public int getItemCount() {
        return funnyList.size();
   }

}
