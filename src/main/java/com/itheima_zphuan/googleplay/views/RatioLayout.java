package com.itheima_zphuan.googleplay.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.itheima_zphuan.googleplay.R;
import com.itheima_zphuan.googleplay.utils.LogUtils;
import com.itheima_zphuan.googleplay.utils.UIUtils;


/**
 * 公式:
 *      图片的宽高比=当前控件的宽高比
 * 作用：
 *      在已知宽度的情况下,动态计算高度；
 *      在已知高度的情况下,动态计算宽度
 */
public class RatioLayout extends FrameLayout {

    public static final String TAG = RatioLayout.class.getSimpleName();

    public static final int RELATIVE_WIDTH = 0;//已知宽度,动态计算高度
    public static final int RELATIVE_HEIGHT = 1;//已知高度,动态计算宽度
    private float mPicRatio; // 宽高比例
    private int mRelative = RELATIVE_WIDTH; // 相对谁来计算值

    public RatioLayout(Context context) {
        this(context, null);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //取出自定义的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);

        mPicRatio = typedArray.getFloat(R.styleable.RatioLayout_picRatio, 1);
        mRelative = typedArray.getInt(R.styleable.RatioLayout_relative, RELATIVE_WIDTH);

        typedArray.recycle();
    }


    /**
        * MeasureSpec.EXACTLY 确定的 match_parent fill_parent 100dp 100px
        * MeasureSpec.UNSPECIFIED 不确定的
        * MeasureSpec.AT_MOST 至多wrap_content
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //自己测量
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY && mRelative == RELATIVE_WIDTH) {
            //得到当前控件的宽度
            int selfWidth = MeasureSpec.getSize(widthMeasureSpec);

            //根据公式计算,当前控件应有的高度
            //图片的宽高比=当前控件的宽/当前控件的高
            int selfHeight = (int) (selfWidth / mPicRatio + .5f);

            //保存测量结果
            setMeasuredDimension(selfWidth, selfHeight);

            int childWidth = selfWidth - getPaddingLeft() - getPaddingRight();
            int childHeight = selfHeight - getPaddingTop() - getPaddingBottom();

            //让孩子测量
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

            LogUtils.i(TAG, "selfHeight-->" + UIUtils.px2Dip(selfHeight) + "dp");
        } else if (heightMode == MeasureSpec.EXACTLY && mRelative == RELATIVE_HEIGHT) {
            //得到控件的高度
            int selfHeight = MeasureSpec.getSize(heightMeasureSpec);

            //根据公式动态计算宽度
            // 图片的宽高比=当前控件的宽/当前控件的高
            int selfWidth = (int) (mPicRatio * selfHeight + .5f);

            //保存测量结果
            setMeasuredDimension(selfWidth, selfHeight);

            int childWidth = selfWidth - getPaddingLeft() - getPaddingRight();
            int childHeight = selfHeight - getPaddingTop() - getPaddingBottom();

            //让孩子测量
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

            LogUtils.i(TAG, "selfWidth-->" + UIUtils.px2Dip(selfWidth) + "dp");
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * 设置图片的宽高比
     */
    public void setPicRatio(float picRatio) {
        mPicRatio = picRatio;
    }

    /**
     * 设置相对于宽度还是高度计算
     */
    public void setRelative(int relative) {
        mRelative = relative;
    }
}
