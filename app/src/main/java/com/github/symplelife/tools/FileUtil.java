package com.github.symplelife.tools;

import android.os.Environment;
import android.util.Log;

import org.xutils.common.util.MD5;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;


/**
 * 文件工具类
 * 
 * @author YE
 * 
 */
public class FileUtil {

	public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
	public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
	public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
	public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值

	/**
	 * 判断SD是否存在
	 * 
	 * @return
	 */
	public static boolean isSdcardExisting() {
		final String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param path
	 *            创建路径
	 */
	public static void createPath(String path) {
		File file = new File(path);
		if (!file.exists()) {// 判断文件的路径是否存在
			// 按照指定的路径创建文件夹
			file.mkdirs();
		}
	}

	public static File updateDir = null;
	public static File updateFile = null;

	/**
	 * 创建文件
	 */
	/*public static void createFile(String name) {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			updateDir = new File(Constant.PROJECTDOWNLOADPATH);
			updateFile = new File(updateDir + name + ".apk");

			if (!updateDir.exists()) {
				updateDir.mkdirs();
			}
			if (!updateFile.exists()) {
				try {
					updateFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}*/


	/**
	 * 获取xutils缓存图片的路径
	 * @param imgUrl
	 * @return
	 */
	public static String getXutilImg(String imgUrl){
		return org.xutils.common.util.FileUtil.getCacheDir("xUtils_img").
				getAbsolutePath()+ java.io.File.separator + MD5.md5(imgUrl);
	}

	/**
	 * byte转MB
	 * 
	 * @param byteSize
	 * @return
	 */
	public static Double byteToMB(Long byteSize) {
		if (byteSize == null) {
			return 0.0;
		}
		Double doubleSize = (double) (byteSize / 1024 / 1024);
		return doubleSize;
	}

	/**
	 * Mb转byte
	 * 
	 * @param doubleSize
	 * @return
	 */
	public static Long MBtoByte(Double doubleSize) {
		if (doubleSize == null) {
			return (long) 0;
		}
		Long longSize = (long) (doubleSize * 1024 * 1024);
		return longSize;
	}


	/**
	 * 获取文件总个数
	 * @param file
	 * @return
	 */
	public static int getTotalCount(File file){

		int i = 0;
		if (file.isDirectory()){
			File[] files = file.listFiles();
			i+=files.length;
			for (File insideFile : files) {
				if (insideFile.isDirectory()){
					i+= getTotalCount(insideFile);
				}
			}

		}
		return i;
	}

	/**
	 * 获取文件总大小
	 * @param file
	 * @return
	 */
	public static long getTotalSize(File file){

		long i = 0;
		if (file.isDirectory()){
			File[] files = file.listFiles();
			for (File insideFile : files) {
				if (insideFile.isDirectory()){
					i+= getTotalSize(insideFile);
				}else {
					i += insideFile.length();
				}
			}

		}else {
			i += file.length();
		}
		return i;
	}


	/**
	 * 调用此方法自动计算指定文件或指定文件夹的大小
	 * 系统默认缓存路径为 context.getExternalCacheDir()
	 * @param filePath
	 *            文件路径
	 * @return 计算好的带B、KB、MB、GB的字符串
	 */
	public static String getAutoFileOrFilesSize(String filePath) {
		if (!isSdcardExisting()){
			return null;
		}

		File file = new File(filePath);
		long blockSize = 0;
		try {
			if (file.isDirectory()) {
				blockSize = getFileSizes(file);
			} else {
				blockSize = getFileSize(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("获取文件大小", "获取失败!");
		}
		return FormetFileSize(blockSize);
	}
	/**
	 * 获取指定文件大小
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private static long getFileSize(File file) throws Exception {
		long size = 0;
		if (file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			size = fis.available();
		} else {
			file.createNewFile();
			Log.e("获取文件大小", "文件不存在!");
		}
		return size;
	}
	/**
	 * 获取指定文件夹大小
	 *
	 * @param f
	 * @return
	 * @throws Exception
	 */
	private static long getFileSizes(File f) throws Exception {
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSizes(flist[i]);
			} else {
				size = size + getFileSize(flist[i]);
			}
		}
		return size;
	}

	/**
	 * 转换文件大小
	 *
	 * @param fileS
	 * @return
	 */
	public static String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		String wrongSize = "0B";
		if (fileS == 0) {
			return wrongSize;
		}
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "GB";
		}
		return fileSizeString;
	}

	/*public static void deleteAllFiles(File root) {
        File files[] = root.listFiles();  
        if (files != null)  
            for (File f : files) {  
                if (f.isDirectory()) { // 判断是否为文件夹  
                    deleteAllFiles(f);  
                    try {  
                        f.delete();  
                    } catch (Exception e) {  
                    }  
                } else {  
                    if (f.exists()) { // 判断是否存在  
                        deleteAllFiles(f);
                        try {
                            f.delete();  
                        } catch (Exception e) {  
                        }  
                    }  
                }  
            }  
    }*/

	public static boolean deleteFileOrDir(File path) {
		if (path == null || !path.exists()) {
			return true;
		}
		if (path.isFile()) {
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
