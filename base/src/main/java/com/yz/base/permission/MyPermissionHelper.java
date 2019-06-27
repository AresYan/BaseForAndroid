package com.yz.base.permission;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import com.yz.base.R;
import com.yz.base.ui.base.BaseAlertDialog;
import com.yz.base.ui.view.MyMsgDialog;
import com.yz.base.utils.MyStrHelper;

public class MyPermissionHelper {

	public static void storage(final Activity activity, ZbPermission.ZbPermissionCallback callback){
		ZbPermission.needPermission(activity, Permission.REQUEST_STORAGE, Permission.STORAGE, callback);
	}

	public static void camera(Activity activity, ZbPermission.ZbPermissionCallback callback){
		ZbPermission.needPermission(activity,Permission.REQUEST_CAMERA, Permission.CAMERA, callback);
	}

	public static void location(Activity activity, ZbPermission.ZbPermissionCallback callback){
		ZbPermission.needPermission(activity,Permission.REQUEST_LOCATION, Permission.LOCATION, callback);
	}

	public static void phone(Activity activity, ZbPermission.ZbPermissionCallback callback){
		ZbPermission.needPermission(activity,Permission.REQUEST_PHONE, Permission.PHONE, callback);
	}

	public static void microphone(Activity activity, ZbPermission.ZbPermissionCallback callback){
		ZbPermission.needPermission(activity,Permission.REQUEST_MICROPHONE, Permission.MICROPHONE, callback);
	}

	public static void needPermission(Activity activity, String permission, ZbPermission.ZbPermissionCallback callback){
		ZbPermission.needPermission(activity,Permission.REQUEST_COMMON, new String[] { permission }, callback);
	}

	public static void needPermissions(Activity activity, String[] permissions, ZbPermission.ZbPermissionCallback callback){
		ZbPermission.needPermission(activity,Permission.REQUEST_COMMON, permissions, callback);
	}

	public static void dialog(final Activity activity){
			String msg = MyStrHelper.getString(activity, R.string.yz_base_permissions_error);
			String exit = MyStrHelper.getString(activity, R.string.yz_base_exit);
			String setting = MyStrHelper.getString(activity, R.string.yz_base_setting);
			new MyMsgDialog
					.Builder(activity)
					.setMessgae(msg)
					.setLeft(exit)
					.setRight(setting)
					.setMyAlertDialogListener(new BaseAlertDialog.MyAlertDialogListener() {
						@Override
						public void left(BaseAlertDialog dialog) {
							dialog.dismiss();
							activity.finish();
						}

						@Override
						public void right(BaseAlertDialog dialog) {
							dialog.dismiss();
							Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
							intent.setData(Uri.parse("package:" + activity.getPackageName()));
							activity.startActivity(intent);
						}
					})
					.build()
					.show();
		}

}
