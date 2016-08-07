package com.github.symplelife.fragment;


import java.util.HashMap;
import java.util.Map;

public class FragmentFactory {

	public static final int FRAGEMENT_FUNNY = 0;
	public static final int FRAGEMENT_IMAGE = 1;
	public static final int FRAGEMENT_NEWS = 2;
	public static final int VIDEO_PANOROMA = 3;
	public static final int FRAGEMENT_SETTING = 4;

	private static Map<Integer, BaseFragment> mFragmentCache = new HashMap<Integer, BaseFragment>();

	public static BaseFragment createFragment(int position){
		BaseFragment fragment = mFragmentCache.get(position);
		if (fragment == null) {
			switch (position) {
				case FRAGEMENT_FUNNY:
					fragment = new FunnyFragment();
					break;
				case FRAGEMENT_IMAGE:
					fragment = new PictureFragment();
					break;
				case FRAGEMENT_NEWS:
					fragment = new NewsFragment();
					break;
				case VIDEO_PANOROMA:
					fragment = new VideoFragment();
					break;
				case FRAGEMENT_SETTING:
					fragment = new SettingFragment();
					break;

				default:
					break;
			}
			mFragmentCache.put(position,fragment);
		}
		return fragment;
	}

	public static void clear(){
		mFragmentCache.clear();
	}
}
