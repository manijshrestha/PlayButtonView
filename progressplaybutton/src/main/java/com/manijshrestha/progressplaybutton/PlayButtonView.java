package com.manijshrestha.progressplaybutton;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Play Button View with custom circular progress
 * <p/>
 * Created by manijshrestha on 6/23/16.
 */
public class PlayButtonView extends View {

    private static final int DEFAULT_ANIMATION_DURATION = 300;

    private Paint mProgressPaint;
    private float mStrokeSize;

    //region Settable Properties
    // Color of the progress bar
    @ColorInt
    private int mProgressColor;

    // Width of the progress bar
    private float mProgressWidthDP;

    // Progress
    @FloatRange(from = 0, to = 100)
    private float mProgress;

    // ProgressViewRect
    private RectF mProgressRect;

    private Rect mButtonStateRect;

    // Player Button Drawables
    private Drawable mPlayDrawable;
    private Drawable mPauseDrawable;
    //endregion

    public PlayButtonView(Context context) {
        super(context);
        init();
    }

    public PlayButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PlayButtonView, 0, 0);
        try {
            mProgressWidthDP = typedArray.getDimension(R.styleable.PlayButtonView_progressWidth, 8);
            mProgress = typedArray.getFloat(R.styleable.PlayButtonView_progress, 0);
            mProgressColor = typedArray.getColor(R.styleable.PlayButtonView_progressColor, ContextCompat.getColor(context, android.R.color.holo_blue_dark));
        } finally {
            typedArray.recycle();
        }

        init();
    }

    public PlayButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * View Initializer
     */
    private void init() {
        // Progress Paint
        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mStrokeSize = mProgressWidthDP * getResources().getDisplayMetrics().density;
        mProgressPaint.setStrokeWidth(mStrokeSize);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        mProgressPaint.setStrokeJoin(Paint.Join.ROUND);
        mProgressRect = new RectF();
        mButtonStateRect = new Rect();
        mPlayDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_play_arrow);
        mPauseDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_pause);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int targetWidthHeight = Math.min(width, height);
        float mProgressViewSize = targetWidthHeight - mStrokeSize;
        mProgressRect.set(mStrokeSize, mStrokeSize, mProgressViewSize, mProgressViewSize);
        mProgressRect.roundOut(mButtonStateRect);
        mPlayDrawable.setBounds(mButtonStateRect);
        mPauseDrawable.setBounds(mButtonStateRect);
        setMeasuredDimension(targetWidthHeight, targetWidthHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(mProgressRect, -90, getSweepAngle(mProgress), false, mProgressPaint);
        mPlayDrawable.draw(canvas);
    }

    public void setProgress(@FloatRange(from = 0, to = 100) float progress, boolean animate) {
        if (animate) {
            ObjectAnimator progressInternal = ObjectAnimator.ofFloat(this, "ProgressInternal", mProgress, progress);
            progressInternal.setDuration(DEFAULT_ANIMATION_DURATION);
            progressInternal.start();
        } else {
            setProgressInternal(progress);
        }
    }

    private void setProgressInternal(float progressInternal) {
        mProgress = progressInternal;
        invalidate();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    /**
     * Given % calculates the progress sweep angle
     * Progress 100 = 360
     *
     * @param progress between 0 - 100
     * @return angle to draw
     */
    private float getSweepAngle(@FloatRange(from = 0, to = 100) float progress) {
        return progress * 3.6f;
    }
}
