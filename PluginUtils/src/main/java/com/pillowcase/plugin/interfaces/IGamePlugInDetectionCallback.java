package com.pillowcase.plugin.interfaces;

import com.pillowcase.plugin.modules.App;

import java.util.List;

/**
 * Author      : PillowCase
 * Create On   : 2020-07-29 14:01
 * Description : 检测结果返回
 */
public interface IGamePlugInDetectionCallback {
    /**
     * @param isFound  是否检测到外挂软件
     * @param dataList 监测到的外挂软件信息
     */
    void onResult(boolean isFound, List<App> dataList);

    /**
     * 检测到包体被反编译修改，包体损坏。
     */
    void onDamage();
}
