package com.pillowcase.ui.dialog;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.pillowcase.normal.tools.logger.LoggerUtils;
import com.pillowcase.normal.tools.logger.impl.ILoggerOperation;
import com.pillowcase.ui.R;
import com.pillowcase.ui.interfaces.ICountDownStateListener;
import com.pillowcase.ui.utils.DimensionUtils;
import com.pillowcase.ui.utils.TimeCountUtils;

/**
 * Author      : PillowCase
 * Create On   : 2019-12-23 13:57
 * Description : Toast 消息弹窗
 */
public class ToastDialog extends Dialog implements ILoggerOperation {
    private TextView contentTv;
    private TimeCountUtils mCountUtils;
    private LoggerUtils mLoggerUtils;

    public ToastDialog(@NonNull Context context) {
        this(context, R.style.DialogTheme);
    }

    public ToastDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);

        if (mLoggerUtils == null) {
            mLoggerUtils = new LoggerUtils(false, getClass().getSimpleName());
        }

        Window window = getWindow();
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        if (window != null) {
            window.setAttributes(params);
        }
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DimensionUtils.dp2px(context, 50));
        layoutParams.bottomMargin = DimensionUtils.dp2px(context, 50);

        LinearLayout layout = new LinearLayout(context);
        layout.setBackgroundResource(R.drawable.layout_toast_bg);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(
                DimensionUtils.dp2px(context, 10),
                DimensionUtils.dp2px(context, 10),
                DimensionUtils.dp2px(context, 10),
                DimensionUtils.dp2px(context, 10)
        );
        addContentView(layout, layoutParams);

        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentTv = new TextView(context);
        contentTv.setTextColor(Color.WHITE);
        contentTv.setGravity(Gravity.CENTER);
        contentTv.setTextSize(DimensionUtils.textSize(context, 12));
        layout.addView(contentTv, layoutParams);

        initTimeCount();
    }

    public void show(String content) {
        try {
            if (contentTv != null) {
                contentTv.setText(content);
                show();

                if (mCountUtils.isStart()) {
                    initTimeCount();
                }
                mCountUtils.start();
            }
        } catch (Exception e) {
            error(e, "show");
        }
    }

    private void initTimeCount() {
        try {
            mCountUtils = new TimeCountUtils();
            mCountUtils.countDown(2 * 1000, new ICountDownStateListener() {
                @Override
                public void onFinish() {
                    dismiss();
                }

                @Override
                public void onTick(long second) {

                }
            });
        } catch (Exception e) {
            error(e, "initTimeCount");
        }
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
