package com.yz.base.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import com.yz.base.R;
import com.yz.base.ui.base.BasePopupWindow;

import java.util.Calendar;

/**
 * @author :yanzheng
 * @describe ：
 * @date ：2016年4月20日 下午7:09:44
 */
public class MyDatePopupWindow extends BasePopupWindow {

	public static final int NUM = 7;
	public static final int TYPE_YMD = 1;
	public static final int TYPE_YMDHM = 2;
	
	private View Wv1,Wv2,Wv3,Wv4,Wv5;
	
	private MyWheelView yearWv;
	private MyWheelView monthWv;
	private MyWheelView dayWv;
	private MyWheelView hourWv;
	private MyWheelView minWv;

	private int mYear=0;
	private int mMonth=0;
	private int mDay=0;
	private int mHour=0;
	private int mMin=0;
	private int yearSize = 100;
	private int h;

	private MyDatePopupWindowListener mDatelistener;
	private int type;
	private int forthYear;

	public void setDatelistener(MyDatePopupWindowListener mDatelistener) {
		this.mDatelistener = mDatelistener;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setForthYear(int forthYear) {
		this.forthYear = forthYear;
	}

	private MyDatePopupWindow(Context context) {
		super(context);
	}

	@Override
	protected int getConentView() {
		return R.layout.common_popup_wheel;
	}

	@Override
	public void show(View parent) {
		h = (int) mContext.getResources().getDimension(R.dimen.d120);
		setHeight(h*(NUM+1));
		mConentView.findViewById(R.id.common_wheel_popup_TextView_commit).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
				if(mDatelistener!=null){
					mDatelistener.onCommit(mYear,mMonth,mDay,mHour,mMin);
				}
			}
		});
		mConentView.findViewById(R.id.common_wheel_popup_TextView_cannel).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});

		Wv1 = mConentView.findViewById(R.id.common_wheel_popup_wv1);
		Wv2 = mConentView.findViewById(R.id.common_wheel_popup_wv2);
		Wv3 = mConentView.findViewById(R.id.common_wheel_popup_wv3);
		Wv4 = mConentView.findViewById(R.id.common_wheel_popup_wv4);
		Wv5 = mConentView.findViewById(R.id.common_wheel_popup_wv5);

		if(type==TYPE_YMD){
			Wv4.setVisibility(View.GONE);
			Wv5.setVisibility(View.GONE);
		}
		yearWv = getYearWheelView(getYearArray());
		yearWv.setCurrent(forthYear);
		hourWv = getHourWheelView(getHourArray());
		hourWv.setCurrent(0);
		minWv = getMinWheelView(getMinArray());
		minWv.setCurrent(0);
		super.show(parent);
	}

	public int getYearCurrentItem() {
		if (yearWv != null) {
			return yearWv.getCurrentIndex();
		}
		return 0;
	}

	public int getMonthCurrentItem() {
		if (monthWv != null) {
			return monthWv.getCurrentIndex();
		}
		return 0;
	}

	public int getDayCurrentItem() {
		if (dayWv != null) {
			return dayWv.getCurrentIndex();
		}
		return 0;
	}

	private int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR)-forthYear;
	}

	private int getCurrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH)+1;
	}

	private int getCurrentDay() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	private String[] getYearArray() {
		int year = getCurrentYear();
		String[] array = new String[yearSize];
		for (int i = 0; i < yearSize; i++) {
			String y=""+(year+i);
			if((year+i)<10){
				y="0"+(year+i);
			}
			array[i] = y + "年";
		}
		return array;
	}

	private String[] getMonthArray(int year) {
		int month = 1;
		if(year==getCurrentYear()){
			month=getCurrentMonth();
		}
		String[] array = new String[13-month];
		for(int i = 0; i < array.length; i++){
			String m=""+(month + i);
			if((month + i)<10){
				m="0"+(month + i);
			}
			array[i] = m + "月";
		}
		return array;
	}

	private String[] getDayArray(int year, int month) {
		int day=1;
		if(year==getCurrentYear()&&month==getCurrentMonth()){
			day=getCurrentDay();
		}
		int days = 0;
		switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				days = 31;
				break;
			case 2:
				days = year % 4 == 0 ? 29 : 28;
				break;
			default:
				days = 30;
				break;
		}
		String[] array = new String[days+1-day];
		for (int i = 0; i < array.length; i++) {
			String d=""+(day + i);
			if((day + i)<10){
				d="0"+(day + i);
			}
			array[i] = d + "日";
		}
		return array;
	}

	private String[] getHourArray() {
		String[] array = new String[24];
		for (int i = 0; i < 24; i++) {
			String h=""+i;
			if(i<10){
				h="0"+i;
			}
			array[i] = h + "时";
		}
		return array;
	}

	private String[] getMinArray() {
		String[] array = new String[60];
		for (int i = 0; i < 60; i++) {
			String m=""+i;
			if(i<10){
				m="0"+i;
			}
			array[i] = m + "分";
		}
		return array;
	}

	private MyWheelView getYearWheelView(String[] items) {
		return 	yearWv = new MyWheelView(Wv1, false, NUM, h, items, new OnItemChangeListener() {

			@Override
			public void onItemChange(int index, String item) {
				try {
					mYear = getYearCurrentItem()+getCurrentYear();
					monthWv = getMonthWheelView(getMonthArray(mYear));
					monthWv.setCurrent(getCurrentMonth()-1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private MyWheelView getMonthWheelView(String[] items) {
		return new MyWheelView(Wv2, false, NUM, h, items, new OnItemChangeListener() {

			@Override
			public void onItemChange(int index, String item) {
				try {
					if(mYear==getCurrentYear()){
						mMonth=index+getCurrentMonth();
					}else{
						mMonth=index+1;
					}
					dayWv = getDayWheelView(getDayArray(mYear,mMonth));
					dayWv.setCurrent(getCurrentDay()-1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private MyWheelView getDayWheelView(String[] items) {
		return new MyWheelView(Wv3, false, NUM, h, items, new OnItemChangeListener() {

			@Override
			public void onItemChange(int index, String item) {
				if(mYear==getCurrentYear()&&mMonth==getCurrentMonth()){
					mDay=index+getCurrentDay();
				}else{
					mDay=index+1;
				}
			}

		});
	}

	private MyWheelView getHourWheelView(String[] items) {
		return new MyWheelView(Wv4, false, NUM, h, items, new OnItemChangeListener() {

			@Override
			public void onItemChange(int index, String item) {
				mHour=index;
			}

		});
	}

	private MyWheelView getMinWheelView(String[] items) {
		return new MyWheelView(Wv5, false, NUM, h, items, new OnItemChangeListener() {

			@Override
			public void onItemChange(int index, String item) {
				mMin=index;
			}

		});
	}

	public interface MyDatePopupWindowListener {
		void onCommit(int year, int month, int day, int hour, int min);
	}

	public interface OnItemChangeListener {
		public void onItemChange(int currentIndex, String currentItem);
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

		public MyDatePopupWindowListener listener;
		public int type;
		public int forthYear;

		public Builder(Context context) {
			super(context);
		}

		public Builder setType(int type) {
			this.type=type;
			return this;
		}

		public Builder setForthYear(int forthYear) {
			this.forthYear=forthYear;
			return this;
		}

		public Builder setDateListener(MyDatePopupWindowListener listener) {
			this.listener=listener;
			return this;
		}

		private void construct(MyDatePopupWindow dialog) {
			dialog.setType(type);
			dialog.setForthYear(forthYear);
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
