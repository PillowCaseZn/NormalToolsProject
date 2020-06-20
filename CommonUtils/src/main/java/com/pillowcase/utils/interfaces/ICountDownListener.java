package com.pillowcase.utils.interfaces;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-20 11:38
 * Description ： 倒计时监听接口
 */
public interface ICountDownListener {
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
