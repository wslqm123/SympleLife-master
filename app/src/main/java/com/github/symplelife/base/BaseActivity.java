package com.github.symplelife.base;

import android.app.Activity;
import android.os.Bundle;

import java.util.LinkedList;
import java.util.List;

public class BaseActivity extends Activity {

    // 管理所有Activities
    final static List<BaseActivity> mActivities = new LinkedList<BaseActivity>();

    public static BaseActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synchronized (mActivities){
            mActivities.add(this);
        }

        init();
        initView();
        initActionBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activity=this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        activity=null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities){
            mActivities.remove(this);
        }
    }

    public void killAll(){
        LinkedList<BaseActivity> copy;
        // 复制一份Activity集合
        synchronized (mActivities){
            copy = new LinkedList<BaseActivity>(mActivities);
        }
        for (BaseActivity activity:copy){
            activity.finish();
        }
        // 杀死当前进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    protected void initActionBar() {

    }

    protected void initView() {

    }

    protected void init() {

    }
}
