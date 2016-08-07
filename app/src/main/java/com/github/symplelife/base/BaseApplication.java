package com.github.symplelife.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.github.symplelife.common.Constants;
import com.github.symplelife.tools.UiUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;

/**
 * 版权归本人所有
 * <p/>
 * 版本：1.0
 * <p/>
 * 描述：
 * <p/>
 * Created by AUTO-ROBOT on 2015/10/23.
 */
public class BaseApplication extends Application {

    private static BaseApplication application;
    private static int mainTid;
    private static Handler handler;

    public static int mTheme = 0;

    @Override
//  在主线程运行的
    public void onCreate() {
        super.onCreate();
        application=this;
        mainTid = android.os.Process.myTid();
        handler=new Handler();
        mTheme = Constants.themes[UiUtils.getThemeId()];

        // 在使用 Vitamio 前必须先配置
//        Vitamio.isInitialized(this);

        // 初始化
        x.Ext.init(this);
        // 设置是否输出debug
        x.Ext.setDebug(true);

        // 如果没有UUID则生成一个
//        if (TextUtils.isEmpty(UserData.getInstance(getApplication()).getUUID())){
//            UserData.getInstance(getApplication()).setUUID();
//        }
//
//        if (TextUtils.isEmpty(UserData.getInstance(getApplication()).getDeviceId())){
//            UserData.getInstance(getApplication()).setDeviceId();
//        }


//        创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);
//        File cacheDir = StorageUtils.getCacheDirectory(this);
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
//                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
//                .diskCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null)
//                .taskExecutor(...)
//                .taskExecutorForCachedImages(...)
//                .threadPoolSize(3) // default
//                .threadPriority(Thread.NORM_PRIORITY - 1) // default
//                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
//                .denyCacheImageMultipleSizesInMemory()
//                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
//                .memoryCacheSize(2 * 1024 * 1024)
//                .memoryCacheSizePercentage(13) // default
//                .diskCache(new UnlimitedDiscCache(cacheDir)) // default
//                .diskCacheSize(50 * 1024 * 1024)
//                .diskCacheFileCount(100)
//                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
//                .imageDownloader(new BaseImageDownloader(this)) // default
//                .imageDecoder(new BaseImageDecoder()) // default
//                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
//                .writeDebugLogs()
//                .build();

//          Initialize ImageLoader with configuration.
          ImageLoader.getInstance().init(configuration);
    }
    public static Context getApplication() {
        return application;
    }
    public static int getMainTid() {
        return mainTid;
    }
    public static Handler getHandler() {
        return handler;
    }
}
