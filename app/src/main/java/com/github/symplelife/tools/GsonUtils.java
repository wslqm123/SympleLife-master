package com.github.symplelife.tools;

import com.google.gson.Gson;

public class GsonUtils {

//	private static Gson gson = null;
//
//	static {
//		if (gson == null) {
//			gson = new Gson();
//		}
//	}
//
//	private GsonUtils() {
//
//	}

	public static<T> T jsonToBean(String jsonResult,Class<T> clz){
		Gson gson = new Gson();
		T t = gson.fromJson(jsonResult, clz);
		 return t;
	}

}
