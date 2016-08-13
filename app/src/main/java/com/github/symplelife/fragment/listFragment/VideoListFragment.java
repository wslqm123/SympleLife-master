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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.symplelife.R;
import com.github.symplelife.adapter.VideoAdapter;
import com.github.symplelife.bean.VideoListEntity;
import com.github.symplelife.common.Constants;
import com.github.symplelife.tools.GsonUtils;
import com.github.symplelife.tools.PromptManager;
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
public class VideoListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private static int FIRST_LOAD = 0;
    private static int RE_LOAD = 1;
    private static int LOAD_MORE = 2;

    private LoadMoreReceiver loadMoreReceiver;

    private List<VideoListEntity.VideoList> videoList =new ArrayList<VideoListEntity.VideoList>();
    private VideoAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private String keyWord ;
    private int position;
    private String nextPageUrl = null;

    private VideoListEntity videoListEntity;

    /** 是否已被加载过一次，第二次就不再去请求数据了 */
    protected boolean mHasLoadedOnce;

    /** Fragment当前状态是否可见 */
    protected boolean isVisible;

    public static VideoListFragment newInstance(String keyWord, int position)
    {
        VideoListFragment f = new VideoListFragment();
        Bundle bdl = new Bundle();
        bdl.putString("keyWord", keyWord);
        bdl.putInt("position", position);
        f.setArguments(bdl);
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 动态注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.LOAD_MORE_NEWS);
        loadMoreReceiver = new LoadMoreReceiver();
        getActivity().registerReceiver(loadMoreReceiver, filter);

        keyWord = getArguments().getString("keyWord");
        position = getArguments().getInt("position");

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.news_layout, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setProgressBackgroundColorSchemeResource(R.color.refresh_bg);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

//        init();
        adapter = new VideoAdapter(getActivity(),keyWord, videoList);
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
                refreshLayout.setRefreshing(false);
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
            loadData(LOAD_MORE);
        }

    }

    /**
     * 具体加载数据
     * @param type
     */
    public void loadData(final int type){

        String url ;
        if (type == LOAD_MORE){
            if (TextUtils.isEmpty(nextPageUrl)){

                PromptManager.showToast(getContext(), "没有内容啦！");
                return;
            }
            url = nextPageUrl;

        }else {
//            url = ApiConstants.PANORAMA_LIST;
            url = UriHelper.getInstance().getVideoListUrl(position);
        }
        RequestParams params = new RequestParams(url);
        LogUtil.e(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                videoListEntity = GsonUtils.jsonToBean(result, VideoListEntity.class);

                if (type == RE_LOAD){
                    videoList.clear();
                }

                nextPageUrl = videoListEntity.nextPageUrl;
                videoList.addAll(videoListEntity.itemList);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                PromptManager.showToast(getContext(),"出错鸟~");
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

}
