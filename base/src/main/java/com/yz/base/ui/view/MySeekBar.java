package com.yz.base.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatSeekBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySeekBar extends AppCompatSeekBar {

	private final static int DEFAULT_POINT_RADIUS = 10; // 默认原点半径
	private final static int DEFAULT_LINE_WIDTH = 10; // 默认原点半径

	private Paint mLinePaint;
	private float mLineWidth = DEFAULT_LINE_WIDTH;
    private List<Integer> mLineColors; // 线段颜色集合
    private Paint mMarkPaint;
    private float mMarkRadius = DEFAULT_POINT_RADIUS;
    private int mMarkColor;
    private Map<Integer,Mark> mMarks = new HashMap<>(); // 标记点集合

	public MySeekBar(Context context) {
		super(context);
		init();
	}

	public MySeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MySeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mLinePaint.setColor(Color.TRANSPARENT);
        mMarkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMarkPaint.setColor(Color.TRANSPARENT);
	}

    public void setLineWidth(float width){
        mLineWidth=width;
        invalidate();
    }

	public void setLineColors(List<Integer> colors){
		mLineColors=colors;
		if(colors!=null&&!colors.isEmpty()){
			setBackgroundColor(Color.TRANSPARENT);
			setProgressDrawable(new ColorDrawable(Color.TRANSPARENT));
			invalidate();
		}
	}

    public void setMarkColor(int colors){
        mMarkColor=colors;
        invalidate();
    }

	public void addMark(int postion){
		if(mMarks.get(postion)==null){
			mMarks.put(postion,new Mark(postion));
			invalidate();
		}
	}

    public void removeMark(int postion){
        if(mMarks.get(postion)!=null){
            mMarks.remove(postion);
            invalidate();
        }
    }

    public void removeAllMark(){
	    if(!mMarks.isEmpty()){
            mMarks.clear();
            invalidate();
        }
    }

	@Override
	protected void onDraw(Canvas canvas) {
		drawMark(canvas);
		drawSegmentLine(canvas);
		super.onDraw(canvas);
	}

	private void drawMark(Canvas canvas){
		int thumbOffset = getThumbOffset();
		float width = getWidth();
		float cy = getHeight() / 2;
		float tmpX = 0;
		for (Map.Entry<Integer,Mark> entry : mMarks.entrySet()) {
			Mark mark=entry.getValue();
			float x = width * (mark.postion/(float)getMax());
			if(x < thumbOffset){
				tmpX = thumbOffset + mMarkRadius;
			}
			else if(x > (width - thumbOffset)){
				tmpX = width - thumbOffset - mMarkRadius;
			}
			else {
				tmpX = x;
			}
			mark.left = tmpX - mMarkRadius;
			mark.top = cy - mMarkRadius;
			mark.right = tmpX + mMarkRadius;
			mark.bottom = cy + mMarkRadius;
			mMarkPaint.setColor(mMarkColor);
			canvas.drawCircle(tmpX, cy, mMarkRadius, mMarkPaint);
		}
	}

	private void drawSegmentLine(Canvas canvas){
		if(mLineColors!=null&&!mLineColors.isEmpty()){
            int thumbOffset = getThumbOffset();
			float width = getWidth();
			float cy = getHeight() / 2;
			float startX=thumbOffset;
			float segmentWidth=(width-thumbOffset*2)/mLineColors.size();
			for (Integer color : mLineColors) {
				mLinePaint.setStrokeWidth(mLineWidth);
				mLinePaint.setColor(color);
				canvas.drawLine(startX, cy, width-thumbOffset, cy, mLinePaint);
				startX+=segmentWidth;
			}
		}
	}

	private float mTouchDownX,mTouchDownY;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!isEnabled()) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            	mTouchDownX = event.getX();
            	mTouchDownY = event.getY();
				break;
            case MotionEvent.ACTION_UP:
            	if(mTouchDownX == event.getX() && mTouchDownY == event.getY()){
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    int postion = -1;
					for (Map.Entry<Integer,Mark> entry : mMarks.entrySet()) {
						Mark mark=entry.getValue();
						if(mark.contains(x, y)){
							postion = mark.postion;
							break;
						}
					}
                    if(postion != -1){
                    	setProgress(postion);
                    	invalidate();
                    }
				}
                break;
        }
        return super.onTouchEvent(event);
	}

	public class Mark extends RectF {
		public int postion;
		public Mark(int postion) {
			this.postion = postion;
		}
	}
}
