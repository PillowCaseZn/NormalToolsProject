package com.pillowcase.ui.utils;

import android.os.CountDownTimer;

import com.pillowcase.normal.tools.logger.LoggerUtils;
import com.pillowcase.normal.tools.logger.impl.ILoggerOperation;
import com.pillowcase.ui.interfaces.ICountDownStateListener;

/**
 * Author      : PillowCase
 * Create On   : 2019-12-23 14:41
 * Description :
 */
public class TimeCountUtils implements ILoggerOperation {
    private static TimeCountUtils instance;
    private ICountDownStateListener mListener;
    private TimeCount mTimeCount;
    private boolean isStart = false;
    private LoggerUtils mLoggerUtils;

    public TimeCountUtils() {
        if (mLoggerUtils == null) {
            mLoggerUtils = new LoggerUtils(false, getClass().getSimpleName());
        }
    }

    public synchronized static TimeCountUtils getInstance() {
        if (instance == null) {
            instance = new TimeCountUtils();
        }
        return instance;
    }

    public TimeCountUtils countDown(long second, ICountDownStateListener listener) {
        this.mListener = listener;
        mTimeCount = new TimeCount(second, 1000);
        return instance;
    }

    public void start() {
        log("start", "mTimeCount:" + mTimeCount);
        if (mTimeCount != null) {
            mTimeCount.start();
            isStart = true;
        }
    }

    public void stop() {
        log("stop", "");
        if (mTimeCount != null) {
            mTimeCount.onFinish();
            isStart = false;
        }
    }

    public void cancel() {
        log("cancel", "");
        if (mTimeCount != null) {
            mTimeCount.cancel();
            isStart = false;
        }
    }

    public boolean isStart() {
        return isStart;
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

    private class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
//            log("onTick", "");
            if (mListener != null) {
                mListener.onTick(millisUntilFinished / 1000);
            }
        }

        @Override
        public void onFinish() {
            log("onFinish", "");
            cancel();
            if (mListener != null) {
                mListener.onFinish();
            }
        }
    }
}
