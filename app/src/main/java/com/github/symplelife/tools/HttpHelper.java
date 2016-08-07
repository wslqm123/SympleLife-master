package com.github.symplelife.tools;

import android.os.Handler;
import android.os.Message;

import com.github.symplelife.bean.ImagesListEntity;
import com.github.symplelife.bean.ResponseImagesListEntity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 版权归本人所有
 * <p>
 * 版本：1.0
 * <p>
 * 描述：
 * <p>
 * Created by AUTO-ROBOT on 2016/1/23.
 */
public class HttpHelper {

    private static HttpHelper instance = null;

    private HttpHelper() {
    }

    public static synchronized HttpHelper getInstance() {
        if (null == instance) {
            instance = new HttpHelper();
        }
        return instance;
    }

    private ResponseImagesListEntity imageEntry;
    private List<ImagesListEntity> imgList;
    private RequestParams params;
    public void loadData(String url, final Handler handler){
        imgList = new ArrayList<ImagesListEntity>();
        params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                imageEntry= GsonUtils.jsonToBean(result, ResponseImagesListEntity.class);
                imgList.addAll(imageEntry.imgs);
                Message msg = new Message();
                msg.what = 0;
                msg.obj = imgList;
                handler.sendMessage(msg);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }



}
