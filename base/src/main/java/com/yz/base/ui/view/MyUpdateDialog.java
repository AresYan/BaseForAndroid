package com.yz.base.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.blankj.utilcode.util.AppUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yz.base.R;
import com.yz.base.http.MyEasyHttpHelper;
import com.yz.base.http.MyResultProgressListener;
import com.yz.base.item.CommonItem;
import com.yz.base.ui.base.BaseActivity;
import com.yz.base.ui.base.BaseAlertDialog;
import com.yz.base.ui.base.BaseRecyclerDialog;
import com.yz.base.utils.MyFileUtils;
import com.yz.base.utils.MyMainHandler;
import com.yz.base.utils.MyStrHelper;
import com.yz.base.utils.MyToasty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :yanzheng
 * @describe ：自定义update提示框
 */
public class MyUpdateDialog extends BaseRecyclerDialog {

	private List<CommonItem> mInfos=new ArrayList<>();
	private MyUpdateInfoDialogAdapter mAdapter;
	private String url;
	private boolean isForce;

	private MyUpdateDialog(Context context) {
		super(context);
		mAdapter=new MyUpdateInfoDialogAdapter(mInfos);
		setAdapter(mAdapter);
	}

	public void setContent(String content) {
		mInfos.clear();
		addMsg(content);
	}

	public void setUrl(String url) {
		this.url=url;
	}

	public void setForce(boolean force) {
		isForce = force;
		setLeftEnabled(!force);
		setRightEnabled(true);
	}

	public void download(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			if (!mContext.getPackageManager().canRequestPackageInstalls()) {
				String msg= MyStrHelper.getString(getContext(),R.string.yz_base_permissions_error);
				String exit= MyStrHelper.getString(getContext(),R.string.yz_base_exit);
				String setting= MyStrHelper.getString(getContext(),R.string.yz_base_setting);
				new MyMsgDialog
						.Builder(mContext)
						.setMessgae(msg)
						.setLeft(exit)
						.setRight(setting)
						.setMyAlertDialogListener(new MyAlertDialogListener() {
							@Override
							public void left(BaseAlertDialog dialog) {
								dialog.dismiss();
								((BaseActivity)mContext).finish();
							}
							@Override
							public void right(BaseAlertDialog dialog) {
								dialog.dismiss();
								Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + mContext.getPackageName()));
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								mContext.startActivity(intent);
							}
						})
						.build()
						.show();
				return;
			}
		}
		setLeftEnabled(false);
		setRightEnabled(false);
		if(TextUtils.isEmpty(url)){
			failure();
			return;
		}
		mInfos.clear();
		addProgress();
		String name = "";
		String[] ss=url.split("/");
		if(ss!=null&&ss.length>0){
			name = ss[ss.length-1];
			if(!name.contains(".apk")){
				failure();
				return;
			}
		}
		String dir = MyFileUtils.getTYJWPath();
		String path = dir + name;
		MyEasyHttpHelper.getInstance().download(url.replace(name,""), name, dir, name, new MyResultProgressListener() {
			@Override
			public void onStart() {
			}
			@Override
			public void onFinished() {
			}
			@Override
			public void onSuccess(Object result) {
			}
			@Override
			public void onFailure(String msg) {
				failure();
			}
			@Override
			public void onProgress(int progress) {
				setProgress(progress);
				if(progress==100){
					success(path);
				}
			}
		});
	}

	private void success(String path){
		dismiss();
		AppUtils.installApp(path);
	}

	private void failure(){
		dismiss();
		if(isForce){
			MyToasty.show((Activity) getContext(),"强制更新失败，应用退出",MyToasty.TYPE_WARNING);
			MyMainHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					android.os.Process.killProcess(android.os.Process.myPid());
					System.exit(1);
				}
			},1500);
		}else{
			MyToasty.show((Activity) getContext(),"更新失败",MyToasty.TYPE_WARNING);
		}
	}

	private void addMsg(String content){
		CommonItem item=new CommonItem();
		item.setContent(content);
		item.setType(CommonItem.COMMON_TYPE_MSG);
		mInfos.add(item);
		mAdapter.notifyDataSetChanged();
	}

	private void addProgress(){
		CommonItem item=new CommonItem();
		item.setType(CommonItem.COMMON_TYPE_PROGRESS);
		mInfos.add(item);
		mAdapter.notifyDataSetChanged();
	}

	private void setProgress(int progress) {
		if(mAdapter!=null){
			mAdapter.setProgress(progress);
		}
	}

	private class MyUpdateInfoDialogAdapter extends BaseMultiItemQuickAdapter<CommonItem, BaseViewHolder> {

		private ProgressBar mProgressBar;
		private TextView mProgressTv;

		public void setProgress(int progress){
			if(mProgressBar!=null){
				mProgressBar.setProgress(progress);
			}
			if(mProgressTv!=null){
				mProgressTv.setText(progress+"%");
			}
		}

		public MyUpdateInfoDialogAdapter(List<CommonItem> list) {
			super(list);
			addItemType(CommonItem.COMMON_TYPE_MSG, R.layout.common_msg_item);
			addItemType(CommonItem.COMMON_TYPE_PROGRESS, R.layout.common_progress_item);
		}

		@Override
		protected void convert(BaseViewHolder helper, CommonItem item) {
			item.setPosition(helper.getAdapterPosition());
			switch(item.getItemType()){
				case CommonItem.COMMON_TYPE_MSG:
					TextView msgTv=helper.getView(R.id.common_msg_item_TextView);
					msgTv.setGravity(Gravity.CENTER_VERTICAL);
					msgTv.setTextSize(14);
					msgTv.setText(item.getContent());
					break;
				case CommonItem.COMMON_TYPE_PROGRESS:
					mProgressBar=helper.getView(R.id.common_progress_item_ProgressBar);
					mProgressTv=helper.getView(R.id.common_progress_item_TextView);
					mProgressBar.setMax(100);
					setProgress(0);
					break;
			}
		}
	}

	public static class Builder extends BaseAlertDialog.Builder{

		private String content;
		private String url;
		private boolean isForce;

		public Builder(Context context) {
			super(context);
		}

		public Builder setContent(String content) {
			this.content=content;
			return this;
		}

		public Builder setUrl(String url) {
			this.url=url;
			return this;
		}

		public Builder setForce(boolean force) {
			isForce = force;
			return this;
		}

		private void construct(MyUpdateDialog dialog) {
			super.construct(dialog);
			dialog.setContent(content);
			dialog.setUrl(url);
			dialog.setForce(isForce);
		}

		@Override
		public MyUpdateDialog build() {
			MyUpdateDialog dialog = new MyUpdateDialog(context);
			construct(dialog);
			return dialog;
		}
	}
}
