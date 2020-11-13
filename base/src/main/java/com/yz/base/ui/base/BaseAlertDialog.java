package com.yz.base.ui.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yz.base.R;
import com.yz.base.utils.MyStrHelper;

public abstract class BaseAlertDialog extends BaseDialog {

	private TextView mTitleTv;
	private TextView mLeftTv;
	private TextView mRightTv;
	private FrameLayout mFrameLayout;
	private RelativeLayout mButtonLayout;
	private View mCenterView;
	private MyAlertDialogListener mListener;
	private boolean isLeftEnabled=true;
	private boolean isRightEnabled=true;

	public BaseAlertDialog(Context context) {
		super(context);
		View view = LayoutInflater.from(context).inflate(R.layout.common_dialog_alert, null);
        setContentView(view);
        setCancelable(false);
		mTitleTv = view.findViewById(R.id.common_dialog_alert_TextView_title);
		mFrameLayout = view.findViewById(R.id.common_dialog_alert_FrameLayout);
		mButtonLayout = view.findViewById(R.id.common_dialog_alert_RelativeLayout_button);
		mLeftTv = view.findViewById(R.id.common_dialog_alert_TextView_left);
		mRightTv = view.findViewById(R.id.common_dialog_alert_TextView_right);
		mCenterView = view.findViewById(R.id.common_dialog_alert_View_center);
		mFrameLayout.addView(getFrameLayoutView());
		mLeftTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.left(BaseAlertDialog.this);
                }else{
					dismiss();
				}
            }
        });
        mRightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
		mLeftTv.setVisibility(isLeftEnabled?View.VISIBLE:View.GONE);
		mCenterView.setVisibility((!isLeftEnabled||!isRightEnabled)?View.GONE:View.VISIBLE);
		mButtonLayout.setVisibility((!isLeftEnabled&&!isRightEnabled)?View.GONE:View.VISIBLE);
	}

	public void setRightEnabled(boolean isRightEnabled) {
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
		public MyAlertDialogListener mListener;

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

		public Builder setMyAlertDialogListener(MyAlertDialogListener listener) {
			this.mListener = listener;
			return this;
		}

		public abstract BaseAlertDialog build();
	}
}
