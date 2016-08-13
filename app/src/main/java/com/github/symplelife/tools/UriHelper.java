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

package com.github.symplelife.tools;


import com.github.symplelife.api.ApiConstants;
import com.github.symplelife.common.UserData;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Author:  Tau.Chen
 * Email:   1076559197@qq.com | tauchen1990@gmail.com
 * Date:    2015/3/20.
 * Description:
 */
public class UriHelper {

    private static volatile UriHelper instance = null;

    /**
     * 20 datas per page
     */
    public static final int PAGE_LIMIT = 20;


    private UriHelper() {
    }

    public static UriHelper getInstance() {
        if (null == instance) {
            synchronized (UriHelper.class) {
                if (null == instance) {
                    instance = new UriHelper();
                }
            }
        }
        return instance;
    }

    public int calculateTotalPages(int totalNumber) {
        if (totalNumber > 0) {
            return totalNumber % PAGE_LIMIT != 0 ? (totalNumber / PAGE_LIMIT + 1) : totalNumber / PAGE_LIMIT;
        } else {
            return 0;
        }
    }

    /**
     * 获取图片列表
     * @param category
     * @param pageNum
     * @return
     */
    public String getImagesListUrl(String category, int pageNum) {
        StringBuffer sb = new StringBuffer();
        sb.append(ApiConstants.BAIDU_IMAGES_URLS);
        sb.append("?col=");
        try {
            sb.append(URLEncoder.encode(category, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("&tag=");
        try {
            sb.append(URLEncoder.encode("全部", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("&pn=");
        sb.append(pageNum * PAGE_LIMIT);
        sb.append("&rn=");
        sb.append(PAGE_LIMIT);
        sb.append("&from=1");
        return sb.toString();
    }

    /**
     * 获取新闻列表
     * @param category
     * @param pageNum
     * @return
     */
    public String getNewsListUrl(String category, int pageNum) {
        StringBuffer sb = new StringBuffer();
        sb.append(ApiConstants.NETEASE_NEWS_URLS);
        sb.append(category);
        sb.append("/");
        sb.append(pageNum * PAGE_LIMIT);
        sb.append("-");
        sb.append(PAGE_LIMIT);
        sb.append(".html");
        return sb.toString();
    }

    /**
     * 普通类新闻url
     * @param docid
     * @return
     */
    public String getCommonNewsUrl(String docid) {
        return ApiConstants.NETEASE_NEWS_COMMON+docid;
    }

    /**
     * 图片类新闻url
     * @param setid
     * @param channelid
     * @return
     */
    public String getPhotoNewsUrl(String setid ,String channelid){
        return ApiConstants.NETEASE_NEWS_PHOTO+ setid +"&channelid="+channelid;
    }

    /**
     * 直播类新闻url
     * @param roomID
     * @return
     */
    public String getLiveNewsUrl(int roomID){
        return ApiConstants.NETEASE_NEWS_LIVE+roomID;
    }


    /**
     * 内涵详情url
     * @param group_id
     * @param item_id
     * @return
     */
    public String getEssenceUrl(String group_id ,String item_id){
        StringBuffer sb = new StringBuffer();
        sb.append(ApiConstants.ESSENCE_DETAIL);
        sb.append(group_id);
        sb.append("/?item_id=");
        sb.append(item_id);
        return sb.toString();
    }

    /**
     * 内涵段子数据url
     * @param keyword
     * @param type
     * @param time
     * @return
     */
    public String getJokesVistorUrl(String keyword, int type, String time){

        StringBuffer sb = new StringBuffer();
        sb.append(ApiConstants.JOKES_VISTOR_LIST);
        if (keyword.equals("段子")){
            sb.append("-102&count=30&am_loc_time=");
        }else if (keyword.equals("图片")){
            sb.append("-103&count=30&am_loc_time=");
        }else if (keyword.equals("视频")){
            sb.append("-104&count=30&am_loc_time=");
        }else {
            sb.append("-101&count=30&am_loc_time=");
        }
        sb.append(System.currentTimeMillis());
        if (type == 1){
            sb.append("&min_time=");
        }else {
            sb.append("&max_time=");
        }
        sb.append(time);
        return sb.toString();
    }



    public String getJokesUrl(String keyword , int type , String time){

        if (UserData.getInstance(UiUtils.getContext()).getModel()){
            return getJokesVistorUrl(keyword,type,time);
        }

        StringBuffer sb = new StringBuffer();
        sb.append(ApiConstants.JOKES_LOGIN_LIST);
        if (keyword.equals("段子")){
            sb.append("-102&message_cursor=-1&longitude=&latitude=&am_longitude=&am_latitude=&am_city=&am_loc_time=");
        }else if (keyword.equals("图片")){
            sb.append("-103&message_cursor=-1&longitude=&latitude=&am_longitude=&am_latitude=&am_city=&am_loc_time=");
        }else if (keyword.equals("视频")){
            sb.append("-104&message_cursor=-1&longitude=&latitude=&am_longitude=&am_latitude=&am_city=&am_loc_time=");
        }else {
            sb.append("-101&message_cursor=-1&longitude=&latitude=&am_longitude=&am_latitude=&am_city=&am_loc_time=");
        }
        sb.append(System.currentTimeMillis());
        if (type == 1){
            sb.append("&count=30&min_time=");
        }else {
            sb.append("&count=30&max_time=");
        }
        sb.append(time);
        sb.append("&screen_width=1080&iid=3672781563&device_id=11350916501&ac=wifi&channel=wandoujia&aid=7&app_name=joke_essay&version_code=500&version_name=5.0.0&device_platform=android&ssmix=a&device_type=&os_api=22&os_version=5.1&uuid=960432507381412&openudid=79747fc96c7773d6&manifest_version_code=500");
        return sb.toString();
    }


    /**
     * 视频url
     * @param position
     * @return
     */
    public String getVideoListUrl(int position){

        switch (position){
            case 0:
                return ApiConstants.TOP_VIDEO_LIST;
            case 1:
                return ApiConstants.PANORAMA_LIST;
            default:
                return ApiConstants.EYES_VIDEO_LIST + (position-1)*2;

        }

    }

    public String testUrl(String time){
        StringBuffer sb = new StringBuffer();
        sb.append(ApiConstants.JOKES_TEST);
        sb.append(System.currentTimeMillis());
        sb.append("&count=21&min_time=");
        sb.append(time);
        sb.append("&screen_width=1080&iid=3672781563&device_id=11350916501&ac=wifi&channel=wandoujia&aid=7&app_name=joke_essay&version_code=500&version_name=5.0.0&device_platform=android&ssmix=a&device_type=&os_api=22&os_version=5.1&uuid=960432507381412&openudid=79747fc96c7773d6&manifest_version_code=500");
        return sb.toString();
    }

    public String getFunnyVideo(String uri){
        return "http://i.snssdk.com/neihan/video/playback/?video_id="+uri+
                "&quality=origin&line=0&is_gif=0.mp4";
    }

/*    public String getVideosListUrl(String category, int pageNum) {
        StringBuffer sb = new StringBuffer();
        sb.append(ApiConstants.Urls.YOUKU_VIDEOS_URLS);
        sb.append("?keyword=");
        try {
            sb.append(URLEncoder.encode(category, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("&page=");
        sb.append(pageNum);
        sb.append("&count=");
        sb.append(PAGE_LIMIT);
        sb.append("&public_type=all&paid=0&period=today&orderby=published&client_id=6ecd6970268b4c53");
        return sb.toString().trim();
    }

    public String getVideoUserUrl(int userId) {
        StringBuffer sb = new StringBuffer();
        sb.append(ApiConstants.Urls.YOUKU_USER_URLS);
        sb.append("?user_id=");
        sb.append(userId);
        sb.append("&client_id=6ecd6970268b4c53");
        return sb.toString().trim();
    }

    public String getDoubanPlayListUrl(String channelId) {
        StringBuffer sb = new StringBuffer();
        sb.append(ApiConstants.Urls.DOUBAN_PLAY_LIST_URLS);
        sb.append("?channel=");
        sb.append(channelId);
        sb.append("&app_name=radio_desktop_win&version=100&type=&sid=0");
        return sb.toString().trim();
    }*/
}
