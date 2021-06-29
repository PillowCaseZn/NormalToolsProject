package com.pillowcase.plugin.interfaces;

import com.pillowcase.models.AppBean;

import java.util.List;

/**
 * Author      : PillowCase
 * Create On   : 2020-07-29 14:01
 * Description : 检测结果返回
 */
public interface IGamePlugInDetectionCallback {
    /**
     * @param isDetection  是否检测到外挂软件
     * @param dataList 监测到的外挂软件信息
     */
    void onResult(boolean isDetection, List<AppBean> dataList);
}
