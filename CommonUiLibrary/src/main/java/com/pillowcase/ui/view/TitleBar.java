package com.pillowcase.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.pillowcase.normal.tools.logger.LoggerUtils;
import com.pillowcase.normal.tools.logger.impl.ILoggerOperation;
import com.pillowcase.ui.R;
import com.pillowcase.ui.control.Control;
import com.pillowcase.ui.interfaces.ITitleBarInterfaces;
import com.pillowcase.ui.utils.DimensionUtils;

/**
 * Author      : PillowCase
 * Create On   : 2020-04-07 15:26
 * Description :
 */
public class TitleBar extends RelativeLayout implements ILoggerOperation {
    private LoggerUtils mLoggerUtils;
    private ITitleBarInterfaces mInterfaces;
    private String titleName;
    private float leftSize, rightSize;
    private Drawable leftDrawable, rightDrawable;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        try {

            if (mLoggerUtils == null) {
                mLoggerUtils = new LoggerUtils(Control.Logger.SHOW_VIEW_LOGGER, getClass().getSimpleName());
            }
            initAttributeSet(context, attrs);

            setBackgroundResource(R.drawable.layout_normal_bg);

            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            AppCompatTextView titleTv = new AppCompatTextView(context);
            titleTv.setGravity(Gravity.CENTER);
            titleTv.setText(titleName);
            titleTv.setTextColor(Color.parseColor("#333333"));
            titleTv.setTextSize(DimensionUtils.textSize(context, 14));
            addView(titleTv, params);

            params = new LayoutParams((int) leftSize, (int) leftSize);
            params.addRule(RelativeLayout.ALIGN_PARENT_START);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            AppCompatImageView leftImg = new AppCompatImageView(context);
            leftImg.setImageDrawable(leftDrawable);
            leftImg.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            leftImg.setOnClickListener(v -> {
                if (mInterfaces != null) {
                    mInterfaces.onLeftClick();
                }
            });
            addView(leftImg, params);

            params = new LayoutParams((int) rightSize, (int) rightSize);
            params.addRule(RelativeLayout.ALIGN_PARENT_END);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            AppCompatImageView rightImg = new AppCompatImageView(context);
            rightImg.setImageDrawable(rightDrawable);
            rightImg.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            rightImg.setOnClickListener(v -> {
                if (mInterfaces != null) {
                    mInterfaces.onRightClick();
                }
            });
            addView(rightImg, params);
        } catch (Exception e) {
            error(e, "TitleBar");
        }
    }

    private void initAttributeSet(Context context, AttributeSet attrs) {
        try {
            //取出自定义的属性并赋值
            @SuppressLint("CustomViewStyleable") TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleBarView);
            leftDrawable = array.getDrawable(R.styleable.TitleBarView_left_src_resource);
            leftSize = array.getDimension(R.styleable.TitleBarView_left_src_size, 30f);
            titleName = array.getString(R.styleable.TitleBarView_title_name);
            rightDrawable = array.getDrawable(R.styleable.TitleBarView_right_src_resource);
            rightSize = array.getDimension(R.styleable.TitleBarView_right_src_size, 30f);
            array.recycle();
        } catch (Exception e) {
            error(e, "initAttributeSet");
        }
    }

    public void setInterfaces(ITitleBarInterfaces interfaces) {
        this.mInterfaces = interfaces;
    }

    @Override
    public void log(String s, Object o) {
        if (mLoggerUtils != null) {
            mLoggerUtils.log(s, o);
        }
    }

    @Override
    public void warn(String s, String s1) {
        if (mLoggerUtils != null) {
            mLoggerUtils.warn(s, s1);
        }
    }

    @Override
    public void error(Throwable throwable, String s) {
        if (mLoggerUtils != null) {
            mLoggerUtils.error(throwable, s);
        }
    }
}
