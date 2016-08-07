package com.github.symplelife.common;

import android.os.Environment;

import com.github.symplelife.R;

/**
 * 版权归本人所有
 * <p/>
 * 版本：1.0
 * <p/>
 * 描述：
 * <p/>
 * Created by AUTO-ROBOT on 2016/1/7.
 */
public class Constants {

    public static int i = -1;
    public static int[] themes = {
         R.style.DefaultTheme,
         R.style.DefaultTheme,
         R.style.DefaultLightTheme,
         R.style.DefaultLightDarkTheme,
         R.style.RedTheme,
         R.style.PinkTheme,
         R.style.PurpleTheme,
         R.style.DeepPurpleTheme,
         R.style.IndigoTheme,
         R.style.BlueTheme,
         R.style.LightBlueTheme,
         R.style.CyanTheme,
         R.style.TealTheme,
         R.style.GreenTheme,
         R.style.LightGreenTheme,
         R.style.LimeTheme,
         R.style.YellowTheme,
         R.style.AmberTheme,
         R.style.OrangeTheme,
         R.style.DeepOrangeTheme,
         R.style.BrownTheme,
         R.style.GreyTheme,
         R.style.BlueGreyTheme
    };

    public static int[] dialogThemes = {
            R.style.DefaultThemeDialog,
            R.style.DefaultThemeDialog,
            R.style.DefaultLightThemeDialog,
            R.style.DefaultLightThemeDialog,
            R.style.RedThemeDialog,
            R.style.PinkThemeDialog,
            R.style.PurpleThemeDialog,
            R.style.DeepPurpleThemeDialog,
            R.style.IndigoThemeDialog,
            R.style.BlueThemeDialog,
            R.style.LightBlueThemeDialog,
            R.style.CyanThemeDialog,
            R.style.TealThemeDialog,
            R.style.GreenThemeDialog,
            R.style.LightGreenThemeDialog,
            R.style.LimeThemeDialog,
            R.style.YellowThemeDialog,
            R.style.AmberThemeDialog,
            R.style.OrangeThemeDialog,
            R.style.DeepOrangeThemeDialog,
            R.style.BrownThemeDialog,
            R.style.GreyThemeDialog,
            R.style.BlueGreyThemeDialog
    };

     public static final String LOAD_MORE_IMAGE = "action.loadMoreData.image";
     public static final String LOAD_MORE_NEWS = "action.loadMoreData.news";


    /**
     * 项目路径
     */
    public final static String PROJECTPATH = Environment.getExternalStorageDirectory().toString()
            + java.io.File.separator + "SimplyLife" + java.io.File.separator;

    /**
     * 项目图片路径
     */
    public final static String PROJECTIMAGEPATH = PROJECTPATH + "Image" + java.io.File.separator;
}
