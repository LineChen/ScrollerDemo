package com.beiing.scrollerdemo.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by chenliu on 2016/10/20.<br/>
 * 描述：主要测试
 * </br>
 */
public class TestScroll extends View {

    String TAG = "TestScroll";

    private Paint paint;

    /**
     * mMove为偏移量
     */
    int mLastX, mMove;

    private Scroller mScroller;

    private GestureDetectorCompat gestureDetector;

    public TestScroll(Context context) {
        super(context);
    }

    public TestScroll(Context context, AttributeSet attrs) {
        super(context, attrs);

        mScroller = new Scroller(getContext());

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setTextSize(60);

        gestureDetector = new GestureDetectorCompat(getContext(), new SimpleGestureListener());

    }

    public TestScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.MAGENTA);
        paint.setAlpha(100);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);

        paint.setAlpha(255);
        paint.setColor(Color.BLACK);

        canvas.drawText("noScroll", 0, 50, paint);

        mMove = Math.max(0, mMove);
        mMove = Math.min(getMeasuredWidth() - 200, mMove);
//        Log.e(TAG, "mMove=" + mMove);
        canvas.drawText("...TestScroll...", mMove, 150, paint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        int xPosition = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mScroller.abortAnimation();
                mLastX = xPosition;
                return true;
            case MotionEvent.ACTION_MOVE:
                smoothScrollBy(xPosition - mLastX, 0);
                break;
        }
        mLastX = xPosition;
        return true;
    }


    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {
        Log.e(TAG, "smoothScrollBy:" + dx);
        //设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {
        Log.e(TAG, "-----------computeScroll");
        if (mScroller.computeScrollOffset()) {
            mMove = mScroller.getCurrX();
            Log.e(TAG, "mMove:" + mMove);
            postInvalidate();
        }
    }

    private class SimpleGestureListener extends
            GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            mScroller.fling(mMove, 0, (int)velocityX, (int)velocityY, 0, getMeasuredWidth(), 0, 0);
            return true;
        }
    }

}
