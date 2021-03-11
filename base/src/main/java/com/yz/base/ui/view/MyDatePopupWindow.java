package com.yz.base.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yz.base.R;
import com.yz.base.R2;
import com.yz.base.ui.base.BasePopupWindow;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author :yanzheng
 * @describe ：
 * @date ：2016年4月20日 下午7:09:44
 */
public class MyDatePopupWindow extends BasePopupWindow {

	public static final int ITEM_NUM = 7;
	public static final int TYPE_YMD = 1;
	public static final int TYPE_YMDHM = 2;
	private static final int YAER_SIZE = 100;

	private MyWheelView yearWv;
	private MyWheelView monthWv;
	private MyWheelView dayWv;
	private MyWheelView hourWv;
	private MyWheelView minWv;

	private int type=TYPE_YMDHM;
	private int year =0;
	private int month =0;
	private int day =0;
	private int hour =0;
	private int min =0;

	private int outYear=0;
	private int outMonth=0;
	private int outDay=0;
	private int outHour=0;
	private int outMin=0;

	private int itemHeight;

	@BindView(R2.id.common_wheel_popup_wv1)
	View Wv1;
	@BindView(R2.id.common_wheel_popup_wv2)
	View Wv2;
	@BindView(R2.id.common_wheel_popup_wv3)
	View Wv3;
	@BindView(R2.id.common_wheel_popup_wv4)
	View Wv4;
	@BindView(R2.id.common_wheel_popup_wv5)
	View Wv5;

	@SuppressLint("InvalidR2Usage")
	@OnClick(value = {
			R2.id.common_wheel_popup_TextView_commit})
	protected void onViewClick(View v){
		switch(v.getId()){
			case R2.id.common_wheel_popup_TextView_commit:
				dismiss();
				if(mDatelistener!=null){
					mDatelistener.onCommit(outYear,outMonth,outDay,outHour,outMin);
				}
				break;
			case R2.id.common_wheel_popup_TextView_cannel:
				dismiss();
				break;
		}
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public void setMin(int min) {
		this.min = min;
	}

	private MyDatePopupWindowListener mDatelistener;

	public void setDatelistener(MyDatePopupWindowListener mDatelistener) {
		this.mDatelistener = mDatelistener;
	}

	private MyDatePopupWindow(Context context) {
		super(context);
		itemHeight = (int) mContext.getResources().getDimension(R.dimen.d120);
		setHeight(itemHeight*(ITEM_NUM +1));
		dayWv = getDayWheelView(getDayArray(getStartYear(),1));
		monthWv = getMonthWheelView(getMonthArray());
		yearWv = getYearWheelView(getYearArray());
		hourWv = getHourWheelView(getHourArray());
		minWv = getMinWheelView(getMinArray());
	}

	@Override
	protected int getConentView() {
		return R.layout.common_popup_wheel;
	}

	@Override
	public void show(View parent) {
		if(type==TYPE_YMD){
			Wv4.setVisibility(View.GONE);
			Wv5.setVisibility(View.GONE);
		}
		yearWv.setCurrent(year -getStartYear());
		monthWv.setCurrent(month -1);
		try{
			dayWv.setCurrent(day -1);
		}catch(Exception e){
			String[] days=getDayArray(year, month);
			dayWv.setCurrent(days.length-1);
		}
		hourWv.setCurrent(hour);
		minWv.setCurrent(min);
		super.show(parent);
	}

	private int getStartYear(){
		return Calendar.getInstance().get(Calendar.YEAR)-50;
	}

	private String[] getYearArray() {
		int startYear = getStartYear();
		String[] array = new String[YAER_SIZE];
		for (int i = 0; i < YAER_SIZE; i++) {
			array[i] = (startYear+i) + "年";
		}
		return array;
	}

	private String[] getMonthArray() {
		String[] array = new String[12];
		for(int i = 1; i <= array.length; i++){
			array[i-1] = (i<10?("0"+i):i)+ "月";
		}
		return array;
	}

	private String[] getDayArray(int year, int month) {
		int size = 0;
		switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				size = 31;
				break;
			case 2:
				size = year % 4 == 0 ? 29 : 28;
				break;
			default:
				size = 30;
				break;
		}
		String[] array = new String[size];
		for (int i = 1; i <= array.length; i++) {
			array[i-1] = (i<10?("0"+i):i)+ "日";
		}
		return array;
	}

	private String[] getHourArray() {
		String[] array = new String[24];
		for (int i = 0; i < 24; i++) {
			array[i] = (i<10?("0"+i):i)+ "时";
		}
		return array;
	}

	private String[] getMinArray() {
		String[] array = new String[60];
		for (int i = 0; i < 60; i++) {
			array[i] = (i<10?("0"+i):i)+ "分";
		}
		return array;
	}

	private MyWheelView getYearWheelView(String[] items) {
		return 	yearWv = new MyWheelView(Wv1, false, ITEM_NUM, itemHeight, items, new OnItemChangeListener() {
			@Override
			public void onItemChange(int index, String item) {
				outYear=Integer.parseInt(item.substring(0,item.length()-1));
			}
		});
	}

	private MyWheelView getMonthWheelView(String[] items) {
		return new MyWheelView(Wv2, false, ITEM_NUM, itemHeight, items, new OnItemChangeListener() {
			@Override
			public void onItemChange(int index, String item) {
				outMonth=Integer.parseInt(item.substring(0,item.length()-1));
				dayWv.setItems(getDayArray(outYear,outMonth));
			}
		});
	}

	private MyWheelView getDayWheelView(String[] items) {
		return new MyWheelView(Wv3, false, ITEM_NUM, itemHeight, items, new OnItemChangeListener() {
			@Override
			public void onItemChange(int index, String item) {
				outDay=Integer.parseInt(item.substring(0,item.length()-1));
			}
		});
	}

	private MyWheelView getHourWheelView(String[] items) {
		return new MyWheelView(Wv4, false, ITEM_NUM, itemHeight, items, new OnItemChangeListener() {

			@Override
			public void onItemChange(int index, String item) {
				outHour=index;
			}
		});
	}

	private MyWheelView getMinWheelView(String[] items) {
		return new MyWheelView(Wv5, false, ITEM_NUM, itemHeight, items, new OnItemChangeListener() {

			@Override
			public void onItemChange(int index, String item) {
				outMin=index;
			}
		});
	}

	public interface MyDatePopupWindowListener {
		void onCommit(int year, int month, int day, int hour, int min);
	}

	public interface OnItemChangeListener {
		void onItemChange(int currentIndex, String currentItem);
	}

	public class MyWheelView {

		private Context mContext;
		private RelativeLayout wheelRl;
		private ListView lv;
		private View wheelTopShadowView;
		private View wheelBottomShadowView;

		private String[] items;

		private int showItemNum;

		private int itemHeight;

		private ItemAdapter adapter;

		private int currentItem = 0;

		private int currentIndex = 0;

		private OnItemChangeListener listener;

		private boolean isCircle;

		public MyWheelView(View wheelView, boolean isCircle, int showItemNum,
						   int itemHeight, String[] items, OnItemChangeListener listener) {
			mContext=wheelView.getContext();
			wheelRl = (RelativeLayout) wheelView.findViewById(R.id.wheelRl);
			lv = (ListView) wheelView.findViewById(R.id.wheelLv);
			wheelTopShadowView = wheelView.findViewById(R.id.wheelTopShadowView);
			wheelBottomShadowView = wheelView
					.findViewById(R.id.wheelBottomShadowView);

			this.showItemNum = showItemNum;
			this.itemHeight = itemHeight;
			this.items = items;
			this.isCircle = isCircle;

			this.listener = listener;

			initLv();
		}

		private void setViewHeight(View view, int height) {
			ViewGroup.LayoutParams lp = view.getLayoutParams();
			lp.height = height;
			view.setLayoutParams(lp);
		}

		private void initLv() {

			setViewHeight(wheelRl, showItemNum * itemHeight);

			setViewHeight(wheelTopShadowView, (showItemNum - 1) / 2 * itemHeight);
			setViewHeight(wheelBottomShadowView, (showItemNum - 1) / 2 * itemHeight);

			adapter = new MyWheelView.ItemAdapter();

			lv.setAdapter(adapter);

			lv.setOnScrollListener(new AbsListView.OnScrollListener() {

				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {

					if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {

						View c = lv.getChildAt(0);
						int firstVisiblePosition = lv.getFirstVisiblePosition();
						int top = c.getTop();
						int height = c.getHeight();
						if (top < -(height / 2)) {
							top += height;
							firstVisiblePosition += 1;
						}

						// lv.smoothScrollBy(top);

						lv.setSelection(firstVisiblePosition);

						if (isCircle) {
							int cha = firstVisiblePosition - (showItemNum - 1) / 2
									+ currentItem;

							cha = cha % items.length;

							currentIndex = cha < 0 ? (items.length + cha) : cha;
						} else {
							currentIndex = firstVisiblePosition;
						}

						listener.onItemChange(currentIndex, items[currentIndex]);

					}

				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
									 int visibleItemCount, int totalItemCount) {

					if (isCircle) {

						if (firstVisibleItem == 0) {
							currentItem -= 2;

							if (currentItem < 0) {
								currentItem += items.length;
							}

							currentIndex = currentItem;

							update();
						} else if (firstVisibleItem >= (showItemNum - 2)) {

							currentItem += firstVisibleItem - (showItemNum - 1) / 2;

							if (currentItem >= items.length) {
								currentItem -= items.length;
							}

							currentIndex = currentItem;

							update();
						}
					}

				}
			});

			currentItem = 0;
			currentIndex = 0;
			update();
		}

		private void update() {
			adapter.notifyDataSetChanged();
			if (isCircle) {
				lv.setSelection((showItemNum - 1) / 2);
			} else {
				lv.setSelection(currentItem);
			}

			listener.onItemChange(currentIndex, items[currentIndex]);
		}

		private class ItemAdapter extends BaseAdapter {

			@Override
			public int getCount() {
				return isCircle ? (showItemNum * 2 - 1) : (items.length
						+ showItemNum - 1);
			}

			@Override
			public Object getItem(int position) {
				return null;
			}

			@Override
			public long getItemId(int position) {
				return 0;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.common_wheel_item, null, false);

				LinearLayout itemLl = (LinearLayout) convertView
						.findViewById(R.id.itemLl);

				ViewGroup.LayoutParams lp = itemLl.getLayoutParams();
				lp.height = itemHeight;
				itemLl.setLayoutParams(lp);

				TextView tv = (TextView) convertView.findViewById(R.id.itemTv);

				if (isCircle) {
					int cha = position - showItemNum + 1 + currentItem;

					cha = cha % items.length;

					cha = cha < 0 ? (items.length + cha) : cha;

					tv.setText(items[cha]);
				} else {

					int index = position - 3;

					if (index >= items.length || index < 0) {
						tv.setText("");
					} else {
						tv.setText(items[index]);
					}

				}

				return convertView;
			}

		}

		public int getCurrentIndex() {
			return currentIndex;
		}

		public void setCurrent(int index) {
			if (index >= items.length) {
				index = items.length - 1;
			}
			currentIndex = index;
			currentItem = index;

			update();
		}

		public String getCurrentItem() {
			return items[currentIndex];
		}

		public void setItems(String[] items) {
			this.items = items;

			if (currentIndex >= items.length) {
				currentIndex = items.length - 1;
				currentItem = items.length - 1;
			}
			update();
		}
	}

	public static class Builder extends BasePopupWindow.Builder{

		private MyDatePopupWindowListener listener;
		private int type;
		private int year=0;
		private int month=0;
		private int day=0;
		private int hour=0;
		private int min=0;

		public Builder(Context context) {
			super(context);
		}

		public Builder setType(int type) {
			this.type=type;
			return this;
		}

		public Builder setYear(int year) {
			this.year=year;
			return this;
		}

		public Builder setMonth(int month) {
			this.month=month;
			return this;
		}

		public Builder setDay(int day) {
			this.day=day;
			return this;
		}

		public Builder setHour(int hour) {
			this.hour=hour;
			return this;
		}

		public Builder setMin(int min) {
			this.min=min;
			return this;
		}

		public Builder setDateListener(MyDatePopupWindowListener listener) {
			this.listener=listener;
			return this;
		}

		private void construct(MyDatePopupWindow dialog) {
			dialog.setType(type);
			dialog.setYear(year);
			dialog.setMonth(month);
			dialog.setDay(day);
			dialog.setHour(hour);
			dialog.setMin(min);
			dialog.setDatelistener(listener);
		}

		@Override
		public MyDatePopupWindow build() {
			MyDatePopupWindow dialog = new MyDatePopupWindow(context);
			construct(dialog);
			return dialog;
		}
	}
}
