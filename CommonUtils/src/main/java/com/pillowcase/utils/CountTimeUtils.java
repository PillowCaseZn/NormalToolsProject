package com.pillowcase.utils;

import android.os.CountDownTimer;

import com.pillowcase.logger.impl.ILoggerOperation;
import com.pillowcase.utils.interfaces.ICountDownListener;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-20 11:27
 * Description ： 倒计时
 */
public class CountTimeUtils implements ILoggerOperation {
    private LoggerUtils mLoggerUtils;
    private CountDownTimer mTimer;
    /**
     * 是否已开始倒计时
     */
    private boolean isStart = false;

    /**
     * @param millSecond 倒计时 单位：毫秒
     */
    public CountTimeUtils(Long millSecond, ICountDownListener listener) {
        try {
            log("CountTimeUtils", "millSecond : " + millSecond);

            if (mLoggerUtils == null) {
                mLoggerUtils = new LoggerUtils(false, getClass().getSimpleName());
            }
            if (mTimer == null) {
                mTimer = new CountDownTimer(millSecond, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if (listener != null) {
                            listener.onTick(millisUntilFinished / 1000);
                        }
                    }

                    @Override
                    public void onFinish() {
                        if (listener != null) {
                            listener.onFinish();
                        }
                    }
                };
            }
        } catch (Exception e) {
            error(e, "CountTimeUtils");
        }
    }

    /**
     * 开始倒计时
     */
    public void start() {
        try {
            log("start", "");
            if (mTimer != null && !isStart()) {
                isStart = true;
                mTimer.start();
            }
        } catch (Exception e) {
            error(e, "start");
        }
    }

    /**
     * 取消倒计时
     */
    public void cancel() {
        try {
            log("cancel", "");
            if (mTimer != null && isStart()) {
                isStart = false;
                mTimer.cancel();
            }
        } catch (Exception e) {
            error(e, "cancel");
        }
    }

    /**
     * @return 是否已开始倒计时
     */
    public boolean isStart() {
        return isStart;
    }

    @Override
    public void log(String method, Object object) {
        if (mLoggerUtils != null) {
            mLoggerUtils.log(method, object);
        }
    }

    @Override
    public void warn(String method, String message) {
        if (mLoggerUtils != null) {
            mLoggerUtils.warn(method, message);
        }
    }

    @Override
    public void error(Throwable throwable, String method) {
        if (mLoggerUtils != null) {
            mLoggerUtils.error(throwable, method);
        }
    }
}
