package com.yz.base.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class MyArcMenu extends ViewGroup {
    
    private int radius = 200;//半径
    private Position position = Position.RIGHT_BOTTOM;
    private int mWidth;//控件的宽度
    private int mHeight;//控件的高度
    private View mFixedView;
    private boolean isRotate=false;
    private AtcMenuListener mListener;
    private Status curStatus = Status.STATUS_CLOSE;

    public enum Position {
        LEFT_TOP, LEFT_BOTTOM, RIGHT_BOTTOM, RIGHT_TOP;
    }

    public enum Status {
        STATUS_CLOSE, STATUS_OPEN;
    }

    public MyArcMenu(Context context) {
        this(context, null);
    }

    public MyArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public View getFixedView() {
        if(mFixedView==null){
            mFixedView=getChildAt(0);
        }
        return mFixedView;
    }

    public void setFixedView(View view) {
        this.mFixedView = view;
    }

    public boolean isRotate() {
        return isRotate;
    }

    public void setRotate(boolean rotate) {
        isRotate = rotate;
    }

    public AtcMenuListener getListener() {
        return mListener;
    }

    public void setListener(AtcMenuListener listener) {
        this.mListener = listener;
    }

    public void open(){
        if (curStatus == Status.STATUS_CLOSE) {
            switchArcView();
        }
    }

    public void close(){
        if (curStatus == Status.STATUS_OPEN) {
            switchArcView();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            layoutFixed();
            int count = getChildCount();
            //除去第一个button
            for (int i = 1; i < count ; i++) {
                View child = getChildAt(i);
                child.setVisibility(GONE);
                //Math.PI/180得到的结果就是1°  这里用Math.PI来计算表示180°，
                // 显示的范围为90度，所以是以90来平分的 Math.PI / 2 为90度
                int left = (int) ((Math.sin(Math.PI / 2 / (count - 2) * (i-1))) * radius);
                int top = (int) ((Math.cos(Math.PI / 2 / (count - 2) * (i-1))) * radius);
                //左下，右上
                if (position == Position.LEFT_BOTTOM || position == Position.RIGHT_BOTTOM) {
                    top = getMeasuredHeight() - top - child.getMeasuredHeight();
                }
                //右上，右下
                if (position == Position.RIGHT_TOP || position == Position.RIGHT_BOTTOM) {
                    left = getMeasuredWidth() - left - child.getMeasuredWidth();
                }
                child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
            }
        }
    }

    private void layoutFixed() {
        mFixedView = getChildAt(0);
        mFixedView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateFixedView(v);
                switchArcView();
                if(mListener!=null){
                    mListener.onFixedClick();
                }
            }
        });
        int l = 0;
        int t = 0;
        switch (position) {
            case LEFT_TOP:
                l = 0;
                t = 0;
                break;
            case LEFT_BOTTOM:
                l = 0;
                t = getMeasuredHeight() - mFixedView.getMeasuredHeight();
                break;
            case RIGHT_TOP:
                l = getMeasuredWidth() - mFixedView.getMeasuredWidth();
                t = 0;
                break;
            case RIGHT_BOTTOM:
                l = getMeasuredWidth() - mFixedView.getMeasuredWidth();
                t = getMeasuredHeight() - mFixedView.getMeasuredHeight();
                break;
        }
        mFixedView.layout(l, t, l + mFixedView.getMeasuredWidth(), t + mFixedView.getMeasuredHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
        if (count >= 2) {
            mWidth = radius + getChildAt(0).getMeasuredWidth() / 2 + getChildAt(1).getMeasuredWidth() / 2;
            mHeight = radius + getChildAt(0).getMeasuredHeight() / 2 + getChildAt(1).getMeasuredHeight() / 2;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    private void rotateFixedView(View view) {
        if (curStatus == Status.STATUS_CLOSE) {
            ObjectAnimator.ofFloat(view,"rotation",0,90).setDuration(300).start();
        }else{
            ObjectAnimator.ofFloat(view,"rotation",90,0).setDuration(300).start();
        }
    }

    private void switchArcView() {
        //需要移动到目标点--即按钮的中心位置
        int toX = mFixedView.getLeft();
        int toY = mFixedView.getTop();
        int count = getChildCount();
        for (int i = 1; i < count ; i++) {
            AnimatorSet set=new AnimatorSet();
            final View view = getChildAt(i);
            
            if(isRotate){
                Animator rotationAnimator;
                if (curStatus == Status.STATUS_OPEN) {
                    rotationAnimator=ObjectAnimator.ofFloat(view,"rotation",0,360);
                }else{
                    rotationAnimator=ObjectAnimator.ofFloat(view,"rotation",360,0);
                }
                set.playTogether(rotationAnimator);
            }

            Animator translateXAnimator;
            Animator translateYAnimator;
            if (curStatus == Status.STATUS_OPEN) {
                translateXAnimator=ObjectAnimator.ofFloat(view,"translationX", 0, toX - view.getLeft());
                translateYAnimator=ObjectAnimator.ofFloat(view,"translationY", 0, toY - view.getTop());
            } else {
                translateXAnimator=ObjectAnimator.ofFloat(view,"translationX", toX - view.getLeft(), 0);
                translateYAnimator=ObjectAnimator.ofFloat(view,"translationY", toY - view.getTop(), 0);
            }
            set.playTogether(translateXAnimator);
            set.playTogether(translateYAnimator);
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    view.setVisibility(VISIBLE);
                }
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (curStatus == Status.STATUS_CLOSE) {
                        view.setVisibility(GONE);
                    }
                }
                @Override
                public void onAnimationCancel(Animator animation) {
                }
                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            set.setDuration(300).start();

            final int position = i-1;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    scaleArcView(v);
                    if(mListener!=null){
                        mListener.onPopClick(v,position);
                    }
                }
            });
        }
        changeStatus();
    }

    private void scaleArcView(View view) {
        AnimatorSet set=new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(view,"scaleX", 1.0f, 1.5f, 1.0f));
        set.playTogether(ObjectAnimator.ofFloat(view,"scaleY", 1.0f, 1.5f, 1.0f));
        set.setDuration(300).start();
    }

    private void changeStatus() {
        if (curStatus == Status.STATUS_CLOSE) {
            curStatus = Status.STATUS_OPEN;
        } else {
            curStatus = Status.STATUS_CLOSE;
        }
    }
    
    public interface AtcMenuListener{
        void onFixedClick();
        void onPopClick(View view, int position);
    }
}
