package com.github.symplelife.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;

import com.github.symplelife.tools.UiUtils;

import java.util.UUID;

/**
 * 版权归本人所有
 * <p>
 * 版本：1.0
 * <p>
 * 描述：
 * <p>
 * Created by AUTO-ROBOT on 2016/1/17.
 */
public class UserData {

    private static UserData userData;
    private SharedPreferences sp;
    private Editor editor;

    private UserData(Context context) {
        sp = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static UserData getInstance(Context context) {
        if (userData == null) {
            userData = new UserData(context);
        }
        return userData;
    }

    /**
     * 设置访客模式
     */
    public void setModel(boolean isVistor) {
        editor.putBoolean("model", isVistor);
        commit();
    }

    /**
     * 获取访客模式
     */
    public boolean getModel() {
        return sp.getBoolean("model", false);
    }


    /**
     * @说明:获取用户主题
     */
    public int getThemeId() {
        return sp.getInt("theme", 0);
    }

    /**
     * @说明:设置用户主题
     */
    public void setThemeId(int themeId) {
        editor.putInt("theme", themeId);
        commit();
    }

    /**
     * @说明:设置UUID
     */
    public void setUUID() {
        String s = UUID.randomUUID().toString();
        editor.putString("uuid", s);
        commit();
    }

    /**
     * 保存DeviceId
     */
    public void setDeviceId(){
        TelephonyManager tm = (TelephonyManager) UiUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        editor.putString("deviceId", tm.getDeviceId());
        commit();
    }

    /**
     *
     * @return
     */
    public String getDeviceId(){
        return sp.getString("deviceId", null);
    }

    /**
     * 通用保存数据方法
     * @param keyword
     * @param content
     */
    public void setData(String keyword , String content){
        editor.putString(keyword, content);
        commit();
    }

    /**
     * 通用获取数据方法
     * @param keyword
     * @return
     */
    public String getData(String keyword ){
        return sp.getString(keyword, null);
    }

    /**
     * @说明:获取UUID
     */
    public String getUUID() {
        return sp.getString("uuid", null);
    }

    public void commit() {
        editor.commit();
    }
}
