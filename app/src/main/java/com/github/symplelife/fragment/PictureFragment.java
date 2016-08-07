package com.github.symplelife.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.symplelife.R;
import com.github.symplelife.fragment.listFragment.PictureListFragment;
import com.github.symplelife.tools.UiUtils;


public class PictureFragment extends BaseFragment implements MenuItem.OnMenuItemClickListener{

	private ViewPager mViewPager;
	private PagerTabStrip pager_tab_trip;
	private String[] tab_names;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	protected View initView(){

		View view = View.inflate(getActivity(), R.layout.fragment_widget, null);
		tab_names = UiUtils.getStringArray(R.array.pictures_tab);

		mViewPager = (ViewPager) view.findViewById(R.id.demo);
		pager_tab_trip = (PagerTabStrip) view.findViewById(R.id.pager_tab_strip);
		//设置缓存个数（不包括当前显示的view）
		mViewPager.setOffscreenPageLimit(tab_names.length);
		mViewPager.setAdapter(new MainAdapter(getActivity().getSupportFragmentManager()));
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
//				Fragment creatFragment = PictureFragmentFactory.createFragment(position);
//				creatFragment.show();//当页面切换的时候重新请求服务器修改状态

			}
		});
//		pager_tab_trip.setTabIndicatorColor(getResources().getColor(R.color.indicatorcolor));

		return view;
	}
//
//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		if (tab_names == null) {
//			tab_names = UiUtils.getStringArray(R.array.tab_names);
//		}
//
//		for (String name : tab_names){
//			MenuItem menuItem = menu.add(name);
//			menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
//			menuItem.setOnMenuItemClickListener(this);
//		}
//		super.onCreateOptionsMenu(menu, inflater);
//	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (tab_names == null) {
			tab_names = UiUtils.getStringArray(R.array.pictures_tab);
		}

		for (String name : tab_names){
			MenuItem menuItem = menu.add(name);
			menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
			menuItem.setOnMenuItemClickListener(this);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		for (int i = 0; i < tab_names.length; i++) {

			if (item.getTitle().equals(tab_names[i])) {
				mViewPager.setCurrentItem(i);
//				setTheme(i);
//				reload();
				return true;
			}
		}
		return false;
	}


	private class MainAdapter extends FragmentStatePagerAdapter {
		public MainAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
//			return PictureFragmentFactory.createFragment(position);
			return PictureListFragment.newInstance(tab_names[position]);
		}


		@Override
		public int getCount() {
			return tab_names.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return tab_names[position];
		}
	}

	@Override
	public String getUrl() {
		return "file:///android_asset/widget.html";
	}


}
