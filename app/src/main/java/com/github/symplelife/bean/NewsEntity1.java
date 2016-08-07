package com.github.symplelife.bean;

import java.util.List;

/**
 * 原创
 */
public class NewsEntity1 {

	public List<PhotoSet> ads;
//	类别：头条、原创
	public String alias;
	public String boardid;
	public String cid;
//	内容
	public String digest;
	public String docid;
	public String ename;
	public int hasAD;
	public boolean hasCover;
	public int hasHead;
	public boolean hasIcon;
	public int hasImg;
//	多图
	public List<ImgExtra> imgextra;
	public String imgsrc;
	public String lmodify;
	public int order;
	public String photosetID;
	public String postid;
	public int priority;
//	时间
	public String ptime;
//	跟帖数
	public int replyCount;
	public String skipID;
//	专题
	public String skipType;
	public String template;
//	标题
	public String title;
	public String tname;
	public int votecount;

//	小标题
	public String ltitle;
//	来源
	public String source;
	public String specialID;
	public String subtitle;
	public String url;
	public String url_3w;

//	标签：独家、正在直播
	public String TAG;
	public String TAGS;
//	网易小编
	public List<NetEditor> editor;
	public int imgType;
//	直播
	public LiveInfo live_info;


	/**
	 * 页头轮播图
	 */
	public static class PhotoSet {
		public String imgsrc;
		public String subtitle;
		public String tag;
		public String title;
		public String url;

	}

	/**
	 * 多图
	  */
	public static class ImgExtra{
		public String imgsrc;
	}


	/**
	 * 网易小编
	 */
	public static class NetEditor{
		public String editorImg;
		public String editorName;
	}

	/**
	 * 直播
	 */
	public static class LiveInfo{
		public String end_time;
		public int roomId;
		public String start_time;
		public int type;
		public int user_count;
		public boolean video;
	}

}






















