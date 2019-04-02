package com.pn.android.portal.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class CircleRectFooter extends View {

    private final static String TAG = CircleRectFooter.class.getSimpleName();
    private static Handler mainHandler = new Handler(Looper.getMainLooper());
    /**
     * View的宽度
     **/
    private int mWidth;
    /**
     * View的高度
     **/
    private int mHeight;
    /**
     * 右侧矩形的宽度
     **/
    private float mRectWidth = 48f;
    /**
     * 右侧矩形中文字字体大小
     **/
    private int mTextSize = 42;

    /**
     * 右侧矩形中文字Padding
     **/
    private int mTextPadding = 12;

    /**
     * 滑动时相对初始位置横向偏移量
     **/
    private float mOffSetX = 0f;
    /**
     * 滑动相对初始位置最大横向偏移量
     **/
    private float mMaxOffSetX = 180f;
    /**
     * 从查看更多滑动至松开查看临界时刻的横向偏移量
     **/
    private float mCriticalOffSetX = 90f;
    /**
     * 滑动最大横向偏移量时刻右侧矩形最低高度
     **/
    private float mMinHeight;
    /**
     * 右侧矩形中文字中心点
     **/
    private PointF mTextCenterPoint;
    private Path mPath;
    private Paint mPaint;
    private TextPaint mTextPaint;
    private RightCircleRect mRightCircleRect;
    private ResetViewRunnable mResetViewRunnable;
    private String strMore = "查看更多";
    private String strReleaseToMore = "松开查看";

    public CircleRectFooter(Context context) {
        this(context, null, 0);
    }

    public CircleRectFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleRectFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mResetViewRunnable != null) {
            //TODO 替换方法
            mainHandler.removeCallbacks(mResetViewRunnable);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        calculateCoordinate();
        canvas.translate(mWidth, mHeight / 2f);
        mPath.moveTo(mRightCircleRect.topLeft.x, mRightCircleRect.topLeft.y);
        if (mRightCircleRect.controlPoint.x < -mRectWidth) {
            mPath.quadTo(mRightCircleRect.controlPoint.x, mRightCircleRect.controlPoint.y, mRightCircleRect.bottomLeft.x, mRightCircleRect.bottomLeft.y);
        } else {
            mPath.lineTo(mRightCircleRect.bottomLeft.x, mRightCircleRect.bottomLeft.y);
        }
        mPath.lineTo(mRightCircleRect.bottomRight.x, mRightCircleRect.bottomRight.y);
        mPath.lineTo(mRightCircleRect.topRight.x, mRightCircleRect.topRight.y);
        mPath.lineTo(mRightCircleRect.topLeft.x, mRightCircleRect.topLeft.y);
        canvas.drawPath(mPath, mPaint);
        if (mOffSetX > mCriticalOffSetX) {
            drawText(strReleaseToMore, mTextPaint, canvas, mTextCenterPoint);
        } else {
            drawText(strMore, mTextPaint, canvas, mTextCenterPoint);
        }
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(android.R.color.holo_red_light));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);
        mPath = new Path();
        mRightCircleRect = new RightCircleRect();
        mTextCenterPoint = new PointF();
        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    private void calculateCoordinate() {
        if (mOffSetX < 0) {
            mOffSetX = 0f;
        } else if (mOffSetX > mMaxOffSetX) {
            mOffSetX = mMaxOffSetX;
        }
        mRightCircleRect.setControlX(-mRectWidth);
        mRightCircleRect.setY(-mHeight / 2f, mHeight / 2f);
        mRightCircleRect.setX(-mRectWidth, 0f);
        mRightCircleRect.adjustControlX(-mOffSetX);
        mRightCircleRect.adjustAllY((mHeight - mMinHeight) / 2 * (mOffSetX / mMaxOffSetX));
    }

    private void initLayout() {
        mWidth = getWidth();
        mHeight = getHeight();
        Rect rect = new Rect();
        mTextPaint.getTextBounds(strMore, 0, strMore.length(), rect);
        int singleTextWidth = rect.width() / strMore.length();
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        mMinHeight = (strMore.length() - 1)* (-top + bottom) + (-fontMetrics.ascent + fontMetrics.descent)+ mTextPadding * 2;
        if (mMinHeight > mHeight) {
            mHeight = (int) (mMinHeight + 0.5f);
        }
        if (singleTextWidth + mTextPadding * 2 > mRectWidth) {
            mRectWidth = singleTextWidth + mTextPadding * 2;
        }
        if (mRectWidth > mWidth) {
            mWidth = (int) (mRectWidth + 0.5f);
        }
        mTextCenterPoint.set(-mRectWidth / 2, 0f);
    }

    /**
     * 在右侧矩形中间绘制文字
     */
    private void drawText(String text, Paint paint, Canvas canvas, PointF point) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        int length = text.length();
        float total = (length - 1) * (-top + bottom) + (-fontMetrics.ascent + fontMetrics.descent);
        float offset = total / 2 - bottom;
        for (int i = 0; i < length; i++) {
            float yAxis = -(length - i - 1) * (-top + bottom) + offset;
            canvas.drawText(text.charAt(i) + "", point.x, point.y + yAxis, paint);
        }
    }

    private void resetView() {
        if (mResetViewRunnable == null) {
            mResetViewRunnable = new ResetViewRunnable();
        }
        // TODO ThreadUtil.excute(mResetViewRunnable);
        new Thread(mResetViewRunnable).start();
    }

    public void startAnimation() {
        MoveAnimation move = new MoveAnimation();
        move.setDuration(1000);
        startAnimation(move);
    }

    private static class RightCircleRect {
        private PointF controlPoint = new PointF();
        private PointF topLeft = new PointF();
        private PointF topRight = new PointF();
        private PointF bottomLeft = new PointF();
        private PointF bottomRight = new PointF();

        private void setControlX(float x) {
            controlPoint.x = x;
        }

        private void setX(float leftX, float rightX) {
            topLeft.x = leftX;
            topRight.x = rightX;
            bottomLeft.x = leftX;
            bottomRight.x = rightX;
        }

        private void setY(float topY, float bottomY) {
            topLeft.y = topY;
            topRight.y = topY;
            bottomLeft.y = bottomY;
            bottomRight.y = bottomY;
        }

        private void adjustAllY(float offset) {
            topLeft.y += offset;
            topRight.y += offset;
            bottomLeft.y -= offset;
            bottomRight.y -= offset;
        }

        private void adjustControlX(float offset) {
            controlPoint.x += offset;
        }
    }

    /**
     * 回弹
     **/
    private class ResetViewRunnable implements Runnable {

        @Override
        public void run() {
            //回弹持续时间
            final int time = 1500;
            final float offset = mMaxOffSetX;
            final long startTime = System.currentTimeMillis();
            long nowTime = System.currentTimeMillis();
            while (startTime + time >= nowTime) {
                nowTime = System.currentTimeMillis();
                final float dt = nowTime - startTime;
                mOffSetX = offset * (time - dt) / time;
                postInvalidate();
            }
        }
    }

    private class MoveAnimation extends Animation {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime >= 0 && interpolatedTime < 0.5) {
                mOffSetX = mMaxOffSetX *2 * interpolatedTime;
                invalidate();
            } else if (interpolatedTime > 0.5 && interpolatedTime < 1.0) {
                mOffSetX = mMaxOffSetX *2 * (1-interpolatedTime);
                invalidate();
            } else if (interpolatedTime == 1.0) {
//                resetView();
            }
        }
    }
}
