package com.pillowcase.utils.interfaces.assets;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;

/**
 * Author      : PillowCase
 * Create On   : 2020-06-23 01:41
 * Description : assets文件夹内容读取返回接口
 */
public interface IAssetsVideoFileListener {
    /**
     * @param data 文本文件的内容
     */
    void TextFileResult(String data);

    /**
     * @param bitmap 图片对象
     */
    void ImageFileResult(Bitmap bitmap);

    /**
     * @param fileDescriptor 指定文件的AssetFileDescriptor对象
     */
    void VideoFileResult(AssetFileDescriptor fileDescriptor);
}
