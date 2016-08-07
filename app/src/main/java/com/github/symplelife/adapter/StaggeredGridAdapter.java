package com.github.symplelife.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.symplelife.R;
import com.github.symplelife.bean.ImagesListEntity;
import com.github.symplelife.common.Constants;
import com.github.symplelife.hodler.StaggerdImageHolder;

import org.xutils.common.util.LogUtil;

import java.util.List;


public class StaggeredGridAdapter extends RecyclerView.Adapter<StaggerdImageHolder> {

    private Context context;
    private List<ImagesListEntity> imgList;
    private String keyWord;

    public StaggeredGridAdapter(Context context,String keyWord,List<ImagesListEntity> imgList){
        this.context = context;
        this.imgList = imgList;
        this.keyWord = keyWord;
    }

    @Override
    public StaggerdImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.staggered_grid_item, parent, false);
        return new StaggerdImageHolder(context , view);
    }

    @Override
    public void onBindViewHolder(StaggerdImageHolder holder, int position) {
        holder.setData(imgList.get(position));

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ImagesDetailActivity.class);
//                intent.putExtra("images", imgList.get(position).thumbnailUrl);
//                intent.putExtra("orignalImage", imgList.get(position).imageUrl);
//                int[] location = new int[2];
//                v.getLocationOnScreen(location);
//                intent.putExtra("locationX", location[0]);
//                intent.putExtra("locationY", location[1]);
//
//                intent.putExtra("width", v.getWidth());
//                intent.putExtra("height", v.getHeight());
//                intent.putExtra("orignalWidth", imgList.get(position).thumbnailWidth);
//                intent.putExtra("orignalHeight", imgList.get(position).thumbnailHeight);
//                context.startActivity(intent);
//                ((MainActivity) context).overridePendingTransition(0, 0);
//            }
//        });

        if (position == getItemCount()-2){
            LogUtil.e("加载更多数据");
//            HomeFragment.instantiate(context,).loadData(2);
            Intent intent = new Intent();
            intent.putExtra("keyWord",keyWord);
            intent.setAction(Constants.LOAD_MORE_IMAGE);
            context.sendBroadcast(intent);
        }
    }


    @Override
    public int getItemCount() {
//      百度图片API多返回了一个空参数
        return imgList.size()-1;
    }

}
