package com.github.symplelife;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.github.symplelife.common.Constants;
import com.github.symplelife.tools.FileUtil;
import com.github.symplelife.tools.UiUtils;

import java.io.File;

/**
 * 版权归本人所有
 * <p/>
 * 版本：1.0
 * <p/>
 * 描述：
 * <p/>
 * Created by AUTO-ROBOT on 2016/3/6.
 */
public class ProgressBarAsyncTask extends AsyncTask<Integer, Integer, String> {

//    private TextView textView;
//    private ProgressBar progressBar;
    private Context context;
    private ProgressDialog dialog;
    private TextView textView;
    private int total = 0;
    private int current = 0;


//    public ProgressBarAsyncTask(TextView textView, ProgressBar progressBar) {
    public ProgressBarAsyncTask(Context context ,TextView textView) {
//    public ProgressBarAsyncTask(Context context) {
            super();
        this.context = context;
        this.textView = textView;
        dialog = new ProgressDialog(context,
                Constants.dialogThemes[UiUtils.getThemeId()]);
//        this.progressBar = progressBar;
        total = FileUtil.getTotalCount(context.getExternalCacheDir());
    }


    /**
     * 这里的Integer参数对应AsyncTask中的第一个参数
     * 这里的String返回值对应AsyncTask的第三个参数
     * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
     * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
     */
    @Override
    protected String doInBackground(Integer... params) {
//        NetOperator netOperator = new NetOperator();
//        int i = 0;
//        for (i = 10; i <= 100; i+=10) {
//            netOperator.operator();
//            publishProgress(i);
//        }
//        return i + params[0].intValue() + "";

        File path = context.getExternalCacheDir();

        deleteFileOrDir(path);

        return null;
    }


    /**
     * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
     * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
     */
    @Override
    protected void onPostExecute(String result) {
//        textView.setText("异步操作执行结束" + result);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            long size = FileUtil.getTotalSize(context.getExternalCacheDir());
            textView.setText(FileUtil.FormetFileSize(size));
        }

    }


    //该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
    @Override
    protected void onPreExecute() {
//        textView.setText("开始执行异步线程");
        dialog.setMessage("正在计算...");
        dialog.show();
    }


    /**
     * 这里的Intege参数对应AsyncTask中的第二个参数
     * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
     * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
//        int vlaue = values[0];
//        progressBar.setProgress(vlaue);
        current+=1;
        dialog.setMessage("缓存文件清理中... "+ current*100/total + "%");
    }


    public boolean deleteFileOrDir(File path) {

        if (path == null || !path.exists()) {
            return true;
        }
        if (path.isFile()) {
            publishProgress();
            return path.delete();

        }
        File[] files = path.listFiles();
        if (files != null) {
            for (File file : files) {
                deleteFileOrDir(file);
            }
        }
        return path.delete();
    }

}
