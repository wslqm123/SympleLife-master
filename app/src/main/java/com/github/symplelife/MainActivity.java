package com.github.symplelife;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;

import com.github.symplelife.base.BaseApplication;
import com.github.symplelife.fragment.BaseFragment;
import com.github.symplelife.fragment.DrawerFragment;
import com.github.symplelife.fragment.FragmentFactory;
import com.github.symplelife.tools.PromptManager;
import com.github.symplelife.tools.UiUtils;

import java.lang.reflect.Field;

public class MainActivity extends FragmentActivity implements DrawerLayout.DrawerListener, DrawerFragment.OnDrawerItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private DrawerFragment mDrawerFragment;
    private ActionBarDrawerToggle mToggle;
    private BaseFragment mCurrentFragment;
    private CharSequence mTitle;
    private String[] drawer_items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if ( BaseApplication.mTheme != 0) {
            setTheme(BaseApplication.mTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer_items = UiUtils.getStringArray(R.array.drawer_items);
        initDrawer();
        initActionBar();
//        在所有手机上显示actionbar的overflow按钮
        setOverflowShowingAlways();

    }

    private void initDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.mipmap.drawer_shadow, GravityCompat.START);
        mDrawerLayout.setDrawerListener(this);

        //菜单
        mDrawerFragment = (DrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mDrawerFragment.setOnDrawerItemSelectedListener(this);
//      默认选取第一个fragment作为主页显示
        mDrawerFragment.selectItem(0);
    }

    private void initActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//设置显示左侧按钮
        actionBar.setHomeButtonEnabled(true);//设置左侧按钮可点
        actionBar.setDisplayShowTitleEnabled(true);//设置显示标题

//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

        //初始化开关，并和drawer关联
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mToggle.syncState();//该方法会自动和actionBar关联

        actionBar.setTitle(drawer_items[0]);//设置标题
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            // 不显示栏目按钮
//            getMenuInflater().inflate(R.menu.main, menu);
            mCurrentFragment.onCreateOptionsMenu(menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || mCurrentFragment.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    /**
     * Drawer的回调方法，需要在该方法中对Toggle做对应的操作
     */
    @Override
    public void onDrawerOpened(View drawerView) {// 打开drawer
        mToggle.onDrawerOpened(drawerView);//需要把开关也变为打开
        invalidateOptionsMenu();
    }

    @Override
    public void onDrawerClosed(View drawerView) {// 关闭drawer
        mToggle.onDrawerClosed(drawerView);//需要把开关也变为关闭
        invalidateOptionsMenu();
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {// drawer滑动的回调
        mToggle.onDrawerSlide(drawerView, slideOffset);
    }

    @Override
    public void onDrawerStateChanged(int newState) {// drawer状态改变的回调
        mToggle.onDrawerStateChanged(newState);
    }

    /**
     * DrawerMenu的回调方法，需要在该方法中添加对应的Framgment
     */
    @Override
    public void onDrawerItemSelected(int position) {
        getActionBar().setTitle(drawer_items[position]);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        FragmentManager fragmentManager = getSupportFragmentManager();
        BaseFragment fragment = FragmentFactory.createFragment(position);
        mCurrentFragment = fragment;
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

//    退出应用
    @Override
    public void onBackPressed() {
        if (mDrawerFragment.isVisible()){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }else {
            PromptManager.showExitSystem(this);
        }
    }

    /**
     * 利用反射使在有menu键的手机上也可以显示overflow按钮
     */
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
