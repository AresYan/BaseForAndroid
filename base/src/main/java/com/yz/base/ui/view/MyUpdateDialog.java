package com.yz.base.ui.view;

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
import com.yz.base.api.APIBase;
import com.yz.base.http.MyResultDownloadListener;
import com.yz.base.item.CommonItem;
import com.yz.base.ui.base.BaseActivity;
import com.yz.base.ui.base.BaseAlertDialog;
import com.yz.base.ui.base.BaseRecyclerDialog;
import com.yz.base.utils.MyFileUtils;
import com.yz.base.utils.MyLogger;
import com.yz.base.utils.MyStrHelper;
import com.yz.base.utils.MyToasty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :yanzheng
 * @describe ：自定义update提示框
 */
public class MyUpdateDialog extends BaseRecyclerDialog implements BaseAlertDialog.MyAlertDialogListener {

	private Object mRequest;
	private List<CommonItem> mInfos=new ArrayList<>();
	private MyUpdateInfoDialogAdapter mAdapter;
	private boolean isDownloading=false;
	private String version;
	private String content;
	private String url;
	private int force;
	private int status;

	private MyUpdateDialog(Context context) {
		super(context);
		setMyAlertDialogListener(this);
		mAdapter=new MyUpdateInfoDialogAdapter(mInfos);
		setAdapter(mAdapter);
	}


	public void setVersion(String version) {
		this.version=version;
	}

	public void setContent(String content) {
		this.content=content;
	}

	public void setUrl(String url) {
		this.url=url;
	}

	public void setForce(int force) {
		this.force=force;
	}

	public void setStatus(int status) {
		this.status=status;
	}

	@Override
	public void left(BaseAlertDialog dialog) {
		dialog.dismiss();
		if(isDownloading){
			cannel();
		}
	}

	@Override
	public void right(BaseAlertDialog dialog) {
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
		if(isDownloading){
			dialog.dismiss();
		}else{
			download(url);
		}
	}

	public static class Builder extends BaseAlertDialog.Builder{

		private String version;
		private String content;
		private String url;
		private int force;
		private int status;

		public Builder(Context context) {
			super(context);
		}

		public Builder setVersion(String version) {
			this.version=version;
			return this;
		}

		public Builder setContent(String content) {
			this.content=content;
			return this;
		}

		public Builder setUrl(String url) {
			this.url=url;
			return this;
		}

		public Builder setForce(int force) {
			this.force=force;
			return this;
		}

		public Builder setStatus(int status) {
			this.status=status;
			return this;
		}

		private void construct(MyUpdateDialog dialog) {
			dialog.setLeft(left);
			dialog.setRight(right);
			dialog.setVersion(version);
			dialog.setContent(content);
			dialog.setUrl(url);
			dialog.setForce(force);
			dialog.setStatus(status);
		}

		@Override
		public MyUpdateDialog build() {
			MyUpdateDialog dialog = new MyUpdateDialog(context);
			construct(dialog);
			return dialog;
		}
	}

	public void show() {
		mInfos.clear();
		String title= MyStrHelper.getString(mContext, R.string.yz_base_check_update);
		String left= MyStrHelper.getString(mContext,R.string.yz_base_cancel);
		String right=isDownloading ? MyStrHelper.getString(mContext,R.string.yz_base_background_download) : MyStrHelper.getString(mContext,R.string.yz_base_ok);
		setTitle(title);
		setLeft(left);
		setRight(right);
		if(isDownloading){
			addProgress();
		}else{
			addMsg(content);
		}
		super.show();
	}

	private void addProgress(){
		CommonItem item=new CommonItem();
		item.setType(CommonItem.COMMON_TYPE_PROGRESS);
		mInfos.add(item);
		mAdapter.notifyDataSetChanged();
	}

	private void addMsg(String content){
		CommonItem item=new CommonItem();
		item.setContent(content);
		item.setType(CommonItem.COMMON_TYPE_MSG);
		mInfos.add(item);
		mAdapter.notifyDataSetChanged();
	}

	private void download(String url){
		if(TextUtils.isEmpty(url)){
			MyToasty.error(MyStrHelper.getString(mContext,R.string.yz_base_data_error));
			return;
		}
		mInfos.clear();
		addProgress();
		String rihgt= MyStrHelper.getString(mContext,R.string.yz_base_background_download);
		setRight(rihgt);
		if(isDownloading){
			return;
		}
		String name = "";
		String[] ss=url.split("/");
		if(ss!=null&&ss.length>0){
			name = ss[ss.length-1];
			if(!name.contains(".apk")){
				MyToasty.error(MyStrHelper.getString(mContext,R.string.yz_base_data_error));
				return;
			}
		}
		String dir = MyFileUtils.getTYJWPath();
		final String path = dir + name;
		mRequest=APIBase.download(url.replace(name,""), name, dir, name, new MyResultDownloadListener() {
			@Override
			public void onStart() {
				isDownloading=true;
			}
			@Override
			public void onFinished() {
			}
			@Override
			public void onSuccess(Object result) {
			}
			@Override
			public void onDownloading(int progress, boolean done) {
				setProgress(progress);
				if(done){
					dismiss();
					isDownloading=false;
					AppUtils.installApp(path);
				}
			}
			@Override
			public void onFailure(String msg) {
				dismiss();
				isDownloading=false;
				MyToasty.error("更新失败");
			}
		});
	}

	private void cannel(){
		isDownloading=false;
		APIBase.cancel(mRequest);
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

}
