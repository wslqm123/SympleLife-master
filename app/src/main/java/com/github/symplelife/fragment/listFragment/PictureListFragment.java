package com.github.symplelife.fragment.listFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.symplelife.R;
import com.github.symplelife.adapter.StaggeredGridAdapter;
import com.github.symplelife.bean.ImagesListEntity;
import com.github.symplelife.bean.ResponseImagesListEntity;
import com.github.symplelife.common.Constants;
import com.github.symplelife.tools.GsonUtils;
import com.github.symplelife.tools.UriHelper;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 版权归本人所有
 * <p/>
 * 版本：1.0
 * <p/>
 * 描述：
 * <p/>
 * Created by AUTO-ROBOT on 2016/1/8.
 */
public class PictureListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private static int FIRST_LOAD = 0;
    private static int RE_LOAD = 1;
    private static int LOAD_MORE = 2;

    private LoadMoreReceiver loadMoreReceiver;

    private List<ImagesListEntity> imgList = new ArrayList<>();
    private StaggeredGridAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private int page = 1;
    private String keyWord;

    /** 是否已被加载过一次，第二次就不再去请求数据了 */
    protected boolean mHasLoadedOnce;

    /** Fragment当前状态是否可见 */
    protected boolean isVisible;

    public static PictureListFragment newInstance(String keyWord)
    {
        PictureListFragment f = new PictureListFragment();
        Bundle bdl = new Bundle();
        bdl.putString("keyWord", keyWord);
        f.setArguments(bdl);
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 动态注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.LOAD_MORE_IMAGE);
        loadMoreReceiver = new LoadMoreReceiver();
        getActivity().registerReceiver(loadMoreReceiver, filter);

        keyWord = getArguments().getString("keyWord");
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.widget_layout, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setProgressBackgroundColorSchemeResource(R.color.refresh_bg);

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

//        init();
        adapter = new StaggeredGridAdapter(getActivity(),keyWord, imgList);
        recyclerView.setAdapter(adapter);

        return view;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }

    }

    protected class LoadMoreReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            loadData(intent.getStringExtra("keyWord"));
        }
    }

    /**
     * 延迟加载
     */
    protected void lazyLoad() {
        if (!isVisible||mHasLoadedOnce||refreshLayout==null) {
            return;
        }
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
//                type = FIRST_LOAD;
                loadData(FIRST_LOAD);

            }
        });
        mHasLoadedOnce =true;
    }

    @Override
    public void onRefresh() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
//                type = RE_LOAD;
                loadData(RE_LOAD);
            }
        }, 2000);
    }

    /**
     * 根据广播的tag判断是否加载更多
     * @param what
     */
//    public abstract void loadData(String what);
    public void loadData(String what) {
        if (what.equals(keyWord)){
//            type = LOAD_MORE;
            loadData(LOAD_MORE);
        }

    }

    ResponseImagesListEntity imageEntry;
    /**
     * 具体加载数据
     * @param type
     */
    public void loadData(final int type){

        if (type == LOAD_MORE){
            page++;
        }

//        HttpHelper.getInstance().loadData(UriHelper.getInstance().getImagesListUrl(keyWord,page),mHnadler);

//        String url = UriHelper.getInstance().getImagesListUrl(keyWord,page);
//        RequestParams params = new RequestParams(ApiConstants.Urls.BAIDU_IMAGES_URLS);
        RequestParams params = new RequestParams(UriHelper.getInstance().getImagesListUrl(keyWord,page));
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                LogUtil.e("成功获取数据");

                imageEntry= GsonUtils.jsonToBean(result, ResponseImagesListEntity.class);

                if (type == FIRST_LOAD) {
                    imgList.addAll(imageEntry.imgs);
                    adapter.notifyDataSetChanged();
                } else if (type == RE_LOAD) {
                    imgList.clear();
                    imgList.addAll(imageEntry.imgs);
                    adapter.notifyDataSetChanged();
                } else if (type == LOAD_MORE) {
//                    最后的一组数据为空，要先去掉
                    imgList.remove(imgList.size() - 1);
                    imgList.addAll(imageEntry.imgs);
                    adapter.notifyDataSetChanged();
                }
                LogUtil.e("图片数量：" + imgList.size());

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                if (refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(loadMoreReceiver);
        super.onDestroy();
    }

//    int type;
//    Handler mHnadler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == 0){
//
//            }
//        }
//    };

}
