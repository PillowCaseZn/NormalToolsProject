package com.pillowcase.emulator.interfaces;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-20 13:16
 * Description ： 模拟器检测监听结果回调接口
 */
public interface IEmulatorCheckListener {
    /**
     * @param isEmulator 是否模拟器
     * @param info       相关信息
     */
    void result(boolean isEmulator, String info);
}
