package com.pillowcase.utils.interfaces.assets;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;

/**
 * Author      : PillowCase
 * Create On   : 2020-06-23 01:41
 * Description : assets文件夹内容读取返回接口
 */
public interface IAssetImageFilesListener {
    /**
     * @param bitmap 图片对象
     */
    void ImageFileResult(Bitmap bitmap);

}
