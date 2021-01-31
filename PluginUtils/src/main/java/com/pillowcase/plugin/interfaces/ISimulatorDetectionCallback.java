package com.pillowcase.plugin.interfaces;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-12-14 15:09
 * Description ： 检测结果返回
 */
public interface ISimulatorDetectionCallback {
    /**
     * @param isSimulator 是否模拟器
     */
    void onResult(boolean isSimulator);
}
