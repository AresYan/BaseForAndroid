package com.yz.base.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.yz.base.utils.Md5Utils;
import com.yz.base.utils.MyFileUtils;
import com.yz.base.utils.MyMainHandler;

import java.io.File;

/**
 * Created by YZ on 2016/11/29.
 */
public class MyGlideHelper {

    public static final int WIDTH=150;
    public static final int HEIGHT=150;

    public static DrawableRequestBuilder<Object> getHelper(View view, Object object){
        return Glide.with(view.getContext())
                .load(object)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESULT);
    }

    public static void into(ImageView view, Object object) {
        into(view,object,0,null,null);
    }

    public static void into(ImageView view, Object object, IntoListener listener) {
        into(view,object,0,null,listener);
    }

    public static void into(ImageView view, Object object, int errorRid) {
        into(view,object,errorRid,null,null);
    }

    public static void into(ImageView view, Object object, int errorRid, IntoListener listener) {
        into(view,object,errorRid,null,listener);
    }

    public static void intoCircle(ImageView view, Object object) {
        into(view,object,0,new GlideCircleTransform(view.getContext()),null);
    }

    public static void intoCircle(ImageView view, Object object, int errorRid) {
        into(view,object,errorRid,new GlideCircleTransform(view.getContext()),null);
    }

    public static void intoRound(ImageView view, Object object) {
        into(view,object,0,new GlideRoundTransform(view.getContext()),null);
    }
    
    public static void intoRound(ImageView view, Object object, int errorRid) {
        into(view,object,errorRid,new GlideRoundTransform(view.getContext()),null);
    }

    public static void into(ImageView view, Object object, int errorRid, BitmapTransformation transformation, final IntoListener listener) {
        if(view!=null&&object!=null&&!object.toString().equals(view.getTag())){
            view.setTag(null);
            DrawableRequestBuilder<Object> helper=getHelper(view,object);
            if(listener!=null){
                helper.listener(new RequestListener<Object, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, Object model, Target<GlideDrawable> target, boolean isFirstResource) {
                        if(listener!=null){
                            listener.onException();
                        }
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Object model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if(listener!=null){
                            listener.onSuccess(resource);
                        }
                        return false;
                    }
                });
            }
            if(transformation!=null){
                helper.transform(transformation);
            }
            if(errorRid!=0){
                helper.error(errorRid);
            }
            helper.into(view);
            view.setTag(object.toString());
        }
    }

    public static void getCacheFile(Context context, final Object object, final CacheFileCallback callback) {
        Glide.with(context)
                .load(object)
                .asBitmap()
                .toBytes()
                .into(new SimpleTarget<byte[]>(WIDTH, HEIGHT) {
                    @Override
                    public void onLoadFailed(Exception ex, Drawable errorDrawable) {
                        MyMainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.result(null);
                            }
                        });
                    }

                    @Override
                    public void onResourceReady(final byte[] data, GlideAnimation anim) {
                        MyMainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(data!=null){
                                    File file=new File(MyFileUtils.getTYJWPath()+MyFileUtils.BITMAP_DIR+Md5Utils.MD5(object));
                                    MyFileUtils.writeFile(file, data, false);
                                    callback.result(file);
                                }else{
                                    callback.result(null);
                                }
                            }
                        });
                    }
                });
    }

    public interface IntoListener{
        public void onSuccess(GlideDrawable resource);
        public void onException();
    }

    public interface CacheFileCallback{
        public void result(File file);
    }
}
