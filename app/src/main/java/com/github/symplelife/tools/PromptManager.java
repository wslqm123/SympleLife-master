package com.github.symplelife.tools;


import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.widget.Toast;

import com.github.symplelife.R;
import com.github.symplelife.common.Constants;

/**
 * 提示信息的管理
 */

public class PromptManager {
	private static ProgressDialog dialog;

	public static void showProgressDialog(Context context,String msg) {
		dialog = new ProgressDialog(context,
				Constants.dialogThemes[UiUtils.getThemeId()]);
		dialog.setIcon(R.mipmap.ic_launcher);
//		dialog.setTitle(R.string.app_name);

		dialog.setMessage(msg);
		dialog.show();
	}

	public static void closeProgressDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	public static void showLoadingProgressBar(Context context){

	}

	/**
	 * 当判断当前手机没有网络时使用
	 * 
	 * @param context
	 */
	public static void showNoNetWork(final Context context) {
		Builder builder = new Builder(context,
				Constants.dialogThemes[UiUtils.getThemeId()]);
		builder.setIcon(R.mipmap.ic_launcher)//
				.setTitle(R.string.app_name)//
				.setMessage("当前无网络").setPositiveButton("设置", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 跳转到系统的网络设置界面
						Intent intent = new Intent();
						intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
						context.startActivity(intent);

					}
				}).setNegativeButton("知道了", null).show();
	}

	/**
	 * 退出系统
	 * 
	 * @param context
	 */
	public static void showExitSystem(Context context) {
		Builder builder = new Builder(context,
//				Constants.dialogThemes[UiUtils.getThemeId()]);
				Constants.dialogThemes[UiUtils.getThemeId()]);
		builder.setIcon(R.mipmap.ic_launcher)//
				.setTitle(R.string.app_name)//
				.setMessage("是否退出应用").setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
//						退出前清理缓存
//						x.image().clearCacheFiles();
//						ImageLoader.getInstance().clearDiskCache();
						android.os.Process.killProcess(android.os.Process.myPid());
						// 多个Activity——懒人听书：没有彻底退出应用
						// 将所有用到的Activity都存起来，获取全部，干掉
						// BaseActivity——onCreated——放到容器中
					}
				})//
				.setNegativeButton("取消", null)//
				.show();

	}

	/**
	 * 显示错误提示框
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showErrorDialog(Context context, String msg) {
		new Builder(context,
				Constants.dialogThemes[UiUtils.getThemeId()])//
				.setIcon(R.mipmap.ic_launcher)//
				.setTitle(R.string.app_name)//
				.setMessage(msg)//
				.setNegativeButton(context.getString(R.string.is_positive), null)//
				.show();
	}

	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	public static void showToast(Context context, int msgResId) {
		Toast.makeText(context, msgResId, Toast.LENGTH_LONG).show();
	}

	// 当测试阶段时true
	private static final boolean isShow = true;

	/**
	 * 测试用 在正式投入市场：删
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showToastTest(Context context, String msg) {
		if (isShow) {
			Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		}
	}

}
