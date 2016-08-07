/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.symplelife.api;

import android.os.Environment;

/**
 * Author:  Tau.Chen
 * Email:   1076559197@qq.com | tauchen1990@gmail.com
 * Date:    2015/3/9.
 * Description:
 */
public class ApiConstants {

    //        百度图片
    public static final String BAIDU_IMAGES_URLS = "http://image.baidu.com/data/imgs";
    //        网易新闻
    public static final String NETEASE_NEWS_URLS = "http://c.3g.163.com/nc/article/list/";
    //        普通新闻详情
    public static final String NETEASE_NEWS_COMMON = "http://3g.163.com/touch/article.html?docid=";
    //        LIVE直播
    public static final String NETEASE_NEWS_LIVE = "http://3g.163.com/ntes/special/003417GE/touch_live.html?channel=&roomid=";
    //        图片新闻
    public static final String NETEASE_NEWS_PHOTO = "http://3g.163.com/touch/photoview.html?channel=&setid=";
    //        新闻评论
    public static final String NETEASE_NEWS_COMMENT = "http://3g.163.com/touch/comment.html?docid=";
    //        内涵精华
    public static final String ESSENCE_LIST= "http://toutiao.com/ma/?media_id=5234320301&count=10&max_behot_time=";
    //        精华详情
    public static final String ESSENCE_DETAIL= "http://m.neihanshequ.com/share/essence/";
    //        段子(仿客)
    public static final String JOKES_VISTOR_LIST = "http://ic.snssdk.com/neihan/stream/mix/v1/?&content_type=";
    //        段子
    public static final String JOKES_LOGIN_LIST = "http://lf.snssdk.com/neihan/stream/mix/v1/?mpic=1&webp=1&essence=1&content_type=";

    //        段子详情
    public static final String JOKES_DETAIL= "http://m.neihanshequ.com/share/group/";
    //        test
    public static final String JOKES_TEST= "http://lf.snssdk.com/neihan/stream/mix/v1/?mpic=1&webp=1&essence=1&content_type=-101&message_cursor=-1&longitude=&latitude=&am_longitude=&am_latitude=&am_city=&am_loc_time=";


    //        360°全景视频
    public static final String PANORAMA_LIST= "http://baobab.wandoujia.com/api/v3/tag/videos?tagId=658&strategy=date";


    public static final class Urls {

        public static final String YOUKU_VIDEOS_URLS = "https://openapi.youku.com/v2/searches/video/by_keyword.json";
        public static final String YOUKU_USER_URLS = "https://openapi.youku.com/v2/users/show.json";
        public static final String DOUBAN_PLAY_LIST_URLS = "http://www.douban.com/j/app/radio/people";
    }

    public static final class Paths {
        public static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        public static final String IMAGE_LOADER_CACHE_PATH = "/SimplifyReader/Images/";
    }

    public static final class Integers {
        public static final int PAGE_LAZY_LOAD_DELAY_TIME_MS = 200;
    }
}