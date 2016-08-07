package com.github.symplelife.bean;

import java.util.List;


public class VideoListEntity {

	public int count;
	public List<VideoList> itemList;
	public String nextPageUrl;
	public VideoTag tag;
	public int total;


	/**
	 * 视频列表
	 */
	public static class VideoList {
		public VideoData data;
		public String type;
	}


	public static class VideoTag{
		public String actionUrl;
		public int id;
		public String name;
	}


	public static class VideoData{
		public String category;
//		public Object consumption;
		public CoverImg cover;
		public String dataType;
		public String date;
		public String description;
		public int duration;
		public int id;
		public int idx;
//		public Object label;
//		public Array playInfo;
		public String playUrl;
//		public Object provider;
		public String releaseTime;
//		public Array tags;
		public String title;
		public String type;
//		public Object webUrl;

	}

	public static class CoverImg{
		public String blurred;
		public String detail;
		public String feed;
	}

}






















