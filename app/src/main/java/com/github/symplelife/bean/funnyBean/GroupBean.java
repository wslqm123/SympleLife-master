package com.github.symplelife.bean.funnyBean;

import java.io.Serializable;
import java.util.List;

/**
 * 版权归本人所有
 * <p/>
 * 版本：1.0
 * <p/>
 * 描述：
 * <p/>
 * Created by AUTO-ROBOT on 2016/2/21.
 */
public class GroupBean {

    // 类型: 2-视频 4-推荐 5-多图
    public int type;

    // 标签
    public String category_name;
    // 评论数量
    public String comment_count;
    // 点赞数量
    public String digg_count;
    // 踩数量
    public String bury_count;
    // 发表时间
    public String create_time;
    // 内容
    public String content;
    // 投稿状态
    public String status_desc;
    // 网址
    public String share_url;
    // 热评数
    public String has_hot_comments;
    // 用户信息
    public UserInfo user;
    // 图片信息
    public ImageInfo large_image;
    // 是否是gif
    public int is_gif;
    // 图片列表
    public List<multiImage> large_image_list;
    public List<multiImage> thumb_image_list;
    // mp4信息
    public MP4Info gifvideo;
    // id
    public String group_id;

    // 视频封面
    public CoverImage large_cover;
    // 视频uri
    public String uri;
    public int video_height;
    public int video_width;

    // 推荐精华标题
    public String title;
    // 推荐内容网址
    public String open_url;

    public static class UserInfo{
        // 头像
        public String avatar_url;
        // 名称
        public String name;

    }

    public static class ImageInfo implements Serializable {
        public int height;
        public int r_height;
        public int r_width;
        public String uri;

    }

    public static class MP4Info{
        public int video_height;
        public int video_width;
        public String mp4_url;

    }

    public static class CoverImage{
        public String uri;
    }

    public static class multiImage{
        public int height;
        public int width;
        public boolean is_gif;
        public String uri;
        public String url;
    }

}
