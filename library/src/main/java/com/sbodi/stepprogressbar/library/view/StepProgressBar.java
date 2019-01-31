package com.sbodi.stepprogressbar.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.sbodi.stepprogressbar.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Satish Bodi
 * <p>
 * <p>
 * <p>
 * This custom StepView displays progress through a series of steps.
 * <p>
 * </p>
 * <p>
 * It places the steps with equal padding based on number of steps and available width.
 * <p>
 * It supports the below customizations through attributes:
 * <p>
 * "checkoutStepTitlesList" format="reference" - List of title texts
 * "checkoutStepTitleTextSize" format="dimension" - Title text size
 * "checkoutStepTitleTextColor" format="color" - Title text color
 * "checkoutStepTotalSteps" format="integer" - Number of steps
 * "checkoutStepBackgroundColor" format="color" - Background color
 * "checkoutStepCompletedDrawable" format="reference" - Drawable for completed state
 * "checkoutStepCurrentDrawable" format="reference" - Drawable for Current state
 * "checkoutStepFutureDrawable" format="reference" - Drawable for future state
 * "checkoutStepCompletedLineColor" format="color" - Color of completed line
 * "checkoutStepFutureLineColor" format="color" - Color of future line
 * "checkoutStepLineHeight" format="dimension" - Height of line
 * "checkoutStepCircleRadius" format="dimension" - Circle radius
 **/

public class StepProgressBar extends FrameLayout {

    private Drawable mCompletedIcon;
    private Drawable mCurrentIcon;
    private Drawable mFutureIcon;

    private List<ProgressStep> mProgressStepList;
    private List<Float> mCircleCenterPositionList;

    private float mCenterY;
    private float mLeftY;
    private float mRightY;
    private float mTextSize;
    private float mLinePadding;
    private float mCompletedLineHeight;
    private float mCircleRadius;

    private Paint mFutureLinePaint;
    private Paint mCompletedLinePaint;

    private int mStepNum;
    private int mScreenWidth;
    private int mTitleTextColor;

    private Typeface mTitleTypeface;

    public StepProgressBar(Context context) {
        this(context, null);
    }

    public StepProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {

        mProgressStepList = new ArrayList<>();
        mCircleCenterPositionList = new ArrayList<>();

        mFutureLinePaint = new Paint();
        mCompletedLinePaint = new Paint();

        TypedArray typedArray = context.obtainStyledAttributes(attributeSet,
                R.styleable.StepProgressBar);

        String fontName = typedArray.getString(R.styleable.StepProgressBar_stepProgressBarTitleFont);
        if (fontName == null) {
            fontName = "interstate_regular.ttf";
        }
        mTitleTypeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/"+fontName);

        mTextSize = typedArray.getDimension(R.styleable.StepProgressBar_stepProgressBarTitleTextSize,
                context.getResources().getDimension(R.dimen.default_text_size));

        mTitleTextColor = typedArray.getColor(
                R.styleable.StepProgressBar_stepProgressBarTitleTextColor,
                context.getResources().getColor(android.R.color.white));

        mStepNum = typedArray.getInt(R.styleable.StepProgressBar_stepProgressBarTotalSteps, 0);

        mLinePadding = 1.25f;

        int completedLineColor = typedArray.getColor(
                R.styleable.StepProgressBar_stepProgressBarCompletedLineColor,
                context.getResources().getColor(android.R.color.white));

        int futureLineColor = typedArray.getColor(
                R.styleable.StepProgressBar_stepProgressBarFutureLineColor,
                context.getResources().getColor(R.color.default_steps_view_future_line_color));

        mCompletedIcon = typedArray.getDrawable(
                R.styleable.StepProgressBar_stepProgressBarCompletedDrawable);

        mCurrentIcon = typedArray.getDrawable(
                R.styleable.StepProgressBar_stepProgressBarCurrentDrawable);

        mFutureIcon = typedArray.getDrawable(
                R.styleable.StepProgressBar_stepProgressBarFutureDrawable);

        int backgroundColor = typedArray.getColor(
                R.styleable.StepProgressBar_stepProgressBarBackgroundColor,
                context.getResources().getColor(R.color.default_steps_view_background_color));

        mCompletedLineHeight = typedArray.getDimension(
                R.styleable.StepProgressBar_stepProgressBarLineHeight,
                context.getResources().getDimension(R.dimen.default_line_height));

        mCircleRadius = typedArray.getDimension(
                R.styleable.StepProgressBar_stepProgressBarCircleRadius,
                context.getResources().getDimension(R.dimen.dimen_15dp));

        int titlesId = typedArray.getResourceId(R.styleable.StepProgressBar_stepProgressBarTitlesList,
                0);
        String[] titlesArray = typedArray.getResources().getStringArray(titlesId);
        setStepsList(titlesArray);

        typedArray.recycle();

        setWillNotDraw(false);
        setClipChildren(false);
        setBackgroundColor(backgroundColor);

        mFutureLinePaint.setAntiAlias(true);
        mFutureLinePaint.setColor(futureLineColor);
        mFutureLinePaint.setStrokeWidth(2);
        mFutureLinePaint.setStyle(Paint.Style.FILL);

        mCompletedLinePaint.setAntiAlias(true);
        mCompletedLinePaint.setColor(completedLineColor);
        mCompletedLinePaint.setStrokeWidth(2);
        mCompletedLinePaint.setStyle(Paint.Style.FILL);

    }

    private void setStepsList(String[] steps) {
        mProgressStepList = getStepList(steps);
        mStepNum = mProgressStepList.size();
    }

    /**
     * Call this method to update the progress bar position.
     *
     * @param position - new position of the progress.
     */
    public void updateStepState(int position) {
        for (int i = 0; i < mProgressStepList.size(); i++) {
            if (i < position) {
                mProgressStepList.get(i).setState(ProgressStep.STEP_COMPLETED);
            } else if (i == position) {
                mProgressStepList.get(i).setState(ProgressStep.STEP_CURRENT);
            } else {
                mProgressStepList.get(i).setState(ProgressStep.STEP_FUTURE);
            }
        }
        invalidate();
    }

    private List<ProgressStep> getStepList(String[] steps) {
        List<ProgressStep> stepsList = new ArrayList<>();

        for (String stepTitle : steps) {
            ProgressStep progressStep = new ProgressStep(stepTitle, ProgressStep.STEP_FUTURE);
            stepsList.add(progressStep);
        }
        return stepsList;
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = mScreenWidth =
                (int) (mStepNum * mCircleRadius * 4 * mLinePadding); // Default min width
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(widthMeasureSpec)) {
            width = mScreenWidth = MeasureSpec.getSize(widthMeasureSpec);
        }
        int height = (int) (mCircleRadius * 6); // Default min height
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(heightMeasureSpec)) {
            height = MeasureSpec.getSize(heightMeasureSpec);
            height += mCircleRadius * 3; // Increase height for title texts
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldwidth, int oldheight) {
        super.onSizeChanged(width, height, oldwidth, oldheight);

        mCenterY = 0.45f * getHeight(); // Considering the titles, starting just above the center
        mLeftY = mCenterY - (mCompletedLineHeight / 2);
        mRightY = mCenterY + mCompletedLineHeight / 2;

        mCircleCenterPositionList.clear();

        for (int i = 0; i < mStepNum; i++) {

            // Calculation for padding between each step:
            // Screen width - number of steps * circle radius * 2
            float padding = (mScreenWidth - (mStepNum * mCircleRadius * 2)) / (mStepNum - 1);

            // This list will have center positions of all step circles based on no. of steps and
            // width.
            // To calculate center point of each step circle:
            // Multiply padding with its position + number of steps so far + current step radius
            mCircleCenterPositionList.add(
                    (padding * i) + (i * mCircleRadius * 2) + (mCircleRadius));
        }

        requestLayout();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the view in 3 steps - Icons, Lines and Titles

        //=============== Step 1: Draw Icons ===================//
        for (int i = 0; i < mCircleCenterPositionList.size(); i++) {
            final float currentCompletedXPosition = mCircleCenterPositionList.get(i);
            Rect rect = new Rect((int) (currentCompletedXPosition - mCircleRadius),
                    (int) (mCenterY - mCircleRadius),
                    (int) (currentCompletedXPosition + mCircleRadius),
                    (int) (mCenterY + mCircleRadius));

            ProgressStep step = mProgressStepList.get(i);

            if (step.getState() == ProgressStep.STEP_FUTURE) {
                mFutureIcon.setBounds(rect);
                mFutureIcon.draw(canvas);
            } else if (step.getState() == ProgressStep.STEP_CURRENT) {
                mCurrentIcon.setBounds(rect);
                mCurrentIcon.draw(canvas);
            } else if (step.getState() == ProgressStep.STEP_COMPLETED) {
                mCompletedIcon.setBounds(rect);
                mCompletedIcon.draw(canvas);
            }
        }
        //=============== Step 1: Draw Icons ===================//


        //=============== Step 2: Draw Lines ===================//
        for (int i = 0; i < mCircleCenterPositionList.size() - 1; i++) {

            final float preCompletedXPosition = mCircleCenterPositionList.get(i);
            final float afterCompletedXPosition = mCircleCenterPositionList.get(i + 1);

            if (mProgressStepList.get(i).getState()
                    == ProgressStep.STEP_COMPLETED) {
                canvas.drawRect(preCompletedXPosition + mCircleRadius * mLinePadding, mLeftY,
                        afterCompletedXPosition - mCircleRadius * mLinePadding, mRightY,
                        mCompletedLinePaint);
            } else {
                canvas.drawRect(preCompletedXPosition + mCircleRadius * mLinePadding, mLeftY,
                        afterCompletedXPosition - mCircleRadius * mLinePadding, mRightY,
                        mFutureLinePaint);
            }
        }
        //=============== Step 2: Draw Lines ===================//


        //=============== Step 3: Draw Titles ==================//

        if (mProgressStepList != null && mCircleCenterPositionList != null
                && mCircleCenterPositionList.size() > 0) {

            for (int i = 0; i < mProgressStepList.size(); i++) {
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColor(mTitleTextColor);
                paint.setTextSize(mTextSize);
                canvas.drawText(mProgressStepList.get(i).getName(),
                        (mCircleCenterPositionList.get(i) - getTextWidth(i) / 2),
                        (mCenterY + mCircleRadius * 2.5f), paint);
            }
        }

        //=============== Step 3: Draw Titles ==================//
    }

    private int getTextWidth(int position) {
        // Measure the text view to get width for aligning it center to circle.
        Paint paint = new Paint();
        Rect bounds = new Rect();
        paint.setTextSize(mTextSize);
        paint.setTypeface(mTitleTypeface);

        String text = mProgressStepList.get(position).getName();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
    }

    public static class ProgressStep {
        public static final int STEP_FUTURE = -1;
        public static final int STEP_CURRENT = 0;
        public static final int STEP_COMPLETED = 1;

        private String name;
        private int state;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public ProgressStep(String name, int state) {
            this.name = name;
            this.state = state;
        }
    }
}
