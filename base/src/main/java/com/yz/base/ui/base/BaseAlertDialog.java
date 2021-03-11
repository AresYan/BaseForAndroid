package com.yz.base.ui.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yz.base.R;
import com.yz.base.R2;
import com.yz.base.utils.MyStrHelper;

import butterknife.BindView;

public abstract class BaseAlertDialog extends BaseDialog {

	private MyAlertDialogListener mListener;
	private boolean isLeftEnabled=true;
	private boolean isRightEnabled=true;

	@BindView(R2.id.common_dialog_alert_TextView_title)
	TextView mTitleTv;
	@BindView(R2.id.common_dialog_alert_TextView_left)
	TextView mLeftTv;
	@BindView(R2.id.common_dialog_alert_TextView_right)
	TextView mRightTv;
	@BindView(R2.id.common_dialog_alert_FrameLayout)
	FrameLayout mFrameLayout;
	@BindView(R2.id.common_dialog_alert_RelativeLayout_button)
	RelativeLayout mButtonLayout;
	@BindView(R2.id.common_dialog_alert_View_center)
	View mCenterView;

	public BaseAlertDialog(Context context) {
		super(context);
		View view = LayoutInflater.from(context).inflate(R.layout.common_dialog_alert, null);
        setContentView(view);
        setCancelable(false);
		mFrameLayout.addView(getFrameLayoutView());
		mLeftTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(mListener!=null){
					mListener.left(BaseAlertDialog.this);
				}else{
					dismiss();
				}
			}
		});
		mRightTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(mListener!=null){
					mListener.right(BaseAlertDialog.this);
				}else{
					dismiss();
				}
			}
		});
	}

	public abstract View getFrameLayoutView();

	public void setMyAlertDialogListener(MyAlertDialogListener listener) {
		mListener = listener;
	}

	public void setTitle(String title){
		mTitleTv.setText(title);
		mTitleTv.setVisibility(!TextUtils.isEmpty(title)?View.VISIBLE:View.GONE);
	}

	public void setLeft(String left){
		mLeftTv.setText(left);
	}

	public void setRight(String right){
		mRightTv.setText(right);
	}

	public void setLeftEnabled(boolean isLeftEnabled) {
		this.isLeftEnabled=isLeftEnabled;
		mLeftTv.setVisibility(isLeftEnabled?View.VISIBLE:View.GONE);
		mCenterView.setVisibility((!isLeftEnabled||!isRightEnabled)?View.GONE:View.VISIBLE);
		mButtonLayout.setVisibility((!isLeftEnabled&&!isRightEnabled)?View.GONE:View.VISIBLE);
	}

	public void setRightEnabled(boolean isRightEnabled) {
		this.isRightEnabled=isRightEnabled;
		mRightTv.setVisibility(isRightEnabled?View.VISIBLE:View.GONE);
		mCenterView.setVisibility((!isLeftEnabled||!isRightEnabled)?View.GONE:View.VISIBLE);
		mButtonLayout.setVisibility((!isLeftEnabled&&!isRightEnabled)?View.GONE:View.VISIBLE);
	}

	public interface MyAlertDialogListener{
		void left(BaseAlertDialog dialog);
		void right(BaseAlertDialog dialog);
	}

	public static abstract class Builder {
		public Context context;
		public String title;
        public String left;
        public String right;
		public boolean isLeftEnabled=true;
		public boolean isRightEnabled=true;
		public MyAlertDialogListener listener;

		public Builder(Context context){
            this.context=context;
			this.left = MyStrHelper.getString(context, R.string.yz_base_cancel);
			this.right = MyStrHelper.getString(context, R.string.yz_base_ok);
        }

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setLeft(String left) {
			this.left = left;
			return this;
		}

		public Builder setRight(String right) {
			this.right = right;
			return this;
		}

		public Builder setLeftEnabled(boolean isleftEnabled) {
			this.isLeftEnabled = isleftEnabled;
			return this;
		}

        public Builder setRightEnabled(boolean isRightEnabled) {
            this.isRightEnabled = isRightEnabled;
            return this;
        }

		public Builder setMyAlertDialogListener(MyAlertDialogListener listener) {
			this.listener = listener;
			return this;
		}

		public void construct(BaseAlertDialog dialog) {
			dialog.setTitle(title);
			dialog.setLeft(left);
			dialog.setRight(right);
			dialog.setLeftEnabled(isLeftEnabled);
			dialog.setRightEnabled(isRightEnabled);
			dialog.setMyAlertDialogListener(listener);
		}

		public abstract BaseAlertDialog build();
	}
}
