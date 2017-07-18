package com.example.g150s.overwatchloadingview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by G150S on 2017/3/8.
 */

public class HexagonDrawable extends View {

    private static final String TAG = HexagonDrawable.class.getSimpleName();

    private int HexagonAnimatorFlag = 1;

    //最中心的六边形画笔 标号为①
    private Paint mHexagonPaint1;
    //六边形路径
    private Path mHexagonPath;
    //②六边形画笔
    private Paint mHexagonPaint2;
    //③六边形画笔
    private Paint mHexagonPaint3;
    private Paint mHexagonPaint4;
    private Paint mHexagonPaint5;
    private Paint mHexagonPaint6;
    private Paint mHexagonPaint7;

    private ValueAnimator mHexagonAnimatorStart1;
    private ValueAnimator mHexagonAnimatorStart2;
    private ValueAnimator mHexagonAnimatorStart3;
    private ValueAnimator mHexagonAnimatorStart4;
    private ValueAnimator mHexagonAnimatorStart5;
    private ValueAnimator mHexagonAnimatorStart6;
    private ValueAnimator mHexagonAnimatorStart7;

    private ValueAnimator mHexagonAnimatorEnd1;
    private ValueAnimator mHexagonAnimatorEnd2;
    private ValueAnimator mHexagonAnimatorEnd3;
    private ValueAnimator mHexagonAnimatorEnd4;
    private ValueAnimator mHexagonAnimatorEnd5;
    private ValueAnimator mHexagonAnimatorEnd6;
    private ValueAnimator mHexagonAnimatorEnd7;

    private float mHandleValue2 = 0;
    private ValueAnimator.AnimatorUpdateListener mUpdateListener2;
    private Animator.AnimatorListener mAnimatorListener2;
    private Handler mAnimatorHandle2;

    public static enum State2 {
        Start1, Start2, Start3, Start4, Start5, Start6, Start7,
        End1, End2, End3, End4, End5, End6, End7
    }

    private State2 mCurrentState2 = State2.Start1;

    /**
     * 初始化外轮变化的Handle
     */
    private void initHandle() {
        mAnimatorHandle1 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (mCurrentState) {
                    case Start:
                        mCurrentState = State.FirstCircluation;
                        mStartingAnimator.cancel();
                        mFirstCircluationAnimator.start();
                        break;
                    case FirstCircluation:
                        mCurrentState = State.SecondCircluation;
                        mFirstCircluationAnimator.cancel();
                        mSecondCircluationAnimator.start();
                        break;
                    case SecondCircluation:
                        mCurrentState = State.Start;
                        mSecondCircluationAnimator.cancel();
                        mStartingAnimator.start();
                        break;
                }
            }
        };
    }

    /**
     * 初始化中间真六边形的
     */
    private void initHandle2() {
        mAnimatorHandle2 = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (mCurrentState2) {
                    case Start1:
                        mCurrentState2 = State2.Start2;
                        mHexagonAnimatorStart1.cancel();
                        mHexagonAnimatorStart2.start();
                        break;
                    case Start2:
                        mCurrentState2 = State2.Start3;
                        mHexagonAnimatorStart2.cancel();
                        mHexagonAnimatorStart3.start();
                        break;
                    case Start3:
                        mCurrentState2 = State2.Start4;
                        mHexagonAnimatorStart3.cancel();
                        mHexagonAnimatorStart4.start();
                        break;
                    case Start4:

                        mCurrentState2 = State2.Start5;
                        mHexagonAnimatorStart4.cancel();
                        mHexagonAnimatorStart5.start();
                        break;
                    case Start5:

                        mCurrentState2 = State2.Start6;
                        mHexagonAnimatorStart5.cancel();
                        mHexagonAnimatorStart6.start();
                        break;
                    case Start6:
                        mCurrentState2 = State2.Start7;
                        mHexagonAnimatorStart6.cancel();
                        mHexagonAnimatorStart7.start();
                        break;
                    case Start7:

                        mCurrentState2 = State2.End1;
                        mHexagonAnimatorStart7.cancel();
                        mHexagonAnimatorEnd1.start();
                        break;
                    case End1:
                        mCurrentState2 = State2.End2;
                        mHexagonAnimatorEnd1.cancel();
                        mHexagonAnimatorEnd2.start();
                        break;
                    case End2:
                        mCurrentState2 = State2.End3;
                        mHexagonAnimatorEnd2.cancel();
                        mHexagonAnimatorEnd3.start();
                        break;
                    case End3:
                        mCurrentState2 = State2.End4;
                        mHexagonAnimatorEnd3.cancel();
                        mHexagonAnimatorEnd4.start();
                        break;
                    case End4:
                        mCurrentState2 = State2.End5;
                        mHexagonAnimatorEnd4.cancel();
                        mHexagonAnimatorEnd5.start();
                        break;
                    case End5:
                        mCurrentState2 = State2.End6;
                        mHexagonAnimatorEnd5.cancel();
                        mHexagonAnimatorEnd6.start();
                        break;
                    case End6:
                        mCurrentState2 = State2.End7;
                        mHexagonAnimatorEnd6.cancel();
                        mHexagonAnimatorEnd7.start();
                        break;
                    case End7:
                        mCurrentState2 = State2.Start1;
                        mHexagonAnimatorEnd7.cancel();
                        mHexagonAnimatorStart1.start();
                        break;
                }
            }
        };

    }


    //最外面默认不变圆环的画笔
    private Paint mDefaultCirclePaint;
    private int mDefaultCirclePadding = 10;


    //中间动画圆环的底部默认圆环
    private Paint mAnimatorDeafauleCirclePaint;
    private int AnimatorRadius = 90;

    //中间普通循环的圆圈路径
    private Path normalCirculation;
    private Paint normalCirculationPaint;
    float startAngle = 150;
    float sweepAngle = 30;
    private ValueAnimator mCirclua;
    private int defaultRecycleDuration = 4000;//3s

    //画布
    private Canvas mCanvas;
    //六边形边长的dp值
    int lengthDp = 20;
    //六边形的边长
    float length = (float) dip2px(lengthDp);
    float length1 = length;
    float length2 = length;
    float length3 = length;
    float length4 = length;
    float length5 = length;
    float length6 = length;
    float length7 = length;
    //六边形之间的间距
    float HexagonPadding = (float) dip2px(7);
    //六边形中心点之间的间距
    float distance = length * (float) Math.sqrt(3) + HexagonPadding;

    public HexagonDrawable(Context context) {
        this(context, null);
    }

    public HexagonDrawable(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HexagonDrawable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mHexagonPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHexagonPaint1.setStyle(Paint.Style.FILL);
        mHexagonPaint1.setColor(Color.BLUE);

        mHexagonPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHexagonPaint2.setStyle(Paint.Style.FILL);
        mHexagonPaint2.setColor(Color.BLUE);

        mHexagonPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHexagonPaint3.setStyle(Paint.Style.FILL);
        mHexagonPaint3.setColor(Color.BLUE);

        mHexagonPaint4 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHexagonPaint4.setStyle(Paint.Style.FILL);
        mHexagonPaint4.setColor(Color.BLUE);

        mHexagonPaint5 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHexagonPaint5.setStyle(Paint.Style.FILL);
        mHexagonPaint5.setColor(Color.BLUE);

        mHexagonPaint6 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHexagonPaint6.setStyle(Paint.Style.FILL);
        mHexagonPaint6.setColor(Color.BLUE);

        mHexagonPaint7 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHexagonPaint7.setStyle(Paint.Style.FILL);
        mHexagonPaint7.setColor(Color.BLUE);

        mHexagonPath = new Path();

        mDefaultCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDefaultCirclePaint.setStyle(Paint.Style.STROKE);
        mDefaultCirclePaint.setStrokeWidth(dip2px(10));
        mDefaultCirclePaint.setColor(getContext().getResources().getColor(R.color.RoundColor));
        mDefaultCirclePaint.setAlpha(100);

        mAnimatorDeafauleCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAnimatorDeafauleCirclePaint.setStyle(Paint.Style.STROKE);
        mAnimatorDeafauleCirclePaint.setStrokeWidth(dip2px(8));
        mAnimatorDeafauleCirclePaint.setColor(getContext().getResources().getColor(R.color.RoundColor));

        normalCirculationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        normalCirculationPaint.setStyle(Paint.Style.STROKE);
        normalCirculationPaint.setStrokeWidth(dip2px(8));
        normalCirculationPaint.setColor(Color.BLUE);

        path_circle_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path_circle_Paint.setStyle(Paint.Style.STROKE);
        path_circle_Paint.setStrokeWidth(dip2px(8));
        path_circle_Paint.setColor(getContext().getResources().getColor(R.color.CircleInnnerColor));

        initListener();
        initListener2();
        initHandle();
        initHandle2();
        initAnimator();
        initAnimator1();
        initAnimator2();
        mCirclua.start();
        mStartingAnimator.start();
        mHexagonAnimatorStart1.start();
    }

    //中间变换的路径
    private Path path_circle;
    private Paint path_circle_Paint;
    private PathMeasure pathMeasure;

    private ValueAnimator mStartingAnimator;
    private ValueAnimator mFirstCircluationAnimator;
    private ValueAnimator mSecondCircluationAnimator;
    private float mHandleValue1 = 0;
    private ValueAnimator.AnimatorUpdateListener mUpdateListener1;
    private Animator.AnimatorListener mAnimatorListener1;
    private Handler mAnimatorHandle1;

    public static enum State {
        Start,//90 4000/4 = 1000ms
        FirstCircluation,//60 4000/6 =
        SecondCircluation,//210 1750ms
    }

    private State mCurrentState = State.Start;

    private void initListener() {
        mUpdateListener1 = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mHandleValue1 = value;
                invalidate();
            }
        };
        mAnimatorListener1 = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // getHandle发消息通知动画状态更新
                mAnimatorHandle1.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        };
    }

    private void initListener2() {
        mUpdateListener2 = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mHandleValue2 = (float) animation.getAnimatedValue();
                invalidate();
            }
        };
        mAnimatorListener2 = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if (mCurrentState2 == State2.End7) {
                    mAnimatorHandle2.sendEmptyMessageDelayed(0, 500);
                } else
                    mAnimatorHandle2.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };

    }

    /**
     * 初始化控制中心六边形动画的ValueAnimator
     */
    private void initAnimator2() {
        mHexagonAnimatorStart1 = ValueAnimator.ofFloat(1, 0).setDuration(300);
        mHexagonAnimatorStart2 = ValueAnimator.ofFloat(1, 0).setDuration(300);
        mHexagonAnimatorStart3 = ValueAnimator.ofFloat(1, 0).setDuration(300);
        mHexagonAnimatorStart4 = ValueAnimator.ofFloat(1, 0).setDuration(300);
        mHexagonAnimatorStart5 = ValueAnimator.ofFloat(1, 0).setDuration(300);
        mHexagonAnimatorStart6 = ValueAnimator.ofFloat(1, 0).setDuration(300);
        mHexagonAnimatorStart7 = ValueAnimator.ofFloat(1, 0).setDuration(300);

        mHexagonAnimatorEnd1 = ValueAnimator.ofFloat(0, 1).setDuration(300);
        mHexagonAnimatorEnd2 = ValueAnimator.ofFloat(0, 1).setDuration(300);
        mHexagonAnimatorEnd3 = ValueAnimator.ofFloat(0, 1).setDuration(300);
        mHexagonAnimatorEnd4 = ValueAnimator.ofFloat(0, 1).setDuration(300);
        mHexagonAnimatorEnd5 = ValueAnimator.ofFloat(0, 1).setDuration(300);
        mHexagonAnimatorEnd6 = ValueAnimator.ofFloat(0, 1).setDuration(300);
        mHexagonAnimatorEnd7 = ValueAnimator.ofFloat(0, 1).setDuration(300);

        mHexagonAnimatorStart1.addUpdateListener(mUpdateListener2);
        mHexagonAnimatorStart2.addUpdateListener(mUpdateListener2);
        mHexagonAnimatorStart3.addUpdateListener(mUpdateListener2);
        mHexagonAnimatorStart4.addUpdateListener(mUpdateListener2);
        mHexagonAnimatorStart5.addUpdateListener(mUpdateListener2);
        mHexagonAnimatorStart6.addUpdateListener(mUpdateListener2);
        mHexagonAnimatorStart7.addUpdateListener(mUpdateListener2);
        mHexagonAnimatorEnd1.addUpdateListener(mUpdateListener2);
        mHexagonAnimatorEnd2.addUpdateListener(mUpdateListener2);
        mHexagonAnimatorEnd3.addUpdateListener(mUpdateListener2);
        mHexagonAnimatorEnd4.addUpdateListener(mUpdateListener2);
        mHexagonAnimatorEnd5.addUpdateListener(mUpdateListener2);
        mHexagonAnimatorEnd6.addUpdateListener(mUpdateListener2);
        mHexagonAnimatorEnd7.addUpdateListener(mUpdateListener2);

        mHexagonAnimatorStart1.addListener(mAnimatorListener2);
        mHexagonAnimatorStart2.addListener(mAnimatorListener2);
        mHexagonAnimatorStart3.addListener(mAnimatorListener2);
        mHexagonAnimatorStart4.addListener(mAnimatorListener2);
        mHexagonAnimatorStart5.addListener(mAnimatorListener2);
        mHexagonAnimatorStart6.addListener(mAnimatorListener2);
        mHexagonAnimatorStart7.addListener(mAnimatorListener2);
        mHexagonAnimatorEnd1.addListener(mAnimatorListener2);
        mHexagonAnimatorEnd2.addListener(mAnimatorListener2);
        mHexagonAnimatorEnd3.addListener(mAnimatorListener2);
        mHexagonAnimatorEnd4.addListener(mAnimatorListener2);
        mHexagonAnimatorEnd5.addListener(mAnimatorListener2);
        mHexagonAnimatorEnd6.addListener(mAnimatorListener2);
        mHexagonAnimatorEnd7.addListener(mAnimatorListener2);

    }

    private void initAnimator() {
        mCirclua = ValueAnimator.ofFloat(0f, 360f);
        mCirclua.setDuration(defaultRecycleDuration).setRepeatCount(-1);
        mCirclua.setInterpolator(new LinearInterpolator());
        mCirclua.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                startAngle = value;
                invalidate();
            }
        });
    }

    private void initAnimator1() {
        mStartingAnimator = ValueAnimator.ofFloat(1, 0).setDuration(1000);
        mFirstCircluationAnimator = ValueAnimator.ofFloat(0, 1).setDuration(667);
        mSecondCircluationAnimator = ValueAnimator.ofFloat(0, 1).setDuration(2333);

        mStartingAnimator.addUpdateListener(mUpdateListener1);
        mFirstCircluationAnimator.addUpdateListener(mUpdateListener1);
        mSecondCircluationAnimator.addUpdateListener(mUpdateListener1);

        mStartingAnimator.addListener(mAnimatorListener1);
        mFirstCircluationAnimator.addListener(mAnimatorListener1);
        mSecondCircluationAnimator.addListener(mAnimatorListener1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureDimension(widthMeasureSpec), measureDimension(heightMeasureSpec));

    }

    private int measureDimension(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 1000;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCanvas = canvas;
        drawFirstHexagon();
        drawSecondHexagon();
        drawThirdHexagon();
        draw4thHexagon();
        draw5thHexagon();
        draw6thHexagon();
        draw7thHexagon();
    /*    drawDefaultCirlce();*/
        drawAnimatorCircle();
        drawAnimator1();
        drawAnimator2();
    }

    private void drawFirstHexagon() {
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;

        drawHexagon(mHexagonPath, centerX, centerY, length1);

        mCanvas.drawPath(mHexagonPath, mHexagonPaint1);
    }

    private void drawSecondHexagon() {
        float centerX = getWidth() / 2 - distance / 2;
        float centerY = getHeight() / 2 - (float) (distance / 2 * Math.sqrt(3));

        drawHexagon(mHexagonPath, centerX, centerY, length2);

        mCanvas.drawPath(mHexagonPath, mHexagonPaint2);
    }

    private void drawThirdHexagon() {
        float centerX = getWidth() / 2 + distance / 2;
        float centerY = getHeight() / 2 - (float) (distance / 2 * Math.sqrt(3));

        drawHexagon(mHexagonPath, centerX, centerY, length3);
        mCanvas.drawPath(mHexagonPath, mHexagonPaint3);
    }

    private void draw4thHexagon() {
        float centerX = getWidth() / 2 + distance;
        float centerY = getHeight() / 2;

        drawHexagon(mHexagonPath, centerX, centerY, length4);
        mCanvas.drawPath(mHexagonPath, mHexagonPaint4);
    }

    private void draw5thHexagon() {
        float centerX = getWidth() / 2 + distance / 2;
        float centerY = getHeight() / 2 + (float) (distance / 2 * Math.sqrt(3));

        drawHexagon(mHexagonPath, centerX, centerY, length5);
        mCanvas.drawPath(mHexagonPath, mHexagonPaint5);
    }

    private void draw6thHexagon() {
        float centerX = getWidth() / 2 - distance / 2;
        float centerY = getHeight() / 2 + (float) (distance / 2 * Math.sqrt(3));

        drawHexagon(mHexagonPath, centerX, centerY, length6);
        mCanvas.drawPath(mHexagonPath, mHexagonPaint6);
    }

    private void draw7thHexagon() {
        float centerX = getWidth() / 2 - distance;
        float centerY = getHeight() / 2;

        drawHexagon(mHexagonPath, centerX, centerY, length7);
        mCanvas.drawPath(mHexagonPath, mHexagonPaint7);
    }

    /**
     * 画最外面默认不变的浅蓝色圆环
     */
    private void drawDefaultCirlce() {
        RectF bigCircle = new RectF(dip2px(mDefaultCirclePadding), dip2px(mDefaultCirclePadding)
                , getWidth() - dip2px(mDefaultCirclePadding), getHeight() - dip2px(mDefaultCirclePadding));
        mCanvas.drawArc(bigCircle, 0, 360, false, mDefaultCirclePaint);
    }

    /**
     * 画中间动画圆环的底部圆环
     */
    private void drawAnimatorCircle() {
        RectF midCircle = new RectF(getWidth() / 2 - dip2px(AnimatorRadius), getHeight() / 2 - dip2px(AnimatorRadius)
                , getWidth() / 2 + dip2px(AnimatorRadius), getHeight() / 2 + dip2px(AnimatorRadius));
        mCanvas.drawArc(midCircle, 0, 360, false, mAnimatorDeafauleCirclePaint);

        normalCirculation = new Path();
        normalCirculation.addArc(midCircle, startAngle, sweepAngle);
        mCanvas.drawPath(normalCirculation, normalCirculationPaint);

    }

    /**
     * 外轮廓速度3次变化动画绘图
     */
    private void drawAnimator1() {
        RectF midCircle1 = new RectF(getWidth() / 2 - dip2px(AnimatorRadius), getHeight() / 2 - dip2px(AnimatorRadius)
                , getWidth() / 2 + dip2px(AnimatorRadius), getHeight() / 2 + dip2px(AnimatorRadius));
        path_circle = new Path();
        path_circle.addArc(midCircle1, startAngle + sweepAngle, 360 - sweepAngle);

        pathMeasure = new PathMeasure();
        switch (mCurrentState) {
            case Start:
                mCanvas.drawPath(path_circle, path_circle_Paint);
                break;
            case FirstCircluation:
                pathMeasure.setPath(path_circle, false);
                Path dst = new Path();
                float start = pathMeasure.getLength() * mHandleValue1;
                pathMeasure.getSegment(start, pathMeasure.getLength(), dst, true);
                mCanvas.drawPath(dst, path_circle_Paint);
                break;
            case SecondCircluation:
                pathMeasure.setPath(path_circle, false);
                Path dst1 = new Path();
                float end = pathMeasure.getLength() * mHandleValue1;
                pathMeasure.getSegment(0, end, dst1, true);
                mCanvas.drawPath(dst1, path_circle_Paint);
                break;
        }
    }

    private void drawAnimator2() {
        switch (mCurrentState2) {
            case Start1:
                mHexagonPaint1.setAlpha((int) (255 * mHandleValue2 * 0.6));
                length1 = length * mHandleValue2;
                break;
            case Start2:
                mHexagonPaint2.setAlpha((int) (255 * mHandleValue2 * 0.6));
                length2 = length * mHandleValue2;
                break;
            case Start3:
                mHexagonPaint3.setAlpha((int) (255 * mHandleValue2 * 0.6));
                length3 = length * mHandleValue2;
                break;
            case Start4:
                mHexagonPaint4.setAlpha((int) (255 * mHandleValue2 * 0.6));
                length4 = length * mHandleValue2;
                break;
            case Start5:
                mHexagonPaint5.setAlpha((int) (255 * mHandleValue2 * 0.6));
                length5 = length * mHandleValue2;
                break;
            case Start6:
                mHexagonPaint6.setAlpha((int) (255 * mHandleValue2 * 0.6));
                length6 = length * mHandleValue2;
                break;
            case Start7:
                mHexagonPaint7.setAlpha((int) (255 * mHandleValue2 * 0.6));
                length7 = length * mHandleValue2;
                break;
            case End1:
                mHexagonPaint1.setAlpha((int) (255 * mHandleValue2));
                length1 = length * mHandleValue2;
                break;
            case End2:
                mHexagonPaint2.setAlpha((int) (255 * mHandleValue2));
                length2 = length * mHandleValue2;
                break;
            case End3:
                mHexagonPaint3.setAlpha((int) (255 * mHandleValue2));
                length3 = length * mHandleValue2;
                break;
            case End4:
                mHexagonPaint4.setAlpha((int) (255 * mHandleValue2));
                length4 = length * mHandleValue2;
                break;
            case End5:
                mHexagonPaint5.setAlpha((int) (255 * mHandleValue2));
                length5 = length * mHandleValue2;
                break;
            case End6:
                mHexagonPaint6.setAlpha((int) (255 * mHandleValue2));
                length6 = length * mHandleValue2;
                break;
            case End7:
                mHexagonPaint7.setAlpha((int) (255 * mHandleValue2));
                length7 = length * mHandleValue2;
                break;
        }
    }

    /**
     * 正六变形棱角圆滑过渡
     */
    private void drawHexagon(Path path, float centerX, float centerY, float length) {
        float ArcRadius = length / 10;
        RectF rectF1 = new RectF(centerX - ArcRadius, (float) (centerY - (length - 2 * ArcRadius / Math.sqrt(3) + ArcRadius))
                , centerX + ArcRadius, (float) (centerY - (length - 2 * ArcRadius / Math.sqrt(3) - ArcRadius)));

        RectF rectF2 = new RectF((float) (centerX - Math.sqrt(3) * length / 2), (float) (centerY - (length / 2 + ArcRadius / (2 * Math.sqrt(3))))
                , (float) (centerX - Math.sqrt(3) * length / 2 + 2 * ArcRadius), (float) (centerY - (length / 2 + ArcRadius / (2 * Math.sqrt(3))) + 2 * ArcRadius));

        RectF rectF3 = new RectF((float) (centerX - Math.sqrt(3) * length / 2), (float) (centerY + (length / 2 + ArcRadius / (2 * Math.sqrt(3))) - 2 * ArcRadius)
                , (float) (centerX - Math.sqrt(3) * length / 2 + 2 * ArcRadius), (float) (centerY + (length / 2 + ArcRadius / (2 * Math.sqrt(3)))));

        RectF rectF4 = new RectF(centerX - ArcRadius, (float) (centerY + (length - 2 * ArcRadius / Math.sqrt(3) - ArcRadius))
                , centerX + ArcRadius, (float) (centerY + (length - 2 * ArcRadius / Math.sqrt(3) + ArcRadius)));

        RectF rectF5 = new RectF((float) (centerX + Math.sqrt(3) * length / 2 - 2 * ArcRadius), (float) (centerY + (length / 2 + ArcRadius / (2 * Math.sqrt(3))) - 2 * ArcRadius)
                , (float) (centerX + Math.sqrt(3) * length / 2), (float) (centerY + (length / 2 + ArcRadius / (2 * Math.sqrt(3)))));

        RectF rectF6 = new RectF((float) (centerX + Math.sqrt(3) * length / 2 - 2 * ArcRadius), (float) (centerY - (length / 2 + ArcRadius / (2 * Math.sqrt(3))))
                , (float) (centerX + Math.sqrt(3) * length / 2), (float) (centerY - (length / 2 + ArcRadius / (2 * Math.sqrt(3))) + 2 * ArcRadius));

        path.reset();
        path.arcTo(rectF5, 0, 60);
        path.arcTo(rectF4, 60, 60);
        path.arcTo(rectF3, 120, 60);
        path.arcTo(rectF2, 180, 60);
        path.arcTo(rectF1, 240, 60);
        path.arcTo(rectF6, 300, 60);
        path.close();

        /**
         *  这是正六边形顶角为不圆滑的实现 代码量真的是少了很多。。。
         * path.reset();
         path.moveTo(centerX,centerY - length);
         path.lineTo(centerX-(float)(length*Math.sqrt(3)/2),centerY-length/2);
         path.lineTo(centerX-(float)(length*Math.sqrt(3)/2),centerY+length/2);
         path.lineTo(centerX,centerY + length);
         path.lineTo(centerX +(float)(length*Math.sqrt(3)/2),centerY+length/2);
         path.lineTo(centerX +(float)(length*Math.sqrt(3)/2),centerY-length/2);
         path.close();*/
    }

    //dp转px
    private int dip2px(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }
}
