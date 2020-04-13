package com.pillowcase.ui.interfaces;

/**
 * Author      : PillowCase
 * Create On   : 2019-12-23 14:42
 * Description : 倒计时事件
 */
public interface ICountDownStateListener {
    /**
     * 倒计时完成
     */
    void onFinish();

    /**
     * 倒计时进行中
     *
     * @param second 秒
     */
    void onTick(long second);
}
