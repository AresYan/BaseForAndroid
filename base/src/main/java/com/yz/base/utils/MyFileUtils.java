package com.yz.base.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.view.View;
import com.blankj.utilcode.util.SDCardUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyFileUtils {

	public static final String TEMP_NAME="temp.jpg";
	
	public static final String VIDEOS_DIR="videos/";
	public static final String PIC_DIR="pic/";
	public static final String BITMAP_DIR="bitmap/";
	public static final String LIVE_DIR="live/";
	public static final String CRASH_DIR="crash/";

	public static String getTYJWPath(){
		return SDCardUtils.getSDCardPathByEnvironment() +"/tyjw/";
	}

	public static Bitmap fileToBitmap(File file) throws IOException {
		return BitmapFactory.decodeFile(file.getAbsolutePath());
	}

	public static File bitmapToTempFile(Bitmap bitmap) throws IOException {
		return bitmapToFile(bitmap,TEMP_NAME);
	}

	public static File bitmapToFile(Bitmap bitmap, String fileName) throws IOException {
		String dirPath = getTYJWPath();
		File dir = new File(dirPath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		File file = new File(dirPath + fileName);
		if(file.exists()){
			file.delete();
		}
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
		bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
		return file;
	}

	public static String writeFile(String fileName, byte[] data) {
		return writeFile(getTYJWPath(),fileName,data);
	}

	public static String writeFile(String dirPath, String fileName, byte[] data) {
		BufferedOutputStream out = null;
		try {
			File dir = new File(dirPath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			File file = new File(dirPath + fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			out = new BufferedOutputStream(new FileOutputStream(file));
			out.write(data);
			out.flush();
			return file.getAbsolutePath();
		} catch (Exception e) {
			MyLogger.e(e.getMessage(),e);
		} finally {
			try {
				if(out!=null){
					out.close();
				}
			} catch (IOException e) {
				MyLogger.e(e.getMessage(),e);
			}
		}
		return "";
	}

	public static String appendWriteFile(String fileName, byte[] data) {
		return appendWriteFile(getTYJWPath(),fileName,data);
	}

	public static String appendWriteFile(String dirPath, String fileName, byte[] data) {
		BufferedOutputStream out = null;
		try {
			File dir = new File(dirPath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			File file = new File(dirPath + fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			out = new BufferedOutputStream(new FileOutputStream(file, true));
			out.write(data);
			out.flush();
			return file.getAbsolutePath();
		} catch (Exception e) {
			MyLogger.e(e.getMessage(),e);
		} finally {
			try {
				if(out!=null){
					out.close();
				}
			} catch (IOException e) {
				MyLogger.e(e.getMessage(),e);
			}
		}
		return "";
	}

	public static void delFile(String path){
		File file=new File(path);//将要保存图片的路径
		if(file.exists()){
			file.delete();
		}
	}

	public static void setExif(String path, String date, String lat, String lng, String alt) throws IOException {
		ExifInterface exifInterface = new ExifInterface(path);
		exifInterface.setAttribute(ExifInterface.TAG_DATETIME,date);
		exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE ,lat);
		exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE,lng);
		exifInterface.setAttribute(ExifInterface.TAG_GPS_ALTITUDE,alt);
//		exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION,ori);
		exifInterface.saveAttributes();
	}

	public static Bitmap viewToBitmap(View view){
		view.buildDrawingCache();
		return Bitmap.createBitmap(view.getDrawingCache());
	}

}
