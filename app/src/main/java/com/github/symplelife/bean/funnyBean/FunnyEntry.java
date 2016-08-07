package com.github.symplelife.bean.funnyBean;

import java.util.List;

/**
 * 版权归本人所有
 * <p/>
 * 版本：1.0
 * <p/>
 * 描述：
 * <p/>
 * Created by AUTO-ROBOT on 2016/2/18.
 */
public class FunnyEntry {

    public List<Essence> data;

    public String has_more;
    public String message;
    public Bohot next;


    public static class Essence{
        public String title;
        public String image_url;
        public String group_id;
        public String item_id;
        public String article_url;
    }

    public static class Bohot{
        public String max_behot_time;
    }

}
