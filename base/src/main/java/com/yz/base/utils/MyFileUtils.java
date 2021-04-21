package com.yz.base.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import com.blankj.utilcode.util.SDCardUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

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

	public static void writeFile(File file, byte[] data, boolean isAppend) {
		OutputStream out = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			out = new FileOutputStream(file, isAppend);
			out.write(data);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(out!=null){
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.gc();
		}
	}

	public static Bitmap fileToBitmap(File file) {
		return BitmapFactory.decodeFile(file.getAbsolutePath());
	}

	public static void bitmapToFile(Bitmap bitmap, File file){
		try{
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			bos.flush();
			bos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static boolean del(File file) {
		if (!file.exists()) {
			return false;
		}
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				del(f);
			}
		}
		return file.delete();
	}

	public static byte[] toByteArray(File file) {
		if (!file.exists()) {
			return null;
		}
		FileChannel channel = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(file);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			while ((channel.read(byteBuffer)) > 0)
				return byteBuffer.array();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Bitmap viewToBitmap(View view){
		view.buildDrawingCache();
		return Bitmap.createBitmap(view.getDrawingCache());
	}

}
