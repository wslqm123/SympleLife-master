package com.github.symplelife.bean;

/**
 * 版权归本人所有
 * <p/>
 * 版本：1.0
 * <p/>
 * 描述：
 * <p/>
 * Created by AUTO-ROBOT on 2016/1/31.
 */
public class PhotoSet {
    public String imgsrc;
//	public String subtitle;
//	public String tag;
    public String title;
    public String url;

    public PhotoSet(String imgsrc, String title, String url) {
        this.imgsrc = imgsrc;
        this.title = title;
        this.url = url;
    }
}
