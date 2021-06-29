package com.pillowcase.utils;

import android.os.CountDownTimer;

import com.pillowcase.logger.LoggerUtils;
import com.pillowcase.utils.interfaces.ICountDownListener;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-20 11:27
 * Description ： 倒计时
 */
public class CountTimeUtils {
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
            if (mLoggerUtils == null) {
                mLoggerUtils = LoggerUtils.getInstance();
            }
            mLoggerUtils.log("CountTimeUtils", "millSecond : " + millSecond);
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
            mLoggerUtils.error("CountTimeUtils", e);
        }
    }

    /**
     * 开始倒计时
     */
    public void start() {
        try {
            mLoggerUtils.log("start", "");
            if (mTimer != null && !isStart()) {
                isStart = true;
                mTimer.start();
            }
        } catch (Exception e) {
            mLoggerUtils.error("start", e);
        }
    }

    /**
     * 取消倒计时
     */
    public void cancel() {
        try {
            mLoggerUtils.log("cancel", "");
            if (mTimer != null && isStart()) {
                isStart = false;
                mTimer.cancel();
            }
        } catch (Exception e) {
            mLoggerUtils.error("cancel", e);
        }
    }

    /**
     * @return 是否已开始倒计时
     */
    public boolean isStart() {
        return isStart;
    }
}
