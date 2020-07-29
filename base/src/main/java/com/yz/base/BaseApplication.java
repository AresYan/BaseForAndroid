package com.yz.base;

import androidx.multidex.MultiDexApplication;
import com.bumptech.glide.request.target.ViewTarget;
import com.king.thread.nevercrash.NeverCrash;
import com.yz.base.utils.*;

import java.io.*;
import java.text.SimpleDateFormat;

import es.dmoral.toasty.Toasty;

public abstract class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ViewTarget.setTagId(R.id.tag_glide);
        initNeverCrash();
        Toasty.Config.getInstance().allowQueue(false).apply();
    }

    private void initNeverCrash(){
        NeverCrash.init(new NeverCrash.CrashHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable ex) {
                StringBuffer sb = new StringBuffer();
                try {
                    SimpleDateFormat sDateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss");
                    String date = sDateFormat.format(new java.util.Date());
                    sb.append("\r\n" + date + "\n");
                    Writer writer = new StringWriter();
                    PrintWriter printWriter = new PrintWriter(writer);
                    ex.printStackTrace(printWriter);
                    printWriter.flush();
                    printWriter.close();
                    String result = writer.toString();
                    sb.append(result);
                } catch (Exception e) {
                    MyLogger.e("an error occured while writing file...", e);
                    sb.append("an error occured while writing file...\r\n");
                }
                try {
                    MyLogger.e(sb.toString());
                    writeFile(sb.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public abstract void writeFile(String sb) throws Exception;
}
