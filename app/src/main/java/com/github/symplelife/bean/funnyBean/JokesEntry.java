package com.github.symplelife.bean.funnyBean;

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
public class JokesEntry {

    public List<JokesContent> data;

    public String has_more;
    public String max_time;
    public String min_time;
    public String tip;


    public static class JokesContent{
        public List<CommmetBean> comments;
        public GroupBean group;
        public String display_time;
        public String online_time;
        public int type;

    }
}
