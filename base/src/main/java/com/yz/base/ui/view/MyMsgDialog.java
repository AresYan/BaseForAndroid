package com.yz.base.ui.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yz.base.R;
import com.yz.base.item.CommonItem;
import com.yz.base.ui.base.BaseAlertDialog;
import com.yz.base.ui.base.BaseRecyclerDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :yanzheng
 * @describe ：自定义alert提示框
 */
public class MyMsgDialog extends BaseRecyclerDialog {

	private MyMsgDialog(Context context) {
		super(context);
	}

	public void setMessgae(String msg) {
		List<CommonItem> list=new ArrayList<CommonItem>();
		CommonItem item=new CommonItem();
		item.setContent(msg);
		item.setType(CommonItem.COMMON_TYPE_MSG);
		list.add(item);
		setAdapter(new MyMsgDialogAdapter(list));
	}

	public class MyMsgDialogAdapter extends BaseMultiItemQuickAdapter<CommonItem, BaseViewHolder> {

		public MyMsgDialogAdapter(List<CommonItem> list) {
			super(list);
			addItemType(CommonItem.COMMON_TYPE_MSG, R.layout.common_msg_item);
		}

		@Override
		protected void convert(BaseViewHolder helper, CommonItem item) {
			item.setPosition(helper.getAdapterPosition());
			TextView msgTv=helper.getView(R.id.common_msg_item_TextView);
			msgTv.setGravity(Gravity.CENTER);
			msgTv.setText(item.getContent());
		}
	}

	public static class Builder extends BaseAlertDialog.Builder{

		public String msg;

		public Builder(Context context) {
			super(context);
		}

		public Builder setMessgae(String msg) {
			this.msg=msg;
			return this;
		}

		private void construct(MyMsgDialog dialog) {
			dialog.setTitle(title);
			dialog.setLeft(left);
			dialog.setRight(right);
			dialog.setLeftEnabled(isLeftEnabled);
			dialog.setMyAlertDialogListener(mListener);
			dialog.setMessgae(msg);
		}

		@Override
		public MyMsgDialog build() {
			MyMsgDialog dialog = new MyMsgDialog(context);
			construct(dialog);
			return dialog;
		}
	}

}
