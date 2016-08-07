package com.github.symplelife.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.symplelife.R;
import com.github.symplelife.activity.EssenceActivity;
import com.github.symplelife.bean.funnyBean.FunnyEntry;
import com.github.symplelife.common.Constants;
import com.github.symplelife.hodler.NormalNewsHolder;
import com.github.symplelife.tools.UriHelper;

import java.util.List;


public class EssenceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ESSENCE_VIEW = 0;


    Context context;
    List<FunnyEntry.Essence> funnyList;
    String keyWord;

    public
    EssenceAdapter(Context context, String keyWord, List<FunnyEntry.Essence> funnyList) {
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

        return ESSENCE_VIEW;

    }

    /**
     * 渲染具体的ViewHolder
     * @param parent ViewHolder的容器
     * @param viewType 一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_normal, parent, false);
        return new NormalNewsHolder(view);

    }

    /**
     * 绑定ViewHolder的数据。
     * @param holder
     * @param position 数据源list的下标
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ((NormalNewsHolder)holder).setData(funnyList.get(position).image_url,"" , funnyList.get(position).title,"");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,EssenceActivity.class);
                intent.putExtra("url",  UriHelper.getInstance().getEssenceUrl(
                        funnyList.get(position).group_id, funnyList.get(position).item_id));
//                intent.putExtra("url", VideoList.get(position).article_url);
                intent.putExtra("title",funnyList.get(position).title);
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
