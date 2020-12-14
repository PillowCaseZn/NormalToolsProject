package com.pillowcase.plugin.interfaces.assets;

import android.graphics.Bitmap;

/**
 * Author      : PillowCase
 * Create On   : 2020-06-23 01:41
 * Description : assets Image图片读取
 */
public interface IImageFilesListener {
    /**
     * @param bitmap 图片对象
     */
    void onImageFileResult(Bitmap bitmap);
}
