package com.pillowcase.plugin.interfaces.assets;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;

/**
 * Author      : PillowCase
 * Create On   : 2020-06-23 01:41
 * Description : assets Video音视频内容读取
 */
public interface IVideoFileListener {
    /**
     * @param fileDescriptor 指定文件的AssetFileDescriptor对象
     */
    void onVideoFileResult(AssetFileDescriptor fileDescriptor);
}
