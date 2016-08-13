package com.github.symplelife.tools;

/**
 * Created by yuanzheng on 2016/7/24.
 */
public class TimeUtil {

    /**
     * 秒转化成分钟制
     * @param sec
     * @return
     */
    public static String second2Min(int sec){
        int second = sec%60;
        int minute  = sec/60;
        return minute + "′" + second + "″";
    }

    /**
     * 秒转化成小时制
     * @param sec
     * @return
     */
    public static String second2Hour(int sec){

        StringBuilder sb = new StringBuilder();
        int second = sec%60;
        int minute  = (sec/60)%60;
        int hour = sec/3600;

        if (hour>0){
            sb.append(hour);
            sb.append(":");
        }
        if (minute<10){
            sb.append(0);
        }
        sb.append(minute);
        sb.append(":");
        if (second<10){
            sb.append(0);
        }
        sb.append(second);
        return sb.toString();
    }
}
